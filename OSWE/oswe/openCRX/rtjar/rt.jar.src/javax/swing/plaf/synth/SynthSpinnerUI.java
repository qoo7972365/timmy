/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.FocusListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JFormattedTextField;
/*     */ import javax.swing.JSpinner;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.SpinnerUI;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.plaf.basic.BasicSpinnerUI;
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
/*     */ public class SynthSpinnerUI
/*     */   extends BasicSpinnerUI
/*     */   implements PropertyChangeListener, SynthUI
/*     */ {
/*     */   private SynthStyle style;
/*  54 */   private EditorFocusHandler editorFocusHandler = new EditorFocusHandler();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  64 */     return new SynthSpinnerUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners() {
/*  72 */     super.installListeners();
/*  73 */     this.spinner.addPropertyChangeListener(this);
/*  74 */     JComponent jComponent = this.spinner.getEditor();
/*  75 */     if (jComponent instanceof JSpinner.DefaultEditor) {
/*  76 */       JFormattedTextField jFormattedTextField = ((JSpinner.DefaultEditor)jComponent).getTextField();
/*  77 */       if (jFormattedTextField != null) {
/*  78 */         jFormattedTextField.addFocusListener(this.editorFocusHandler);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners() {
/*  88 */     super.uninstallListeners();
/*  89 */     this.spinner.removePropertyChangeListener(this);
/*  90 */     JComponent jComponent = this.spinner.getEditor();
/*  91 */     if (jComponent instanceof JSpinner.DefaultEditor) {
/*  92 */       JFormattedTextField jFormattedTextField = ((JSpinner.DefaultEditor)jComponent).getTextField();
/*  93 */       if (jFormattedTextField != null) {
/*  94 */         jFormattedTextField.removeFocusListener(this.editorFocusHandler);
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
/*     */   protected void installDefaults() {
/* 114 */     LayoutManager layoutManager = this.spinner.getLayout();
/*     */     
/* 116 */     if (layoutManager == null || layoutManager instanceof UIResource) {
/* 117 */       this.spinner.setLayout(createLayout());
/*     */     }
/* 119 */     updateStyle(this.spinner);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateStyle(JSpinner paramJSpinner) {
/* 124 */     SynthContext synthContext = getContext(paramJSpinner, 1);
/* 125 */     SynthStyle synthStyle = this.style;
/* 126 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/* 127 */     if (this.style != synthStyle && 
/* 128 */       synthStyle != null)
/*     */     {
/*     */       
/* 131 */       installKeyboardActions();
/*     */     }
/*     */     
/* 134 */     synthContext.dispose();
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
/*     */   protected void uninstallDefaults() {
/* 147 */     if (this.spinner.getLayout() instanceof UIResource) {
/* 148 */       this.spinner.setLayout((LayoutManager)null);
/*     */     }
/*     */     
/* 151 */     SynthContext synthContext = getContext(this.spinner, 1);
/*     */     
/* 153 */     this.style.uninstallDefaults(synthContext);
/* 154 */     synthContext.dispose();
/* 155 */     this.style = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LayoutManager createLayout() {
/* 163 */     return new SpinnerLayout();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Component createPreviousButton() {
/* 172 */     SynthArrowButton synthArrowButton = new SynthArrowButton(5);
/* 173 */     synthArrowButton.setName("Spinner.previousButton");
/* 174 */     installPreviousButtonListeners(synthArrowButton);
/* 175 */     return synthArrowButton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Component createNextButton() {
/* 184 */     SynthArrowButton synthArrowButton = new SynthArrowButton(1);
/* 185 */     synthArrowButton.setName("Spinner.nextButton");
/* 186 */     installNextButtonListeners(synthArrowButton);
/* 187 */     return synthArrowButton;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JComponent createEditor() {
/* 216 */     JComponent jComponent = this.spinner.getEditor();
/* 217 */     jComponent.setName("Spinner.editor");
/* 218 */     updateEditorAlignment(jComponent);
/* 219 */     return jComponent;
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
/*     */   protected void replaceEditor(JComponent paramJComponent1, JComponent paramJComponent2) {
/* 240 */     this.spinner.remove(paramJComponent1);
/* 241 */     this.spinner.add(paramJComponent2, "Editor");
/* 242 */     if (paramJComponent1 instanceof JSpinner.DefaultEditor) {
/* 243 */       JFormattedTextField jFormattedTextField = ((JSpinner.DefaultEditor)paramJComponent1).getTextField();
/* 244 */       if (jFormattedTextField != null) {
/* 245 */         jFormattedTextField.removeFocusListener(this.editorFocusHandler);
/*     */       }
/*     */     } 
/* 248 */     if (paramJComponent2 instanceof JSpinner.DefaultEditor) {
/* 249 */       JFormattedTextField jFormattedTextField = ((JSpinner.DefaultEditor)paramJComponent2).getTextField();
/* 250 */       if (jFormattedTextField != null) {
/* 251 */         jFormattedTextField.addFocusListener(this.editorFocusHandler);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateEditorAlignment(JComponent paramJComponent) {
/* 257 */     if (paramJComponent instanceof JSpinner.DefaultEditor) {
/* 258 */       SynthContext synthContext = getContext(this.spinner);
/* 259 */       Integer integer = (Integer)synthContext.getStyle().get(synthContext, "Spinner.editorAlignment");
/*     */       
/* 261 */       JFormattedTextField jFormattedTextField = ((JSpinner.DefaultEditor)paramJComponent).getTextField();
/* 262 */       if (integer != null) {
/* 263 */         jFormattedTextField.setHorizontalAlignment(integer.intValue());
/*     */       }
/*     */ 
/*     */       
/* 267 */       jFormattedTextField.putClientProperty("JComponent.sizeVariant", this.spinner
/* 268 */           .getClientProperty("JComponent.sizeVariant"));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 277 */     return getContext(paramJComponent, SynthLookAndFeel.getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 281 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
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
/*     */   public void update(Graphics paramGraphics, JComponent paramJComponent) {
/* 298 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 300 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 301 */     synthContext.getPainter().paintSpinnerBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/* 302 */         .getWidth(), paramJComponent.getHeight());
/* 303 */     paint(synthContext, paramGraphics);
/* 304 */     synthContext.dispose();
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
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 319 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 321 */     paint(synthContext, paramGraphics);
/* 322 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paint(SynthContext paramSynthContext, Graphics paramGraphics) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 341 */     paramSynthContext.getPainter().paintSpinnerBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SpinnerLayout
/*     */     implements LayoutManager, UIResource
/*     */   {
/* 351 */     private Component nextButton = null;
/* 352 */     private Component previousButton = null;
/* 353 */     private Component editor = null;
/*     */     
/*     */     public void addLayoutComponent(String param1String, Component param1Component) {
/* 356 */       if ("Next".equals(param1String)) {
/* 357 */         this.nextButton = param1Component;
/*     */       }
/* 359 */       else if ("Previous".equals(param1String)) {
/* 360 */         this.previousButton = param1Component;
/*     */       }
/* 362 */       else if ("Editor".equals(param1String)) {
/* 363 */         this.editor = param1Component;
/*     */       } 
/*     */     }
/*     */     
/*     */     public void removeLayoutComponent(Component param1Component) {
/* 368 */       if (param1Component == this.nextButton) {
/* 369 */         this.nextButton = null;
/*     */       }
/* 371 */       else if (param1Component == this.previousButton) {
/* 372 */         this.previousButton = null;
/*     */       }
/* 374 */       else if (param1Component == this.editor) {
/* 375 */         this.editor = null;
/*     */       } 
/*     */     }
/*     */     
/*     */     private Dimension preferredSize(Component param1Component) {
/* 380 */       return (param1Component == null) ? new Dimension(0, 0) : param1Component.getPreferredSize();
/*     */     }
/*     */     
/*     */     public Dimension preferredLayoutSize(Container param1Container) {
/* 384 */       Dimension dimension1 = preferredSize(this.nextButton);
/* 385 */       Dimension dimension2 = preferredSize(this.previousButton);
/* 386 */       Dimension dimension3 = preferredSize(this.editor);
/*     */ 
/*     */ 
/*     */       
/* 390 */       dimension3.height = (dimension3.height + 1) / 2 * 2;
/*     */       
/* 392 */       Dimension dimension4 = new Dimension(dimension3.width, dimension3.height);
/* 393 */       dimension4.width += Math.max(dimension1.width, dimension2.width);
/* 394 */       Insets insets = param1Container.getInsets();
/* 395 */       dimension4.width += insets.left + insets.right;
/* 396 */       dimension4.height += insets.top + insets.bottom;
/* 397 */       return dimension4;
/*     */     }
/*     */     
/*     */     public Dimension minimumLayoutSize(Container param1Container) {
/* 401 */       return preferredLayoutSize(param1Container);
/*     */     }
/*     */     
/*     */     private void setBounds(Component param1Component, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 405 */       if (param1Component != null)
/* 406 */         param1Component.setBounds(param1Int1, param1Int2, param1Int3, param1Int4); 
/*     */     }
/*     */     
/*     */     public void layoutContainer(Container param1Container) {
/*     */       int i2, i3;
/* 411 */       Insets insets = param1Container.getInsets();
/* 412 */       int i = param1Container.getWidth() - insets.left + insets.right;
/* 413 */       int j = param1Container.getHeight() - insets.top + insets.bottom;
/* 414 */       Dimension dimension1 = preferredSize(this.nextButton);
/* 415 */       Dimension dimension2 = preferredSize(this.previousButton);
/* 416 */       int k = j / 2;
/* 417 */       int m = j - k;
/* 418 */       int n = Math.max(dimension1.width, dimension2.width);
/* 419 */       int i1 = i - n;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 424 */       if (param1Container.getComponentOrientation().isLeftToRight()) {
/* 425 */         i2 = insets.left;
/* 426 */         i3 = i2 + i1;
/*     */       } else {
/*     */         
/* 429 */         i3 = insets.left;
/* 430 */         i2 = i3 + n;
/*     */       } 
/*     */       
/* 433 */       int i4 = insets.top + k;
/* 434 */       setBounds(this.editor, i2, insets.top, i1, j);
/* 435 */       setBounds(this.nextButton, i3, insets.top, n, k);
/* 436 */       setBounds(this.previousButton, i3, i4, n, m);
/*     */     }
/*     */ 
/*     */     
/*     */     private SpinnerLayout() {}
/*     */   }
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 445 */     JSpinner jSpinner = (JSpinner)paramPropertyChangeEvent.getSource();
/* 446 */     SpinnerUI spinnerUI = jSpinner.getUI();
/*     */     
/* 448 */     if (spinnerUI instanceof SynthSpinnerUI) {
/* 449 */       SynthSpinnerUI synthSpinnerUI = (SynthSpinnerUI)spinnerUI;
/*     */       
/* 451 */       if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent))
/* 452 */         synthSpinnerUI.updateStyle(jSpinner); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private class EditorFocusHandler
/*     */     implements FocusListener {
/*     */     private EditorFocusHandler() {}
/*     */     
/*     */     public void focusGained(FocusEvent param1FocusEvent) {
/* 461 */       SynthSpinnerUI.this.spinner.repaint();
/*     */     }
/*     */ 
/*     */     
/*     */     public void focusLost(FocusEvent param1FocusEvent) {
/* 466 */       SynthSpinnerUI.this.spinner.repaint();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthSpinnerUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */