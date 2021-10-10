/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.JEditorPane;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.event.CaretEvent;
/*     */ import javax.swing.event.CaretListener;
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
/*     */ public class StyledEditorKit
/*     */   extends DefaultEditorKit
/*     */ {
/*     */   public StyledEditorKit() {
/*  53 */     createInputAttributeUpdated();
/*  54 */     createInputAttributes();
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
/*     */   public MutableAttributeSet getInputAttributes() {
/*  69 */     return this.inputAttributes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element getCharacterAttributeRun() {
/*  79 */     return this.currentRun;
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
/*     */   public Action[] getActions() {
/*  93 */     this; return TextAction.augmentList(super.getActions(), defaultActions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Document createDefaultDocument() {
/* 103 */     return new DefaultStyledDocument();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void install(JEditorPane paramJEditorPane) {
/* 113 */     paramJEditorPane.addCaretListener(this.inputAttributeUpdater);
/* 114 */     paramJEditorPane.addPropertyChangeListener(this.inputAttributeUpdater);
/* 115 */     Caret caret = paramJEditorPane.getCaret();
/* 116 */     if (caret != null) {
/* 117 */       this.inputAttributeUpdater
/* 118 */         .updateInputAttributes(caret.getDot(), caret.getMark(), paramJEditorPane);
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
/*     */   public void deinstall(JEditorPane paramJEditorPane) {
/* 130 */     paramJEditorPane.removeCaretListener(this.inputAttributeUpdater);
/* 131 */     paramJEditorPane.removePropertyChangeListener(this.inputAttributeUpdater);
/*     */ 
/*     */     
/* 134 */     this.currentRun = null;
/* 135 */     this.currentParagraph = null;
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
/*     */   public ViewFactory getViewFactory() {
/* 154 */     return defaultFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 163 */     StyledEditorKit styledEditorKit = (StyledEditorKit)super.clone();
/* 164 */     styledEditorKit.currentRun = styledEditorKit.currentParagraph = null;
/* 165 */     styledEditorKit.createInputAttributeUpdated();
/* 166 */     styledEditorKit.createInputAttributes();
/* 167 */     return styledEditorKit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createInputAttributes() {
/* 174 */     this.inputAttributes = new SimpleAttributeSet() {
/*     */         public AttributeSet getResolveParent() {
/* 176 */           return (StyledEditorKit.this.currentParagraph != null) ? StyledEditorKit.this.currentParagraph
/* 177 */             .getAttributes() : null;
/*     */         }
/*     */         
/*     */         public Object clone() {
/* 181 */           return new SimpleAttributeSet(this);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createInputAttributeUpdated() {
/* 190 */     this.inputAttributeUpdater = new AttributeTracker();
/*     */   }
/*     */ 
/*     */   
/* 194 */   private static final ViewFactory defaultFactory = new StyledViewFactory();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Element currentRun;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Element currentParagraph;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MutableAttributeSet inputAttributes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AttributeTracker inputAttributeUpdater;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class AttributeTracker
/*     */     implements CaretListener, PropertyChangeListener, Serializable
/*     */   {
/*     */     void updateInputAttributes(int param1Int1, int param1Int2, JTextComponent param1JTextComponent) {
/*     */       Element element;
/* 229 */       Document document = param1JTextComponent.getDocument();
/* 230 */       if (!(document instanceof StyledDocument)) {
/*     */         return;
/*     */       }
/* 233 */       int i = Math.min(param1Int1, param1Int2);
/*     */       
/* 235 */       StyledDocument styledDocument = (StyledDocument)document;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 240 */       StyledEditorKit.this.currentParagraph = styledDocument.getParagraphElement(i);
/* 241 */       if (StyledEditorKit.this.currentParagraph.getStartOffset() == i || param1Int1 != param1Int2) {
/*     */ 
/*     */         
/* 244 */         element = styledDocument.getCharacterElement(i);
/*     */       } else {
/*     */         
/* 247 */         element = styledDocument.getCharacterElement(Math.max(i - 1, 0));
/*     */       } 
/* 249 */       if (element != StyledEditorKit.this.currentRun) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 258 */         StyledEditorKit.this.currentRun = element;
/* 259 */         StyledEditorKit.this.createInputAttributes(StyledEditorKit.this.currentRun, StyledEditorKit.this.getInputAttributes());
/*     */       } 
/*     */     }
/*     */     
/*     */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 264 */       Object object1 = param1PropertyChangeEvent.getNewValue();
/* 265 */       Object object2 = param1PropertyChangeEvent.getSource();
/*     */       
/* 267 */       if (object2 instanceof JTextComponent && object1 instanceof Document)
/*     */       {
/*     */         
/* 270 */         updateInputAttributes(0, 0, (JTextComponent)object2);
/*     */       }
/*     */     }
/*     */     
/*     */     public void caretUpdate(CaretEvent param1CaretEvent) {
/* 275 */       updateInputAttributes(param1CaretEvent.getDot(), param1CaretEvent.getMark(), (JTextComponent)param1CaretEvent
/* 276 */           .getSource());
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
/*     */   protected void createInputAttributes(Element paramElement, MutableAttributeSet paramMutableAttributeSet) {
/* 291 */     if (paramElement.getAttributes().getAttributeCount() > 0 || paramElement
/* 292 */       .getEndOffset() - paramElement.getStartOffset() > 1 || paramElement
/* 293 */       .getEndOffset() < paramElement.getDocument().getLength()) {
/* 294 */       paramMutableAttributeSet.removeAttributes(paramMutableAttributeSet);
/* 295 */       paramMutableAttributeSet.addAttributes(paramElement.getAttributes());
/* 296 */       paramMutableAttributeSet.removeAttribute(StyleConstants.ComponentAttribute);
/* 297 */       paramMutableAttributeSet.removeAttribute(StyleConstants.IconAttribute);
/* 298 */       paramMutableAttributeSet.removeAttribute("$ename");
/* 299 */       paramMutableAttributeSet.removeAttribute(StyleConstants.ComposedTextAttribute);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static class StyledViewFactory
/*     */     implements ViewFactory
/*     */   {
/*     */     public View create(Element param1Element) {
/* 308 */       String str = param1Element.getName();
/* 309 */       if (str != null) {
/* 310 */         if (str.equals("content"))
/* 311 */           return new LabelView(param1Element); 
/* 312 */         if (str.equals("paragraph"))
/* 313 */           return new ParagraphView(param1Element); 
/* 314 */         if (str.equals("section"))
/* 315 */           return new BoxView(param1Element, 1); 
/* 316 */         if (str.equals("component"))
/* 317 */           return new ComponentView(param1Element); 
/* 318 */         if (str.equals("icon")) {
/* 319 */           return new IconView(param1Element);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 324 */       return new LabelView(param1Element);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 331 */   private static final Action[] defaultActions = new Action[] { new FontFamilyAction("font-family-SansSerif", "SansSerif"), new FontFamilyAction("font-family-Monospaced", "Monospaced"), new FontFamilyAction("font-family-Serif", "Serif"), new FontSizeAction("font-size-8", 8), new FontSizeAction("font-size-10", 10), new FontSizeAction("font-size-12", 12), new FontSizeAction("font-size-14", 14), new FontSizeAction("font-size-16", 16), new FontSizeAction("font-size-18", 18), new FontSizeAction("font-size-24", 24), new FontSizeAction("font-size-36", 36), new FontSizeAction("font-size-48", 48), new AlignmentAction("left-justify", 0), new AlignmentAction("center-justify", 1), new AlignmentAction("right-justify", 2), new BoldAction(), new ItalicAction(), new StyledInsertBreakAction(), new UnderlineAction() };
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
/*     */   public static abstract class StyledTextAction
/*     */     extends TextAction
/*     */   {
/*     */     public StyledTextAction(String param1String) {
/* 386 */       super(param1String);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final JEditorPane getEditor(ActionEvent param1ActionEvent) {
/* 396 */       JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/* 397 */       if (jTextComponent instanceof JEditorPane) {
/* 398 */         return (JEditorPane)jTextComponent;
/*     */       }
/* 400 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final StyledDocument getStyledDocument(JEditorPane param1JEditorPane) {
/* 411 */       Document document = param1JEditorPane.getDocument();
/* 412 */       if (document instanceof StyledDocument) {
/* 413 */         return (StyledDocument)document;
/*     */       }
/* 415 */       throw new IllegalArgumentException("document must be StyledDocument");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final StyledEditorKit getStyledEditorKit(JEditorPane param1JEditorPane) {
/* 426 */       EditorKit editorKit = param1JEditorPane.getEditorKit();
/* 427 */       if (editorKit instanceof StyledEditorKit) {
/* 428 */         return (StyledEditorKit)editorKit;
/*     */       }
/* 430 */       throw new IllegalArgumentException("EditorKit must be StyledEditorKit");
/*     */     }
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
/*     */     protected final void setCharacterAttributes(JEditorPane param1JEditorPane, AttributeSet param1AttributeSet, boolean param1Boolean) {
/* 447 */       int i = param1JEditorPane.getSelectionStart();
/* 448 */       int j = param1JEditorPane.getSelectionEnd();
/* 449 */       if (i != j) {
/* 450 */         StyledDocument styledDocument = getStyledDocument(param1JEditorPane);
/* 451 */         styledDocument.setCharacterAttributes(i, j - i, param1AttributeSet, param1Boolean);
/*     */       } 
/* 453 */       StyledEditorKit styledEditorKit = getStyledEditorKit(param1JEditorPane);
/* 454 */       MutableAttributeSet mutableAttributeSet = styledEditorKit.getInputAttributes();
/* 455 */       if (param1Boolean) {
/* 456 */         mutableAttributeSet.removeAttributes(mutableAttributeSet);
/*     */       }
/* 458 */       mutableAttributeSet.addAttributes(param1AttributeSet);
/*     */     }
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
/*     */     protected final void setParagraphAttributes(JEditorPane param1JEditorPane, AttributeSet param1AttributeSet, boolean param1Boolean) {
/* 474 */       int i = param1JEditorPane.getSelectionStart();
/* 475 */       int j = param1JEditorPane.getSelectionEnd();
/* 476 */       StyledDocument styledDocument = getStyledDocument(param1JEditorPane);
/* 477 */       styledDocument.setParagraphAttributes(i, j - i, param1AttributeSet, param1Boolean);
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
/*     */   public static class FontFamilyAction
/*     */     extends StyledTextAction
/*     */   {
/*     */     private String family;
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
/*     */     public FontFamilyAction(String param1String1, String param1String2) {
/* 506 */       super(param1String1);
/* 507 */       this.family = param1String2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 516 */       JEditorPane jEditorPane = getEditor(param1ActionEvent);
/* 517 */       if (jEditorPane != null) {
/* 518 */         String str = this.family;
/* 519 */         if (param1ActionEvent != null && param1ActionEvent.getSource() == jEditorPane) {
/* 520 */           String str1 = param1ActionEvent.getActionCommand();
/* 521 */           if (str1 != null) {
/* 522 */             str = str1;
/*     */           }
/*     */         } 
/* 525 */         if (str != null) {
/* 526 */           SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
/* 527 */           StyleConstants.setFontFamily(simpleAttributeSet, str);
/* 528 */           setCharacterAttributes(jEditorPane, simpleAttributeSet, false);
/*     */         } else {
/* 530 */           UIManager.getLookAndFeel().provideErrorFeedback(jEditorPane);
/*     */         } 
/*     */       } 
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
/*     */   public static class FontSizeAction
/*     */     extends StyledTextAction
/*     */   {
/*     */     private int size;
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
/*     */     public FontSizeAction(String param1String, int param1Int) {
/* 562 */       super(param1String);
/* 563 */       this.size = param1Int;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 572 */       JEditorPane jEditorPane = getEditor(param1ActionEvent);
/* 573 */       if (jEditorPane != null) {
/* 574 */         int i = this.size;
/* 575 */         if (param1ActionEvent != null && param1ActionEvent.getSource() == jEditorPane) {
/* 576 */           String str = param1ActionEvent.getActionCommand();
/*     */           try {
/* 578 */             i = Integer.parseInt(str, 10);
/* 579 */           } catch (NumberFormatException numberFormatException) {}
/*     */         } 
/*     */         
/* 582 */         if (i != 0) {
/* 583 */           SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
/* 584 */           StyleConstants.setFontSize(simpleAttributeSet, i);
/* 585 */           setCharacterAttributes(jEditorPane, simpleAttributeSet, false);
/*     */         } else {
/* 587 */           UIManager.getLookAndFeel().provideErrorFeedback(jEditorPane);
/*     */         } 
/*     */       } 
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
/*     */   public static class ForegroundAction
/*     */     extends StyledTextAction
/*     */   {
/*     */     private Color fg;
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
/*     */     public ForegroundAction(String param1String, Color param1Color) {
/* 629 */       super(param1String);
/* 630 */       this.fg = param1Color;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 639 */       JEditorPane jEditorPane = getEditor(param1ActionEvent);
/* 640 */       if (jEditorPane != null) {
/* 641 */         Color color = this.fg;
/* 642 */         if (param1ActionEvent != null && param1ActionEvent.getSource() == jEditorPane) {
/* 643 */           String str = param1ActionEvent.getActionCommand();
/*     */           try {
/* 645 */             color = Color.decode(str);
/* 646 */           } catch (NumberFormatException numberFormatException) {}
/*     */         } 
/*     */         
/* 649 */         if (color != null) {
/* 650 */           SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
/* 651 */           StyleConstants.setForeground(simpleAttributeSet, color);
/* 652 */           setCharacterAttributes(jEditorPane, simpleAttributeSet, false);
/*     */         } else {
/* 654 */           UIManager.getLookAndFeel().provideErrorFeedback(jEditorPane);
/*     */         } 
/*     */       } 
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
/*     */   public static class AlignmentAction
/*     */     extends StyledTextAction
/*     */   {
/*     */     private int a;
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
/*     */     public AlignmentAction(String param1String, int param1Int) {
/* 695 */       super(param1String);
/* 696 */       this.a = param1Int;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 705 */       JEditorPane jEditorPane = getEditor(param1ActionEvent);
/* 706 */       if (jEditorPane != null) {
/* 707 */         int i = this.a;
/* 708 */         if (param1ActionEvent != null && param1ActionEvent.getSource() == jEditorPane) {
/* 709 */           String str = param1ActionEvent.getActionCommand();
/*     */           try {
/* 711 */             i = Integer.parseInt(str, 10);
/* 712 */           } catch (NumberFormatException numberFormatException) {}
/*     */         } 
/*     */         
/* 715 */         SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
/* 716 */         StyleConstants.setAlignment(simpleAttributeSet, i);
/* 717 */         setParagraphAttributes(jEditorPane, simpleAttributeSet, false);
/*     */       } 
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
/*     */   public static class BoldAction
/*     */     extends StyledTextAction
/*     */   {
/*     */     public BoldAction() {
/* 742 */       super("font-bold");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 751 */       JEditorPane jEditorPane = getEditor(param1ActionEvent);
/* 752 */       if (jEditorPane != null) {
/* 753 */         StyledEditorKit styledEditorKit = getStyledEditorKit(jEditorPane);
/* 754 */         MutableAttributeSet mutableAttributeSet = styledEditorKit.getInputAttributes();
/* 755 */         boolean bool = StyleConstants.isBold(mutableAttributeSet) ? false : true;
/* 756 */         SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
/* 757 */         StyleConstants.setBold(simpleAttributeSet, bool);
/* 758 */         setCharacterAttributes(jEditorPane, simpleAttributeSet, false);
/*     */       } 
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
/*     */   public static class ItalicAction
/*     */     extends StyledTextAction
/*     */   {
/*     */     public ItalicAction() {
/* 781 */       super("font-italic");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 790 */       JEditorPane jEditorPane = getEditor(param1ActionEvent);
/* 791 */       if (jEditorPane != null) {
/* 792 */         StyledEditorKit styledEditorKit = getStyledEditorKit(jEditorPane);
/* 793 */         MutableAttributeSet mutableAttributeSet = styledEditorKit.getInputAttributes();
/* 794 */         boolean bool = StyleConstants.isItalic(mutableAttributeSet) ? false : true;
/* 795 */         SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
/* 796 */         StyleConstants.setItalic(simpleAttributeSet, bool);
/* 797 */         setCharacterAttributes(jEditorPane, simpleAttributeSet, false);
/*     */       } 
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
/*     */   public static class UnderlineAction
/*     */     extends StyledTextAction
/*     */   {
/*     */     public UnderlineAction() {
/* 820 */       super("font-underline");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 829 */       JEditorPane jEditorPane = getEditor(param1ActionEvent);
/* 830 */       if (jEditorPane != null) {
/* 831 */         StyledEditorKit styledEditorKit = getStyledEditorKit(jEditorPane);
/* 832 */         MutableAttributeSet mutableAttributeSet = styledEditorKit.getInputAttributes();
/* 833 */         boolean bool = StyleConstants.isUnderline(mutableAttributeSet) ? false : true;
/* 834 */         SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
/* 835 */         StyleConstants.setUnderline(simpleAttributeSet, bool);
/* 836 */         setCharacterAttributes(jEditorPane, simpleAttributeSet, false);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class StyledInsertBreakAction
/*     */     extends StyledTextAction
/*     */   {
/*     */     private SimpleAttributeSet tempSet;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     StyledInsertBreakAction() {
/* 853 */       super("insert-break");
/*     */     }
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 857 */       JEditorPane jEditorPane = getEditor(param1ActionEvent);
/*     */       
/* 859 */       if (jEditorPane != null) {
/* 860 */         if (!jEditorPane.isEditable() || !jEditorPane.isEnabled()) {
/* 861 */           UIManager.getLookAndFeel().provideErrorFeedback(jEditorPane);
/*     */           return;
/*     */         } 
/* 864 */         StyledEditorKit styledEditorKit = getStyledEditorKit(jEditorPane);
/*     */         
/* 866 */         if (this.tempSet != null) {
/* 867 */           this.tempSet.removeAttributes(this.tempSet);
/*     */         } else {
/*     */           
/* 870 */           this.tempSet = new SimpleAttributeSet();
/*     */         } 
/* 872 */         this.tempSet.addAttributes(styledEditorKit.getInputAttributes());
/* 873 */         jEditorPane.replaceSelection("\n");
/*     */         
/* 875 */         MutableAttributeSet mutableAttributeSet = styledEditorKit.getInputAttributes();
/*     */         
/* 877 */         mutableAttributeSet.removeAttributes(mutableAttributeSet);
/* 878 */         mutableAttributeSet.addAttributes(this.tempSet);
/* 879 */         this.tempSet.removeAttributes(this.tempSet);
/*     */       }
/*     */       else {
/*     */         
/* 883 */         JTextComponent jTextComponent = getTextComponent(param1ActionEvent);
/*     */         
/* 885 */         if (jTextComponent != null) {
/* 886 */           if (!jTextComponent.isEditable() || !jTextComponent.isEnabled()) {
/* 887 */             UIManager.getLookAndFeel().provideErrorFeedback(jEditorPane);
/*     */             return;
/*     */           } 
/* 890 */           jTextComponent.replaceSelection("\n");
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/StyledEditorKit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */