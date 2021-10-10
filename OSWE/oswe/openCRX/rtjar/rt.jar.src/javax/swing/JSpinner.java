/*      */ package javax.swing;
/*      */ 
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Insets;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.text.DateFormat;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.NumberFormat;
/*      */ import java.text.ParseException;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.text.spi.DateFormatProvider;
/*      */ import java.text.spi.NumberFormatProvider;
/*      */ import java.util.Locale;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleAction;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleEditableText;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.accessibility.AccessibleText;
/*      */ import javax.accessibility.AccessibleValue;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ChangeListener;
/*      */ import javax.swing.plaf.SpinnerUI;
/*      */ import javax.swing.text.AttributeSet;
/*      */ import javax.swing.text.BadLocationException;
/*      */ import javax.swing.text.DateFormatter;
/*      */ import javax.swing.text.DefaultFormatterFactory;
/*      */ import javax.swing.text.DocumentFilter;
/*      */ import javax.swing.text.NumberFormatter;
/*      */ import sun.util.locale.provider.LocaleProviderAdapter;
/*      */ import sun.util.locale.provider.LocaleResources;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JSpinner
/*      */   extends JComponent
/*      */   implements Accessible
/*      */ {
/*      */   private static final String uiClassID = "SpinnerUI";
/*  135 */   private static final Action DISABLED_ACTION = new DisabledAction();
/*      */ 
/*      */   
/*      */   private SpinnerModel model;
/*      */ 
/*      */   
/*      */   private JComponent editor;
/*      */ 
/*      */   
/*      */   private ChangeListener modelListener;
/*      */   
/*      */   private transient ChangeEvent changeEvent;
/*      */   
/*      */   private boolean editorExplicitlySet = false;
/*      */ 
/*      */   
/*      */   public JSpinner(SpinnerModel paramSpinnerModel) {
/*  152 */     if (paramSpinnerModel == null) {
/*  153 */       throw new NullPointerException("model cannot be null");
/*      */     }
/*  155 */     this.model = paramSpinnerModel;
/*  156 */     this.editor = createEditor(paramSpinnerModel);
/*  157 */     setUIProperty("opaque", Boolean.valueOf(true));
/*  158 */     updateUI();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSpinner() {
/*  167 */     this(new SpinnerNumberModel());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SpinnerUI getUI() {
/*  177 */     return (SpinnerUI)this.ui;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUI(SpinnerUI paramSpinnerUI) {
/*  188 */     setUI(paramSpinnerUI);
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
/*      */   public String getUIClassID() {
/*  201 */     return "SpinnerUI";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateUI() {
/*  212 */     setUI((SpinnerUI)UIManager.getUI(this));
/*  213 */     invalidate();
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
/*      */   protected JComponent createEditor(SpinnerModel paramSpinnerModel) {
/*  243 */     if (paramSpinnerModel instanceof SpinnerDateModel) {
/*  244 */       return new DateEditor(this);
/*      */     }
/*  246 */     if (paramSpinnerModel instanceof SpinnerListModel) {
/*  247 */       return new ListEditor(this);
/*      */     }
/*  249 */     if (paramSpinnerModel instanceof SpinnerNumberModel) {
/*  250 */       return new NumberEditor(this);
/*      */     }
/*      */     
/*  253 */     return new DefaultEditor(this);
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
/*      */   public void setModel(SpinnerModel paramSpinnerModel) {
/*  281 */     if (paramSpinnerModel == null) {
/*  282 */       throw new IllegalArgumentException("null model");
/*      */     }
/*  284 */     if (!paramSpinnerModel.equals(this.model)) {
/*  285 */       SpinnerModel spinnerModel = this.model;
/*  286 */       this.model = paramSpinnerModel;
/*  287 */       if (this.modelListener != null) {
/*  288 */         spinnerModel.removeChangeListener(this.modelListener);
/*  289 */         this.model.addChangeListener(this.modelListener);
/*      */       } 
/*  291 */       firePropertyChange("model", spinnerModel, paramSpinnerModel);
/*  292 */       if (!this.editorExplicitlySet) {
/*  293 */         setEditor(createEditor(paramSpinnerModel));
/*  294 */         this.editorExplicitlySet = false;
/*      */       } 
/*  296 */       repaint();
/*  297 */       revalidate();
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
/*      */   public SpinnerModel getModel() {
/*  310 */     return this.model;
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
/*      */   public Object getValue() {
/*  332 */     return getModel().getValue();
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
/*      */   public void setValue(Object paramObject) {
/*  354 */     getModel().setValue(paramObject);
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
/*      */   public Object getNextValue() {
/*  376 */     return getModel().getNextValue();
/*      */   }
/*      */ 
/*      */   
/*      */   private class ModelListener
/*      */     implements ChangeListener, Serializable
/*      */   {
/*      */     private ModelListener() {}
/*      */     
/*      */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/*  386 */       JSpinner.this.fireStateChanged();
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
/*      */   public void addChangeListener(ChangeListener paramChangeListener) {
/*  406 */     if (this.modelListener == null) {
/*  407 */       this.modelListener = new ModelListener();
/*  408 */       getModel().addChangeListener(this.modelListener);
/*      */     } 
/*  410 */     this.listenerList.add(ChangeListener.class, paramChangeListener);
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
/*      */   public void removeChangeListener(ChangeListener paramChangeListener) {
/*  423 */     this.listenerList.remove(ChangeListener.class, paramChangeListener);
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
/*      */   public ChangeListener[] getChangeListeners() {
/*  436 */     return this.listenerList.<ChangeListener>getListeners(ChangeListener.class);
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
/*      */   protected void fireStateChanged() {
/*  452 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*  453 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/*  454 */       if (arrayOfObject[i] == ChangeListener.class) {
/*  455 */         if (this.changeEvent == null) {
/*  456 */           this.changeEvent = new ChangeEvent(this);
/*      */         }
/*  458 */         ((ChangeListener)arrayOfObject[i + 1]).stateChanged(this.changeEvent);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getPreviousValue() {
/*  484 */     return getModel().getPreviousValue();
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
/*      */   public void setEditor(JComponent paramJComponent) {
/*  508 */     if (paramJComponent == null) {
/*  509 */       throw new IllegalArgumentException("null editor");
/*      */     }
/*  511 */     if (!paramJComponent.equals(this.editor)) {
/*  512 */       JComponent jComponent = this.editor;
/*  513 */       this.editor = paramJComponent;
/*  514 */       if (jComponent instanceof DefaultEditor) {
/*  515 */         ((DefaultEditor)jComponent).dismiss(this);
/*      */       }
/*  517 */       this.editorExplicitlySet = true;
/*  518 */       firePropertyChange("editor", jComponent, paramJComponent);
/*  519 */       revalidate();
/*  520 */       repaint();
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
/*      */   public JComponent getEditor() {
/*  535 */     return this.editor;
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
/*      */   public void commitEdit() throws ParseException {
/*  549 */     JComponent jComponent = getEditor();
/*  550 */     if (jComponent instanceof DefaultEditor) {
/*  551 */       ((DefaultEditor)jComponent).commitEdit();
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
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*  563 */     paramObjectOutputStream.defaultWriteObject();
/*  564 */     if (getUIClassID().equals("SpinnerUI")) {
/*  565 */       byte b = JComponent.getWriteObjCounter(this);
/*  566 */       b = (byte)(b - 1); JComponent.setWriteObjCounter(this, b);
/*  567 */       if (b == 0 && this.ui != null) {
/*  568 */         this.ui.installUI(this);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class DefaultEditor
/*      */     extends JPanel
/*      */     implements ChangeListener, PropertyChangeListener, LayoutManager
/*      */   {
/*      */     public DefaultEditor(JSpinner param1JSpinner) {
/*  620 */       super((LayoutManager)null);
/*      */       
/*  622 */       JFormattedTextField jFormattedTextField = new JFormattedTextField();
/*  623 */       jFormattedTextField.setName("Spinner.formattedTextField");
/*  624 */       jFormattedTextField.setValue(param1JSpinner.getValue());
/*  625 */       jFormattedTextField.addPropertyChangeListener(this);
/*  626 */       jFormattedTextField.setEditable(false);
/*  627 */       jFormattedTextField.setInheritsPopupMenu(true);
/*      */       
/*  629 */       String str = param1JSpinner.getToolTipText();
/*  630 */       if (str != null) {
/*  631 */         jFormattedTextField.setToolTipText(str);
/*      */       }
/*      */       
/*  634 */       add(jFormattedTextField);
/*      */       
/*  636 */       setLayout(this);
/*  637 */       param1JSpinner.addChangeListener(this);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  644 */       ActionMap actionMap = jFormattedTextField.getActionMap();
/*      */       
/*  646 */       if (actionMap != null) {
/*  647 */         actionMap.put("increment", JSpinner.DISABLED_ACTION);
/*  648 */         actionMap.put("decrement", JSpinner.DISABLED_ACTION);
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
/*      */     public void dismiss(JSpinner param1JSpinner) {
/*  662 */       param1JSpinner.removeChangeListener(this);
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
/*      */     public JSpinner getSpinner() {
/*  681 */       for (DefaultEditor defaultEditor = this; defaultEditor != null; container = defaultEditor.getParent()) {
/*  682 */         Container container; if (defaultEditor instanceof JSpinner) {
/*  683 */           return (JSpinner)defaultEditor;
/*      */         }
/*      */       } 
/*  686 */       return null;
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
/*      */     public JFormattedTextField getTextField() {
/*  701 */       return (JFormattedTextField)getComponent(0);
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
/*      */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/*  716 */       JSpinner jSpinner = (JSpinner)param1ChangeEvent.getSource();
/*  717 */       getTextField().setValue(jSpinner.getValue());
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
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/*  738 */       JSpinner jSpinner = getSpinner();
/*      */       
/*  740 */       if (jSpinner == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  745 */       Object object = param1PropertyChangeEvent.getSource();
/*  746 */       String str = param1PropertyChangeEvent.getPropertyName();
/*  747 */       if (object instanceof JFormattedTextField && "value".equals(str)) {
/*  748 */         Object object1 = jSpinner.getValue();
/*      */ 
/*      */         
/*      */         try {
/*  752 */           jSpinner.setValue(getTextField().getValue());
/*  753 */         } catch (IllegalArgumentException illegalArgumentException) {
/*      */           
/*      */           try {
/*  756 */             ((JFormattedTextField)object).setValue(object1);
/*  757 */           } catch (IllegalArgumentException illegalArgumentException1) {}
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
/*      */ 
/*      */ 
/*      */     
/*      */     public void addLayoutComponent(String param1String, Component param1Component) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeLayoutComponent(Component param1Component) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Dimension insetSize(Container param1Container) {
/*  793 */       Insets insets = param1Container.getInsets();
/*  794 */       int i = insets.left + insets.right;
/*  795 */       int j = insets.top + insets.bottom;
/*  796 */       return new Dimension(i, j);
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
/*      */     public Dimension preferredLayoutSize(Container param1Container) {
/*  809 */       Dimension dimension = insetSize(param1Container);
/*  810 */       if (param1Container.getComponentCount() > 0) {
/*  811 */         Dimension dimension1 = getComponent(0).getPreferredSize();
/*  812 */         dimension.width += dimension1.width;
/*  813 */         dimension.height += dimension1.height;
/*      */       } 
/*  815 */       return dimension;
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
/*      */     public Dimension minimumLayoutSize(Container param1Container) {
/*  828 */       Dimension dimension = insetSize(param1Container);
/*  829 */       if (param1Container.getComponentCount() > 0) {
/*  830 */         Dimension dimension1 = getComponent(0).getMinimumSize();
/*  831 */         dimension.width += dimension1.width;
/*  832 */         dimension.height += dimension1.height;
/*      */       } 
/*  834 */       return dimension;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void layoutContainer(Container param1Container) {
/*  843 */       if (param1Container.getComponentCount() > 0) {
/*  844 */         Insets insets = param1Container.getInsets();
/*  845 */         int i = param1Container.getWidth() - insets.left + insets.right;
/*  846 */         int j = param1Container.getHeight() - insets.top + insets.bottom;
/*  847 */         getComponent(0).setBounds(insets.left, insets.top, i, j);
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
/*      */     
/*      */     public void commitEdit() throws ParseException {
/*  863 */       JFormattedTextField jFormattedTextField = getTextField();
/*      */       
/*  865 */       jFormattedTextField.commitEdit();
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
/*      */     public int getBaseline(int param1Int1, int param1Int2) {
/*  878 */       super.getBaseline(param1Int1, param1Int2);
/*  879 */       Insets insets = getInsets();
/*  880 */       param1Int1 = param1Int1 - insets.left - insets.right;
/*  881 */       param1Int2 = param1Int2 - insets.top - insets.bottom;
/*  882 */       int i = getComponent(0).getBaseline(param1Int1, param1Int2);
/*  883 */       if (i >= 0) {
/*  884 */         return i + insets.top;
/*      */       }
/*  886 */       return -1;
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
/*      */     public Component.BaselineResizeBehavior getBaselineResizeBehavior() {
/*  898 */       return getComponent(0).getBaselineResizeBehavior();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class DateEditorFormatter
/*      */     extends DateFormatter
/*      */   {
/*      */     private final SpinnerDateModel model;
/*      */ 
/*      */ 
/*      */     
/*      */     DateEditorFormatter(SpinnerDateModel param1SpinnerDateModel, DateFormat param1DateFormat) {
/*  913 */       super(param1DateFormat);
/*  914 */       this.model = param1SpinnerDateModel;
/*      */     }
/*      */     
/*      */     public void setMinimum(Comparable param1Comparable) {
/*  918 */       this.model.setStart(param1Comparable);
/*      */     }
/*      */     
/*      */     public Comparable getMinimum() {
/*  922 */       return this.model.getStart();
/*      */     }
/*      */     
/*      */     public void setMaximum(Comparable param1Comparable) {
/*  926 */       this.model.setEnd(param1Comparable);
/*      */     }
/*      */     
/*      */     public Comparable getMaximum() {
/*  930 */       return this.model.getEnd();
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
/*      */   public static class DateEditor
/*      */     extends DefaultEditor
/*      */   {
/*      */     private static String getDefaultPattern(Locale param1Locale) {
/*  950 */       LocaleProviderAdapter localeProviderAdapter = LocaleProviderAdapter.getAdapter((Class)DateFormatProvider.class, param1Locale);
/*  951 */       LocaleResources localeResources = localeProviderAdapter.getLocaleResources(param1Locale);
/*  952 */       if (localeResources == null) {
/*  953 */         localeResources = LocaleProviderAdapter.forJRE().getLocaleResources(param1Locale);
/*      */       }
/*  955 */       return localeResources.getDateTimePattern(3, 3, null);
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
/*      */     public DateEditor(JSpinner param1JSpinner) {
/*  975 */       this(param1JSpinner, getDefaultPattern(param1JSpinner.getLocale()));
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
/*      */     public DateEditor(JSpinner param1JSpinner, String param1String) {
/* 1000 */       this(param1JSpinner, new SimpleDateFormat(param1String, param1JSpinner
/* 1001 */             .getLocale()));
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
/*      */     private DateEditor(JSpinner param1JSpinner, DateFormat param1DateFormat) {
/* 1025 */       super(param1JSpinner);
/* 1026 */       if (!(param1JSpinner.getModel() instanceof SpinnerDateModel)) {
/* 1027 */         throw new IllegalArgumentException("model not a SpinnerDateModel");
/*      */       }
/*      */ 
/*      */       
/* 1031 */       SpinnerDateModel spinnerDateModel = (SpinnerDateModel)param1JSpinner.getModel();
/* 1032 */       JSpinner.DateEditorFormatter dateEditorFormatter = new JSpinner.DateEditorFormatter(spinnerDateModel, param1DateFormat);
/* 1033 */       DefaultFormatterFactory defaultFormatterFactory = new DefaultFormatterFactory(dateEditorFormatter);
/*      */       
/* 1035 */       JFormattedTextField jFormattedTextField = getTextField();
/* 1036 */       jFormattedTextField.setEditable(true);
/* 1037 */       jFormattedTextField.setFormatterFactory(defaultFormatterFactory);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1044 */         String str1 = dateEditorFormatter.valueToString(spinnerDateModel.getStart());
/* 1045 */         String str2 = dateEditorFormatter.valueToString(spinnerDateModel.getEnd());
/* 1046 */         jFormattedTextField.setColumns(Math.max(str1.length(), str2
/* 1047 */               .length()));
/*      */       }
/* 1049 */       catch (ParseException parseException) {}
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
/*      */     public SimpleDateFormat getFormat() {
/* 1064 */       return (SimpleDateFormat)((DateFormatter)getTextField().getFormatter()).getFormat();
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
/*      */     public SpinnerDateModel getModel() {
/* 1076 */       return (SpinnerDateModel)getSpinner().getModel();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class NumberEditorFormatter
/*      */     extends NumberFormatter
/*      */   {
/*      */     private final SpinnerNumberModel model;
/*      */ 
/*      */ 
/*      */     
/*      */     NumberEditorFormatter(SpinnerNumberModel param1SpinnerNumberModel, NumberFormat param1NumberFormat) {
/* 1090 */       super(param1NumberFormat);
/* 1091 */       this.model = param1SpinnerNumberModel;
/* 1092 */       setValueClass(param1SpinnerNumberModel.getValue().getClass());
/*      */     }
/*      */     
/*      */     public void setMinimum(Comparable param1Comparable) {
/* 1096 */       this.model.setMinimum(param1Comparable);
/*      */     }
/*      */     
/*      */     public Comparable getMinimum() {
/* 1100 */       return this.model.getMinimum();
/*      */     }
/*      */     
/*      */     public void setMaximum(Comparable param1Comparable) {
/* 1104 */       this.model.setMaximum(param1Comparable);
/*      */     }
/*      */     
/*      */     public Comparable getMaximum() {
/* 1108 */       return this.model.getMaximum();
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
/*      */   public static class NumberEditor
/*      */     extends DefaultEditor
/*      */   {
/*      */     private static String getDefaultPattern(Locale param1Locale) {
/* 1131 */       LocaleProviderAdapter localeProviderAdapter = LocaleProviderAdapter.getAdapter((Class)NumberFormatProvider.class, param1Locale);
/*      */       
/* 1133 */       LocaleResources localeResources = localeProviderAdapter.getLocaleResources(param1Locale);
/* 1134 */       if (localeResources == null) {
/* 1135 */         localeResources = LocaleProviderAdapter.forJRE().getLocaleResources(param1Locale);
/*      */       }
/* 1137 */       String[] arrayOfString = localeResources.getNumberPatterns();
/* 1138 */       return arrayOfString[0];
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
/*      */     public NumberEditor(JSpinner param1JSpinner) {
/* 1158 */       this(param1JSpinner, getDefaultPattern(param1JSpinner.getLocale()));
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
/*      */     public NumberEditor(JSpinner param1JSpinner, String param1String) {
/* 1183 */       this(param1JSpinner, new DecimalFormat(param1String));
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
/*      */     private NumberEditor(JSpinner param1JSpinner, DecimalFormat param1DecimalFormat) {
/* 1207 */       super(param1JSpinner);
/* 1208 */       if (!(param1JSpinner.getModel() instanceof SpinnerNumberModel)) {
/* 1209 */         throw new IllegalArgumentException("model not a SpinnerNumberModel");
/*      */       }
/*      */ 
/*      */       
/* 1213 */       SpinnerNumberModel spinnerNumberModel = (SpinnerNumberModel)param1JSpinner.getModel();
/* 1214 */       JSpinner.NumberEditorFormatter numberEditorFormatter = new JSpinner.NumberEditorFormatter(spinnerNumberModel, param1DecimalFormat);
/*      */       
/* 1216 */       DefaultFormatterFactory defaultFormatterFactory = new DefaultFormatterFactory(numberEditorFormatter);
/*      */       
/* 1218 */       JFormattedTextField jFormattedTextField = getTextField();
/* 1219 */       jFormattedTextField.setEditable(true);
/* 1220 */       jFormattedTextField.setFormatterFactory(defaultFormatterFactory);
/* 1221 */       jFormattedTextField.setHorizontalAlignment(4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1228 */         String str1 = numberEditorFormatter.valueToString(spinnerNumberModel.getMinimum());
/* 1229 */         String str2 = numberEditorFormatter.valueToString(spinnerNumberModel.getMaximum());
/* 1230 */         jFormattedTextField.setColumns(Math.max(str1.length(), str2
/* 1231 */               .length()));
/*      */       }
/* 1233 */       catch (ParseException parseException) {}
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
/*      */     public DecimalFormat getFormat() {
/* 1250 */       return (DecimalFormat)((NumberFormatter)getTextField().getFormatter()).getFormat();
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
/*      */     public SpinnerNumberModel getModel() {
/* 1262 */       return (SpinnerNumberModel)getSpinner().getModel();
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
/*      */   public static class ListEditor
/*      */     extends DefaultEditor
/*      */   {
/*      */     public ListEditor(JSpinner param1JSpinner) {
/* 1290 */       super(param1JSpinner);
/* 1291 */       if (!(param1JSpinner.getModel() instanceof SpinnerListModel)) {
/* 1292 */         throw new IllegalArgumentException("model not a SpinnerListModel");
/*      */       }
/* 1294 */       getTextField().setEditable(true);
/* 1295 */       getTextField().setFormatterFactory(new DefaultFormatterFactory(new ListFormatter()));
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
/*      */     public SpinnerListModel getModel() {
/* 1307 */       return (SpinnerListModel)getSpinner().getModel();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private class ListFormatter
/*      */       extends JFormattedTextField.AbstractFormatter
/*      */     {
/*      */       private DocumentFilter filter;
/*      */ 
/*      */       
/*      */       private ListFormatter() {}
/*      */ 
/*      */       
/*      */       public String valueToString(Object param2Object) throws ParseException {
/* 1322 */         if (param2Object == null) {
/* 1323 */           return "";
/*      */         }
/* 1325 */         return param2Object.toString();
/*      */       }
/*      */       
/*      */       public Object stringToValue(String param2String) throws ParseException {
/* 1329 */         return param2String;
/*      */       }
/*      */       
/*      */       protected DocumentFilter getDocumentFilter() {
/* 1333 */         if (this.filter == null) {
/* 1334 */           this.filter = new Filter();
/*      */         }
/* 1336 */         return this.filter;
/*      */       }
/*      */       
/*      */       private class Filter
/*      */         extends DocumentFilter {
/*      */         private Filter() {}
/*      */         
/*      */         public void replace(DocumentFilter.FilterBypass param3FilterBypass, int param3Int1, int param3Int2, String param3String, AttributeSet param3AttributeSet) throws BadLocationException {
/* 1344 */           if (param3String != null && param3Int1 + param3Int2 == param3FilterBypass
/* 1345 */             .getDocument().getLength()) {
/* 1346 */             Object object = JSpinner.ListEditor.this.getModel().findNextMatch(param3FilterBypass
/* 1347 */                 .getDocument().getText(0, param3Int1) + param3String);
/*      */             
/* 1349 */             String str = (object != null) ? object.toString() : null;
/*      */             
/* 1351 */             if (str != null) {
/* 1352 */               param3FilterBypass.remove(0, param3Int1 + param3Int2);
/* 1353 */               param3FilterBypass.insertString(0, str, null);
/* 1354 */               JSpinner.ListEditor.ListFormatter.this.getFormattedTextField().select(param3Int1 + param3String
/* 1355 */                   .length(), str
/* 1356 */                   .length());
/*      */               return;
/*      */             } 
/*      */           } 
/* 1360 */           super.replace(param3FilterBypass, param3Int1, param3Int2, param3String, param3AttributeSet);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public void insertString(DocumentFilter.FilterBypass param3FilterBypass, int param3Int, String param3String, AttributeSet param3AttributeSet) throws BadLocationException {
/* 1366 */           replace(param3FilterBypass, param3Int, 0, param3String, param3AttributeSet);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private static class DisabledAction
/*      */     implements Action
/*      */   {
/*      */     private DisabledAction() {}
/*      */     
/*      */     public Object getValue(String param1String) {
/* 1378 */       return null;
/*      */     }
/*      */     public void putValue(String param1String, Object param1Object) {}
/*      */     
/*      */     public void setEnabled(boolean param1Boolean) {}
/*      */     
/*      */     public boolean isEnabled() {
/* 1385 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void addPropertyChangeListener(PropertyChangeListener param1PropertyChangeListener) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removePropertyChangeListener(PropertyChangeListener param1PropertyChangeListener) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {}
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public AccessibleContext getAccessibleContext() {
/* 1406 */     if (this.accessibleContext == null) {
/* 1407 */       this.accessibleContext = new AccessibleJSpinner();
/*      */     }
/* 1409 */     return this.accessibleContext;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class AccessibleJSpinner
/*      */     extends JComponent.AccessibleJComponent
/*      */     implements AccessibleValue, AccessibleAction, AccessibleText, AccessibleEditableText, ChangeListener
/*      */   {
/* 1421 */     private Object oldModelValue = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected AccessibleJSpinner() {
/* 1428 */       this.oldModelValue = JSpinner.this.model.getValue();
/* 1429 */       JSpinner.this.addChangeListener(this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/* 1439 */       if (param1ChangeEvent == null) {
/* 1440 */         throw new NullPointerException();
/*      */       }
/* 1442 */       Object object = JSpinner.this.model.getValue();
/* 1443 */       firePropertyChange("AccessibleValue", this.oldModelValue, object);
/*      */ 
/*      */       
/* 1446 */       firePropertyChange("AccessibleText", null, 
/*      */           
/* 1448 */           Integer.valueOf(0));
/*      */       
/* 1450 */       this.oldModelValue = object;
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
/*      */     public AccessibleRole getAccessibleRole() {
/* 1474 */       return AccessibleRole.SPIN_BOX;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getAccessibleChildrenCount() {
/* 1484 */       if (JSpinner.this.editor.getAccessibleContext() != null) {
/* 1485 */         return 1;
/*      */       }
/* 1487 */       return 0;
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
/*      */     public Accessible getAccessibleChild(int param1Int) {
/* 1502 */       if (param1Int != 0) {
/* 1503 */         return null;
/*      */       }
/* 1505 */       if (JSpinner.this.editor.getAccessibleContext() != null) {
/* 1506 */         return (Accessible)JSpinner.this.editor;
/*      */       }
/* 1508 */       return null;
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
/*      */     public AccessibleAction getAccessibleAction() {
/* 1521 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleText getAccessibleText() {
/* 1532 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private AccessibleContext getEditorAccessibleContext() {
/* 1539 */       if (JSpinner.this.editor instanceof JSpinner.DefaultEditor) {
/* 1540 */         JFormattedTextField jFormattedTextField = ((JSpinner.DefaultEditor)JSpinner.this.editor).getTextField();
/* 1541 */         if (jFormattedTextField != null) {
/* 1542 */           return jFormattedTextField.getAccessibleContext();
/*      */         }
/* 1544 */       } else if (JSpinner.this.editor instanceof Accessible) {
/* 1545 */         return JSpinner.this.editor.getAccessibleContext();
/*      */       } 
/* 1547 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private AccessibleText getEditorAccessibleText() {
/* 1554 */       AccessibleContext accessibleContext = getEditorAccessibleContext();
/* 1555 */       if (accessibleContext != null) {
/* 1556 */         return accessibleContext.getAccessibleText();
/*      */       }
/* 1558 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private AccessibleEditableText getEditorAccessibleEditableText() {
/* 1565 */       AccessibleText accessibleText = getEditorAccessibleText();
/* 1566 */       if (accessibleText instanceof AccessibleEditableText) {
/* 1567 */         return (AccessibleEditableText)accessibleText;
/*      */       }
/* 1569 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleValue getAccessibleValue() {
/* 1580 */       return this;
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
/*      */     public Number getCurrentAccessibleValue() {
/* 1593 */       Object object = JSpinner.this.model.getValue();
/* 1594 */       if (object instanceof Number) {
/* 1595 */         return (Number)object;
/*      */       }
/* 1597 */       return null;
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
/*      */     public boolean setCurrentAccessibleValue(Number param1Number) {
/*      */       try {
/* 1610 */         JSpinner.this.model.setValue(param1Number);
/* 1611 */         return true;
/* 1612 */       } catch (IllegalArgumentException illegalArgumentException) {
/*      */ 
/*      */         
/* 1615 */         return false;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Number getMinimumAccessibleValue() {
/* 1626 */       if (JSpinner.this.model instanceof SpinnerNumberModel) {
/* 1627 */         SpinnerNumberModel spinnerNumberModel = (SpinnerNumberModel)JSpinner.this.model;
/* 1628 */         Comparable comparable = spinnerNumberModel.getMinimum();
/* 1629 */         if (comparable instanceof Number) {
/* 1630 */           return (Number)comparable;
/*      */         }
/*      */       } 
/* 1633 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Number getMaximumAccessibleValue() {
/* 1644 */       if (JSpinner.this.model instanceof SpinnerNumberModel) {
/* 1645 */         SpinnerNumberModel spinnerNumberModel = (SpinnerNumberModel)JSpinner.this.model;
/* 1646 */         Comparable comparable = spinnerNumberModel.getMaximum();
/* 1647 */         if (comparable instanceof Number) {
/* 1648 */           return (Number)comparable;
/*      */         }
/*      */       } 
/* 1651 */       return null;
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
/*      */     public int getAccessibleActionCount() {
/* 1670 */       return 2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getAccessibleActionDescription(int param1Int) {
/* 1681 */       if (param1Int == 0)
/* 1682 */         return AccessibleAction.INCREMENT; 
/* 1683 */       if (param1Int == 1) {
/* 1684 */         return AccessibleAction.DECREMENT;
/*      */       }
/* 1686 */       return null;
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
/*      */     public boolean doAccessibleAction(int param1Int) {
/*      */       Object object;
/* 1699 */       if (param1Int < 0 || param1Int > 1) {
/* 1700 */         return false;
/*      */       }
/*      */       
/* 1703 */       if (param1Int == 0) {
/* 1704 */         object = JSpinner.this.getNextValue();
/*      */       } else {
/* 1706 */         object = JSpinner.this.getPreviousValue();
/*      */       } 
/*      */       
/*      */       try {
/* 1710 */         JSpinner.this.model.setValue(object);
/* 1711 */         return true;
/* 1712 */       } catch (IllegalArgumentException illegalArgumentException) {
/*      */ 
/*      */         
/* 1715 */         return false;
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
/*      */     private boolean sameWindowAncestor(Component param1Component1, Component param1Component2) {
/* 1727 */       if (param1Component1 == null || param1Component2 == null) {
/* 1728 */         return false;
/*      */       }
/* 1730 */       return 
/* 1731 */         (SwingUtilities.getWindowAncestor(param1Component1) == SwingUtilities.getWindowAncestor(param1Component2));
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
/*      */     public int getIndexAtPoint(Point param1Point) {
/* 1744 */       AccessibleText accessibleText = getEditorAccessibleText();
/* 1745 */       if (accessibleText != null && sameWindowAncestor(JSpinner.this, JSpinner.this.editor)) {
/*      */ 
/*      */         
/* 1748 */         Point point = SwingUtilities.convertPoint(JSpinner.this, param1Point, JSpinner.this
/*      */             
/* 1750 */             .editor);
/* 1751 */         if (point != null) {
/* 1752 */           return accessibleText.getIndexAtPoint(point);
/*      */         }
/*      */       } 
/* 1755 */       return -1;
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
/*      */     public Rectangle getCharacterBounds(int param1Int) {
/* 1769 */       AccessibleText accessibleText = getEditorAccessibleText();
/* 1770 */       if (accessibleText != null) {
/* 1771 */         Rectangle rectangle = accessibleText.getCharacterBounds(param1Int);
/* 1772 */         if (rectangle != null && 
/* 1773 */           sameWindowAncestor(JSpinner.this, JSpinner.this.editor))
/*      */         {
/* 1775 */           return SwingUtilities.convertRectangle(JSpinner.this.editor, rectangle, JSpinner.this);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1780 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getCharCount() {
/* 1789 */       AccessibleText accessibleText = getEditorAccessibleText();
/* 1790 */       if (accessibleText != null) {
/* 1791 */         return accessibleText.getCharCount();
/*      */       }
/* 1793 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getCaretPosition() {
/* 1804 */       AccessibleText accessibleText = getEditorAccessibleText();
/* 1805 */       if (accessibleText != null) {
/* 1806 */         return accessibleText.getCaretPosition();
/*      */       }
/* 1808 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getAtIndex(int param1Int1, int param1Int2) {
/* 1819 */       AccessibleText accessibleText = getEditorAccessibleText();
/* 1820 */       if (accessibleText != null) {
/* 1821 */         return accessibleText.getAtIndex(param1Int1, param1Int2);
/*      */       }
/* 1823 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getAfterIndex(int param1Int1, int param1Int2) {
/* 1834 */       AccessibleText accessibleText = getEditorAccessibleText();
/* 1835 */       if (accessibleText != null) {
/* 1836 */         return accessibleText.getAfterIndex(param1Int1, param1Int2);
/*      */       }
/* 1838 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getBeforeIndex(int param1Int1, int param1Int2) {
/* 1849 */       AccessibleText accessibleText = getEditorAccessibleText();
/* 1850 */       if (accessibleText != null) {
/* 1851 */         return accessibleText.getBeforeIndex(param1Int1, param1Int2);
/*      */       }
/* 1853 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AttributeSet getCharacterAttribute(int param1Int) {
/* 1863 */       AccessibleText accessibleText = getEditorAccessibleText();
/* 1864 */       if (accessibleText != null) {
/* 1865 */         return accessibleText.getCharacterAttribute(param1Int);
/*      */       }
/* 1867 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getSelectionStart() {
/* 1878 */       AccessibleText accessibleText = getEditorAccessibleText();
/* 1879 */       if (accessibleText != null) {
/* 1880 */         return accessibleText.getSelectionStart();
/*      */       }
/* 1882 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getSelectionEnd() {
/* 1893 */       AccessibleText accessibleText = getEditorAccessibleText();
/* 1894 */       if (accessibleText != null) {
/* 1895 */         return accessibleText.getSelectionEnd();
/*      */       }
/* 1897 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getSelectedText() {
/* 1906 */       AccessibleText accessibleText = getEditorAccessibleText();
/* 1907 */       if (accessibleText != null) {
/* 1908 */         return accessibleText.getSelectedText();
/*      */       }
/* 1910 */       return null;
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
/*      */     public void setTextContents(String param1String) {
/* 1924 */       AccessibleEditableText accessibleEditableText = getEditorAccessibleEditableText();
/* 1925 */       if (accessibleEditableText != null) {
/* 1926 */         accessibleEditableText.setTextContents(param1String);
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
/*      */     public void insertTextAtIndex(int param1Int, String param1String) {
/* 1938 */       AccessibleEditableText accessibleEditableText = getEditorAccessibleEditableText();
/* 1939 */       if (accessibleEditableText != null) {
/* 1940 */         accessibleEditableText.insertTextAtIndex(param1Int, param1String);
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
/*      */     public String getTextRange(int param1Int1, int param1Int2) {
/* 1952 */       AccessibleEditableText accessibleEditableText = getEditorAccessibleEditableText();
/* 1953 */       if (accessibleEditableText != null) {
/* 1954 */         return accessibleEditableText.getTextRange(param1Int1, param1Int2);
/*      */       }
/* 1956 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void delete(int param1Int1, int param1Int2) {
/* 1966 */       AccessibleEditableText accessibleEditableText = getEditorAccessibleEditableText();
/* 1967 */       if (accessibleEditableText != null) {
/* 1968 */         accessibleEditableText.delete(param1Int1, param1Int2);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void cut(int param1Int1, int param1Int2) {
/* 1979 */       AccessibleEditableText accessibleEditableText = getEditorAccessibleEditableText();
/* 1980 */       if (accessibleEditableText != null) {
/* 1981 */         accessibleEditableText.cut(param1Int1, param1Int2);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paste(int param1Int) {
/* 1992 */       AccessibleEditableText accessibleEditableText = getEditorAccessibleEditableText();
/* 1993 */       if (accessibleEditableText != null) {
/* 1994 */         accessibleEditableText.paste(param1Int);
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
/*      */     public void replaceText(int param1Int1, int param1Int2, String param1String) {
/* 2007 */       AccessibleEditableText accessibleEditableText = getEditorAccessibleEditableText();
/* 2008 */       if (accessibleEditableText != null) {
/* 2009 */         accessibleEditableText.replaceText(param1Int1, param1Int2, param1String);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void selectText(int param1Int1, int param1Int2) {
/* 2020 */       AccessibleEditableText accessibleEditableText = getEditorAccessibleEditableText();
/* 2021 */       if (accessibleEditableText != null) {
/* 2022 */         accessibleEditableText.selectText(param1Int1, param1Int2);
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
/*      */     public void setAttributes(int param1Int1, int param1Int2, AttributeSet param1AttributeSet) {
/* 2035 */       AccessibleEditableText accessibleEditableText = getEditorAccessibleEditableText();
/* 2036 */       if (accessibleEditableText != null)
/* 2037 */         accessibleEditableText.setAttributes(param1Int1, param1Int2, param1AttributeSet); 
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JSpinner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */