/*      */ package javax.swing;
/*      */ 
/*      */ import java.awt.EventQueue;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.InputMethodEvent;
/*      */ import java.awt.im.InputContext;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.text.AttributedCharacterIterator;
/*      */ import java.text.DateFormat;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.Format;
/*      */ import java.text.NumberFormat;
/*      */ import java.text.ParseException;
/*      */ import javax.swing.event.DocumentEvent;
/*      */ import javax.swing.event.DocumentListener;
/*      */ import javax.swing.text.AbstractDocument;
/*      */ import javax.swing.text.DateFormatter;
/*      */ import javax.swing.text.DefaultFormatter;
/*      */ import javax.swing.text.DefaultFormatterFactory;
/*      */ import javax.swing.text.Document;
/*      */ import javax.swing.text.DocumentFilter;
/*      */ import javax.swing.text.InternationalFormatter;
/*      */ import javax.swing.text.JTextComponent;
/*      */ import javax.swing.text.NavigationFilter;
/*      */ import javax.swing.text.NumberFormatter;
/*      */ import javax.swing.text.TextAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JFormattedTextField
/*      */   extends JTextField
/*      */ {
/*      */   private static final String uiClassID = "FormattedTextFieldUI";
/*  181 */   private static final Action[] defaultActions = new Action[] { new CommitAction(), new CancelAction() };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int COMMIT = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int COMMIT_OR_REVERT = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int REVERT = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int PERSIST = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AbstractFormatterFactory factory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AbstractFormatter format;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object value;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean editValid;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int focusLostBehavior;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean edited;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DocumentListener documentListener;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object mask;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ActionMap textFormatterActionMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean composedTextExists = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FocusLostHandler focusLostHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JFormattedTextField() {
/*  277 */     enableEvents(4L);
/*  278 */     setFocusLostBehavior(1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JFormattedTextField(Object paramObject) {
/*  289 */     this();
/*  290 */     setValue(paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JFormattedTextField(Format paramFormat) {
/*  301 */     this();
/*  302 */     setFormatterFactory(getDefaultFormatterFactory(paramFormat));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JFormattedTextField(AbstractFormatter paramAbstractFormatter) {
/*  313 */     this(new DefaultFormatterFactory(paramAbstractFormatter));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JFormattedTextField(AbstractFormatterFactory paramAbstractFormatterFactory) {
/*  323 */     this();
/*  324 */     setFormatterFactory(paramAbstractFormatterFactory);
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
/*      */   public JFormattedTextField(AbstractFormatterFactory paramAbstractFormatterFactory, Object paramObject) {
/*  337 */     this(paramObject);
/*  338 */     setFormatterFactory(paramAbstractFormatterFactory);
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
/*      */   public void setFocusLostBehavior(int paramInt) {
/*  367 */     if (paramInt != 0 && paramInt != 1 && paramInt != 3 && paramInt != 2)
/*      */     {
/*  369 */       throw new IllegalArgumentException("setFocusLostBehavior must be one of: JFormattedTextField.COMMIT, JFormattedTextField.COMMIT_OR_REVERT, JFormattedTextField.PERSIST or JFormattedTextField.REVERT");
/*      */     }
/*  371 */     this.focusLostBehavior = paramInt;
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
/*      */   public int getFocusLostBehavior() {
/*  386 */     return this.focusLostBehavior;
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
/*      */   public void setFormatterFactory(AbstractFormatterFactory paramAbstractFormatterFactory) {
/*  416 */     AbstractFormatterFactory abstractFormatterFactory = this.factory;
/*      */     
/*  418 */     this.factory = paramAbstractFormatterFactory;
/*  419 */     firePropertyChange("formatterFactory", abstractFormatterFactory, paramAbstractFormatterFactory);
/*  420 */     setValue(getValue(), true, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AbstractFormatterFactory getFormatterFactory() {
/*  431 */     return this.factory;
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
/*      */   protected void setFormatter(AbstractFormatter paramAbstractFormatter) {
/*  456 */     AbstractFormatter abstractFormatter = this.format;
/*      */     
/*  458 */     if (abstractFormatter != null) {
/*  459 */       abstractFormatter.uninstall();
/*      */     }
/*  461 */     setEditValid(true);
/*  462 */     this.format = paramAbstractFormatter;
/*  463 */     if (paramAbstractFormatter != null) {
/*  464 */       paramAbstractFormatter.install(this);
/*      */     }
/*  466 */     setEdited(false);
/*  467 */     firePropertyChange("textFormatter", abstractFormatter, paramAbstractFormatter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AbstractFormatter getFormatter() {
/*  477 */     return this.format;
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
/*      */   public void setValue(Object paramObject) {
/*  498 */     if (paramObject != null && getFormatterFactory() == null) {
/*  499 */       setFormatterFactory(getDefaultFormatterFactory(paramObject));
/*      */     }
/*  501 */     setValue(paramObject, true, true);
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
/*      */   public Object getValue() {
/*  513 */     return this.value;
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
/*      */   public void commitEdit() throws ParseException {
/*  526 */     AbstractFormatter abstractFormatter = getFormatter();
/*      */     
/*  528 */     if (abstractFormatter != null) {
/*  529 */       setValue(abstractFormatter.stringToValue(getText()), false, true);
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
/*      */   private void setEditValid(boolean paramBoolean) {
/*  552 */     if (paramBoolean != this.editValid) {
/*  553 */       this.editValid = paramBoolean;
/*  554 */       firePropertyChange("editValid", Boolean.valueOf(!paramBoolean), 
/*  555 */           Boolean.valueOf(paramBoolean));
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
/*      */   public boolean isEditValid() {
/*  567 */     return this.editValid;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void invalidEdit() {
/*  576 */     UIManager.getLookAndFeel().provideErrorFeedback(this);
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
/*      */   protected void processInputMethodEvent(InputMethodEvent paramInputMethodEvent) {
/*  588 */     AttributedCharacterIterator attributedCharacterIterator = paramInputMethodEvent.getText();
/*  589 */     int i = paramInputMethodEvent.getCommittedCharacterCount();
/*      */ 
/*      */     
/*  592 */     if (attributedCharacterIterator != null) {
/*  593 */       int j = attributedCharacterIterator.getBeginIndex();
/*  594 */       int k = attributedCharacterIterator.getEndIndex();
/*  595 */       this.composedTextExists = (k - j > i);
/*      */     } else {
/*  597 */       this.composedTextExists = false;
/*      */     } 
/*      */     
/*  600 */     super.processInputMethodEvent(paramInputMethodEvent);
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
/*      */   protected void processFocusEvent(FocusEvent paramFocusEvent) {
/*  612 */     super.processFocusEvent(paramFocusEvent);
/*      */ 
/*      */     
/*  615 */     if (paramFocusEvent.isTemporary()) {
/*      */       return;
/*      */     }
/*      */     
/*  619 */     if (isEdited() && paramFocusEvent.getID() == 1005) {
/*  620 */       InputContext inputContext = getInputContext();
/*  621 */       if (this.focusLostHandler == null) {
/*  622 */         this.focusLostHandler = new FocusLostHandler();
/*      */       }
/*      */ 
/*      */       
/*  626 */       if (inputContext != null && this.composedTextExists) {
/*  627 */         inputContext.endComposition();
/*  628 */         EventQueue.invokeLater(this.focusLostHandler);
/*      */       } else {
/*  630 */         this.focusLostHandler.run();
/*      */       }
/*      */     
/*  633 */     } else if (!isEdited()) {
/*      */       
/*  635 */       setValue(getValue(), true, true);
/*      */     } 
/*      */   }
/*      */   
/*      */   private class FocusLostHandler
/*      */     implements Runnable, Serializable {
/*      */     private FocusLostHandler() {}
/*      */     
/*      */     public void run() {
/*  644 */       int i = JFormattedTextField.this.getFocusLostBehavior();
/*  645 */       if (i == 0 || i == 1) {
/*      */         
/*      */         try {
/*  648 */           JFormattedTextField.this.commitEdit();
/*      */           
/*  650 */           JFormattedTextField.this.setValue(JFormattedTextField.this
/*  651 */               .getValue(), true, true);
/*  652 */         } catch (ParseException parseException) {
/*  653 */           if (i == 1) {
/*  654 */             JFormattedTextField.this.setValue(JFormattedTextField.this
/*  655 */                 .getValue(), true, true);
/*      */           }
/*      */         }
/*      */       
/*  659 */       } else if (i == 2) {
/*  660 */         JFormattedTextField.this.setValue(JFormattedTextField.this
/*  661 */             .getValue(), true, true);
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
/*      */   public Action[] getActions() {
/*  676 */     return TextAction.augmentList(super.getActions(), defaultActions);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUIClassID() {
/*  686 */     return "FormattedTextFieldUI";
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
/*      */   public void setDocument(Document paramDocument) {
/*  703 */     if (this.documentListener != null && getDocument() != null) {
/*  704 */       getDocument().removeDocumentListener(this.documentListener);
/*      */     }
/*  706 */     super.setDocument(paramDocument);
/*  707 */     if (this.documentListener == null) {
/*  708 */       this.documentListener = new DocumentHandler();
/*      */     }
/*  710 */     paramDocument.addDocumentListener(this.documentListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*  720 */     paramObjectOutputStream.defaultWriteObject();
/*  721 */     if (getUIClassID().equals("FormattedTextFieldUI")) {
/*  722 */       byte b = JComponent.getWriteObjCounter(this);
/*  723 */       b = (byte)(b - 1); JComponent.setWriteObjCounter(this, b);
/*  724 */       if (b == 0 && this.ui != null) {
/*  725 */         this.ui.installUI(this);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setFormatterActions(Action[] paramArrayOfAction) {
/*  735 */     if (paramArrayOfAction == null) {
/*  736 */       if (this.textFormatterActionMap != null) {
/*  737 */         this.textFormatterActionMap.clear();
/*      */       }
/*      */     } else {
/*      */       
/*  741 */       if (this.textFormatterActionMap == null) {
/*  742 */         ActionMap actionMap = getActionMap();
/*      */         
/*  744 */         this.textFormatterActionMap = new ActionMap();
/*  745 */         while (actionMap != null) {
/*  746 */           ActionMap actionMap1 = actionMap.getParent();
/*      */           
/*  748 */           if (actionMap1 instanceof javax.swing.plaf.UIResource || actionMap1 == null) {
/*  749 */             actionMap.setParent(this.textFormatterActionMap);
/*  750 */             this.textFormatterActionMap.setParent(actionMap1);
/*      */             break;
/*      */           } 
/*  753 */           actionMap = actionMap1;
/*      */         } 
/*      */       } 
/*  756 */       for (int i = paramArrayOfAction.length - 1; i >= 0; 
/*  757 */         i--) {
/*  758 */         Object object = paramArrayOfAction[i].getValue("Name");
/*      */         
/*  760 */         if (object != null) {
/*  761 */           this.textFormatterActionMap.put(object, paramArrayOfAction[i]);
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
/*      */   private void setValue(Object paramObject, boolean paramBoolean1, boolean paramBoolean2) {
/*  774 */     Object object = this.value;
/*      */     
/*  776 */     this.value = paramObject;
/*      */     
/*  778 */     if (paramBoolean1) {
/*  779 */       AbstractFormatter abstractFormatter; AbstractFormatterFactory abstractFormatterFactory = getFormatterFactory();
/*      */ 
/*      */       
/*  782 */       if (abstractFormatterFactory != null) {
/*  783 */         abstractFormatter = abstractFormatterFactory.getFormatter(this);
/*      */       } else {
/*      */         
/*  786 */         abstractFormatter = null;
/*      */       } 
/*  788 */       setFormatter(abstractFormatter);
/*      */     }
/*      */     else {
/*      */       
/*  792 */       setEditValid(true);
/*      */     } 
/*      */     
/*  795 */     setEdited(false);
/*      */     
/*  797 */     if (paramBoolean2) {
/*  798 */       firePropertyChange("value", object, paramObject);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setEdited(boolean paramBoolean) {
/*  806 */     this.edited = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isEdited() {
/*  813 */     return this.edited;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AbstractFormatterFactory getDefaultFormatterFactory(Object paramObject) {
/*  821 */     if (paramObject instanceof DateFormat) {
/*  822 */       return new DefaultFormatterFactory(new DateFormatter((DateFormat)paramObject));
/*      */     }
/*      */     
/*  825 */     if (paramObject instanceof NumberFormat) {
/*  826 */       return new DefaultFormatterFactory(new NumberFormatter((NumberFormat)paramObject));
/*      */     }
/*      */     
/*  829 */     if (paramObject instanceof Format) {
/*  830 */       return new DefaultFormatterFactory(new InternationalFormatter((Format)paramObject));
/*      */     }
/*      */     
/*  833 */     if (paramObject instanceof java.util.Date) {
/*  834 */       return new DefaultFormatterFactory(new DateFormatter());
/*      */     }
/*  836 */     if (paramObject instanceof Number) {
/*  837 */       NumberFormatter numberFormatter1 = new NumberFormatter();
/*  838 */       numberFormatter1.setValueClass(paramObject.getClass());
/*  839 */       NumberFormatter numberFormatter2 = new NumberFormatter(new DecimalFormat("#.#"));
/*      */       
/*  841 */       numberFormatter2.setValueClass(paramObject.getClass());
/*      */       
/*  843 */       return new DefaultFormatterFactory(numberFormatter1, numberFormatter1, numberFormatter2);
/*      */     } 
/*      */     
/*  846 */     return new DefaultFormatterFactory(new DefaultFormatter());
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
/*      */   public static abstract class AbstractFormatterFactory
/*      */   {
/*      */     public abstract JFormattedTextField.AbstractFormatter getFormatter(JFormattedTextField param1JFormattedTextField);
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
/*      */   public static abstract class AbstractFormatter
/*      */     implements Serializable
/*      */   {
/*      */     private JFormattedTextField ftf;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void install(JFormattedTextField param1JFormattedTextField) {
/*  942 */       if (this.ftf != null) {
/*  943 */         uninstall();
/*      */       }
/*  945 */       this.ftf = param1JFormattedTextField;
/*  946 */       if (param1JFormattedTextField != null) {
/*      */         try {
/*  948 */           param1JFormattedTextField.setText(valueToString(param1JFormattedTextField.getValue()));
/*  949 */         } catch (ParseException parseException) {
/*  950 */           param1JFormattedTextField.setText("");
/*  951 */           setEditValid(false);
/*      */         } 
/*  953 */         installDocumentFilter(getDocumentFilter());
/*  954 */         param1JFormattedTextField.setNavigationFilter(getNavigationFilter());
/*  955 */         param1JFormattedTextField.setFormatterActions(getActions());
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
/*      */     public void uninstall() {
/*  967 */       if (this.ftf != null) {
/*  968 */         installDocumentFilter(null);
/*  969 */         this.ftf.setNavigationFilter((NavigationFilter)null);
/*  970 */         this.ftf.setFormatterActions((Action[])null);
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
/*      */     public abstract Object stringToValue(String param1String) throws ParseException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract String valueToString(Object param1Object) throws ParseException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected JFormattedTextField getFormattedTextField() {
/* 1002 */       return this.ftf;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void invalidEdit() {
/* 1010 */       JFormattedTextField jFormattedTextField = getFormattedTextField();
/*      */       
/* 1012 */       if (jFormattedTextField != null) {
/* 1013 */         jFormattedTextField.invalidEdit();
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
/*      */     protected void setEditValid(boolean param1Boolean) {
/* 1026 */       JFormattedTextField jFormattedTextField = getFormattedTextField();
/*      */       
/* 1028 */       if (jFormattedTextField != null) {
/* 1029 */         jFormattedTextField.setEditValid(param1Boolean);
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
/*      */     protected Action[] getActions() {
/* 1041 */       return null;
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
/*      */     protected DocumentFilter getDocumentFilter() {
/* 1053 */       return null;
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
/*      */     protected NavigationFilter getNavigationFilter() {
/* 1065 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Object clone() throws CloneNotSupportedException {
/* 1075 */       AbstractFormatter abstractFormatter = (AbstractFormatter)super.clone();
/*      */       
/* 1077 */       abstractFormatter.ftf = null;
/* 1078 */       return abstractFormatter;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void installDocumentFilter(DocumentFilter param1DocumentFilter) {
/* 1088 */       JFormattedTextField jFormattedTextField = getFormattedTextField();
/*      */       
/* 1090 */       if (jFormattedTextField != null) {
/* 1091 */         Document document = jFormattedTextField.getDocument();
/*      */         
/* 1093 */         if (document instanceof AbstractDocument) {
/* 1094 */           ((AbstractDocument)document).setDocumentFilter(param1DocumentFilter);
/*      */         }
/* 1096 */         document.putProperty(DocumentFilter.class, null);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class CommitAction
/*      */     extends JTextField.NotifyAction
/*      */   {
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1110 */       JTextComponent jTextComponent = getFocusedComponent();
/*      */       
/* 1112 */       if (jTextComponent instanceof JFormattedTextField) {
/*      */         
/*      */         try {
/* 1115 */           ((JFormattedTextField)jTextComponent).commitEdit();
/* 1116 */         } catch (ParseException parseException) {
/* 1117 */           ((JFormattedTextField)jTextComponent).invalidEdit();
/*      */           
/*      */           return;
/*      */         } 
/*      */       }
/*      */       
/* 1123 */       super.actionPerformed(param1ActionEvent);
/*      */     }
/*      */     
/*      */     public boolean isEnabled() {
/* 1127 */       JTextComponent jTextComponent = getFocusedComponent();
/* 1128 */       if (jTextComponent instanceof JFormattedTextField) {
/* 1129 */         JFormattedTextField jFormattedTextField = (JFormattedTextField)jTextComponent;
/* 1130 */         if (!jFormattedTextField.isEdited()) {
/* 1131 */           return false;
/*      */         }
/* 1133 */         return true;
/*      */       } 
/* 1135 */       return super.isEnabled();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class CancelAction
/*      */     extends TextAction
/*      */   {
/*      */     public CancelAction() {
/* 1148 */       super("reset-field-edit");
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1152 */       JTextComponent jTextComponent = getFocusedComponent();
/*      */       
/* 1154 */       if (jTextComponent instanceof JFormattedTextField) {
/* 1155 */         JFormattedTextField jFormattedTextField = (JFormattedTextField)jTextComponent;
/* 1156 */         jFormattedTextField.setValue(jFormattedTextField.getValue());
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean isEnabled() {
/* 1161 */       JTextComponent jTextComponent = getFocusedComponent();
/* 1162 */       if (jTextComponent instanceof JFormattedTextField) {
/* 1163 */         JFormattedTextField jFormattedTextField = (JFormattedTextField)jTextComponent;
/* 1164 */         if (!jFormattedTextField.isEdited()) {
/* 1165 */           return false;
/*      */         }
/* 1167 */         return true;
/*      */       } 
/* 1169 */       return super.isEnabled();
/*      */     }
/*      */   }
/*      */   
/*      */   private class DocumentHandler
/*      */     implements DocumentListener, Serializable
/*      */   {
/*      */     private DocumentHandler() {}
/*      */     
/*      */     public void insertUpdate(DocumentEvent param1DocumentEvent) {
/* 1179 */       JFormattedTextField.this.setEdited(true);
/*      */     }
/*      */     public void removeUpdate(DocumentEvent param1DocumentEvent) {
/* 1182 */       JFormattedTextField.this.setEdited(true);
/*      */     }
/*      */     
/*      */     public void changedUpdate(DocumentEvent param1DocumentEvent) {}
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JFormattedTextField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */