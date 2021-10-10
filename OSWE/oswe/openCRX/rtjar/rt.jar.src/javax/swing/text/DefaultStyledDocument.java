/*      */ package javax.swing.text;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.awt.font.TextAttribute;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.ref.ReferenceQueue;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Stack;
/*      */ import java.util.Vector;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ChangeListener;
/*      */ import javax.swing.event.DocumentEvent;
/*      */ import javax.swing.event.DocumentListener;
/*      */ import javax.swing.event.UndoableEditEvent;
/*      */ import javax.swing.undo.AbstractUndoableEdit;
/*      */ import javax.swing.undo.CannotRedoException;
/*      */ import javax.swing.undo.CannotUndoException;
/*      */ import javax.swing.undo.UndoableEdit;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DefaultStyledDocument
/*      */   extends AbstractDocument
/*      */   implements StyledDocument
/*      */ {
/*      */   public static final int BUFFER_SIZE_DEFAULT = 4096;
/*      */   protected ElementBuffer buffer;
/*      */   private transient Vector<Style> listeningStyles;
/*      */   private transient ChangeListener styleChangeListener;
/*      */   private transient ChangeListener styleContextChangeListener;
/*      */   private transient ChangeUpdateRunnable updateRunnable;
/*      */   
/*      */   public DefaultStyledDocument(AbstractDocument.Content paramContent, StyleContext paramStyleContext) {
/*   82 */     super(paramContent, paramStyleContext);
/*   83 */     this.listeningStyles = new Vector<>();
/*   84 */     this.buffer = new ElementBuffer(createDefaultRoot());
/*   85 */     Style style = paramStyleContext.getStyle("default");
/*   86 */     setLogicalStyle(0, style);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DefaultStyledDocument(StyleContext paramStyleContext) {
/*   96 */     this(new GapContent(4096), paramStyleContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DefaultStyledDocument() {
/*  106 */     this(new GapContent(4096), new StyleContext());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element getDefaultRootElement() {
/*  116 */     return this.buffer.getRootElement();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void create(ElementSpec[] paramArrayOfElementSpec) {
/*      */     try {
/*  127 */       if (getLength() != 0) {
/*  128 */         remove(0, getLength());
/*      */       }
/*  130 */       writeLock();
/*      */ 
/*      */       
/*  133 */       AbstractDocument.Content content = getContent();
/*  134 */       int i = paramArrayOfElementSpec.length;
/*  135 */       StringBuilder stringBuilder = new StringBuilder();
/*  136 */       for (byte b = 0; b < i; b++) {
/*  137 */         ElementSpec elementSpec = paramArrayOfElementSpec[b];
/*  138 */         if (elementSpec.getLength() > 0) {
/*  139 */           stringBuilder.append(elementSpec.getArray(), elementSpec.getOffset(), elementSpec.getLength());
/*      */         }
/*      */       } 
/*  142 */       UndoableEdit undoableEdit = content.insertString(0, stringBuilder.toString());
/*      */ 
/*      */       
/*  145 */       int j = stringBuilder.length();
/*  146 */       AbstractDocument.DefaultDocumentEvent defaultDocumentEvent = new AbstractDocument.DefaultDocumentEvent(this, 0, j, DocumentEvent.EventType.INSERT);
/*      */       
/*  148 */       defaultDocumentEvent.addEdit(undoableEdit);
/*  149 */       this.buffer.create(j, paramArrayOfElementSpec, defaultDocumentEvent);
/*      */ 
/*      */       
/*  152 */       super.insertUpdate(defaultDocumentEvent, null);
/*      */ 
/*      */       
/*  155 */       defaultDocumentEvent.end();
/*  156 */       fireInsertUpdate(defaultDocumentEvent);
/*  157 */       fireUndoableEditUpdate(new UndoableEditEvent(this, defaultDocumentEvent));
/*  158 */     } catch (BadLocationException badLocationException) {
/*  159 */       throw new StateInvariantError("problem initializing");
/*      */     } finally {
/*  161 */       writeUnlock();
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
/*      */   protected void insert(int paramInt, ElementSpec[] paramArrayOfElementSpec) throws BadLocationException {
/*  184 */     if (paramArrayOfElementSpec == null || paramArrayOfElementSpec.length == 0) {
/*      */       return;
/*      */     }
/*      */     
/*      */     try {
/*  189 */       writeLock();
/*      */ 
/*      */       
/*  192 */       AbstractDocument.Content content = getContent();
/*  193 */       int i = paramArrayOfElementSpec.length;
/*  194 */       StringBuilder stringBuilder = new StringBuilder();
/*  195 */       for (byte b = 0; b < i; b++) {
/*  196 */         ElementSpec elementSpec = paramArrayOfElementSpec[b];
/*  197 */         if (elementSpec.getLength() > 0) {
/*  198 */           stringBuilder.append(elementSpec.getArray(), elementSpec.getOffset(), elementSpec.getLength());
/*      */         }
/*      */       } 
/*  201 */       if (stringBuilder.length() == 0) {
/*      */         return;
/*      */       }
/*      */       
/*  205 */       UndoableEdit undoableEdit = content.insertString(paramInt, stringBuilder.toString());
/*      */ 
/*      */       
/*  208 */       int j = stringBuilder.length();
/*  209 */       AbstractDocument.DefaultDocumentEvent defaultDocumentEvent = new AbstractDocument.DefaultDocumentEvent(this, paramInt, j, DocumentEvent.EventType.INSERT);
/*      */       
/*  211 */       defaultDocumentEvent.addEdit(undoableEdit);
/*  212 */       this.buffer.insert(paramInt, j, paramArrayOfElementSpec, defaultDocumentEvent);
/*      */ 
/*      */       
/*  215 */       super.insertUpdate(defaultDocumentEvent, null);
/*      */ 
/*      */       
/*  218 */       defaultDocumentEvent.end();
/*  219 */       fireInsertUpdate(defaultDocumentEvent);
/*  220 */       fireUndoableEditUpdate(new UndoableEditEvent(this, defaultDocumentEvent));
/*      */     } finally {
/*  222 */       writeUnlock();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeElement(Element paramElement) {
/*      */     try {
/*  265 */       writeLock();
/*  266 */       removeElementImpl(paramElement);
/*      */     } finally {
/*  268 */       writeUnlock();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void removeElementImpl(Element paramElement) {
/*  273 */     if (paramElement.getDocument() != this) {
/*  274 */       throw new IllegalArgumentException("element doesn't belong to document");
/*      */     }
/*  276 */     AbstractDocument.BranchElement branchElement = (AbstractDocument.BranchElement)paramElement.getParentElement();
/*  277 */     if (branchElement == null) {
/*  278 */       throw new IllegalArgumentException("can't remove the root element");
/*      */     }
/*      */     
/*  281 */     int i = paramElement.getStartOffset();
/*  282 */     int j = i;
/*  283 */     int k = paramElement.getEndOffset();
/*  284 */     int m = k;
/*  285 */     int n = getLength() + 1;
/*  286 */     AbstractDocument.Content content = getContent();
/*  287 */     boolean bool = false;
/*  288 */     boolean bool1 = Utilities.isComposedTextElement(paramElement);
/*      */     
/*  290 */     if (k >= n) {
/*      */       
/*  292 */       if (i <= 0) {
/*  293 */         throw new IllegalArgumentException("can't remove the whole content");
/*      */       }
/*  295 */       m = n - 1;
/*      */       try {
/*  297 */         if (content.getString(i - 1, 1).charAt(0) == '\n') {
/*  298 */           j--;
/*      */         }
/*  300 */       } catch (BadLocationException badLocationException) {
/*  301 */         throw new IllegalStateException(badLocationException);
/*      */       } 
/*  303 */       bool = true;
/*      */     } 
/*  305 */     int i1 = m - j;
/*      */     
/*  307 */     AbstractDocument.DefaultDocumentEvent defaultDocumentEvent = new AbstractDocument.DefaultDocumentEvent(this, j, i1, DocumentEvent.EventType.REMOVE);
/*      */     
/*  309 */     UndoableEdit undoableEdit = null;
/*      */     
/*  311 */     while (branchElement.getElementCount() == 1) {
/*  312 */       paramElement = branchElement;
/*  313 */       branchElement = (AbstractDocument.BranchElement)branchElement.getParentElement();
/*  314 */       if (branchElement == null) {
/*  315 */         throw new IllegalStateException("invalid element structure");
/*      */       }
/*      */     } 
/*  318 */     Element[] arrayOfElement1 = { paramElement };
/*  319 */     Element[] arrayOfElement2 = new Element[0];
/*  320 */     int i2 = branchElement.getElementIndex(i);
/*  321 */     branchElement.replace(i2, 1, arrayOfElement2);
/*  322 */     defaultDocumentEvent.addEdit(new AbstractDocument.ElementEdit(branchElement, i2, arrayOfElement1, arrayOfElement2));
/*  323 */     if (i1 > 0) {
/*      */       try {
/*  325 */         undoableEdit = content.remove(j, i1);
/*  326 */         if (undoableEdit != null) {
/*  327 */           defaultDocumentEvent.addEdit(undoableEdit);
/*      */         }
/*  329 */       } catch (BadLocationException badLocationException) {
/*      */         
/*  331 */         throw new IllegalStateException(badLocationException);
/*      */       } 
/*  333 */       n -= i1;
/*      */     } 
/*      */     
/*  336 */     if (bool) {
/*      */       
/*  338 */       Element element1 = branchElement.getElement(branchElement.getElementCount() - 1);
/*  339 */       while (element1 != null && !element1.isLeaf()) {
/*  340 */         element1 = element1.getElement(element1.getElementCount() - 1);
/*      */       }
/*  342 */       if (element1 == null) {
/*  343 */         throw new IllegalStateException("invalid element structure");
/*      */       }
/*  345 */       int i3 = element1.getStartOffset();
/*  346 */       AbstractDocument.BranchElement branchElement1 = (AbstractDocument.BranchElement)element1.getParentElement();
/*  347 */       int i4 = branchElement1.getElementIndex(i3);
/*      */       
/*  349 */       Element element2 = createLeafElement(branchElement1, element1.getAttributes(), i3, n);
/*      */       
/*  351 */       Element[] arrayOfElement3 = { element1 };
/*  352 */       Element[] arrayOfElement4 = { element2 };
/*  353 */       branchElement1.replace(i4, 1, arrayOfElement4);
/*  354 */       defaultDocumentEvent.addEdit(new AbstractDocument.ElementEdit(branchElement1, i4, arrayOfElement3, arrayOfElement4));
/*      */     } 
/*      */ 
/*      */     
/*  358 */     postRemoveUpdate(defaultDocumentEvent);
/*  359 */     defaultDocumentEvent.end();
/*  360 */     fireRemoveUpdate(defaultDocumentEvent);
/*  361 */     if (!bool1 || undoableEdit == null)
/*      */     {
/*  363 */       fireUndoableEditUpdate(new UndoableEditEvent(this, defaultDocumentEvent));
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
/*      */   public Style addStyle(String paramString, Style paramStyle) {
/*  384 */     StyleContext styleContext = (StyleContext)getAttributeContext();
/*  385 */     return styleContext.addStyle(paramString, paramStyle);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeStyle(String paramString) {
/*  394 */     StyleContext styleContext = (StyleContext)getAttributeContext();
/*  395 */     styleContext.removeStyle(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Style getStyle(String paramString) {
/*  405 */     StyleContext styleContext = (StyleContext)getAttributeContext();
/*  406 */     return styleContext.getStyle(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Enumeration<?> getStyleNames() {
/*  416 */     return ((StyleContext)getAttributeContext()).getStyleNames();
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
/*      */   public void setLogicalStyle(int paramInt, Style paramStyle) {
/*  436 */     Element element = getParagraphElement(paramInt);
/*  437 */     if (element != null && element instanceof AbstractDocument.AbstractElement) {
/*      */       try {
/*  439 */         writeLock();
/*  440 */         StyleChangeUndoableEdit styleChangeUndoableEdit = new StyleChangeUndoableEdit((AbstractDocument.AbstractElement)element, paramStyle);
/*  441 */         ((AbstractDocument.AbstractElement)element).setResolveParent(paramStyle);
/*  442 */         int i = element.getStartOffset();
/*  443 */         int j = element.getEndOffset();
/*  444 */         AbstractDocument.DefaultDocumentEvent defaultDocumentEvent = new AbstractDocument.DefaultDocumentEvent(this, i, j - i, DocumentEvent.EventType.CHANGE);
/*      */         
/*  446 */         defaultDocumentEvent.addEdit(styleChangeUndoableEdit);
/*  447 */         defaultDocumentEvent.end();
/*  448 */         fireChangedUpdate(defaultDocumentEvent);
/*  449 */         fireUndoableEditUpdate(new UndoableEditEvent(this, defaultDocumentEvent));
/*      */       } finally {
/*  451 */         writeUnlock();
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
/*      */   public Style getLogicalStyle(int paramInt) {
/*  466 */     Style style = null;
/*  467 */     Element element = getParagraphElement(paramInt);
/*  468 */     if (element != null) {
/*  469 */       AttributeSet attributeSet1 = element.getAttributes();
/*  470 */       AttributeSet attributeSet2 = attributeSet1.getResolveParent();
/*  471 */       if (attributeSet2 instanceof Style) {
/*  472 */         style = (Style)attributeSet2;
/*      */       }
/*      */     } 
/*  475 */     return style;
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
/*      */   public void setCharacterAttributes(int paramInt1, int paramInt2, AttributeSet paramAttributeSet, boolean paramBoolean) {
/*  496 */     if (paramInt2 == 0) {
/*      */       return;
/*      */     }
/*      */     try {
/*  500 */       writeLock();
/*  501 */       AbstractDocument.DefaultDocumentEvent defaultDocumentEvent = new AbstractDocument.DefaultDocumentEvent(this, paramInt1, paramInt2, DocumentEvent.EventType.CHANGE);
/*      */ 
/*      */ 
/*      */       
/*  505 */       this.buffer.change(paramInt1, paramInt2, defaultDocumentEvent);
/*      */       
/*  507 */       AttributeSet attributeSet = paramAttributeSet.copyAttributes();
/*      */       
/*      */       int i;
/*      */       
/*  511 */       for (i = paramInt1; i < paramInt1 + paramInt2; i = j) {
/*  512 */         Element element = getCharacterElement(i);
/*  513 */         int j = element.getEndOffset();
/*  514 */         if (i == j) {
/*      */           break;
/*      */         }
/*      */         
/*  518 */         MutableAttributeSet mutableAttributeSet = (MutableAttributeSet)element.getAttributes();
/*  519 */         defaultDocumentEvent.addEdit(new AttributeUndoableEdit(element, attributeSet, paramBoolean));
/*  520 */         if (paramBoolean) {
/*  521 */           mutableAttributeSet.removeAttributes(mutableAttributeSet);
/*      */         }
/*  523 */         mutableAttributeSet.addAttributes(paramAttributeSet);
/*      */       } 
/*  525 */       defaultDocumentEvent.end();
/*  526 */       fireChangedUpdate(defaultDocumentEvent);
/*  527 */       fireUndoableEditUpdate(new UndoableEditEvent(this, defaultDocumentEvent));
/*      */     } finally {
/*  529 */       writeUnlock();
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
/*      */   public void setParagraphAttributes(int paramInt1, int paramInt2, AttributeSet paramAttributeSet, boolean paramBoolean) {
/*      */     try {
/*  550 */       writeLock();
/*  551 */       AbstractDocument.DefaultDocumentEvent defaultDocumentEvent = new AbstractDocument.DefaultDocumentEvent(this, paramInt1, paramInt2, DocumentEvent.EventType.CHANGE);
/*      */ 
/*      */       
/*  554 */       AttributeSet attributeSet = paramAttributeSet.copyAttributes();
/*      */ 
/*      */       
/*  557 */       Element element = getDefaultRootElement();
/*  558 */       int i = element.getElementIndex(paramInt1);
/*  559 */       int j = element.getElementIndex(paramInt1 + ((paramInt2 > 0) ? (paramInt2 - 1) : 0));
/*  560 */       boolean bool = Boolean.TRUE.equals(getProperty("i18n"));
/*  561 */       boolean bool1 = false;
/*  562 */       for (int k = i; k <= j; k++) {
/*  563 */         Element element1 = element.getElement(k);
/*  564 */         MutableAttributeSet mutableAttributeSet = (MutableAttributeSet)element1.getAttributes();
/*  565 */         defaultDocumentEvent.addEdit(new AttributeUndoableEdit(element1, attributeSet, paramBoolean));
/*  566 */         if (paramBoolean) {
/*  567 */           mutableAttributeSet.removeAttributes(mutableAttributeSet);
/*      */         }
/*  569 */         mutableAttributeSet.addAttributes(paramAttributeSet);
/*  570 */         if (bool && !bool1) {
/*  571 */           bool1 = (mutableAttributeSet.getAttribute(TextAttribute.RUN_DIRECTION) != null) ? true : false;
/*      */         }
/*      */       } 
/*      */       
/*  575 */       if (bool1) {
/*  576 */         updateBidi(defaultDocumentEvent);
/*      */       }
/*      */       
/*  579 */       defaultDocumentEvent.end();
/*  580 */       fireChangedUpdate(defaultDocumentEvent);
/*  581 */       fireUndoableEditUpdate(new UndoableEditEvent(this, defaultDocumentEvent));
/*      */     } finally {
/*  583 */       writeUnlock();
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
/*      */   public Element getParagraphElement(int paramInt) {
/*      */     Element element;
/*  597 */     for (element = getDefaultRootElement(); !element.isLeaf(); ) {
/*  598 */       int i = element.getElementIndex(paramInt);
/*  599 */       element = element.getElement(i);
/*      */     } 
/*  601 */     if (element != null)
/*  602 */       return element.getParentElement(); 
/*  603 */     return element;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element getCharacterElement(int paramInt) {
/*      */     Element element;
/*  614 */     for (element = getDefaultRootElement(); !element.isLeaf(); ) {
/*  615 */       int i = element.getElementIndex(paramInt);
/*  616 */       element = element.getElement(i);
/*      */     } 
/*  618 */     return element;
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
/*      */   protected void insertUpdate(AbstractDocument.DefaultDocumentEvent paramDefaultDocumentEvent, AttributeSet paramAttributeSet) {
/*  633 */     int i = paramDefaultDocumentEvent.getOffset();
/*  634 */     int j = paramDefaultDocumentEvent.getLength();
/*  635 */     if (paramAttributeSet == null) {
/*  636 */       paramAttributeSet = SimpleAttributeSet.EMPTY;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  642 */     Element element1 = getParagraphElement(i + j);
/*  643 */     AttributeSet attributeSet1 = element1.getAttributes();
/*      */     
/*  645 */     Element element2 = getParagraphElement(i);
/*  646 */     Element element3 = element2.getElement(element2
/*  647 */         .getElementIndex(i));
/*  648 */     int k = i + j;
/*  649 */     boolean bool = (element3.getEndOffset() == k) ? true : false;
/*  650 */     AttributeSet attributeSet2 = element3.getAttributes();
/*      */     
/*      */     try {
/*  653 */       Segment segment = new Segment();
/*  654 */       Vector<ElementSpec> vector = new Vector();
/*  655 */       ElementSpec elementSpec1 = null;
/*  656 */       boolean bool1 = false;
/*  657 */       short s = 6;
/*      */       
/*  659 */       if (i > 0) {
/*  660 */         getText(i - 1, 1, segment);
/*  661 */         if (segment.array[segment.offset] == '\n') {
/*      */           
/*  663 */           bool1 = true;
/*      */           
/*  665 */           s = createSpecsForInsertAfterNewline(element1, element2, attributeSet1, vector, i, k);
/*      */           
/*  667 */           for (int i3 = vector.size() - 1; i3 >= 0; 
/*  668 */             i3--) {
/*  669 */             ElementSpec elementSpec = vector.elementAt(i3);
/*  670 */             if (elementSpec.getType() == 1) {
/*  671 */               elementSpec1 = elementSpec;
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  679 */       if (!bool1) {
/*  680 */         attributeSet1 = element2.getAttributes();
/*      */       }
/*  682 */       getText(i, j, segment);
/*  683 */       char[] arrayOfChar = segment.array;
/*  684 */       int m = segment.offset + segment.count;
/*  685 */       int n = segment.offset;
/*      */       
/*  687 */       for (int i1 = segment.offset; i1 < m; i1++) {
/*  688 */         if (arrayOfChar[i1] == '\n') {
/*  689 */           int i3 = i1 + 1;
/*  690 */           vector.addElement(new ElementSpec(paramAttributeSet, (short)3, i3 - n));
/*      */ 
/*      */           
/*  693 */           vector.addElement(new ElementSpec(null, (short)2));
/*      */           
/*  695 */           elementSpec1 = new ElementSpec(attributeSet1, (short)1);
/*      */           
/*  697 */           vector.addElement(elementSpec1);
/*  698 */           n = i3;
/*      */         } 
/*      */       } 
/*  701 */       if (n < m) {
/*  702 */         vector.addElement(new ElementSpec(paramAttributeSet, (short)3, m - n));
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  707 */       ElementSpec elementSpec2 = vector.firstElement();
/*      */       
/*  709 */       int i2 = getLength();
/*      */ 
/*      */       
/*  712 */       if (elementSpec2.getType() == 3 && attributeSet2
/*  713 */         .isEqual(paramAttributeSet)) {
/*  714 */         elementSpec2.setDirection((short)4);
/*      */       }
/*      */ 
/*      */       
/*  718 */       if (elementSpec1 != null) {
/*  719 */         if (bool1) {
/*  720 */           elementSpec1.setDirection(s);
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*  725 */         else if (element2.getEndOffset() != k) {
/*  726 */           elementSpec1.setDirection((short)7);
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/*  732 */           Element element = element2.getParentElement();
/*  733 */           int i3 = element.getElementIndex(i);
/*  734 */           if (i3 + 1 < element.getElementCount() && 
/*  735 */             !element.getElement(i3 + 1).isLeaf()) {
/*  736 */             elementSpec1.setDirection((short)5);
/*      */           }
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  747 */       if (bool && k < i2) {
/*  748 */         ElementSpec elementSpec = vector.lastElement();
/*  749 */         if (elementSpec.getType() == 3 && elementSpec
/*  750 */           .getDirection() != 4 && ((elementSpec1 == null && (element1 == element2 || bool1)) || (elementSpec1 != null && elementSpec1
/*      */ 
/*      */           
/*  753 */           .getDirection() != 6)))
/*      */         {
/*  755 */           Element element = element1.getElement(element1
/*  756 */               .getElementIndex(k));
/*      */           
/*  758 */           if (element.isLeaf() && paramAttributeSet
/*  759 */             .isEqual(element.getAttributes())) {
/*  760 */             elementSpec.setDirection((short)5);
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       }
/*  767 */       else if (!bool && elementSpec1 != null && elementSpec1
/*  768 */         .getDirection() == 7) {
/*      */         
/*  770 */         ElementSpec elementSpec = vector.lastElement();
/*  771 */         if (elementSpec.getType() == 3 && elementSpec
/*  772 */           .getDirection() != 4 && paramAttributeSet
/*  773 */           .isEqual(attributeSet2)) {
/*  774 */           elementSpec.setDirection((short)5);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  780 */       if (Utilities.isComposedTextAttributeDefined(paramAttributeSet)) {
/*  781 */         MutableAttributeSet mutableAttributeSet = (MutableAttributeSet)paramAttributeSet;
/*  782 */         mutableAttributeSet.addAttributes(attributeSet2);
/*  783 */         mutableAttributeSet.addAttribute("$ename", "content");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  788 */         mutableAttributeSet.addAttribute(StyleConstants.NameAttribute, "content");
/*      */         
/*  790 */         if (mutableAttributeSet.isDefined("CR")) {
/*  791 */           mutableAttributeSet.removeAttribute("CR");
/*      */         }
/*      */       } 
/*      */       
/*  795 */       ElementSpec[] arrayOfElementSpec = new ElementSpec[vector.size()];
/*  796 */       vector.copyInto((Object[])arrayOfElementSpec);
/*  797 */       this.buffer.insert(i, j, arrayOfElementSpec, paramDefaultDocumentEvent);
/*  798 */     } catch (BadLocationException badLocationException) {}
/*      */ 
/*      */     
/*  801 */     super.insertUpdate(paramDefaultDocumentEvent, paramAttributeSet);
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
/*      */   short createSpecsForInsertAfterNewline(Element paramElement1, Element paramElement2, AttributeSet paramAttributeSet, Vector<ElementSpec> paramVector, int paramInt1, int paramInt2) {
/*  815 */     if (paramElement1.getParentElement() == paramElement2.getParentElement()) {
/*      */ 
/*      */       
/*  818 */       ElementSpec elementSpec = new ElementSpec(paramAttributeSet, (short)2);
/*  819 */       paramVector.addElement(elementSpec);
/*  820 */       elementSpec = new ElementSpec(paramAttributeSet, (short)1);
/*  821 */       paramVector.addElement(elementSpec);
/*  822 */       if (paramElement2.getEndOffset() != paramInt2) {
/*  823 */         return 7;
/*      */       }
/*  825 */       Element element = paramElement2.getParentElement();
/*  826 */       if (element.getElementIndex(paramInt1) + 1 < element.getElementCount()) {
/*  827 */         return 5;
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  832 */       Vector<Element> vector1 = new Vector();
/*  833 */       Vector<Element> vector2 = new Vector();
/*  834 */       Element element = paramElement2;
/*  835 */       while (element != null) {
/*  836 */         vector1.addElement(element);
/*  837 */         element = element.getParentElement();
/*      */       } 
/*  839 */       element = paramElement1;
/*  840 */       int i = -1;
/*  841 */       while (element != null && (i = vector1.indexOf(element)) == -1) {
/*  842 */         vector2.addElement(element);
/*  843 */         element = element.getParentElement();
/*      */       } 
/*  845 */       if (element != null) {
/*      */ 
/*      */         
/*  848 */         for (byte b = 0; b < i; 
/*  849 */           b++) {
/*  850 */           paramVector.addElement(new ElementSpec(null, (short)2));
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  855 */         int j = vector2.size() - 1;
/*  856 */         for (; j >= 0; j--) {
/*  857 */           ElementSpec elementSpec = new ElementSpec(((Element)vector2.elementAt(j)).getAttributes(), (short)1);
/*      */           
/*  859 */           if (j > 0)
/*  860 */             elementSpec.setDirection((short)5); 
/*  861 */           paramVector.addElement(elementSpec);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  866 */         if (vector2.size() > 0) {
/*  867 */           return 5;
/*      */         }
/*      */         
/*  870 */         return 7;
/*      */       } 
/*      */     } 
/*      */     
/*  874 */     return 6;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void removeUpdate(AbstractDocument.DefaultDocumentEvent paramDefaultDocumentEvent) {
/*  883 */     super.removeUpdate(paramDefaultDocumentEvent);
/*  884 */     this.buffer.remove(paramDefaultDocumentEvent.getOffset(), paramDefaultDocumentEvent.getLength(), paramDefaultDocumentEvent);
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
/*      */   protected AbstractDocument.AbstractElement createDefaultRoot() {
/*  898 */     writeLock();
/*  899 */     SectionElement sectionElement = new SectionElement();
/*  900 */     AbstractDocument.BranchElement branchElement = new AbstractDocument.BranchElement(this, sectionElement, null);
/*      */     
/*  902 */     AbstractDocument.LeafElement leafElement = new AbstractDocument.LeafElement(this, branchElement, null, 0, 1);
/*  903 */     Element[] arrayOfElement = new Element[1];
/*  904 */     arrayOfElement[0] = leafElement;
/*  905 */     branchElement.replace(0, 0, arrayOfElement);
/*      */     
/*  907 */     arrayOfElement[0] = branchElement;
/*  908 */     sectionElement.replace(0, 0, arrayOfElement);
/*  909 */     writeUnlock();
/*  910 */     return sectionElement;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Color getForeground(AttributeSet paramAttributeSet) {
/*  920 */     StyleContext styleContext = (StyleContext)getAttributeContext();
/*  921 */     return styleContext.getForeground(paramAttributeSet);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Color getBackground(AttributeSet paramAttributeSet) {
/*  931 */     StyleContext styleContext = (StyleContext)getAttributeContext();
/*  932 */     return styleContext.getBackground(paramAttributeSet);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Font getFont(AttributeSet paramAttributeSet) {
/*  942 */     StyleContext styleContext = (StyleContext)getAttributeContext();
/*  943 */     return styleContext.getFont(paramAttributeSet);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void styleChanged(Style paramStyle) {
/*  954 */     if (getLength() != 0) {
/*      */       
/*  956 */       if (this.updateRunnable == null) {
/*  957 */         this.updateRunnable = new ChangeUpdateRunnable();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  962 */       synchronized (this.updateRunnable) {
/*  963 */         if (!this.updateRunnable.isPending) {
/*  964 */           SwingUtilities.invokeLater(this.updateRunnable);
/*  965 */           this.updateRunnable.isPending = true;
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
/*      */   public void addDocumentListener(DocumentListener paramDocumentListener) {
/*  978 */     synchronized (this.listeningStyles) {
/*      */       
/*  980 */       int i = this.listenerList.getListenerCount(DocumentListener.class);
/*  981 */       super.addDocumentListener(paramDocumentListener);
/*  982 */       if (i == 0) {
/*  983 */         if (this.styleContextChangeListener == null) {
/*  984 */           this
/*  985 */             .styleContextChangeListener = createStyleContextChangeListener();
/*      */         }
/*  987 */         if (this.styleContextChangeListener != null) {
/*  988 */           StyleContext styleContext = (StyleContext)getAttributeContext();
/*      */           
/*  990 */           List<ChangeListener> list = AbstractChangeHandler.getStaleListeners(this.styleContextChangeListener);
/*  991 */           for (ChangeListener changeListener : list) {
/*  992 */             styleContext.removeChangeListener(changeListener);
/*      */           }
/*  994 */           styleContext.addChangeListener(this.styleContextChangeListener);
/*      */         } 
/*  996 */         updateStylesListeningTo();
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
/*      */   public void removeDocumentListener(DocumentListener paramDocumentListener) {
/* 1008 */     synchronized (this.listeningStyles) {
/* 1009 */       super.removeDocumentListener(paramDocumentListener);
/* 1010 */       if (this.listenerList.getListenerCount(DocumentListener.class) == 0) {
/* 1011 */         for (int i = this.listeningStyles.size() - 1; i >= 0; 
/* 1012 */           i--) {
/* 1013 */           ((Style)this.listeningStyles.elementAt(i))
/* 1014 */             .removeChangeListener(this.styleChangeListener);
/*      */         }
/* 1016 */         this.listeningStyles.removeAllElements();
/* 1017 */         if (this.styleContextChangeListener != null) {
/* 1018 */           StyleContext styleContext = (StyleContext)getAttributeContext();
/* 1019 */           styleContext.removeChangeListener(this.styleContextChangeListener);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ChangeListener createStyleChangeListener() {
/* 1029 */     return new StyleChangeHandler(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   ChangeListener createStyleContextChangeListener() {
/* 1036 */     return new StyleContextChangeHandler(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void updateStylesListeningTo() {
/* 1044 */     synchronized (this.listeningStyles) {
/* 1045 */       StyleContext styleContext = (StyleContext)getAttributeContext();
/* 1046 */       if (this.styleChangeListener == null) {
/* 1047 */         this.styleChangeListener = createStyleChangeListener();
/*      */       }
/* 1049 */       if (this.styleChangeListener != null && styleContext != null) {
/* 1050 */         Enumeration<?> enumeration = styleContext.getStyleNames();
/* 1051 */         Vector<Style> vector = (Vector)this.listeningStyles.clone();
/* 1052 */         this.listeningStyles.removeAllElements();
/*      */         
/* 1054 */         List<ChangeListener> list = AbstractChangeHandler.getStaleListeners(this.styleChangeListener);
/* 1055 */         while (enumeration.hasMoreElements()) {
/* 1056 */           String str = (String)enumeration.nextElement();
/* 1057 */           Style style = styleContext.getStyle(str);
/* 1058 */           int j = vector.indexOf(style);
/* 1059 */           this.listeningStyles.addElement(style);
/* 1060 */           if (j == -1) {
/* 1061 */             for (ChangeListener changeListener : list) {
/* 1062 */               style.removeChangeListener(changeListener);
/*      */             }
/* 1064 */             style.addChangeListener(this.styleChangeListener);
/*      */             continue;
/*      */           } 
/* 1067 */           vector.removeElementAt(j);
/*      */         } 
/*      */         
/* 1070 */         for (int i = vector.size() - 1; i >= 0; i--) {
/* 1071 */           Style style = vector.elementAt(i);
/* 1072 */           style.removeChangeListener(this.styleChangeListener);
/*      */         } 
/* 1074 */         if (this.listeningStyles.size() == 0) {
/* 1075 */           this.styleChangeListener = null;
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
/* 1083 */     this.listeningStyles = new Vector<>();
/* 1084 */     paramObjectInputStream.defaultReadObject();
/*      */     
/* 1086 */     if (this.styleContextChangeListener == null && this.listenerList
/* 1087 */       .getListenerCount(DocumentListener.class) > 0) {
/* 1088 */       this.styleContextChangeListener = createStyleContextChangeListener();
/* 1089 */       if (this.styleContextChangeListener != null) {
/* 1090 */         StyleContext styleContext = (StyleContext)getAttributeContext();
/* 1091 */         styleContext.addChangeListener(this.styleContextChangeListener);
/*      */       } 
/* 1093 */       updateStylesListeningTo();
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class SectionElement
/*      */     extends AbstractDocument.BranchElement
/*      */   {
/*      */     public SectionElement() {
/* 1137 */       super(null, null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getName() {
/* 1146 */       return "section";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class ElementSpec
/*      */   {
/*      */     public static final short StartTagType = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final short EndTagType = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final short ContentType = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final short JoinPreviousDirection = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final short JoinNextDirection = 5;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final short OriginateDirection = 6;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static final short JoinFractureDirection = 7;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private AttributeSet attr;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int len;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private short type;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private short direction;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int offs;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private char[] data;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ElementSpec(AttributeSet param1AttributeSet, short param1Short) {
/* 1225 */       this(param1AttributeSet, param1Short, null, 0, 0);
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
/*      */     public ElementSpec(AttributeSet param1AttributeSet, short param1Short, int param1Int) {
/* 1239 */       this(param1AttributeSet, param1Short, null, 0, param1Int);
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
/*      */     public ElementSpec(AttributeSet param1AttributeSet, short param1Short, char[] param1ArrayOfchar, int param1Int1, int param1Int2) {
/* 1255 */       this.attr = param1AttributeSet;
/* 1256 */       this.type = param1Short;
/* 1257 */       this.data = param1ArrayOfchar;
/* 1258 */       this.offs = param1Int1;
/* 1259 */       this.len = param1Int2;
/* 1260 */       this.direction = 6;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setType(short param1Short) {
/* 1270 */       this.type = param1Short;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public short getType() {
/* 1280 */       return this.type;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setDirection(short param1Short) {
/* 1290 */       this.direction = param1Short;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public short getDirection() {
/* 1299 */       return this.direction;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AttributeSet getAttributes() {
/* 1308 */       return this.attr;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public char[] getArray() {
/* 1317 */       return this.data;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getOffset() {
/* 1327 */       return this.offs;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getLength() {
/* 1336 */       return this.len;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1345 */       String str1 = "??";
/* 1346 */       String str2 = "??";
/* 1347 */       switch (this.type) {
/*      */         case 1:
/* 1349 */           str1 = "StartTag";
/*      */           break;
/*      */         case 3:
/* 1352 */           str1 = "Content";
/*      */           break;
/*      */         case 2:
/* 1355 */           str1 = "EndTag";
/*      */           break;
/*      */       } 
/* 1358 */       switch (this.direction) {
/*      */         case 4:
/* 1360 */           str2 = "JoinPrevious";
/*      */           break;
/*      */         case 5:
/* 1363 */           str2 = "JoinNext";
/*      */           break;
/*      */         case 6:
/* 1366 */           str2 = "Originate";
/*      */           break;
/*      */         case 7:
/* 1369 */           str2 = "Fracture";
/*      */           break;
/*      */       } 
/* 1372 */       return str1 + ":" + str2 + ":" + getLength();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public class ElementBuffer
/*      */     implements Serializable
/*      */   {
/*      */     Element root;
/*      */     
/*      */     transient int pos;
/*      */     
/*      */     transient int offset;
/*      */     
/*      */     transient int length;
/*      */     
/*      */     transient int endOffset;
/*      */     
/*      */     transient Vector<ElemChanges> changes;
/*      */     
/*      */     transient Stack<ElemChanges> path;
/*      */     
/*      */     transient boolean insertOp;
/*      */     
/*      */     transient boolean recreateLeafs;
/*      */     
/*      */     transient ElemChanges[] insertPath;
/*      */     transient boolean createdFracture;
/*      */     transient Element fracturedParent;
/*      */     transient Element fracturedChild;
/*      */     transient boolean offsetLastIndex;
/*      */     transient boolean offsetLastIndexOnReplace;
/*      */     
/*      */     public ElementBuffer(Element param1Element) {
/* 1406 */       this.root = param1Element;
/* 1407 */       this.changes = new Vector<>();
/* 1408 */       this.path = new Stack<>();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Element getRootElement() {
/* 1417 */       return this.root;
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
/*      */     public void insert(int param1Int1, int param1Int2, DefaultStyledDocument.ElementSpec[] param1ArrayOfElementSpec, AbstractDocument.DefaultDocumentEvent param1DefaultDocumentEvent) {
/* 1430 */       if (param1Int2 == 0) {
/*      */         return;
/*      */       }
/*      */       
/* 1434 */       this.insertOp = true;
/* 1435 */       beginEdits(param1Int1, param1Int2);
/* 1436 */       insertUpdate(param1ArrayOfElementSpec);
/* 1437 */       endEdits(param1DefaultDocumentEvent);
/*      */       
/* 1439 */       this.insertOp = false;
/*      */     }
/*      */     
/*      */     void create(int param1Int, DefaultStyledDocument.ElementSpec[] param1ArrayOfElementSpec, AbstractDocument.DefaultDocumentEvent param1DefaultDocumentEvent) {
/* 1443 */       this.insertOp = true;
/* 1444 */       beginEdits(this.offset, param1Int);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1452 */       Element element1 = this.root;
/* 1453 */       int i = element1.getElementIndex(0);
/* 1454 */       while (!element1.isLeaf()) {
/* 1455 */         Element element = element1.getElement(i);
/* 1456 */         push(element1, i);
/* 1457 */         element1 = element;
/* 1458 */         i = element1.getElementIndex(0);
/*      */       } 
/* 1460 */       ElemChanges elemChanges = this.path.peek();
/* 1461 */       Element element2 = elemChanges.parent.getElement(elemChanges.index);
/* 1462 */       elemChanges.added.addElement(DefaultStyledDocument.this.createLeafElement(elemChanges.parent, element2
/* 1463 */             .getAttributes(), DefaultStyledDocument.this.getLength(), element2
/* 1464 */             .getEndOffset()));
/* 1465 */       elemChanges.removed.addElement(element2);
/* 1466 */       while (this.path.size() > 1) {
/* 1467 */         pop();
/*      */       }
/*      */       
/* 1470 */       int j = param1ArrayOfElementSpec.length;
/*      */ 
/*      */       
/* 1473 */       AttributeSet attributeSet = null;
/* 1474 */       if (j > 0 && param1ArrayOfElementSpec[0].getType() == 1) {
/* 1475 */         attributeSet = param1ArrayOfElementSpec[0].getAttributes();
/*      */       }
/* 1477 */       if (attributeSet == null) {
/* 1478 */         attributeSet = SimpleAttributeSet.EMPTY;
/*      */       }
/*      */       
/* 1481 */       MutableAttributeSet mutableAttributeSet = (MutableAttributeSet)this.root.getAttributes();
/* 1482 */       param1DefaultDocumentEvent.addEdit(new DefaultStyledDocument.AttributeUndoableEdit(this.root, attributeSet, true));
/* 1483 */       mutableAttributeSet.removeAttributes(mutableAttributeSet);
/* 1484 */       mutableAttributeSet.addAttributes(attributeSet);
/*      */ 
/*      */       
/* 1487 */       for (byte b = 1; b < j; b++) {
/* 1488 */         insertElement(param1ArrayOfElementSpec[b]);
/*      */       }
/*      */ 
/*      */       
/* 1492 */       while (this.path.size() != 0) {
/* 1493 */         pop();
/*      */       }
/*      */       
/* 1496 */       endEdits(param1DefaultDocumentEvent);
/* 1497 */       this.insertOp = false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void remove(int param1Int1, int param1Int2, AbstractDocument.DefaultDocumentEvent param1DefaultDocumentEvent) {
/* 1508 */       beginEdits(param1Int1, param1Int2);
/* 1509 */       removeUpdate();
/* 1510 */       endEdits(param1DefaultDocumentEvent);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void change(int param1Int1, int param1Int2, AbstractDocument.DefaultDocumentEvent param1DefaultDocumentEvent) {
/* 1521 */       beginEdits(param1Int1, param1Int2);
/* 1522 */       changeUpdate();
/* 1523 */       endEdits(param1DefaultDocumentEvent);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void insertUpdate(DefaultStyledDocument.ElementSpec[] param1ArrayOfElementSpec) {
/*      */       byte b;
/* 1533 */       Element element = this.root;
/* 1534 */       int i = element.getElementIndex(this.offset);
/* 1535 */       while (!element.isLeaf()) {
/* 1536 */         Element element1 = element.getElement(i);
/* 1537 */         push(element, element1.isLeaf() ? i : (i + 1));
/* 1538 */         element = element1;
/* 1539 */         i = element.getElementIndex(this.offset);
/*      */       } 
/*      */ 
/*      */       
/* 1543 */       this.insertPath = new ElemChanges[this.path.size()];
/* 1544 */       this.path.copyInto((Object[])this.insertPath);
/*      */ 
/*      */       
/* 1547 */       this.createdFracture = false;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1552 */       this.recreateLeafs = false;
/* 1553 */       if (param1ArrayOfElementSpec[0].getType() == 3) {
/* 1554 */         insertFirstContent(param1ArrayOfElementSpec);
/* 1555 */         this.pos += param1ArrayOfElementSpec[0].getLength();
/* 1556 */         b = 1;
/*      */       } else {
/*      */         
/* 1559 */         fractureDeepestLeaf(param1ArrayOfElementSpec);
/* 1560 */         b = 0;
/*      */       } 
/*      */ 
/*      */       
/* 1564 */       int j = param1ArrayOfElementSpec.length;
/* 1565 */       for (; b < j; b++) {
/* 1566 */         insertElement(param1ArrayOfElementSpec[b]);
/*      */       }
/*      */ 
/*      */       
/* 1570 */       if (!this.createdFracture) {
/* 1571 */         fracture(-1);
/*      */       }
/*      */       
/* 1574 */       while (this.path.size() != 0) {
/* 1575 */         pop();
/*      */       }
/*      */ 
/*      */       
/* 1579 */       if (this.offsetLastIndex && this.offsetLastIndexOnReplace) {
/* 1580 */         (this.insertPath[this.insertPath.length - 1]).index++;
/*      */       }
/*      */       
/*      */       int k;
/*      */       
/* 1585 */       for (k = this.insertPath.length - 1; k >= 0; 
/* 1586 */         k--) {
/* 1587 */         ElemChanges elemChanges = this.insertPath[k];
/* 1588 */         if (elemChanges.parent == this.fracturedParent)
/* 1589 */           elemChanges.added.addElement(this.fracturedChild); 
/* 1590 */         if ((elemChanges.added.size() > 0 || elemChanges.removed
/* 1591 */           .size() > 0) && !this.changes.contains(elemChanges))
/*      */         {
/* 1593 */           this.changes.addElement(elemChanges);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1600 */       if (this.offset == 0 && this.fracturedParent != null && param1ArrayOfElementSpec[0]
/* 1601 */         .getType() == 2) {
/* 1602 */         k = 0;
/* 1603 */         while (k < param1ArrayOfElementSpec.length && param1ArrayOfElementSpec[k]
/* 1604 */           .getType() == 2) {
/* 1605 */           k++;
/*      */         }
/* 1607 */         ElemChanges elemChanges = this.insertPath[this.insertPath.length - k - 1];
/*      */         
/* 1609 */         elemChanges.removed.insertElementAt(elemChanges.parent
/* 1610 */             .getElement(--elemChanges.index), 0);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void removeUpdate() {
/* 1620 */       removeElements(this.root, this.offset, this.offset + this.length);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void changeUpdate() {
/* 1628 */       boolean bool = split(this.offset, this.length);
/* 1629 */       if (!bool) {
/*      */         
/* 1631 */         while (this.path.size() != 0) {
/* 1632 */           pop();
/*      */         }
/* 1634 */         split(this.offset + this.length, 0);
/*      */       } 
/* 1636 */       while (this.path.size() != 0) {
/* 1637 */         pop();
/*      */       }
/*      */     }
/*      */     
/*      */     boolean split(int param1Int1, int param1Int2) {
/* 1642 */       boolean bool = false;
/*      */       
/* 1644 */       Element element1 = this.root;
/* 1645 */       int i = element1.getElementIndex(param1Int1);
/* 1646 */       while (!element1.isLeaf()) {
/* 1647 */         push(element1, i);
/* 1648 */         element1 = element1.getElement(i);
/* 1649 */         i = element1.getElementIndex(param1Int1);
/*      */       } 
/*      */       
/* 1652 */       ElemChanges elemChanges = this.path.peek();
/* 1653 */       Element element2 = elemChanges.parent.getElement(elemChanges.index);
/*      */ 
/*      */ 
/*      */       
/* 1657 */       if (element2.getStartOffset() < param1Int1 && param1Int1 < element2.getEndOffset()) {
/*      */ 
/*      */         
/* 1660 */         int j = elemChanges.index;
/* 1661 */         int k = j;
/* 1662 */         if (param1Int1 + param1Int2 < elemChanges.parent.getEndOffset() && param1Int2 != 0) {
/*      */           
/* 1664 */           k = elemChanges.parent.getElementIndex(param1Int1 + param1Int2);
/* 1665 */           if (k == j) {
/*      */             
/* 1667 */             elemChanges.removed.addElement(element2);
/* 1668 */             element1 = DefaultStyledDocument.this.createLeafElement(elemChanges.parent, element2.getAttributes(), element2
/* 1669 */                 .getStartOffset(), param1Int1);
/* 1670 */             elemChanges.added.addElement(element1);
/* 1671 */             element1 = DefaultStyledDocument.this.createLeafElement(elemChanges.parent, element2.getAttributes(), param1Int1, param1Int1 + param1Int2);
/*      */             
/* 1673 */             elemChanges.added.addElement(element1);
/* 1674 */             element1 = DefaultStyledDocument.this.createLeafElement(elemChanges.parent, element2.getAttributes(), param1Int1 + param1Int2, element2
/* 1675 */                 .getEndOffset());
/* 1676 */             elemChanges.added.addElement(element1);
/* 1677 */             return true;
/*      */           } 
/* 1679 */           element2 = elemChanges.parent.getElement(k);
/* 1680 */           if (param1Int1 + param1Int2 == element2.getStartOffset())
/*      */           {
/* 1682 */             k = j;
/*      */           }
/*      */           
/* 1685 */           bool = true;
/*      */         } 
/*      */ 
/*      */         
/* 1689 */         this.pos = param1Int1;
/* 1690 */         element2 = elemChanges.parent.getElement(j);
/* 1691 */         elemChanges.removed.addElement(element2);
/* 1692 */         element1 = DefaultStyledDocument.this.createLeafElement(elemChanges.parent, element2.getAttributes(), element2
/* 1693 */             .getStartOffset(), this.pos);
/* 1694 */         elemChanges.added.addElement(element1);
/* 1695 */         element1 = DefaultStyledDocument.this.createLeafElement(elemChanges.parent, element2.getAttributes(), this.pos, element2
/* 1696 */             .getEndOffset());
/* 1697 */         elemChanges.added.addElement(element1);
/*      */ 
/*      */         
/* 1700 */         for (int m = j + 1; m < k; m++) {
/* 1701 */           element2 = elemChanges.parent.getElement(m);
/* 1702 */           elemChanges.removed.addElement(element2);
/* 1703 */           elemChanges.added.addElement(element2);
/*      */         } 
/*      */         
/* 1706 */         if (k != j) {
/* 1707 */           element2 = elemChanges.parent.getElement(k);
/* 1708 */           this.pos = param1Int1 + param1Int2;
/* 1709 */           elemChanges.removed.addElement(element2);
/* 1710 */           element1 = DefaultStyledDocument.this.createLeafElement(elemChanges.parent, element2.getAttributes(), element2
/* 1711 */               .getStartOffset(), this.pos);
/* 1712 */           elemChanges.added.addElement(element1);
/* 1713 */           element1 = DefaultStyledDocument.this.createLeafElement(elemChanges.parent, element2.getAttributes(), this.pos, element2
/* 1714 */               .getEndOffset());
/* 1715 */           elemChanges.added.addElement(element1);
/*      */         } 
/*      */       } 
/* 1718 */       return bool;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void endEdits(AbstractDocument.DefaultDocumentEvent param1DefaultDocumentEvent) {
/* 1726 */       int i = this.changes.size();
/* 1727 */       for (byte b = 0; b < i; b++) {
/* 1728 */         ElemChanges elemChanges = this.changes.elementAt(b);
/* 1729 */         Element[] arrayOfElement1 = new Element[elemChanges.removed.size()];
/* 1730 */         elemChanges.removed.copyInto((Object[])arrayOfElement1);
/* 1731 */         Element[] arrayOfElement2 = new Element[elemChanges.added.size()];
/* 1732 */         elemChanges.added.copyInto((Object[])arrayOfElement2);
/* 1733 */         int j = elemChanges.index;
/* 1734 */         ((AbstractDocument.BranchElement)elemChanges.parent).replace(j, arrayOfElement1.length, arrayOfElement2);
/* 1735 */         AbstractDocument.ElementEdit elementEdit = new AbstractDocument.ElementEdit(elemChanges.parent, j, arrayOfElement1, arrayOfElement2);
/* 1736 */         param1DefaultDocumentEvent.addEdit(elementEdit);
/*      */       } 
/*      */       
/* 1739 */       this.changes.removeAllElements();
/* 1740 */       this.path.removeAllElements();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void beginEdits(int param1Int1, int param1Int2) {
/* 1767 */       this.offset = param1Int1;
/* 1768 */       this.length = param1Int2;
/* 1769 */       this.endOffset = param1Int1 + param1Int2;
/* 1770 */       this.pos = param1Int1;
/* 1771 */       if (this.changes == null) {
/* 1772 */         this.changes = new Vector<>();
/*      */       } else {
/* 1774 */         this.changes.removeAllElements();
/*      */       } 
/* 1776 */       if (this.path == null) {
/* 1777 */         this.path = new Stack<>();
/*      */       } else {
/* 1779 */         this.path.removeAllElements();
/*      */       } 
/* 1781 */       this.fracturedParent = null;
/* 1782 */       this.fracturedChild = null;
/* 1783 */       this.offsetLastIndex = this.offsetLastIndexOnReplace = false;
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
/*      */     void push(Element param1Element, int param1Int, boolean param1Boolean) {
/* 1795 */       ElemChanges elemChanges = new ElemChanges(param1Element, param1Int, param1Boolean);
/* 1796 */       this.path.push(elemChanges);
/*      */     }
/*      */     
/*      */     void push(Element param1Element, int param1Int) {
/* 1800 */       push(param1Element, param1Int, false);
/*      */     }
/*      */     
/*      */     void pop() {
/* 1804 */       ElemChanges elemChanges = this.path.peek();
/* 1805 */       this.path.pop();
/* 1806 */       if (elemChanges.added.size() > 0 || elemChanges.removed.size() > 0) {
/* 1807 */         this.changes.addElement(elemChanges);
/* 1808 */       } else if (!this.path.isEmpty()) {
/* 1809 */         Element element = elemChanges.parent;
/* 1810 */         if (element.getElementCount() == 0) {
/*      */ 
/*      */           
/* 1813 */           elemChanges = this.path.peek();
/* 1814 */           elemChanges.added.removeElement(element);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void advance(int param1Int) {
/* 1823 */       this.pos += param1Int; } void insertElement(DefaultStyledDocument.ElementSpec param1ElementSpec) {
/*      */       Element element1;
/*      */       int i;
/*      */       Element element2;
/* 1827 */       ElemChanges elemChanges = this.path.peek();
/* 1828 */       switch (param1ElementSpec.getType()) {
/*      */         case 1:
/* 1830 */           switch (param1ElementSpec.getDirection()) {
/*      */ 
/*      */             
/*      */             case 5:
/* 1834 */               element1 = elemChanges.parent.getElement(elemChanges.index);
/*      */               
/* 1836 */               if (element1.isLeaf())
/*      */               {
/*      */                 
/* 1839 */                 if (elemChanges.index + 1 < elemChanges.parent.getElementCount()) {
/* 1840 */                   element1 = elemChanges.parent.getElement(elemChanges.index + 1);
/*      */                 } else {
/* 1842 */                   throw new StateInvariantError("Join next to leaf");
/*      */                 } 
/*      */               }
/*      */ 
/*      */ 
/*      */               
/* 1848 */               push(element1, 0, true);
/*      */               break;
/*      */             case 7:
/* 1851 */               if (!this.createdFracture)
/*      */               {
/* 1853 */                 fracture(this.path.size() - 1);
/*      */               }
/*      */ 
/*      */               
/* 1857 */               if (!elemChanges.isFracture) {
/* 1858 */                 push(this.fracturedChild, 0, true);
/*      */                 
/*      */                 break;
/*      */               } 
/* 1862 */               push(elemChanges.parent.getElement(0), 0, true);
/*      */               break;
/*      */           } 
/* 1865 */           element2 = DefaultStyledDocument.this.createBranchElement(elemChanges.parent, param1ElementSpec
/* 1866 */               .getAttributes());
/* 1867 */           elemChanges.added.addElement(element2);
/* 1868 */           push(element2, 0);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 2:
/* 1873 */           pop();
/*      */           break;
/*      */         case 3:
/* 1876 */           i = param1ElementSpec.getLength();
/* 1877 */           if (param1ElementSpec.getDirection() != 5) {
/* 1878 */             element2 = DefaultStyledDocument.this.createLeafElement(elemChanges.parent, param1ElementSpec.getAttributes(), this.pos, this.pos + i);
/*      */             
/* 1880 */             elemChanges.added.addElement(element2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           }
/* 1888 */           else if (!elemChanges.isFracture) {
/* 1889 */             element2 = null;
/* 1890 */             if (this.insertPath != null) {
/* 1891 */               int j = this.insertPath.length - 1;
/* 1892 */               for (; j >= 0; j--) {
/* 1893 */                 if (this.insertPath[j] == elemChanges) {
/* 1894 */                   if (j != this.insertPath.length - 1)
/* 1895 */                     element2 = elemChanges.parent.getElement(elemChanges.index); 
/*      */                   break;
/*      */                 } 
/*      */               } 
/*      */             } 
/* 1900 */             if (element2 == null)
/* 1901 */               element2 = elemChanges.parent.getElement(elemChanges.index + 1); 
/* 1902 */             Element element = DefaultStyledDocument.this.createLeafElement(elemChanges.parent, element2
/* 1903 */                 .getAttributes(), this.pos, element2.getEndOffset());
/* 1904 */             elemChanges.added.addElement(element);
/* 1905 */             elemChanges.removed.addElement(element2);
/*      */           }
/*      */           else {
/*      */             
/* 1909 */             element2 = elemChanges.parent.getElement(0);
/* 1910 */             Element element = DefaultStyledDocument.this.createLeafElement(elemChanges.parent, element2
/* 1911 */                 .getAttributes(), this.pos, element2.getEndOffset());
/* 1912 */             elemChanges.added.addElement(element);
/* 1913 */             elemChanges.removed.addElement(element2);
/*      */           } 
/*      */           
/* 1916 */           this.pos += i;
/*      */           break;
/*      */       } 
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
/*      */     boolean removeElements(Element param1Element, int param1Int1, int param1Int2) {
/* 1930 */       if (!param1Element.isLeaf()) {
/*      */         
/* 1932 */         int i = param1Element.getElementIndex(param1Int1);
/* 1933 */         int j = param1Element.getElementIndex(param1Int2);
/* 1934 */         push(param1Element, i);
/* 1935 */         ElemChanges elemChanges = this.path.peek();
/*      */ 
/*      */ 
/*      */         
/* 1939 */         if (i == j) {
/* 1940 */           Element element = param1Element.getElement(i);
/* 1941 */           if (param1Int1 <= element.getStartOffset() && param1Int2 >= element
/* 1942 */             .getEndOffset()) {
/*      */             
/* 1944 */             elemChanges.removed.addElement(element);
/*      */           }
/* 1946 */           else if (removeElements(element, param1Int1, param1Int2)) {
/* 1947 */             elemChanges.removed.addElement(element);
/*      */           }
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1953 */           Element element1 = param1Element.getElement(i);
/* 1954 */           Element element2 = param1Element.getElement(j);
/* 1955 */           boolean bool = (param1Int2 < param1Element.getEndOffset()) ? true : false;
/* 1956 */           if (bool && canJoin(element1, element2)) {
/*      */             
/* 1958 */             for (int k = i; k <= j; k++) {
/* 1959 */               elemChanges.removed.addElement(param1Element.getElement(k));
/*      */             }
/* 1961 */             Element element = join(param1Element, element1, element2, param1Int1, param1Int2);
/* 1962 */             elemChanges.added.addElement(element);
/*      */           } else {
/*      */             
/* 1965 */             int k = i + 1;
/* 1966 */             int m = j - 1;
/* 1967 */             if (element1.getStartOffset() == param1Int1 || (i == 0 && element1
/*      */               
/* 1969 */               .getStartOffset() > param1Int1 && element1
/* 1970 */               .getEndOffset() <= param1Int2)) {
/*      */               
/* 1972 */               element1 = null;
/* 1973 */               k = i;
/*      */             } 
/* 1975 */             if (!bool) {
/* 1976 */               element2 = null;
/* 1977 */               m++;
/*      */             }
/* 1979 */             else if (element2.getStartOffset() == param1Int2) {
/*      */               
/* 1981 */               element2 = null;
/*      */             } 
/* 1983 */             if (k <= m) {
/* 1984 */               elemChanges.index = k;
/*      */             }
/* 1986 */             for (int n = k; n <= m; n++) {
/* 1987 */               elemChanges.removed.addElement(param1Element.getElement(n));
/*      */             }
/* 1989 */             if (element1 != null && 
/* 1990 */               removeElements(element1, param1Int1, param1Int2)) {
/* 1991 */               elemChanges.removed.insertElementAt(element1, 0);
/* 1992 */               elemChanges.index = i;
/*      */             } 
/*      */             
/* 1995 */             if (element2 != null && 
/* 1996 */               removeElements(element2, param1Int1, param1Int2)) {
/* 1997 */               elemChanges.removed.addElement(element2);
/*      */             }
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 2004 */         pop();
/*      */ 
/*      */         
/* 2007 */         if (param1Element.getElementCount() == elemChanges.removed.size() - elemChanges.added
/* 2008 */           .size()) {
/* 2009 */           return true;
/*      */         }
/*      */       } 
/* 2012 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean canJoin(Element param1Element1, Element param1Element2) {
/* 2020 */       if (param1Element1 == null || param1Element2 == null) {
/* 2021 */         return false;
/*      */       }
/*      */       
/* 2024 */       boolean bool1 = param1Element1.isLeaf();
/* 2025 */       boolean bool2 = param1Element2.isLeaf();
/* 2026 */       if (bool1 != bool2) {
/* 2027 */         return false;
/*      */       }
/* 2029 */       if (bool1)
/*      */       {
/*      */         
/* 2032 */         return param1Element1.getAttributes().isEqual(param1Element2.getAttributes());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 2037 */       String str1 = param1Element1.getName();
/* 2038 */       String str2 = param1Element2.getName();
/* 2039 */       if (str1 != null) {
/* 2040 */         return str1.equals(str2);
/*      */       }
/* 2042 */       if (str2 != null) {
/* 2043 */         return str2.equals(str1);
/*      */       }
/*      */       
/* 2046 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Element join(Element param1Element1, Element param1Element2, Element param1Element3, int param1Int1, int param1Int2) {
/* 2054 */       if (param1Element2.isLeaf() && param1Element3.isLeaf())
/* 2055 */         return DefaultStyledDocument.this.createLeafElement(param1Element1, param1Element2.getAttributes(), param1Element2.getStartOffset(), param1Element3
/* 2056 */             .getEndOffset()); 
/* 2057 */       if (!param1Element2.isLeaf() && !param1Element3.isLeaf()) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2062 */         Element element1 = DefaultStyledDocument.this.createBranchElement(param1Element1, param1Element2.getAttributes());
/* 2063 */         int i = param1Element2.getElementIndex(param1Int1);
/* 2064 */         int j = param1Element3.getElementIndex(param1Int2);
/* 2065 */         Element element2 = param1Element2.getElement(i);
/* 2066 */         if (element2.getStartOffset() >= param1Int1) {
/* 2067 */           element2 = null;
/*      */         }
/* 2069 */         Element element3 = param1Element3.getElement(j);
/* 2070 */         if (element3.getStartOffset() == param1Int2) {
/* 2071 */           element3 = null;
/*      */         }
/* 2073 */         Vector<Element> vector = new Vector();
/*      */         
/*      */         int k;
/* 2076 */         for (k = 0; k < i; k++) {
/* 2077 */           vector.addElement(clone(element1, param1Element2.getElement(k)));
/*      */         }
/*      */ 
/*      */         
/* 2081 */         if (canJoin(element2, element3)) {
/* 2082 */           Element element = join(element1, element2, element3, param1Int1, param1Int2);
/* 2083 */           vector.addElement(element);
/*      */         } else {
/* 2085 */           if (element2 != null) {
/* 2086 */             vector.addElement(cloneAsNecessary(element1, element2, param1Int1, param1Int2));
/*      */           }
/* 2088 */           if (element3 != null) {
/* 2089 */             vector.addElement(cloneAsNecessary(element1, element3, param1Int1, param1Int2));
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 2094 */         k = param1Element3.getElementCount();
/* 2095 */         for (int m = (element3 == null) ? j : (j + 1); m < k; m++) {
/* 2096 */           vector.addElement(clone(element1, param1Element3.getElement(m)));
/*      */         }
/*      */ 
/*      */         
/* 2100 */         Element[] arrayOfElement = new Element[vector.size()];
/* 2101 */         vector.copyInto((Object[])arrayOfElement);
/* 2102 */         ((AbstractDocument.BranchElement)element1).replace(0, 0, arrayOfElement);
/* 2103 */         return element1;
/*      */       } 
/* 2105 */       throw new StateInvariantError("No support to join leaf element with non-leaf element");
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
/*      */     public Element clone(Element param1Element1, Element param1Element2) {
/* 2119 */       if (param1Element2.isLeaf()) {
/* 2120 */         return DefaultStyledDocument.this.createLeafElement(param1Element1, param1Element2.getAttributes(), param1Element2
/* 2121 */             .getStartOffset(), param1Element2
/* 2122 */             .getEndOffset());
/*      */       }
/* 2124 */       Element element = DefaultStyledDocument.this.createBranchElement(param1Element1, param1Element2.getAttributes());
/* 2125 */       int i = param1Element2.getElementCount();
/* 2126 */       Element[] arrayOfElement = new Element[i];
/* 2127 */       for (byte b = 0; b < i; b++) {
/* 2128 */         arrayOfElement[b] = clone(element, param1Element2.getElement(b));
/*      */       }
/* 2130 */       ((AbstractDocument.BranchElement)element).replace(0, 0, arrayOfElement);
/* 2131 */       return element;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Element cloneAsNecessary(Element param1Element1, Element param1Element2, int param1Int1, int param1Int2) {
/* 2140 */       if (param1Element2.isLeaf()) {
/* 2141 */         return DefaultStyledDocument.this.createLeafElement(param1Element1, param1Element2.getAttributes(), param1Element2
/* 2142 */             .getStartOffset(), param1Element2
/* 2143 */             .getEndOffset());
/*      */       }
/* 2145 */       Element element = DefaultStyledDocument.this.createBranchElement(param1Element1, param1Element2.getAttributes());
/* 2146 */       int i = param1Element2.getElementCount();
/* 2147 */       ArrayList<Element> arrayList = new ArrayList(i);
/* 2148 */       for (byte b = 0; b < i; b++) {
/* 2149 */         Element element1 = param1Element2.getElement(b);
/* 2150 */         if (element1.getStartOffset() < param1Int1 || element1.getEndOffset() > param1Int2) {
/* 2151 */           arrayList.add(cloneAsNecessary(element, element1, param1Int1, param1Int2));
/*      */         }
/*      */       } 
/* 2154 */       Element[] arrayOfElement = new Element[arrayList.size()];
/* 2155 */       arrayOfElement = arrayList.<Element>toArray(arrayOfElement);
/* 2156 */       ((AbstractDocument.BranchElement)element).replace(0, 0, arrayOfElement);
/* 2157 */       return element;
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
/*      */     void fracture(int param1Int) {
/* 2172 */       int i = this.insertPath.length;
/* 2173 */       int j = -1;
/* 2174 */       boolean bool = this.recreateLeafs;
/* 2175 */       ElemChanges elemChanges = this.insertPath[i - 1];
/*      */ 
/*      */ 
/*      */       
/* 2179 */       boolean bool1 = (elemChanges.index + 1 < elemChanges.parent.getElementCount()) ? true : false;
/* 2180 */       int k = bool ? i : -1;
/* 2181 */       int m = i - 1;
/*      */       
/* 2183 */       this.createdFracture = true;
/*      */ 
/*      */ 
/*      */       
/* 2187 */       for (int n = i - 2; n >= 0; n--) {
/* 2188 */         ElemChanges elemChanges1 = this.insertPath[n];
/* 2189 */         if (elemChanges1.added.size() > 0 || n == param1Int) {
/* 2190 */           j = n;
/* 2191 */           if (!bool && bool1) {
/* 2192 */             bool = true;
/* 2193 */             if (k == -1)
/* 2194 */               k = m + 1; 
/*      */           } 
/*      */         } 
/* 2197 */         if (!bool1 && elemChanges1.index < elemChanges1.parent
/* 2198 */           .getElementCount()) {
/* 2199 */           bool1 = true;
/* 2200 */           m = n;
/*      */         } 
/*      */       } 
/* 2203 */       if (bool) {
/*      */ 
/*      */         
/* 2206 */         if (j == -1)
/* 2207 */           j = i - 1; 
/* 2208 */         fractureFrom(this.insertPath, j, k);
/*      */       } 
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
/*      */     void fractureFrom(ElemChanges[] param1ArrayOfElemChanges, int param1Int1, int param1Int2) {
/*      */       Element element1, element2;
/* 2224 */       ElemChanges elemChanges = param1ArrayOfElemChanges[param1Int1];
/*      */ 
/*      */       
/* 2227 */       int i = param1ArrayOfElemChanges.length;
/*      */       
/* 2229 */       if (param1Int1 + 1 == i) {
/* 2230 */         element1 = elemChanges.parent.getElement(elemChanges.index);
/*      */       } else {
/* 2232 */         element1 = elemChanges.parent.getElement(elemChanges.index - 1);
/* 2233 */       }  if (element1.isLeaf()) {
/* 2234 */         element2 = DefaultStyledDocument.this.createLeafElement(elemChanges.parent, element1
/* 2235 */             .getAttributes(), Math.max(this.endOffset, element1
/* 2236 */               .getStartOffset()), element1.getEndOffset());
/*      */       } else {
/*      */         
/* 2239 */         element2 = DefaultStyledDocument.this.createBranchElement(elemChanges.parent, element1
/* 2240 */             .getAttributes());
/*      */       } 
/* 2242 */       this.fracturedParent = elemChanges.parent;
/* 2243 */       this.fracturedChild = element2;
/*      */ 
/*      */ 
/*      */       
/* 2247 */       Element element3 = element2;
/*      */       
/* 2249 */       while (++param1Int1 < param1Int2) {
/* 2250 */         Element[] arrayOfElement; int k; boolean bool1 = (param1Int1 + 1 == param1Int2) ? true : false;
/* 2251 */         boolean bool2 = (param1Int1 + 1 == i) ? true : false;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2256 */         elemChanges = param1ArrayOfElemChanges[param1Int1];
/*      */ 
/*      */ 
/*      */         
/* 2260 */         if (bool1) {
/* 2261 */           if (this.offsetLastIndex || !bool2) {
/* 2262 */             element1 = null;
/*      */           } else {
/* 2264 */             element1 = elemChanges.parent.getElement(elemChanges.index);
/*      */           } 
/*      */         } else {
/* 2267 */           element1 = elemChanges.parent.getElement(elemChanges.index - 1);
/*      */         } 
/*      */         
/* 2270 */         if (element1 != null) {
/* 2271 */           if (element1.isLeaf()) {
/* 2272 */             element2 = DefaultStyledDocument.this.createLeafElement(element3, element1
/* 2273 */                 .getAttributes(), Math.max(this.endOffset, element1
/* 2274 */                   .getStartOffset()), element1.getEndOffset());
/*      */           } else {
/*      */             
/* 2277 */             element2 = DefaultStyledDocument.this.createBranchElement(element3, element1
/* 2278 */                 .getAttributes());
/*      */           } 
/*      */         } else {
/*      */           
/* 2282 */           element2 = null;
/*      */         } 
/*      */         
/* 2285 */         int j = elemChanges.parent.getElementCount() - elemChanges.index;
/*      */ 
/*      */ 
/*      */         
/* 2289 */         byte b1 = 1;
/*      */         
/* 2291 */         if (element2 == null) {
/*      */           
/* 2293 */           if (bool2) {
/* 2294 */             j--;
/* 2295 */             k = elemChanges.index + 1;
/*      */           } else {
/*      */             
/* 2298 */             k = elemChanges.index;
/*      */           } 
/* 2300 */           b1 = 0;
/* 2301 */           arrayOfElement = new Element[j];
/*      */         } else {
/*      */           
/* 2304 */           if (!bool1) {
/*      */             
/* 2306 */             j++;
/* 2307 */             k = elemChanges.index;
/*      */           }
/*      */           else {
/*      */             
/* 2311 */             k = elemChanges.index + 1;
/*      */           } 
/* 2313 */           arrayOfElement = new Element[j];
/* 2314 */           arrayOfElement[0] = element2;
/*      */         } 
/*      */         
/* 2317 */         for (byte b2 = b1; b2 < j; 
/* 2318 */           b2++) {
/* 2319 */           Element element = elemChanges.parent.getElement(k++);
/* 2320 */           arrayOfElement[b2] = recreateFracturedElement(element3, element);
/* 2321 */           elemChanges.removed.addElement(element);
/*      */         } 
/* 2323 */         ((AbstractDocument.BranchElement)element3).replace(0, 0, arrayOfElement);
/* 2324 */         element3 = element2;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Element recreateFracturedElement(Element param1Element1, Element param1Element2) {
/* 2335 */       if (param1Element2.isLeaf()) {
/* 2336 */         return DefaultStyledDocument.this.createLeafElement(param1Element1, param1Element2.getAttributes(), 
/* 2337 */             Math.max(param1Element2.getStartOffset(), this.endOffset), param1Element2
/*      */             
/* 2339 */             .getEndOffset());
/*      */       }
/*      */       
/* 2342 */       Element element = DefaultStyledDocument.this.createBranchElement(param1Element1, param1Element2
/* 2343 */           .getAttributes());
/* 2344 */       int i = param1Element2.getElementCount();
/* 2345 */       Element[] arrayOfElement = new Element[i];
/* 2346 */       for (byte b = 0; b < i; b++) {
/* 2347 */         arrayOfElement[b] = recreateFracturedElement(element, param1Element2
/* 2348 */             .getElement(b));
/*      */       }
/* 2350 */       ((AbstractDocument.BranchElement)element).replace(0, 0, arrayOfElement);
/* 2351 */       return element;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void fractureDeepestLeaf(DefaultStyledDocument.ElementSpec[] param1ArrayOfElementSpec) {
/* 2360 */       ElemChanges elemChanges = this.path.peek();
/* 2361 */       Element element = elemChanges.parent.getElement(elemChanges.index);
/*      */ 
/*      */       
/* 2364 */       if (this.offset != 0) {
/* 2365 */         Element element1 = DefaultStyledDocument.this.createLeafElement(elemChanges.parent, element
/* 2366 */             .getAttributes(), element
/* 2367 */             .getStartOffset(), this.offset);
/*      */ 
/*      */         
/* 2370 */         elemChanges.added.addElement(element1);
/*      */       } 
/* 2372 */       elemChanges.removed.addElement(element);
/* 2373 */       if (element.getEndOffset() != this.endOffset) {
/* 2374 */         this.recreateLeafs = true;
/*      */       } else {
/* 2376 */         this.offsetLastIndex = true;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void insertFirstContent(DefaultStyledDocument.ElementSpec[] param1ArrayOfElementSpec) {
/* 2384 */       DefaultStyledDocument.ElementSpec elementSpec = param1ArrayOfElementSpec[0];
/* 2385 */       ElemChanges elemChanges = this.path.peek();
/* 2386 */       Element element1 = elemChanges.parent.getElement(elemChanges.index);
/* 2387 */       int i = this.offset + elementSpec.getLength();
/* 2388 */       boolean bool = (param1ArrayOfElementSpec.length == 1) ? true : false;
/*      */       
/* 2390 */       switch (elementSpec.getDirection()) {
/*      */         case 4:
/* 2392 */           if (element1.getEndOffset() != i && !bool) {
/*      */ 
/*      */             
/* 2395 */             Element element = DefaultStyledDocument.this.createLeafElement(elemChanges.parent, element1
/* 2396 */                 .getAttributes(), element1.getStartOffset(), i);
/*      */             
/* 2398 */             elemChanges.added.addElement(element);
/* 2399 */             elemChanges.removed.addElement(element1);
/*      */             
/* 2401 */             if (element1.getEndOffset() != this.endOffset) {
/* 2402 */               this.recreateLeafs = true;
/*      */             } else {
/* 2404 */               this.offsetLastIndex = true;
/*      */             } 
/*      */           } else {
/* 2407 */             this.offsetLastIndex = true;
/* 2408 */             this.offsetLastIndexOnReplace = true;
/*      */           } 
/*      */           return;
/*      */ 
/*      */         
/*      */         case 5:
/* 2414 */           if (this.offset != 0) {
/*      */ 
/*      */             
/* 2417 */             Element element3 = DefaultStyledDocument.this.createLeafElement(elemChanges.parent, element1
/* 2418 */                 .getAttributes(), element1.getStartOffset(), this.offset);
/*      */             
/* 2420 */             elemChanges.added.addElement(element3);
/*      */ 
/*      */             
/* 2423 */             Element element4 = elemChanges.parent.getElement(elemChanges.index + 1);
/* 2424 */             if (bool) {
/* 2425 */               element3 = DefaultStyledDocument.this.createLeafElement(elemChanges.parent, element4
/* 2426 */                   .getAttributes(), this.offset, element4.getEndOffset());
/*      */             } else {
/* 2428 */               element3 = DefaultStyledDocument.this.createLeafElement(elemChanges.parent, element4
/* 2429 */                   .getAttributes(), this.offset, i);
/* 2430 */             }  elemChanges.added.addElement(element3);
/* 2431 */             elemChanges.removed.addElement(element1);
/* 2432 */             elemChanges.removed.addElement(element4);
/*      */           } 
/*      */           return;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2440 */       if (element1.getStartOffset() != this.offset) {
/* 2441 */         Element element = DefaultStyledDocument.this.createLeafElement(elemChanges.parent, element1
/* 2442 */             .getAttributes(), element1.getStartOffset(), this.offset);
/*      */         
/* 2444 */         elemChanges.added.addElement(element);
/*      */       } 
/* 2446 */       elemChanges.removed.addElement(element1);
/*      */       
/* 2448 */       Element element2 = DefaultStyledDocument.this.createLeafElement(elemChanges.parent, elementSpec
/* 2449 */           .getAttributes(), this.offset, i);
/*      */       
/* 2451 */       elemChanges.added.addElement(element2);
/* 2452 */       if (element1.getEndOffset() != this.endOffset) {
/*      */         
/* 2454 */         this.recreateLeafs = true;
/*      */       } else {
/*      */         
/* 2457 */         this.offsetLastIndex = true;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     class ElemChanges
/*      */     {
/*      */       Element parent;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       int index;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       Vector<Element> added;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       Vector<Element> removed;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       boolean isFracture;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       ElemChanges(Element param2Element, int param2Int, boolean param2Boolean) {
/* 2496 */         this.parent = param2Element;
/* 2497 */         this.index = param2Int;
/* 2498 */         this.isFracture = param2Boolean;
/* 2499 */         this.added = new Vector<>();
/* 2500 */         this.removed = new Vector<>();
/*      */       }
/*      */       
/*      */       public String toString() {
/* 2504 */         return "added: " + this.added + "\nremoved: " + this.removed + "\n";
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class AttributeUndoableEdit
/*      */     extends AbstractUndoableEdit
/*      */   {
/*      */     protected AttributeSet newAttributes;
/*      */     
/*      */     protected AttributeSet copy;
/*      */     
/*      */     protected boolean isReplacing;
/*      */     
/*      */     protected Element element;
/*      */ 
/*      */     
/*      */     public AttributeUndoableEdit(Element param1Element, AttributeSet param1AttributeSet, boolean param1Boolean) {
/* 2524 */       this.element = param1Element;
/* 2525 */       this.newAttributes = param1AttributeSet;
/* 2526 */       this.isReplacing = param1Boolean;
/*      */ 
/*      */       
/* 2529 */       this.copy = param1Element.getAttributes().copyAttributes();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void redo() throws CannotRedoException {
/* 2538 */       super.redo();
/*      */       
/* 2540 */       MutableAttributeSet mutableAttributeSet = (MutableAttributeSet)this.element.getAttributes();
/* 2541 */       if (this.isReplacing)
/* 2542 */         mutableAttributeSet.removeAttributes(mutableAttributeSet); 
/* 2543 */       mutableAttributeSet.addAttributes(this.newAttributes);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void undo() throws CannotUndoException {
/* 2552 */       super.undo();
/* 2553 */       MutableAttributeSet mutableAttributeSet = (MutableAttributeSet)this.element.getAttributes();
/* 2554 */       mutableAttributeSet.removeAttributes(mutableAttributeSet);
/* 2555 */       mutableAttributeSet.addAttributes(this.copy);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class StyleChangeUndoableEdit
/*      */     extends AbstractUndoableEdit
/*      */   {
/*      */     protected AbstractDocument.AbstractElement element;
/*      */ 
/*      */     
/*      */     protected Style newStyle;
/*      */ 
/*      */     
/*      */     protected AttributeSet oldStyle;
/*      */ 
/*      */ 
/*      */     
/*      */     public StyleChangeUndoableEdit(AbstractDocument.AbstractElement param1AbstractElement, Style param1Style) {
/* 2575 */       this.element = param1AbstractElement;
/* 2576 */       this.newStyle = param1Style;
/* 2577 */       this.oldStyle = param1AbstractElement.getResolveParent();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void redo() throws CannotRedoException {
/* 2586 */       super.redo();
/* 2587 */       this.element.setResolveParent(this.newStyle);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void undo() throws CannotUndoException {
/* 2596 */       super.undo();
/* 2597 */       this.element.setResolveParent(this.oldStyle);
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
/*      */   static abstract class AbstractChangeHandler
/*      */     implements ChangeListener
/*      */   {
/*      */     private class DocReference
/*      */       extends WeakReference<DefaultStyledDocument>
/*      */     {
/*      */       DocReference(DefaultStyledDocument param2DefaultStyledDocument, ReferenceQueue<DefaultStyledDocument> param2ReferenceQueue) {
/* 2617 */         super(param2DefaultStyledDocument, param2ReferenceQueue);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       ChangeListener getListener() {
/* 2624 */         return DefaultStyledDocument.AbstractChangeHandler.this;
/*      */       }
/*      */     }
/*      */ 
/*      */     
/* 2629 */     private static final Map<Class, ReferenceQueue<DefaultStyledDocument>> queueMap = (Map)new HashMap<>();
/*      */     
/*      */     private DocReference doc;
/*      */ 
/*      */     
/*      */     AbstractChangeHandler(DefaultStyledDocument param1DefaultStyledDocument) {
/*      */       ReferenceQueue<DefaultStyledDocument> referenceQueue;
/* 2636 */       Class<?> clazz = getClass();
/*      */       
/* 2638 */       synchronized (queueMap) {
/* 2639 */         referenceQueue = queueMap.get(clazz);
/* 2640 */         if (referenceQueue == null) {
/* 2641 */           referenceQueue = new ReferenceQueue();
/* 2642 */           queueMap.put(clazz, referenceQueue);
/*      */         } 
/*      */       } 
/* 2645 */       this.doc = new DocReference(param1DefaultStyledDocument, referenceQueue);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static List<ChangeListener> getStaleListeners(ChangeListener param1ChangeListener) {
/* 2654 */       ArrayList<ChangeListener> arrayList = new ArrayList();
/* 2655 */       ReferenceQueue referenceQueue = queueMap.get(param1ChangeListener.getClass());
/*      */       
/* 2657 */       if (referenceQueue != null)
/*      */       {
/* 2659 */         synchronized (referenceQueue) {
/* 2660 */           DocReference docReference; while ((docReference = (DocReference)referenceQueue.poll()) != null) {
/* 2661 */             arrayList.add(docReference.getListener());
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/* 2666 */       return arrayList;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/* 2673 */       DefaultStyledDocument defaultStyledDocument = this.doc.get();
/* 2674 */       if (defaultStyledDocument != null) {
/* 2675 */         fireStateChanged(defaultStyledDocument, param1ChangeEvent);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     abstract void fireStateChanged(DefaultStyledDocument param1DefaultStyledDocument, ChangeEvent param1ChangeEvent);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class StyleChangeHandler
/*      */     extends AbstractChangeHandler
/*      */   {
/*      */     StyleChangeHandler(DefaultStyledDocument param1DefaultStyledDocument) {
/* 2690 */       super(param1DefaultStyledDocument);
/*      */     }
/*      */     
/*      */     void fireStateChanged(DefaultStyledDocument param1DefaultStyledDocument, ChangeEvent param1ChangeEvent) {
/* 2694 */       Object object = param1ChangeEvent.getSource();
/* 2695 */       if (object instanceof Style) {
/* 2696 */         param1DefaultStyledDocument.styleChanged((Style)object);
/*      */       } else {
/* 2698 */         param1DefaultStyledDocument.styleChanged((Style)null);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class StyleContextChangeHandler
/*      */     extends AbstractChangeHandler
/*      */   {
/*      */     StyleContextChangeHandler(DefaultStyledDocument param1DefaultStyledDocument) {
/* 2711 */       super(param1DefaultStyledDocument);
/*      */     }
/*      */     
/*      */     void fireStateChanged(DefaultStyledDocument param1DefaultStyledDocument, ChangeEvent param1ChangeEvent) {
/* 2715 */       param1DefaultStyledDocument.updateStylesListeningTo();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   class ChangeUpdateRunnable
/*      */     implements Runnable
/*      */   {
/*      */     boolean isPending = false;
/*      */ 
/*      */     
/*      */     public void run() {
/* 2728 */       synchronized (this) {
/* 2729 */         this.isPending = false;
/*      */       } 
/*      */       
/*      */       try {
/* 2733 */         DefaultStyledDocument.this.writeLock();
/*      */         
/* 2735 */         AbstractDocument.DefaultDocumentEvent defaultDocumentEvent = new AbstractDocument.DefaultDocumentEvent(DefaultStyledDocument.this, 0, DefaultStyledDocument.this.getLength(), DocumentEvent.EventType.CHANGE);
/*      */         
/* 2737 */         defaultDocumentEvent.end();
/* 2738 */         DefaultStyledDocument.this.fireChangedUpdate(defaultDocumentEvent);
/*      */       } finally {
/* 2740 */         DefaultStyledDocument.this.writeUnlock();
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/DefaultStyledDocument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */