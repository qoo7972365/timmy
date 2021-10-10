/*      */ package javax.swing;
/*      */ 
/*      */ import java.awt.AWTEvent;
/*      */ import java.awt.Component;
/*      */ import java.awt.EventQueue;
/*      */ import java.awt.IllegalComponentStateException;
/*      */ import java.awt.ItemSelectable;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.InputEvent;
/*      */ import java.awt.event.ItemEvent;
/*      */ import java.awt.event.ItemListener;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.beans.Transient;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Locale;
/*      */ import java.util.Vector;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleAction;
/*      */ import javax.accessibility.AccessibleComponent;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleEditableText;
/*      */ import javax.accessibility.AccessibleIcon;
/*      */ import javax.accessibility.AccessibleRelationSet;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.accessibility.AccessibleSelection;
/*      */ import javax.accessibility.AccessibleState;
/*      */ import javax.accessibility.AccessibleStateSet;
/*      */ import javax.accessibility.AccessibleTable;
/*      */ import javax.accessibility.AccessibleText;
/*      */ import javax.accessibility.AccessibleValue;
/*      */ import javax.swing.event.AncestorEvent;
/*      */ import javax.swing.event.AncestorListener;
/*      */ import javax.swing.event.ListDataEvent;
/*      */ import javax.swing.event.ListDataListener;
/*      */ import javax.swing.event.ListSelectionEvent;
/*      */ import javax.swing.event.ListSelectionListener;
/*      */ import javax.swing.event.PopupMenuEvent;
/*      */ import javax.swing.event.PopupMenuListener;
/*      */ import javax.swing.plaf.ComboBoxUI;
/*      */ import javax.swing.plaf.basic.ComboPopup;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JComboBox<E>
/*      */   extends JComponent
/*      */   implements ItemSelectable, ListDataListener, ActionListener, Accessible
/*      */ {
/*      */   private static final String uiClassID = "ComboBoxUI";
/*      */   protected ComboBoxModel<E> dataModel;
/*      */   protected ListCellRenderer<? super E> renderer;
/*      */   protected ComboBoxEditor editor;
/*  120 */   protected int maximumRowCount = 8;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isEditable = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  137 */   protected KeySelectionManager keySelectionManager = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  145 */   protected String actionCommand = "comboBoxChanged";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  153 */   protected boolean lightWeightPopupEnabled = JPopupMenu.getDefaultLightWeightPopupEnabled();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  159 */   protected Object selectedItemReminder = null;
/*      */ 
/*      */ 
/*      */   
/*      */   private E prototypeDisplayValue;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean firingActionEvent = false;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean selectingItem = false;
/*      */ 
/*      */   
/*      */   private Action action;
/*      */ 
/*      */   
/*      */   private PropertyChangeListener actionPropertyChangeListener;
/*      */ 
/*      */ 
/*      */   
/*      */   public JComboBox(ComboBoxModel<E> paramComboBoxModel) {
/*  182 */     setModel(paramComboBoxModel);
/*  183 */     init();
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
/*      */   public JComboBox(E[] paramArrayOfE) {
/*  196 */     setModel(new DefaultComboBoxModel<>(paramArrayOfE));
/*  197 */     init();
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
/*      */   public JComboBox(Vector<E> paramVector) {
/*  210 */     setModel(new DefaultComboBoxModel<>(paramVector));
/*  211 */     init();
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
/*      */   public JComboBox() {
/*  224 */     setModel(new DefaultComboBoxModel<>());
/*  225 */     init();
/*      */   }
/*      */   
/*      */   private void init() {
/*  229 */     installAncestorListener();
/*  230 */     setUIProperty("opaque", Boolean.valueOf(true));
/*  231 */     updateUI();
/*      */   }
/*      */   
/*      */   protected void installAncestorListener() {
/*  235 */     addAncestorListener(new AncestorListener() {
/*  236 */           public void ancestorAdded(AncestorEvent param1AncestorEvent) { JComboBox.this.hidePopup(); } public void ancestorRemoved(AncestorEvent param1AncestorEvent) {
/*  237 */             JComboBox.this.hidePopup();
/*      */           } public void ancestorMoved(AncestorEvent param1AncestorEvent) {
/*  239 */             if (param1AncestorEvent.getSource() != JComboBox.this) {
/*  240 */               JComboBox.this.hidePopup();
/*      */             }
/*      */           }
/*      */         });
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
/*      */   public void setUI(ComboBoxUI paramComboBoxUI) {
/*  257 */     setUI(paramComboBoxUI);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateUI() {
/*  266 */     setUI((ComboBoxUI)UIManager.getUI(this));
/*      */     
/*  268 */     ListCellRenderer<? super E> listCellRenderer = getRenderer();
/*  269 */     if (listCellRenderer instanceof Component) {
/*  270 */       SwingUtilities.updateComponentTreeUI((Component)listCellRenderer);
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
/*      */   public String getUIClassID() {
/*  283 */     return "ComboBoxUI";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ComboBoxUI getUI() {
/*  293 */     return (ComboBoxUI)this.ui;
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
/*      */   public void setModel(ComboBoxModel<E> paramComboBoxModel) {
/*  308 */     ComboBoxModel<E> comboBoxModel = this.dataModel;
/*  309 */     if (comboBoxModel != null) {
/*  310 */       comboBoxModel.removeListDataListener(this);
/*      */     }
/*  312 */     this.dataModel = paramComboBoxModel;
/*  313 */     this.dataModel.addListDataListener(this);
/*      */ 
/*      */     
/*  316 */     this.selectedItemReminder = this.dataModel.getSelectedItem();
/*      */     
/*  318 */     firePropertyChange("model", comboBoxModel, this.dataModel);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ComboBoxModel<E> getModel() {
/*  328 */     return this.dataModel;
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
/*      */   public void setLightWeightPopupEnabled(boolean paramBoolean) {
/*  364 */     boolean bool = this.lightWeightPopupEnabled;
/*  365 */     this.lightWeightPopupEnabled = paramBoolean;
/*  366 */     firePropertyChange("lightWeightPopupEnabled", bool, this.lightWeightPopupEnabled);
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
/*      */   public boolean isLightWeightPopupEnabled() {
/*  378 */     return this.lightWeightPopupEnabled;
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
/*      */   public void setEditable(boolean paramBoolean) {
/*  399 */     boolean bool = this.isEditable;
/*  400 */     this.isEditable = paramBoolean;
/*  401 */     firePropertyChange("editable", bool, this.isEditable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEditable() {
/*  411 */     return this.isEditable;
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
/*      */   public void setMaximumRowCount(int paramInt) {
/*  427 */     int i = this.maximumRowCount;
/*  428 */     this.maximumRowCount = paramInt;
/*  429 */     firePropertyChange("maximumRowCount", i, this.maximumRowCount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaximumRowCount() {
/*  440 */     return this.maximumRowCount;
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
/*      */   public void setRenderer(ListCellRenderer<? super E> paramListCellRenderer) {
/*  465 */     ListCellRenderer<? super E> listCellRenderer = this.renderer;
/*  466 */     this.renderer = paramListCellRenderer;
/*  467 */     firePropertyChange("renderer", listCellRenderer, this.renderer);
/*  468 */     invalidate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ListCellRenderer<? super E> getRenderer() {
/*  479 */     return this.renderer;
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
/*      */   public void setEditor(ComboBoxEditor paramComboBoxEditor) {
/*  497 */     ComboBoxEditor comboBoxEditor = this.editor;
/*      */     
/*  499 */     if (this.editor != null) {
/*  500 */       this.editor.removeActionListener(this);
/*      */     }
/*  502 */     this.editor = paramComboBoxEditor;
/*  503 */     if (this.editor != null) {
/*  504 */       this.editor.addActionListener(this);
/*      */     }
/*  506 */     firePropertyChange("editor", comboBoxEditor, this.editor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ComboBoxEditor getEditor() {
/*  516 */     return this.editor;
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
/*      */   public void setSelectedItem(Object paramObject) {
/*  552 */     Object object1 = this.selectedItemReminder;
/*  553 */     Object object2 = paramObject;
/*  554 */     if (object1 == null || !object1.equals(paramObject)) {
/*      */       
/*  556 */       if (paramObject != null && !isEditable()) {
/*      */ 
/*      */         
/*  559 */         boolean bool = false;
/*  560 */         for (byte b = 0; b < this.dataModel.getSize(); b++) {
/*  561 */           E e = this.dataModel.getElementAt(b);
/*  562 */           if (paramObject.equals(e)) {
/*  563 */             bool = true;
/*  564 */             object2 = e;
/*      */             break;
/*      */           } 
/*      */         } 
/*  568 */         if (!bool) {
/*      */           return;
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  575 */       this.selectingItem = true;
/*  576 */       this.dataModel.setSelectedItem(object2);
/*  577 */       this.selectingItem = false;
/*      */       
/*  579 */       if (this.selectedItemReminder != this.dataModel.getSelectedItem())
/*      */       {
/*      */ 
/*      */         
/*  583 */         selectedItemChanged();
/*      */       }
/*      */     } 
/*  586 */     fireActionEvent();
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
/*      */   public Object getSelectedItem() {
/*  600 */     return this.dataModel.getSelectedItem();
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
/*      */   public void setSelectedIndex(int paramInt) {
/*  615 */     int i = this.dataModel.getSize();
/*      */     
/*  617 */     if (paramInt == -1)
/*  618 */     { setSelectedItem((Object)null); }
/*  619 */     else { if (paramInt < -1 || paramInt >= i) {
/*  620 */         throw new IllegalArgumentException("setSelectedIndex: " + paramInt + " out of bounds");
/*      */       }
/*  622 */       setSelectedItem(this.dataModel.getElementAt(paramInt)); }
/*      */   
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
/*      */   @Transient
/*      */   public int getSelectedIndex() {
/*  641 */     Object object = this.dataModel.getSelectedItem();
/*      */     
/*      */     byte b;
/*      */     int i;
/*  645 */     for (b = 0, i = this.dataModel.getSize(); b < i; b++) {
/*  646 */       E e = this.dataModel.getElementAt(b);
/*  647 */       if (e != null && e.equals(object))
/*  648 */         return b; 
/*      */     } 
/*  650 */     return -1;
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
/*      */   public E getPrototypeDisplayValue() {
/*  662 */     return this.prototypeDisplayValue;
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
/*      */   public void setPrototypeDisplayValue(E paramE) {
/*  687 */     E e = this.prototypeDisplayValue;
/*  688 */     this.prototypeDisplayValue = paramE;
/*  689 */     firePropertyChange("prototypeDisplayValue", e, paramE);
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
/*      */   public void addItem(E paramE) {
/*  715 */     checkMutableComboBoxModel();
/*  716 */     ((MutableComboBoxModel<E>)this.dataModel).addElement(paramE);
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
/*      */   public void insertItemAt(E paramE, int paramInt) {
/*  730 */     checkMutableComboBoxModel();
/*  731 */     ((MutableComboBoxModel<E>)this.dataModel).insertElementAt(paramE, paramInt);
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
/*      */   public void removeItem(Object paramObject) {
/*  743 */     checkMutableComboBoxModel();
/*  744 */     ((MutableComboBoxModel)this.dataModel).removeElement(paramObject);
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
/*      */   public void removeItemAt(int paramInt) {
/*  758 */     checkMutableComboBoxModel();
/*  759 */     ((MutableComboBoxModel)this.dataModel).removeElementAt(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAllItems() {
/*  766 */     checkMutableComboBoxModel();
/*  767 */     MutableComboBoxModel<Object> mutableComboBoxModel = (MutableComboBoxModel)this.dataModel;
/*  768 */     int i = mutableComboBoxModel.getSize();
/*      */     
/*  770 */     if (mutableComboBoxModel instanceof DefaultComboBoxModel) {
/*  771 */       ((DefaultComboBoxModel)mutableComboBoxModel).removeAllElements();
/*      */     } else {
/*      */       
/*  774 */       for (byte b = 0; b < i; b++) {
/*  775 */         Object object = mutableComboBoxModel.getElementAt(0);
/*  776 */         mutableComboBoxModel.removeElement(object);
/*      */       } 
/*      */     } 
/*  779 */     this.selectedItemReminder = null;
/*  780 */     if (isEditable()) {
/*  781 */       this.editor.setItem(null);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void checkMutableComboBoxModel() {
/*  792 */     if (!(this.dataModel instanceof MutableComboBoxModel)) {
/*  793 */       throw new RuntimeException("Cannot use this method with a non-Mutable data model.");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void showPopup() {
/*  801 */     setPopupVisible(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void hidePopup() {
/*  809 */     setPopupVisible(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPopupVisible(boolean paramBoolean) {
/*  816 */     getUI().setPopupVisible(this, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPopupVisible() {
/*  825 */     return getUI().isPopupVisible(this);
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
/*      */   public void addItemListener(ItemListener paramItemListener) {
/*  840 */     this.listenerList.add(ItemListener.class, paramItemListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeItemListener(ItemListener paramItemListener) {
/*  848 */     this.listenerList.remove(ItemListener.class, paramItemListener);
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
/*      */   public ItemListener[] getItemListeners() {
/*  860 */     return this.listenerList.<ItemListener>getListeners(ItemListener.class);
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
/*      */   public void addActionListener(ActionListener paramActionListener) {
/*  874 */     this.listenerList.add(ActionListener.class, paramActionListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeActionListener(ActionListener paramActionListener) {
/*  882 */     if (paramActionListener != null && getAction() == paramActionListener) {
/*  883 */       setAction((Action)null);
/*      */     } else {
/*  885 */       this.listenerList.remove(ActionListener.class, paramActionListener);
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
/*      */   public ActionListener[] getActionListeners() {
/*  898 */     return this.listenerList.<ActionListener>getListeners(ActionListener.class);
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
/*      */   public void addPopupMenuListener(PopupMenuListener paramPopupMenuListener) {
/*  914 */     this.listenerList.add(PopupMenuListener.class, paramPopupMenuListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removePopupMenuListener(PopupMenuListener paramPopupMenuListener) {
/*  925 */     this.listenerList.remove(PopupMenuListener.class, paramPopupMenuListener);
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
/*      */   public PopupMenuListener[] getPopupMenuListeners() {
/*  937 */     return this.listenerList.<PopupMenuListener>getListeners(PopupMenuListener.class);
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
/*      */   public void firePopupMenuWillBecomeVisible() {
/*  950 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*  951 */     PopupMenuEvent popupMenuEvent = null;
/*  952 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/*  953 */       if (arrayOfObject[i] == PopupMenuListener.class) {
/*  954 */         if (popupMenuEvent == null)
/*  955 */           popupMenuEvent = new PopupMenuEvent(this); 
/*  956 */         ((PopupMenuListener)arrayOfObject[i + 1]).popupMenuWillBecomeVisible(popupMenuEvent);
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
/*      */   public void firePopupMenuWillBecomeInvisible() {
/*  971 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*  972 */     PopupMenuEvent popupMenuEvent = null;
/*  973 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/*  974 */       if (arrayOfObject[i] == PopupMenuListener.class) {
/*  975 */         if (popupMenuEvent == null)
/*  976 */           popupMenuEvent = new PopupMenuEvent(this); 
/*  977 */         ((PopupMenuListener)arrayOfObject[i + 1]).popupMenuWillBecomeInvisible(popupMenuEvent);
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
/*      */   public void firePopupMenuCanceled() {
/*  992 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*  993 */     PopupMenuEvent popupMenuEvent = null;
/*  994 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/*  995 */       if (arrayOfObject[i] == PopupMenuListener.class) {
/*  996 */         if (popupMenuEvent == null)
/*  997 */           popupMenuEvent = new PopupMenuEvent(this); 
/*  998 */         ((PopupMenuListener)arrayOfObject[i + 1]).popupMenuCanceled(popupMenuEvent);
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
/*      */   public void setActionCommand(String paramString) {
/* 1013 */     this.actionCommand = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getActionCommand() {
/* 1024 */     return this.actionCommand;
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
/*      */   public void setAction(Action paramAction) {
/* 1070 */     Action action = getAction();
/* 1071 */     if (this.action == null || !this.action.equals(paramAction)) {
/* 1072 */       this.action = paramAction;
/* 1073 */       if (action != null) {
/* 1074 */         removeActionListener(action);
/* 1075 */         action.removePropertyChangeListener(this.actionPropertyChangeListener);
/* 1076 */         this.actionPropertyChangeListener = null;
/*      */       } 
/* 1078 */       configurePropertiesFromAction(this.action);
/* 1079 */       if (this.action != null) {
/*      */         
/* 1081 */         if (!isListener(ActionListener.class, this.action)) {
/* 1082 */           addActionListener(this.action);
/*      */         }
/*      */         
/* 1085 */         this.actionPropertyChangeListener = createActionPropertyChangeListener(this.action);
/* 1086 */         this.action.addPropertyChangeListener(this.actionPropertyChangeListener);
/*      */       } 
/* 1088 */       firePropertyChange("action", action, this.action);
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean isListener(Class paramClass, ActionListener paramActionListener) {
/* 1093 */     boolean bool = false;
/* 1094 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/* 1095 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 1096 */       if (arrayOfObject[i] == paramClass && arrayOfObject[i + 1] == paramActionListener) {
/* 1097 */         bool = true;
/*      */       }
/*      */     } 
/* 1100 */     return bool;
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
/*      */   public Action getAction() {
/* 1115 */     return this.action;
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
/*      */   protected void configurePropertiesFromAction(Action paramAction) {
/* 1131 */     AbstractAction.setEnabledFromAction(this, paramAction);
/* 1132 */     AbstractAction.setToolTipTextFromAction(this, paramAction);
/* 1133 */     setActionCommandFromAction(paramAction);
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
/*      */   protected PropertyChangeListener createActionPropertyChangeListener(Action paramAction) {
/* 1151 */     return new ComboBoxActionPropertyChangeListener(this, paramAction);
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
/*      */   protected void actionPropertyChanged(Action paramAction, String paramString) {
/* 1174 */     if (paramString == "ActionCommandKey") {
/* 1175 */       setActionCommandFromAction(paramAction);
/* 1176 */     } else if (paramString == "enabled") {
/* 1177 */       AbstractAction.setEnabledFromAction(this, paramAction);
/* 1178 */     } else if ("ShortDescription" == paramString) {
/* 1179 */       AbstractAction.setToolTipTextFromAction(this, paramAction);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setActionCommandFromAction(Action paramAction) {
/* 1184 */     setActionCommand((paramAction != null) ? (String)paramAction
/* 1185 */         .getValue("ActionCommandKey") : null);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class ComboBoxActionPropertyChangeListener
/*      */     extends ActionPropertyChangeListener<JComboBox<?>>
/*      */   {
/*      */     ComboBoxActionPropertyChangeListener(JComboBox<?> param1JComboBox, Action param1Action) {
/* 1193 */       super(param1JComboBox, param1Action);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void actionPropertyChanged(JComboBox<?> param1JComboBox, Action param1Action, PropertyChangeEvent param1PropertyChangeEvent) {
/* 1198 */       if (AbstractAction.shouldReconfigure(param1PropertyChangeEvent)) {
/* 1199 */         param1JComboBox.configurePropertiesFromAction(param1Action);
/*      */       } else {
/* 1201 */         param1JComboBox.actionPropertyChanged(param1Action, param1PropertyChangeEvent.getPropertyName());
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
/*      */   protected void fireItemStateChanged(ItemEvent paramItemEvent) {
/* 1215 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*      */ 
/*      */     
/* 1218 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 1219 */       if (arrayOfObject[i] == ItemListener.class)
/*      */       {
/*      */ 
/*      */         
/* 1223 */         ((ItemListener)arrayOfObject[i + 1]).itemStateChanged(paramItemEvent);
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
/*      */   protected void fireActionEvent() {
/* 1235 */     if (!this.firingActionEvent) {
/*      */       
/* 1237 */       this.firingActionEvent = true;
/* 1238 */       ActionEvent actionEvent = null;
/*      */       
/* 1240 */       Object[] arrayOfObject = this.listenerList.getListenerList();
/* 1241 */       long l = EventQueue.getMostRecentEventTime();
/* 1242 */       int i = 0;
/* 1243 */       AWTEvent aWTEvent = EventQueue.getCurrentEvent();
/* 1244 */       if (aWTEvent instanceof InputEvent) {
/* 1245 */         i = ((InputEvent)aWTEvent).getModifiers();
/* 1246 */       } else if (aWTEvent instanceof ActionEvent) {
/* 1247 */         i = ((ActionEvent)aWTEvent).getModifiers();
/*      */       } 
/*      */ 
/*      */       
/* 1251 */       for (int j = arrayOfObject.length - 2; j >= 0; j -= 2) {
/* 1252 */         if (arrayOfObject[j] == ActionListener.class) {
/*      */           
/* 1254 */           if (actionEvent == null)
/*      */           {
/* 1256 */             actionEvent = new ActionEvent(this, 1001, getActionCommand(), l, i);
/*      */           }
/* 1258 */           ((ActionListener)arrayOfObject[j + 1]).actionPerformed(actionEvent);
/*      */         } 
/*      */       } 
/* 1261 */       this.firingActionEvent = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void selectedItemChanged() {
/* 1270 */     if (this.selectedItemReminder != null) {
/* 1271 */       fireItemStateChanged(new ItemEvent(this, 701, this.selectedItemReminder, 2));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1277 */     this.selectedItemReminder = this.dataModel.getSelectedItem();
/*      */     
/* 1279 */     if (this.selectedItemReminder != null) {
/* 1280 */       fireItemStateChanged(new ItemEvent(this, 701, this.selectedItemReminder, 1));
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
/*      */   public Object[] getSelectedObjects() {
/* 1295 */     Object object = getSelectedItem();
/* 1296 */     if (object == null) {
/* 1297 */       return new Object[0];
/*      */     }
/* 1299 */     Object[] arrayOfObject = new Object[1];
/* 1300 */     arrayOfObject[0] = object;
/* 1301 */     return arrayOfObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void actionPerformed(ActionEvent paramActionEvent) {
/* 1310 */     ComboBoxEditor comboBoxEditor = getEditor();
/* 1311 */     if (comboBoxEditor != null && paramActionEvent != null && (comboBoxEditor == paramActionEvent.getSource() || comboBoxEditor
/* 1312 */       .getEditorComponent() == paramActionEvent.getSource())) {
/* 1313 */       setPopupVisible(false);
/* 1314 */       getModel().setSelectedItem(comboBoxEditor.getItem());
/* 1315 */       String str = getActionCommand();
/* 1316 */       setActionCommand("comboBoxEdited");
/* 1317 */       fireActionEvent();
/* 1318 */       setActionCommand(str);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void contentsChanged(ListDataEvent paramListDataEvent) {
/* 1327 */     Object object1 = this.selectedItemReminder;
/* 1328 */     Object object2 = this.dataModel.getSelectedItem();
/* 1329 */     if (object1 == null || !object1.equals(object2)) {
/* 1330 */       selectedItemChanged();
/* 1331 */       if (!this.selectingItem) {
/* 1332 */         fireActionEvent();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void intervalAdded(ListDataEvent paramListDataEvent) {
/* 1342 */     if (this.selectedItemReminder != this.dataModel.getSelectedItem()) {
/* 1343 */       selectedItemChanged();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void intervalRemoved(ListDataEvent paramListDataEvent) {
/* 1352 */     contentsChanged(paramListDataEvent);
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
/*      */   public boolean selectWithKeyChar(char paramChar) {
/* 1366 */     if (this.keySelectionManager == null) {
/* 1367 */       this.keySelectionManager = createDefaultKeySelectionManager();
/*      */     }
/* 1369 */     int i = this.keySelectionManager.selectionForKey(paramChar, getModel());
/* 1370 */     if (i != -1) {
/* 1371 */       setSelectedIndex(i);
/* 1372 */       return true;
/*      */     } 
/*      */     
/* 1375 */     return false;
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
/*      */   public void setEnabled(boolean paramBoolean) {
/* 1391 */     super.setEnabled(paramBoolean);
/* 1392 */     firePropertyChange("enabled", !isEnabled(), isEnabled());
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
/*      */   public void configureEditor(ComboBoxEditor paramComboBoxEditor, Object paramObject) {
/* 1404 */     paramComboBoxEditor.setItem(paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processKeyEvent(KeyEvent paramKeyEvent) {
/* 1415 */     if (paramKeyEvent.getKeyCode() == 9) {
/* 1416 */       hidePopup();
/*      */     }
/* 1418 */     super.processKeyEvent(paramKeyEvent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean processKeyBinding(KeyStroke paramKeyStroke, KeyEvent paramKeyEvent, int paramInt, boolean paramBoolean) {
/* 1426 */     if (super.processKeyBinding(paramKeyStroke, paramKeyEvent, paramInt, paramBoolean)) {
/* 1427 */       return true;
/*      */     }
/*      */     
/* 1430 */     if (!isEditable() || paramInt != 0 || getEditor() == null || 
/* 1431 */       !Boolean.TRUE.equals(getClientProperty("JComboBox.isTableCellEditor"))) {
/* 1432 */       return false;
/*      */     }
/*      */     
/* 1435 */     Component component = getEditor().getEditorComponent();
/* 1436 */     if (component instanceof JComponent) {
/* 1437 */       JComponent jComponent = (JComponent)component;
/* 1438 */       return jComponent.processKeyBinding(paramKeyStroke, paramKeyEvent, 0, paramBoolean);
/*      */     } 
/* 1440 */     return false;
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
/*      */   public void setKeySelectionManager(KeySelectionManager paramKeySelectionManager) {
/* 1453 */     this.keySelectionManager = paramKeySelectionManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeySelectionManager getKeySelectionManager() {
/* 1462 */     return this.keySelectionManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getItemCount() {
/* 1472 */     return this.dataModel.getSize();
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
/*      */   public E getItemAt(int paramInt) {
/* 1486 */     return this.dataModel.getElementAt(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected KeySelectionManager createDefaultKeySelectionManager() {
/* 1496 */     return new DefaultKeySelectionManager();
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
/*      */   public static interface KeySelectionManager
/*      */   {
/*      */     int selectionForKey(char param1Char, ComboBoxModel param1ComboBoxModel);
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
/*      */   class DefaultKeySelectionManager
/*      */     implements KeySelectionManager, Serializable
/*      */   {
/*      */     public int selectionForKey(char param1Char, ComboBoxModel<Object> param1ComboBoxModel) {
/* 1525 */       byte b2 = -1;
/* 1526 */       Object object = param1ComboBoxModel.getSelectedItem();
/*      */ 
/*      */ 
/*      */       
/* 1530 */       if (object != null) {
/* 1531 */         byte b; int j; for (b = 0, j = param1ComboBoxModel.getSize(); b < j; b++) {
/* 1532 */           if (object == param1ComboBoxModel.getElementAt(b)) {
/* 1533 */             b2 = b;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/* 1539 */       String str = ("" + param1Char).toLowerCase();
/* 1540 */       param1Char = str.charAt(0); byte b1;
/*      */       int i;
/* 1542 */       for (b1 = ++b2, i = param1ComboBoxModel.getSize(); b1 < i; b1++) {
/* 1543 */         Object object1 = param1ComboBoxModel.getElementAt(b1);
/* 1544 */         if (object1 != null && object1.toString() != null) {
/* 1545 */           String str1 = object1.toString().toLowerCase();
/* 1546 */           if (str1.length() > 0 && str1.charAt(0) == param1Char) {
/* 1547 */             return b1;
/*      */           }
/*      */         } 
/*      */       } 
/* 1551 */       for (b1 = 0; b1 < b2; b1++) {
/* 1552 */         Object object1 = param1ComboBoxModel.getElementAt(b1);
/* 1553 */         if (object1 != null && object1.toString() != null) {
/* 1554 */           String str1 = object1.toString().toLowerCase();
/* 1555 */           if (str1.length() > 0 && str1.charAt(0) == param1Char)
/* 1556 */             return b1; 
/*      */         } 
/*      */       } 
/* 1559 */       return -1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1570 */     paramObjectOutputStream.defaultWriteObject();
/* 1571 */     if (getUIClassID().equals("ComboBoxUI")) {
/* 1572 */       byte b = JComponent.getWriteObjCounter(this);
/* 1573 */       b = (byte)(b - 1); JComponent.setWriteObjCounter(this, b);
/* 1574 */       if (b == 0 && this.ui != null) {
/* 1575 */         this.ui.installUI(this);
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
/*      */   protected String paramString() {
/* 1592 */     String str1 = (this.selectedItemReminder != null) ? this.selectedItemReminder.toString() : "";
/*      */     
/* 1594 */     String str2 = this.isEditable ? "true" : "false";
/* 1595 */     String str3 = this.lightWeightPopupEnabled ? "true" : "false";
/*      */ 
/*      */     
/* 1598 */     return super.paramString() + ",isEditable=" + str2 + ",lightWeightPopupEnabled=" + str3 + ",maximumRowCount=" + this.maximumRowCount + ",selectedItemReminder=" + str1;
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
/*      */   public AccessibleContext getAccessibleContext() {
/* 1620 */     if (this.accessibleContext == null) {
/* 1621 */       this.accessibleContext = new AccessibleJComboBox();
/*      */     }
/* 1623 */     return this.accessibleContext;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class AccessibleJComboBox
/*      */     extends JComponent.AccessibleJComponent
/*      */     implements AccessibleAction, AccessibleSelection
/*      */   {
/*      */     private JList popupList;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1645 */     private Accessible previousSelectedAccessible = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private EditorAccessibleContext editorAccessibleContext;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class AccessibleJComboBoxPropertyChangeListener
/*      */       implements PropertyChangeListener
/*      */     {
/*      */       private AccessibleJComboBoxPropertyChangeListener() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void propertyChange(PropertyChangeEvent param2PropertyChangeEvent) {
/* 1676 */         if (param2PropertyChangeEvent.getPropertyName() == "editor")
/*      */         {
/*      */           
/* 1679 */           JComboBox.AccessibleJComboBox.this.setEditorNameAndDescription();
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void setEditorNameAndDescription() {
/* 1688 */       ComboBoxEditor comboBoxEditor = JComboBox.this.getEditor();
/* 1689 */       if (comboBoxEditor != null) {
/* 1690 */         Component component = comboBoxEditor.getEditorComponent();
/* 1691 */         if (component instanceof Accessible) {
/* 1692 */           AccessibleContext accessibleContext = component.getAccessibleContext();
/* 1693 */           if (accessibleContext != null) {
/* 1694 */             accessibleContext.setAccessibleName(getAccessibleName());
/* 1695 */             accessibleContext.setAccessibleDescription(getAccessibleDescription());
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class AccessibleJComboBoxPopupMenuListener
/*      */       implements PopupMenuListener
/*      */     {
/*      */       private AccessibleJComboBoxPopupMenuListener() {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void popupMenuWillBecomeVisible(PopupMenuEvent param2PopupMenuEvent) {
/* 1713 */         if (JComboBox.AccessibleJComboBox.this.popupList == null) {
/*      */           return;
/*      */         }
/* 1716 */         int i = JComboBox.AccessibleJComboBox.this.popupList.getSelectedIndex();
/* 1717 */         if (i < 0) {
/*      */           return;
/*      */         }
/* 1720 */         JComboBox.AccessibleJComboBox.this.previousSelectedAccessible = JComboBox.AccessibleJComboBox.this
/* 1721 */           .popupList.getAccessibleContext().getAccessibleChild(i);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void popupMenuWillBecomeInvisible(PopupMenuEvent param2PopupMenuEvent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void popupMenuCanceled(PopupMenuEvent param2PopupMenuEvent) {}
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class AccessibleJComboBoxListSelectionListener
/*      */       implements ListSelectionListener
/*      */     {
/*      */       private AccessibleJComboBoxListSelectionListener() {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void valueChanged(ListSelectionEvent param2ListSelectionEvent) {
/* 1748 */         if (JComboBox.AccessibleJComboBox.this.popupList == null) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 1753 */         int i = JComboBox.AccessibleJComboBox.this.popupList.getSelectedIndex();
/* 1754 */         if (i < 0) {
/*      */           return;
/*      */         }
/*      */         
/* 1758 */         Accessible accessible = JComboBox.AccessibleJComboBox.this.popupList.getAccessibleContext().getAccessibleChild(i);
/* 1759 */         if (accessible == null) {
/*      */           return;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1767 */         if (JComboBox.AccessibleJComboBox.this.previousSelectedAccessible != null) {
/* 1768 */           PropertyChangeEvent propertyChangeEvent1 = new PropertyChangeEvent(JComboBox.AccessibleJComboBox.this.previousSelectedAccessible, "AccessibleState", AccessibleState.FOCUSED, null);
/*      */ 
/*      */           
/* 1771 */           JComboBox.AccessibleJComboBox.this.firePropertyChange("AccessibleState", null, propertyChangeEvent1);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1776 */         PropertyChangeEvent propertyChangeEvent = new PropertyChangeEvent(accessible, "AccessibleState", null, AccessibleState.FOCUSED);
/*      */ 
/*      */         
/* 1779 */         JComboBox.AccessibleJComboBox.this.firePropertyChange("AccessibleState", null, propertyChangeEvent);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1784 */         JComboBox.AccessibleJComboBox.this.firePropertyChange("AccessibleActiveDescendant", JComboBox.AccessibleJComboBox.this
/* 1785 */             .previousSelectedAccessible, accessible);
/*      */ 
/*      */         
/* 1788 */         JComboBox.AccessibleJComboBox.this.previousSelectedAccessible = accessible;
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
/*      */     public int getAccessibleChildrenCount() {
/* 1802 */       if (JComboBox.this.ui != null) {
/* 1803 */         return JComboBox.this.ui.getAccessibleChildrenCount(JComboBox.this);
/*      */       }
/* 1805 */       return super.getAccessibleChildrenCount();
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
/* 1820 */       if (JComboBox.this.ui != null) {
/* 1821 */         return JComboBox.this.ui.getAccessibleChild(JComboBox.this, param1Int);
/*      */       }
/* 1823 */       return super.getAccessibleChild(param1Int);
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
/*      */     public AccessibleRole getAccessibleRole() {
/* 1835 */       return AccessibleRole.COMBO_BOX;
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
/*      */     public AccessibleStateSet getAccessibleStateSet() {
/* 1854 */       AccessibleStateSet accessibleStateSet = super.getAccessibleStateSet();
/* 1855 */       if (accessibleStateSet == null) {
/* 1856 */         accessibleStateSet = new AccessibleStateSet();
/*      */       }
/* 1858 */       if (JComboBox.this.isPopupVisible()) {
/* 1859 */         accessibleStateSet.add(AccessibleState.EXPANDED);
/*      */       } else {
/* 1861 */         accessibleStateSet.add(AccessibleState.COLLAPSED);
/*      */       } 
/* 1863 */       return accessibleStateSet;
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
/*      */     public AccessibleAction getAccessibleAction() {
/* 1875 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getAccessibleActionDescription(int param1Int) {
/* 1884 */       if (param1Int == 0) {
/* 1885 */         return UIManager.getString("ComboBox.togglePopupText");
/*      */       }
/*      */       
/* 1888 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getAccessibleActionCount() {
/* 1899 */       return 1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean doAccessibleAction(int param1Int) {
/* 1909 */       if (param1Int == 0) {
/* 1910 */         JComboBox.this.setPopupVisible(!JComboBox.this.isPopupVisible());
/* 1911 */         return true;
/*      */       } 
/*      */       
/* 1914 */       return false;
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
/*      */     public AccessibleSelection getAccessibleSelection() {
/* 1928 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getAccessibleSelectionCount() {
/* 1939 */       Object object = JComboBox.this.getSelectedItem();
/* 1940 */       if (object != null) {
/* 1941 */         return 1;
/*      */       }
/* 1943 */       return 0;
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
/*      */     public Accessible getAccessibleSelection(int param1Int) {
/* 1963 */       Accessible accessible = JComboBox.this.getUI().getAccessibleChild(JComboBox.this, 0);
/* 1964 */       if (accessible != null && accessible instanceof ComboPopup) {
/*      */ 
/*      */ 
/*      */         
/* 1968 */         JList jList = ((ComboPopup)accessible).getList();
/*      */ 
/*      */         
/* 1971 */         AccessibleContext accessibleContext = jList.getAccessibleContext();
/* 1972 */         if (accessibleContext != null) {
/* 1973 */           AccessibleSelection accessibleSelection = accessibleContext.getAccessibleSelection();
/* 1974 */           if (accessibleSelection != null) {
/* 1975 */             return accessibleSelection.getAccessibleSelection(param1Int);
/*      */           }
/*      */         } 
/*      */       } 
/* 1979 */       return null;
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
/*      */     public boolean isAccessibleChildSelected(int param1Int) {
/* 1993 */       return (JComboBox.this.getSelectedIndex() == param1Int);
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
/*      */     public void addAccessibleSelection(int param1Int) {
/* 2009 */       clearAccessibleSelection();
/* 2010 */       JComboBox.this.setSelectedIndex(param1Int);
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
/*      */     public void removeAccessibleSelection(int param1Int) {
/* 2023 */       if (JComboBox.this.getSelectedIndex() == param1Int) {
/* 2024 */         clearAccessibleSelection();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void clearAccessibleSelection() {
/* 2034 */       JComboBox.this.setSelectedIndex(-1);
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
/*      */     public void selectAllAccessibleSelection() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleJComboBox() {
/* 2055 */       this.editorAccessibleContext = null; JComboBox.this.addPropertyChangeListener(new AccessibleJComboBoxPropertyChangeListener()); setEditorNameAndDescription(); Accessible accessible = JComboBox.this.getUI().getAccessibleChild(JComboBox.this, 0); if (accessible instanceof ComboPopup) {
/*      */         this.popupList = ((ComboPopup)accessible).getList();
/*      */         this.popupList.addListSelectionListener(new AccessibleJComboBoxListSelectionListener());
/*      */       } 
/* 2059 */       JComboBox.this.addPopupMenuListener(new AccessibleJComboBoxPopupMenuListener()); } private class AccessibleEditor implements Accessible { public AccessibleContext getAccessibleContext() { if (JComboBox.AccessibleJComboBox.this.editorAccessibleContext == null) {
/* 2060 */           Component component = JComboBox.this.getEditor().getEditorComponent();
/* 2061 */           if (component instanceof Accessible) {
/* 2062 */             JComboBox.AccessibleJComboBox.this.editorAccessibleContext = new JComboBox.AccessibleJComboBox.EditorAccessibleContext((Accessible)component);
/*      */           }
/*      */         } 
/*      */         
/* 2066 */         return JComboBox.AccessibleJComboBox.this.editorAccessibleContext; }
/*      */        }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private class EditorAccessibleContext
/*      */       extends AccessibleContext
/*      */     {
/*      */       private AccessibleContext ac;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private EditorAccessibleContext() {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       EditorAccessibleContext(Accessible param2Accessible) {
/* 2089 */         this.ac = param2Accessible.getAccessibleContext();
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
/*      */       public String getAccessibleName() {
/* 2108 */         return this.ac.getAccessibleName();
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
/*      */       public void setAccessibleName(String param2String) {
/* 2126 */         this.ac.setAccessibleName(param2String);
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
/*      */       public String getAccessibleDescription() {
/* 2142 */         return this.ac.getAccessibleDescription();
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
/*      */       public void setAccessibleDescription(String param2String) {
/* 2160 */         this.ac.setAccessibleDescription(param2String);
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
/*      */       public AccessibleRole getAccessibleRole() {
/* 2182 */         return this.ac.getAccessibleRole();
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
/*      */       public AccessibleStateSet getAccessibleStateSet() {
/* 2198 */         return this.ac.getAccessibleStateSet();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Accessible getAccessibleParent() {
/* 2208 */         return this.ac.getAccessibleParent();
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
/*      */       public void setAccessibleParent(Accessible param2Accessible) {
/* 2220 */         this.ac.setAccessibleParent(param2Accessible);
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
/*      */       public int getAccessibleIndexInParent() {
/* 2234 */         return JComboBox.this.getSelectedIndex();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getAccessibleChildrenCount() {
/* 2243 */         return this.ac.getAccessibleChildrenCount();
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
/*      */       public Accessible getAccessibleChild(int param2Int) {
/* 2257 */         return this.ac.getAccessibleChild(param2Int);
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
/*      */       public Locale getLocale() throws IllegalComponentStateException {
/* 2273 */         return this.ac.getLocale();
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
/*      */       public void addPropertyChangeListener(PropertyChangeListener param2PropertyChangeListener) {
/* 2292 */         this.ac.addPropertyChangeListener(param2PropertyChangeListener);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void removePropertyChangeListener(PropertyChangeListener param2PropertyChangeListener) {
/* 2303 */         this.ac.removePropertyChangeListener(param2PropertyChangeListener);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleAction getAccessibleAction() {
/* 2314 */         return this.ac.getAccessibleAction();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleComponent getAccessibleComponent() {
/* 2325 */         return this.ac.getAccessibleComponent();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleSelection getAccessibleSelection() {
/* 2336 */         return this.ac.getAccessibleSelection();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleText getAccessibleText() {
/* 2347 */         return this.ac.getAccessibleText();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleEditableText getAccessibleEditableText() {
/* 2358 */         return this.ac.getAccessibleEditableText();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleValue getAccessibleValue() {
/* 2369 */         return this.ac.getAccessibleValue();
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
/*      */       public AccessibleIcon[] getAccessibleIcon() {
/* 2381 */         return this.ac.getAccessibleIcon();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleRelationSet getAccessibleRelationSet() {
/* 2392 */         return this.ac.getAccessibleRelationSet();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleTable getAccessibleTable() {
/* 2403 */         return this.ac.getAccessibleTable();
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
/*      */       
/*      */       public void firePropertyChange(String param2String, Object param2Object1, Object param2Object2) {
/* 2430 */         this.ac.firePropertyChange(param2String, param2Object1, param2Object2);
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JComboBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */