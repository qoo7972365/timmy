/*      */ package javax.swing.plaf.basic;
/*      */ 
/*      */ import java.awt.AWTEvent;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.FocusTraversalPolicy;
/*      */ import java.awt.Insets;
/*      */ import java.awt.KeyboardFocusManager;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.MouseListener;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.text.AttributedCharacterIterator;
/*      */ import java.text.DateFormat;
/*      */ import java.text.Format;
/*      */ import java.text.ParseException;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Map;
/*      */ import javax.swing.AbstractAction;
/*      */ import javax.swing.ButtonModel;
/*      */ import javax.swing.InputMap;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JFormattedTextField;
/*      */ import javax.swing.JSpinner;
/*      */ import javax.swing.LookAndFeel;
/*      */ import javax.swing.SpinnerDateModel;
/*      */ import javax.swing.SpinnerModel;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.Timer;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.border.Border;
/*      */ import javax.swing.border.CompoundBorder;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ChangeListener;
/*      */ import javax.swing.plaf.ComponentUI;
/*      */ import javax.swing.plaf.SpinnerUI;
/*      */ import javax.swing.plaf.UIResource;
/*      */ import javax.swing.text.InternationalFormatter;
/*      */ import sun.swing.DefaultLookup;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BasicSpinnerUI
/*      */   extends SpinnerUI
/*      */ {
/*      */   protected JSpinner spinner;
/*      */   private Handler handler;
/*   72 */   private static final ArrowButtonHandler nextButtonHandler = new ArrowButtonHandler("increment", true);
/*   73 */   private static final ArrowButtonHandler previousButtonHandler = new ArrowButtonHandler("decrement", false);
/*      */ 
/*      */ 
/*      */   
/*      */   private PropertyChangeListener propertyChangeListener;
/*      */ 
/*      */ 
/*      */   
/*   81 */   private static final Dimension zeroSize = new Dimension(0, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ComponentUI createUI(JComponent paramJComponent) {
/*   93 */     return new BasicSpinnerUI();
/*      */   }
/*      */ 
/*      */   
/*      */   private void maybeAdd(Component paramComponent, String paramString) {
/*   98 */     if (paramComponent != null) {
/*   99 */       this.spinner.add(paramComponent, paramString);
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
/*      */   public void installUI(JComponent paramJComponent) {
/*  117 */     this.spinner = (JSpinner)paramJComponent;
/*  118 */     installDefaults();
/*  119 */     installListeners();
/*  120 */     maybeAdd(createNextButton(), "Next");
/*  121 */     maybeAdd(createPreviousButton(), "Previous");
/*  122 */     maybeAdd(createEditor(), "Editor");
/*  123 */     updateEnabledState();
/*  124 */     installKeyboardActions();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void uninstallUI(JComponent paramJComponent) {
/*  135 */     uninstallDefaults();
/*  136 */     uninstallListeners();
/*  137 */     this.spinner = null;
/*  138 */     paramJComponent.removeAll();
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
/*      */   protected void installListeners() {
/*  153 */     this.propertyChangeListener = createPropertyChangeListener();
/*  154 */     this.spinner.addPropertyChangeListener(this.propertyChangeListener);
/*  155 */     if (DefaultLookup.getBoolean(this.spinner, this, "Spinner.disableOnBoundaryValues", false))
/*      */     {
/*  157 */       this.spinner.addChangeListener(getHandler());
/*      */     }
/*  159 */     JComponent jComponent = this.spinner.getEditor();
/*  160 */     if (jComponent != null && jComponent instanceof JSpinner.DefaultEditor) {
/*  161 */       JFormattedTextField jFormattedTextField = ((JSpinner.DefaultEditor)jComponent).getTextField();
/*  162 */       if (jFormattedTextField != null) {
/*  163 */         jFormattedTextField.addFocusListener(nextButtonHandler);
/*  164 */         jFormattedTextField.addFocusListener(previousButtonHandler);
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
/*      */   protected void uninstallListeners() {
/*  179 */     this.spinner.removePropertyChangeListener(this.propertyChangeListener);
/*  180 */     this.spinner.removeChangeListener(this.handler);
/*  181 */     JComponent jComponent = this.spinner.getEditor();
/*  182 */     removeEditorBorderListener(jComponent);
/*  183 */     if (jComponent instanceof JSpinner.DefaultEditor) {
/*  184 */       JFormattedTextField jFormattedTextField = ((JSpinner.DefaultEditor)jComponent).getTextField();
/*  185 */       if (jFormattedTextField != null) {
/*  186 */         jFormattedTextField.removeFocusListener(nextButtonHandler);
/*  187 */         jFormattedTextField.removeFocusListener(previousButtonHandler);
/*      */       } 
/*      */     } 
/*  190 */     this.propertyChangeListener = null;
/*  191 */     this.handler = null;
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
/*      */   protected void installDefaults() {
/*  209 */     this.spinner.setLayout(createLayout());
/*  210 */     LookAndFeel.installBorder(this.spinner, "Spinner.border");
/*  211 */     LookAndFeel.installColorsAndFont(this.spinner, "Spinner.background", "Spinner.foreground", "Spinner.font");
/*  212 */     LookAndFeel.installProperty(this.spinner, "opaque", Boolean.TRUE);
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
/*      */   protected void uninstallDefaults() {
/*  224 */     this.spinner.setLayout((LayoutManager)null);
/*      */   }
/*      */ 
/*      */   
/*      */   private Handler getHandler() {
/*  229 */     if (this.handler == null) {
/*  230 */       this.handler = new Handler();
/*      */     }
/*  232 */     return this.handler;
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
/*      */   protected void installNextButtonListeners(Component paramComponent) {
/*  246 */     installButtonListeners(paramComponent, nextButtonHandler);
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
/*      */   protected void installPreviousButtonListeners(Component paramComponent) {
/*  259 */     installButtonListeners(paramComponent, previousButtonHandler);
/*      */   }
/*      */ 
/*      */   
/*      */   private void installButtonListeners(Component paramComponent, ArrowButtonHandler paramArrowButtonHandler) {
/*  264 */     if (paramComponent instanceof JButton) {
/*  265 */       ((JButton)paramComponent).addActionListener(paramArrowButtonHandler);
/*      */     }
/*  267 */     paramComponent.addMouseListener(paramArrowButtonHandler);
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
/*      */   protected LayoutManager createLayout() {
/*  284 */     return getHandler();
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
/*      */   protected PropertyChangeListener createPropertyChangeListener() {
/*  300 */     return getHandler();
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
/*      */   protected Component createPreviousButton() {
/*  317 */     Component component = createArrowButton(5);
/*  318 */     component.setName("Spinner.previousButton");
/*  319 */     installPreviousButtonListeners(component);
/*  320 */     return component;
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
/*      */   protected Component createNextButton() {
/*  337 */     Component component = createArrowButton(1);
/*  338 */     component.setName("Spinner.nextButton");
/*  339 */     installNextButtonListeners(component);
/*  340 */     return component;
/*      */   }
/*      */   
/*      */   private Component createArrowButton(int paramInt) {
/*  344 */     BasicArrowButton basicArrowButton = new BasicArrowButton(paramInt);
/*  345 */     Border border = UIManager.getBorder("Spinner.arrowButtonBorder");
/*  346 */     if (border instanceof UIResource) {
/*      */ 
/*      */       
/*  349 */       basicArrowButton.setBorder(new CompoundBorder(border, null));
/*      */     } else {
/*  351 */       basicArrowButton.setBorder(border);
/*      */     } 
/*  353 */     basicArrowButton.setInheritsPopupMenu(true);
/*  354 */     return basicArrowButton;
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
/*      */   protected JComponent createEditor() {
/*  382 */     JComponent jComponent = this.spinner.getEditor();
/*  383 */     maybeRemoveEditorBorder(jComponent);
/*  384 */     installEditorBorderListener(jComponent);
/*  385 */     jComponent.setInheritsPopupMenu(true);
/*  386 */     updateEditorAlignment(jComponent);
/*  387 */     return jComponent;
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
/*      */   protected void replaceEditor(JComponent paramJComponent1, JComponent paramJComponent2) {
/*  407 */     this.spinner.remove(paramJComponent1);
/*  408 */     maybeRemoveEditorBorder(paramJComponent2);
/*  409 */     installEditorBorderListener(paramJComponent2);
/*  410 */     paramJComponent2.setInheritsPopupMenu(true);
/*  411 */     this.spinner.add(paramJComponent2, "Editor");
/*      */   }
/*      */   
/*      */   private void updateEditorAlignment(JComponent paramJComponent) {
/*  415 */     if (paramJComponent instanceof JSpinner.DefaultEditor) {
/*      */       
/*  417 */       int i = UIManager.getInt("Spinner.editorAlignment");
/*  418 */       JFormattedTextField jFormattedTextField = ((JSpinner.DefaultEditor)paramJComponent).getTextField();
/*  419 */       jFormattedTextField.setHorizontalAlignment(i);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void maybeRemoveEditorBorder(JComponent paramJComponent) {
/*  428 */     if (!UIManager.getBoolean("Spinner.editorBorderPainted")) {
/*  429 */       if (paramJComponent instanceof javax.swing.JPanel && paramJComponent
/*  430 */         .getBorder() == null && paramJComponent
/*  431 */         .getComponentCount() > 0)
/*      */       {
/*  433 */         paramJComponent = (JComponent)paramJComponent.getComponent(0);
/*      */       }
/*      */       
/*  436 */       if (paramJComponent != null && paramJComponent.getBorder() instanceof UIResource) {
/*  437 */         paramJComponent.setBorder((Border)null);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void installEditorBorderListener(JComponent paramJComponent) {
/*  447 */     if (!UIManager.getBoolean("Spinner.editorBorderPainted")) {
/*  448 */       if (paramJComponent instanceof javax.swing.JPanel && paramJComponent
/*  449 */         .getBorder() == null && paramJComponent
/*  450 */         .getComponentCount() > 0)
/*      */       {
/*  452 */         paramJComponent = (JComponent)paramJComponent.getComponent(0);
/*      */       }
/*  454 */       if (paramJComponent != null && (paramJComponent
/*  455 */         .getBorder() == null || paramJComponent
/*  456 */         .getBorder() instanceof UIResource)) {
/*  457 */         paramJComponent.addPropertyChangeListener(getHandler());
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void removeEditorBorderListener(JComponent paramJComponent) {
/*  463 */     if (!UIManager.getBoolean("Spinner.editorBorderPainted")) {
/*  464 */       if (paramJComponent instanceof javax.swing.JPanel && paramJComponent
/*  465 */         .getComponentCount() > 0)
/*      */       {
/*  467 */         paramJComponent = (JComponent)paramJComponent.getComponent(0);
/*      */       }
/*  469 */       if (paramJComponent != null) {
/*  470 */         paramJComponent.removePropertyChangeListener(getHandler());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateEnabledState() {
/*  481 */     updateEnabledState(this.spinner, this.spinner.isEnabled());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateEnabledState(Container paramContainer, boolean paramBoolean) {
/*  490 */     for (int i = paramContainer.getComponentCount() - 1; i >= 0; i--) {
/*  491 */       Component component = paramContainer.getComponent(i);
/*      */       
/*  493 */       if (DefaultLookup.getBoolean(this.spinner, this, "Spinner.disableOnBoundaryValues", false)) {
/*      */         
/*  495 */         SpinnerModel spinnerModel = this.spinner.getModel();
/*  496 */         if (component.getName() == "Spinner.nextButton" && spinnerModel
/*  497 */           .getNextValue() == null) {
/*  498 */           component.setEnabled(false);
/*      */         }
/*  500 */         else if (component.getName() == "Spinner.previousButton" && spinnerModel
/*  501 */           .getPreviousValue() == null) {
/*  502 */           component.setEnabled(false);
/*      */         } else {
/*      */           
/*  505 */           component.setEnabled(paramBoolean);
/*      */         } 
/*      */       } else {
/*      */         
/*  509 */         component.setEnabled(paramBoolean);
/*      */       } 
/*  511 */       if (component instanceof Container) {
/*  512 */         updateEnabledState((Container)component, paramBoolean);
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
/*      */   protected void installKeyboardActions() {
/*  524 */     InputMap inputMap = getInputMap(1);
/*      */ 
/*      */     
/*  527 */     SwingUtilities.replaceUIInputMap(this.spinner, 1, inputMap);
/*      */ 
/*      */ 
/*      */     
/*  531 */     LazyActionMap.installLazyActionMap(this.spinner, BasicSpinnerUI.class, "Spinner.actionMap");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private InputMap getInputMap(int paramInt) {
/*  539 */     if (paramInt == 1) {
/*  540 */       return (InputMap)DefaultLookup.get(this.spinner, this, "Spinner.ancestorInputMap");
/*      */     }
/*      */     
/*  543 */     return null;
/*      */   }
/*      */   
/*      */   static void loadActionMap(LazyActionMap paramLazyActionMap) {
/*  547 */     paramLazyActionMap.put("increment", nextButtonHandler);
/*  548 */     paramLazyActionMap.put("decrement", previousButtonHandler);
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
/*      */   public int getBaseline(JComponent paramJComponent, int paramInt1, int paramInt2) {
/*  560 */     super.getBaseline(paramJComponent, paramInt1, paramInt2);
/*  561 */     JComponent jComponent = this.spinner.getEditor();
/*  562 */     Insets insets = this.spinner.getInsets();
/*  563 */     paramInt1 = paramInt1 - insets.left - insets.right;
/*  564 */     paramInt2 = paramInt2 - insets.top - insets.bottom;
/*  565 */     if (paramInt1 >= 0 && paramInt2 >= 0) {
/*  566 */       int i = jComponent.getBaseline(paramInt1, paramInt2);
/*  567 */       if (i >= 0) {
/*  568 */         return insets.top + i;
/*      */       }
/*      */     } 
/*  571 */     return -1;
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
/*      */   public Component.BaselineResizeBehavior getBaselineResizeBehavior(JComponent paramJComponent) {
/*  584 */     super.getBaselineResizeBehavior(paramJComponent);
/*  585 */     return this.spinner.getEditor().getBaselineResizeBehavior();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class ArrowButtonHandler
/*      */     extends AbstractAction
/*      */     implements FocusListener, MouseListener, UIResource
/*      */   {
/*      */     final Timer autoRepeatTimer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean isNext;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  608 */     JSpinner spinner = null;
/*  609 */     JButton arrowButton = null;
/*      */     
/*      */     ArrowButtonHandler(String param1String, boolean param1Boolean) {
/*  612 */       super(param1String);
/*  613 */       this.isNext = param1Boolean;
/*  614 */       this.autoRepeatTimer = new Timer(60, this);
/*  615 */       this.autoRepeatTimer.setInitialDelay(300);
/*      */     }
/*      */     
/*      */     private JSpinner eventToSpinner(AWTEvent param1AWTEvent) {
/*  619 */       Object object = param1AWTEvent.getSource();
/*  620 */       while (object instanceof Component && !(object instanceof JSpinner)) {
/*  621 */         object = ((Component)object).getParent();
/*      */       }
/*  623 */       return (object instanceof JSpinner) ? (JSpinner)object : null;
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/*  627 */       JSpinner jSpinner = this.spinner;
/*      */       
/*  629 */       if (!(param1ActionEvent.getSource() instanceof Timer)) {
/*      */         
/*  631 */         jSpinner = eventToSpinner(param1ActionEvent);
/*  632 */         if (param1ActionEvent.getSource() instanceof JButton) {
/*  633 */           this.arrowButton = (JButton)param1ActionEvent.getSource();
/*      */         }
/*      */       }
/*  636 */       else if (this.arrowButton != null && !this.arrowButton.getModel().isPressed() && this.autoRepeatTimer
/*  637 */         .isRunning()) {
/*  638 */         this.autoRepeatTimer.stop();
/*  639 */         jSpinner = null;
/*  640 */         this.arrowButton = null;
/*      */       } 
/*      */       
/*  643 */       if (jSpinner != null) {
/*      */         try {
/*  645 */           int i = getCalendarField(jSpinner);
/*  646 */           jSpinner.commitEdit();
/*  647 */           if (i != -1) {
/*  648 */             ((SpinnerDateModel)jSpinner.getModel())
/*  649 */               .setCalendarField(i);
/*      */           }
/*      */           
/*  652 */           Object object = this.isNext ? jSpinner.getNextValue() : jSpinner.getPreviousValue();
/*  653 */           if (object != null) {
/*  654 */             jSpinner.setValue(object);
/*  655 */             select(jSpinner);
/*      */           } 
/*  657 */         } catch (IllegalArgumentException illegalArgumentException) {
/*  658 */           UIManager.getLookAndFeel().provideErrorFeedback(jSpinner);
/*  659 */         } catch (ParseException parseException) {
/*  660 */           UIManager.getLookAndFeel().provideErrorFeedback(jSpinner);
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void select(JSpinner param1JSpinner) {
/*  670 */       JComponent jComponent = param1JSpinner.getEditor();
/*      */       
/*  672 */       if (jComponent instanceof JSpinner.DateEditor) {
/*  673 */         JSpinner.DateEditor dateEditor = (JSpinner.DateEditor)jComponent;
/*  674 */         JFormattedTextField jFormattedTextField = dateEditor.getTextField();
/*  675 */         SimpleDateFormat simpleDateFormat = dateEditor.getFormat();
/*      */         
/*      */         Object object;
/*  678 */         if (simpleDateFormat != null && (object = param1JSpinner.getValue()) != null) {
/*  679 */           SpinnerDateModel spinnerDateModel = dateEditor.getModel();
/*  680 */           DateFormat.Field field = DateFormat.Field.ofCalendarField(spinnerDateModel
/*  681 */               .getCalendarField());
/*      */           
/*  683 */           if (field != null) {
/*      */             
/*      */             try {
/*  686 */               AttributedCharacterIterator attributedCharacterIterator = simpleDateFormat.formatToCharacterIterator(object);
/*  687 */               if (!select(jFormattedTextField, attributedCharacterIterator, field) && field == DateFormat.Field.HOUR0)
/*      */               {
/*  689 */                 select(jFormattedTextField, attributedCharacterIterator, DateFormat.Field.HOUR1);
/*      */               }
/*      */             }
/*  692 */             catch (IllegalArgumentException illegalArgumentException) {}
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean select(JFormattedTextField param1JFormattedTextField, AttributedCharacterIterator param1AttributedCharacterIterator, DateFormat.Field param1Field) {
/*  705 */       int i = param1JFormattedTextField.getDocument().getLength();
/*      */       
/*  707 */       param1AttributedCharacterIterator.first();
/*      */       while (true) {
/*  709 */         Map<AttributedCharacterIterator.Attribute, Object> map = param1AttributedCharacterIterator.getAttributes();
/*      */         
/*  711 */         if (map != null && map.containsKey(param1Field)) {
/*  712 */           int j = param1AttributedCharacterIterator.getRunStart(param1Field);
/*  713 */           int k = param1AttributedCharacterIterator.getRunLimit(param1Field);
/*      */           
/*  715 */           if (j != -1 && k != -1 && j <= i && k <= i)
/*      */           {
/*  717 */             param1JFormattedTextField.select(j, k);
/*      */           }
/*  719 */           return true;
/*      */         } 
/*  721 */         if (param1AttributedCharacterIterator.next() == Character.MAX_VALUE) {
/*  722 */           return false;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int getCalendarField(JSpinner param1JSpinner) {
/*  731 */       JComponent jComponent = param1JSpinner.getEditor();
/*      */       
/*  733 */       if (jComponent instanceof JSpinner.DateEditor) {
/*  734 */         JSpinner.DateEditor dateEditor = (JSpinner.DateEditor)jComponent;
/*  735 */         JFormattedTextField jFormattedTextField = dateEditor.getTextField();
/*  736 */         int i = jFormattedTextField.getSelectionStart();
/*      */         
/*  738 */         JFormattedTextField.AbstractFormatter abstractFormatter = jFormattedTextField.getFormatter();
/*      */         
/*  740 */         if (abstractFormatter instanceof InternationalFormatter) {
/*      */           
/*  742 */           Format.Field[] arrayOfField = ((InternationalFormatter)abstractFormatter).getFields(i);
/*      */           
/*  744 */           for (byte b = 0; b < arrayOfField.length; b++) {
/*  745 */             if (arrayOfField[b] instanceof DateFormat.Field) {
/*      */               int j;
/*      */               
/*  748 */               if (arrayOfField[b] == DateFormat.Field.HOUR1) {
/*  749 */                 j = 10;
/*      */               }
/*      */               else {
/*      */                 
/*  753 */                 j = ((DateFormat.Field)arrayOfField[b]).getCalendarField();
/*      */               } 
/*  755 */               if (j != -1) {
/*  756 */                 return j;
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  762 */       return -1;
/*      */     }
/*      */     
/*      */     public void mousePressed(MouseEvent param1MouseEvent) {
/*  766 */       if (SwingUtilities.isLeftMouseButton(param1MouseEvent) && param1MouseEvent.getComponent().isEnabled()) {
/*  767 */         this.spinner = eventToSpinner(param1MouseEvent);
/*  768 */         this.autoRepeatTimer.start();
/*      */         
/*  770 */         focusSpinnerIfNecessary();
/*      */       } 
/*      */     }
/*      */     
/*      */     public void mouseReleased(MouseEvent param1MouseEvent) {
/*  775 */       this.autoRepeatTimer.stop();
/*  776 */       this.arrowButton = null;
/*  777 */       this.spinner = null;
/*      */     }
/*      */ 
/*      */     
/*      */     public void mouseClicked(MouseEvent param1MouseEvent) {}
/*      */     
/*      */     public void mouseEntered(MouseEvent param1MouseEvent) {
/*  784 */       if (this.spinner != null && !this.autoRepeatTimer.isRunning() && this.spinner == eventToSpinner(param1MouseEvent)) {
/*  785 */         this.autoRepeatTimer.start();
/*      */       }
/*      */     }
/*      */     
/*      */     public void mouseExited(MouseEvent param1MouseEvent) {
/*  790 */       if (this.autoRepeatTimer.isRunning()) {
/*  791 */         this.autoRepeatTimer.stop();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void focusSpinnerIfNecessary() {
/*  801 */       Component component = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
/*  802 */       if (this.spinner.isRequestFocusEnabled() && (component == null || 
/*      */         
/*  804 */         !SwingUtilities.isDescendingFrom(component, this.spinner))) {
/*  805 */         Container container = this.spinner;
/*      */         
/*  807 */         if (!container.isFocusCycleRoot()) {
/*  808 */           container = container.getFocusCycleRootAncestor();
/*      */         }
/*  810 */         if (container != null) {
/*  811 */           FocusTraversalPolicy focusTraversalPolicy = container.getFocusTraversalPolicy();
/*  812 */           Component component1 = focusTraversalPolicy.getComponentAfter(container, this.spinner);
/*      */           
/*  814 */           if (component1 != null && SwingUtilities.isDescendingFrom(component1, this.spinner))
/*      */           {
/*  816 */             component1.requestFocus();
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void focusGained(FocusEvent param1FocusEvent) {}
/*      */     
/*      */     public void focusLost(FocusEvent param1FocusEvent) {
/*  826 */       if (this.spinner == eventToSpinner(param1FocusEvent)) {
/*  827 */         if (this.autoRepeatTimer.isRunning()) {
/*  828 */           this.autoRepeatTimer.stop();
/*      */         }
/*  830 */         this.spinner = null;
/*  831 */         if (this.arrowButton != null) {
/*  832 */           ButtonModel buttonModel = this.arrowButton.getModel();
/*  833 */           buttonModel.setPressed(false);
/*  834 */           buttonModel.setArmed(false);
/*  835 */           this.arrowButton = null;
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class Handler
/*      */     implements LayoutManager, PropertyChangeListener, ChangeListener
/*      */   {
/*  847 */     private Component nextButton = null;
/*  848 */     private Component previousButton = null;
/*  849 */     private Component editor = null;
/*      */     
/*      */     public void addLayoutComponent(String param1String, Component param1Component) {
/*  852 */       if ("Next".equals(param1String)) {
/*  853 */         this.nextButton = param1Component;
/*      */       }
/*  855 */       else if ("Previous".equals(param1String)) {
/*  856 */         this.previousButton = param1Component;
/*      */       }
/*  858 */       else if ("Editor".equals(param1String)) {
/*  859 */         this.editor = param1Component;
/*      */       } 
/*      */     }
/*      */     
/*      */     public void removeLayoutComponent(Component param1Component) {
/*  864 */       if (param1Component == this.nextButton) {
/*  865 */         this.nextButton = null;
/*      */       }
/*  867 */       else if (param1Component == this.previousButton) {
/*  868 */         this.previousButton = null;
/*      */       }
/*  870 */       else if (param1Component == this.editor) {
/*  871 */         this.editor = null;
/*      */       } 
/*      */     }
/*      */     
/*      */     private Dimension preferredSize(Component param1Component) {
/*  876 */       return (param1Component == null) ? BasicSpinnerUI.zeroSize : param1Component.getPreferredSize();
/*      */     }
/*      */     
/*      */     public Dimension preferredLayoutSize(Container param1Container) {
/*  880 */       Dimension dimension1 = preferredSize(this.nextButton);
/*  881 */       Dimension dimension2 = preferredSize(this.previousButton);
/*  882 */       Dimension dimension3 = preferredSize(this.editor);
/*      */ 
/*      */ 
/*      */       
/*  886 */       dimension3.height = (dimension3.height + 1) / 2 * 2;
/*      */       
/*  888 */       Dimension dimension4 = new Dimension(dimension3.width, dimension3.height);
/*  889 */       dimension4.width += Math.max(dimension1.width, dimension2.width);
/*  890 */       Insets insets = param1Container.getInsets();
/*  891 */       dimension4.width += insets.left + insets.right;
/*  892 */       dimension4.height += insets.top + insets.bottom;
/*  893 */       return dimension4;
/*      */     }
/*      */     
/*      */     public Dimension minimumLayoutSize(Container param1Container) {
/*  897 */       return preferredLayoutSize(param1Container);
/*      */     }
/*      */     
/*      */     private void setBounds(Component param1Component, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  901 */       if (param1Component != null) {
/*  902 */         param1Component.setBounds(param1Int1, param1Int2, param1Int3, param1Int4);
/*      */       }
/*      */     }
/*      */     
/*      */     public void layoutContainer(Container param1Container) {
/*  907 */       int n, i1, i2, i = param1Container.getWidth();
/*  908 */       int j = param1Container.getHeight();
/*      */       
/*  910 */       Insets insets1 = param1Container.getInsets();
/*      */       
/*  912 */       if (this.nextButton == null && this.previousButton == null) {
/*  913 */         setBounds(this.editor, insets1.left, insets1.top, i - insets1.left - insets1.right, j - insets1.top - insets1.bottom);
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  919 */       Dimension dimension1 = preferredSize(this.nextButton);
/*  920 */       Dimension dimension2 = preferredSize(this.previousButton);
/*  921 */       int k = Math.max(dimension1.width, dimension2.width);
/*  922 */       int m = j - insets1.top + insets1.bottom;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  929 */       Insets insets2 = UIManager.getInsets("Spinner.arrowButtonInsets");
/*  930 */       if (insets2 == null) {
/*  931 */         insets2 = insets1;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  937 */       if (param1Container.getComponentOrientation().isLeftToRight()) {
/*  938 */         n = insets1.left;
/*  939 */         i1 = i - insets1.left - k - insets2.right;
/*  940 */         i2 = i - k - insets2.right;
/*      */       } else {
/*  942 */         i2 = insets2.left;
/*  943 */         n = i2 + k;
/*  944 */         i1 = i - insets2.left - k - insets1.right;
/*      */       } 
/*      */       
/*  947 */       int i3 = insets2.top;
/*  948 */       int i4 = j / 2 + j % 2 - i3;
/*  949 */       int i5 = insets2.top + i4;
/*  950 */       int i6 = j - i5 - insets2.bottom;
/*      */       
/*  952 */       setBounds(this.editor, n, insets1.top, i1, m);
/*  953 */       setBounds(this.nextButton, i2, i3, k, i4);
/*  954 */       setBounds(this.previousButton, i2, i5, k, i6);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/*  963 */       String str = param1PropertyChangeEvent.getPropertyName();
/*  964 */       if (param1PropertyChangeEvent.getSource() instanceof JSpinner) {
/*  965 */         JSpinner jSpinner = (JSpinner)param1PropertyChangeEvent.getSource();
/*  966 */         SpinnerUI spinnerUI = jSpinner.getUI();
/*      */         
/*  968 */         if (spinnerUI instanceof BasicSpinnerUI) {
/*  969 */           BasicSpinnerUI basicSpinnerUI = (BasicSpinnerUI)spinnerUI;
/*      */           
/*  971 */           if ("editor".equals(str)) {
/*  972 */             JComponent jComponent1 = (JComponent)param1PropertyChangeEvent.getOldValue();
/*  973 */             JComponent jComponent2 = (JComponent)param1PropertyChangeEvent.getNewValue();
/*  974 */             basicSpinnerUI.replaceEditor(jComponent1, jComponent2);
/*  975 */             basicSpinnerUI.updateEnabledState();
/*  976 */             if (jComponent1 instanceof JSpinner.DefaultEditor) {
/*      */               
/*  978 */               JFormattedTextField jFormattedTextField = ((JSpinner.DefaultEditor)jComponent1).getTextField();
/*  979 */               if (jFormattedTextField != null) {
/*  980 */                 jFormattedTextField.removeFocusListener(BasicSpinnerUI.nextButtonHandler);
/*  981 */                 jFormattedTextField.removeFocusListener(BasicSpinnerUI.previousButtonHandler);
/*      */               } 
/*      */             } 
/*  984 */             if (jComponent2 instanceof JSpinner.DefaultEditor) {
/*      */               
/*  986 */               JFormattedTextField jFormattedTextField = ((JSpinner.DefaultEditor)jComponent2).getTextField();
/*  987 */               if (jFormattedTextField != null) {
/*  988 */                 if (jFormattedTextField.getFont() instanceof UIResource) {
/*  989 */                   jFormattedTextField.setFont(jSpinner.getFont());
/*      */                 }
/*  991 */                 jFormattedTextField.addFocusListener(BasicSpinnerUI.nextButtonHandler);
/*  992 */                 jFormattedTextField.addFocusListener(BasicSpinnerUI.previousButtonHandler);
/*      */               }
/*      */             
/*      */             } 
/*  996 */           } else if ("enabled".equals(str) || "model"
/*  997 */             .equals(str)) {
/*  998 */             basicSpinnerUI.updateEnabledState();
/*      */           }
/* 1000 */           else if ("font".equals(str)) {
/* 1001 */             JComponent jComponent = jSpinner.getEditor();
/* 1002 */             if (jComponent != null && jComponent instanceof JSpinner.DefaultEditor)
/*      */             {
/* 1004 */               JFormattedTextField jFormattedTextField = ((JSpinner.DefaultEditor)jComponent).getTextField();
/* 1005 */               if (jFormattedTextField != null && 
/* 1006 */                 jFormattedTextField.getFont() instanceof UIResource) {
/* 1007 */                 jFormattedTextField.setFont(jSpinner.getFont());
/*      */               }
/*      */             }
/*      */           
/*      */           }
/* 1012 */           else if ("ToolTipText".equals(str)) {
/* 1013 */             updateToolTipTextForChildren(jSpinner);
/*      */           } 
/*      */         } 
/* 1016 */       } else if (param1PropertyChangeEvent.getSource() instanceof JComponent) {
/* 1017 */         JComponent jComponent = (JComponent)param1PropertyChangeEvent.getSource();
/* 1018 */         if (jComponent.getParent() instanceof javax.swing.JPanel && jComponent
/* 1019 */           .getParent().getParent() instanceof JSpinner && "border"
/* 1020 */           .equals(str)) {
/*      */           
/* 1022 */           JSpinner jSpinner = (JSpinner)jComponent.getParent().getParent();
/* 1023 */           SpinnerUI spinnerUI = jSpinner.getUI();
/* 1024 */           if (spinnerUI instanceof BasicSpinnerUI) {
/* 1025 */             BasicSpinnerUI basicSpinnerUI = (BasicSpinnerUI)spinnerUI;
/* 1026 */             basicSpinnerUI.maybeRemoveEditorBorder(jComponent);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void updateToolTipTextForChildren(JComponent param1JComponent) {
/* 1035 */       String str = param1JComponent.getToolTipText();
/* 1036 */       Component[] arrayOfComponent = param1JComponent.getComponents();
/* 1037 */       for (byte b = 0; b < arrayOfComponent.length; b++) {
/* 1038 */         if (arrayOfComponent[b] instanceof JSpinner.DefaultEditor) {
/* 1039 */           JFormattedTextField jFormattedTextField = ((JSpinner.DefaultEditor)arrayOfComponent[b]).getTextField();
/* 1040 */           if (jFormattedTextField != null) {
/* 1041 */             jFormattedTextField.setToolTipText(str);
/*      */           }
/* 1043 */         } else if (arrayOfComponent[b] instanceof JComponent) {
/* 1044 */           ((JComponent)arrayOfComponent[b]).setToolTipText(param1JComponent.getToolTipText());
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/* 1050 */       if (param1ChangeEvent.getSource() instanceof JSpinner) {
/* 1051 */         JSpinner jSpinner = (JSpinner)param1ChangeEvent.getSource();
/* 1052 */         SpinnerUI spinnerUI = jSpinner.getUI();
/* 1053 */         if (DefaultLookup.getBoolean(jSpinner, spinnerUI, "Spinner.disableOnBoundaryValues", false) && spinnerUI instanceof BasicSpinnerUI) {
/*      */ 
/*      */           
/* 1056 */           BasicSpinnerUI basicSpinnerUI = (BasicSpinnerUI)spinnerUI;
/* 1057 */           basicSpinnerUI.updateEnabledState();
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     private Handler() {}
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicSpinnerUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */