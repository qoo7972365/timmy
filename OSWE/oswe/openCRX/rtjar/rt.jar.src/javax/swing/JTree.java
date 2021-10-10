/*      */ package javax.swing;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Cursor;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.HeadlessException;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.beans.ConstructorProperties;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collections;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Locale;
/*      */ import java.util.Set;
/*      */ import java.util.Stack;
/*      */ import java.util.Vector;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleAction;
/*      */ import javax.accessibility.AccessibleComponent;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.accessibility.AccessibleSelection;
/*      */ import javax.accessibility.AccessibleState;
/*      */ import javax.accessibility.AccessibleStateSet;
/*      */ import javax.accessibility.AccessibleText;
/*      */ import javax.accessibility.AccessibleValue;
/*      */ import javax.swing.event.TreeExpansionEvent;
/*      */ import javax.swing.event.TreeExpansionListener;
/*      */ import javax.swing.event.TreeModelEvent;
/*      */ import javax.swing.event.TreeModelListener;
/*      */ import javax.swing.event.TreeSelectionEvent;
/*      */ import javax.swing.event.TreeSelectionListener;
/*      */ import javax.swing.event.TreeWillExpandListener;
/*      */ import javax.swing.plaf.TreeUI;
/*      */ import javax.swing.text.Position;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import javax.swing.tree.DefaultTreeModel;
/*      */ import javax.swing.tree.DefaultTreeSelectionModel;
/*      */ import javax.swing.tree.ExpandVetoException;
/*      */ import javax.swing.tree.RowMapper;
/*      */ import javax.swing.tree.TreeCellEditor;
/*      */ import javax.swing.tree.TreeCellRenderer;
/*      */ import javax.swing.tree.TreeModel;
/*      */ import javax.swing.tree.TreeNode;
/*      */ import javax.swing.tree.TreePath;
/*      */ import javax.swing.tree.TreeSelectionModel;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.swing.SwingUtilities2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JTree
/*      */   extends JComponent
/*      */   implements Scrollable, Accessible
/*      */ {
/*      */   private static final String uiClassID = "TreeUI";
/*      */   protected transient TreeModel treeModel;
/*      */   protected transient TreeSelectionModel selectionModel;
/*      */   protected boolean rootVisible;
/*      */   protected transient TreeCellRenderer cellRenderer;
/*      */   protected int rowHeight;
/*      */   private boolean rowHeightSet = false;
/*      */   private transient Hashtable<TreePath, Boolean> expandedState;
/*      */   protected boolean showsRootHandles;
/*      */   private boolean showsRootHandlesSet = false;
/*      */   protected transient TreeSelectionRedirector selectionRedirector;
/*      */   protected transient TreeCellEditor cellEditor;
/*      */   protected boolean editable;
/*      */   protected boolean largeModel;
/*      */   protected int visibleRowCount;
/*      */   protected boolean invokesStopCellEditing;
/*      */   protected boolean scrollsOnExpand;
/*      */   private boolean scrollsOnExpandSet = false;
/*      */   protected int toggleClickCount;
/*      */   protected transient TreeModelListener treeModelListener;
/*      */   private transient Stack<Stack<TreePath>> expandedStack;
/*      */   private TreePath leadPath;
/*      */   private TreePath anchorPath;
/*      */   private boolean expandsSelectedPaths;
/*      */   private boolean settingUI;
/*      */   private boolean dragEnabled;
/*  316 */   private DropMode dropMode = DropMode.USE_SELECTION;
/*      */ 
/*      */ 
/*      */   
/*      */   private transient DropLocation dropLocation;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class DropLocation
/*      */     extends TransferHandler.DropLocation
/*      */   {
/*      */     private final TreePath path;
/*      */ 
/*      */     
/*      */     private final int index;
/*      */ 
/*      */ 
/*      */     
/*      */     private DropLocation(Point param1Point, TreePath param1TreePath, int param1Int) {
/*  335 */       super(param1Point);
/*  336 */       this.path = param1TreePath;
/*  337 */       this.index = param1Int;
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
/*      */     
/*      */     public int getChildIndex() {
/*  365 */       return this.index;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TreePath getPath() {
/*  404 */       return this.path;
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
/*      */     public String toString() {
/*  416 */       return getClass().getName() + "[dropPoint=" + 
/*  417 */         getDropPoint() + ",path=" + this.path + ",childIndex=" + this.index + "]";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  426 */   private int expandRow = -1;
/*      */   private TreeTimer dropTimer;
/*      */   private transient TreeExpansionListener uiTreeExpansionListener;
/*      */   
/*      */   private class TreeTimer extends Timer { public TreeTimer() {
/*  431 */       super(2000, null);
/*  432 */       setRepeats(false);
/*      */     }
/*      */     
/*      */     public void fireActionPerformed(ActionEvent param1ActionEvent) {
/*  436 */       JTree.this.expandRow(JTree.this.expandRow);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  458 */   private static int TEMP_STACK_SIZE = 11;
/*      */ 
/*      */   
/*      */   public static final String CELL_RENDERER_PROPERTY = "cellRenderer";
/*      */ 
/*      */   
/*      */   public static final String TREE_MODEL_PROPERTY = "model";
/*      */ 
/*      */   
/*      */   public static final String ROOT_VISIBLE_PROPERTY = "rootVisible";
/*      */ 
/*      */   
/*      */   public static final String SHOWS_ROOT_HANDLES_PROPERTY = "showsRootHandles";
/*      */ 
/*      */   
/*      */   public static final String ROW_HEIGHT_PROPERTY = "rowHeight";
/*      */ 
/*      */   
/*      */   public static final String CELL_EDITOR_PROPERTY = "cellEditor";
/*      */ 
/*      */   
/*      */   public static final String EDITABLE_PROPERTY = "editable";
/*      */ 
/*      */   
/*      */   public static final String LARGE_MODEL_PROPERTY = "largeModel";
/*      */ 
/*      */   
/*      */   public static final String SELECTION_MODEL_PROPERTY = "selectionModel";
/*      */ 
/*      */   
/*      */   public static final String VISIBLE_ROW_COUNT_PROPERTY = "visibleRowCount";
/*      */ 
/*      */   
/*      */   public static final String INVOKES_STOP_CELL_EDITING_PROPERTY = "invokesStopCellEditing";
/*      */ 
/*      */   
/*      */   public static final String SCROLLS_ON_EXPAND_PROPERTY = "scrollsOnExpand";
/*      */ 
/*      */   
/*      */   public static final String TOGGLE_CLICK_COUNT_PROPERTY = "toggleClickCount";
/*      */   
/*      */   public static final String LEAD_SELECTION_PATH_PROPERTY = "leadSelectionPath";
/*      */   
/*      */   public static final String ANCHOR_SELECTION_PATH_PROPERTY = "anchorSelectionPath";
/*      */   
/*      */   public static final String EXPANDS_SELECTED_PATHS_PROPERTY = "expandsSelectedPaths";
/*      */ 
/*      */   
/*      */   protected static TreeModel getDefaultTreeModel() {
/*  507 */     DefaultMutableTreeNode defaultMutableTreeNode1 = new DefaultMutableTreeNode("JTree");
/*      */ 
/*      */     
/*  510 */     DefaultMutableTreeNode defaultMutableTreeNode2 = new DefaultMutableTreeNode("colors");
/*  511 */     defaultMutableTreeNode1.add(defaultMutableTreeNode2);
/*  512 */     defaultMutableTreeNode2.add(new DefaultMutableTreeNode("blue"));
/*  513 */     defaultMutableTreeNode2.add(new DefaultMutableTreeNode("violet"));
/*  514 */     defaultMutableTreeNode2.add(new DefaultMutableTreeNode("red"));
/*  515 */     defaultMutableTreeNode2.add(new DefaultMutableTreeNode("yellow"));
/*      */     
/*  517 */     defaultMutableTreeNode2 = new DefaultMutableTreeNode("sports");
/*  518 */     defaultMutableTreeNode1.add(defaultMutableTreeNode2);
/*  519 */     defaultMutableTreeNode2.add(new DefaultMutableTreeNode("basketball"));
/*  520 */     defaultMutableTreeNode2.add(new DefaultMutableTreeNode("soccer"));
/*  521 */     defaultMutableTreeNode2.add(new DefaultMutableTreeNode("football"));
/*  522 */     defaultMutableTreeNode2.add(new DefaultMutableTreeNode("hockey"));
/*      */     
/*  524 */     defaultMutableTreeNode2 = new DefaultMutableTreeNode("food");
/*  525 */     defaultMutableTreeNode1.add(defaultMutableTreeNode2);
/*  526 */     defaultMutableTreeNode2.add(new DefaultMutableTreeNode("hot dogs"));
/*  527 */     defaultMutableTreeNode2.add(new DefaultMutableTreeNode("pizza"));
/*  528 */     defaultMutableTreeNode2.add(new DefaultMutableTreeNode("ravioli"));
/*  529 */     defaultMutableTreeNode2.add(new DefaultMutableTreeNode("bananas"));
/*  530 */     return new DefaultTreeModel(defaultMutableTreeNode1);
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
/*      */   protected static TreeModel createTreeModel(Object paramObject) {
/*      */     DefaultMutableTreeNode defaultMutableTreeNode;
/*  550 */     if (paramObject instanceof Object[] || paramObject instanceof Hashtable || paramObject instanceof Vector) {
/*      */       
/*  552 */       defaultMutableTreeNode = new DefaultMutableTreeNode("root");
/*  553 */       DynamicUtilTreeNode.createChildren(defaultMutableTreeNode, paramObject);
/*      */     } else {
/*      */       
/*  556 */       defaultMutableTreeNode = new DynamicUtilTreeNode("root", paramObject);
/*      */     } 
/*  558 */     return new DefaultTreeModel(defaultMutableTreeNode, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JTree() {
/*  569 */     this(getDefaultTreeModel());
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
/*      */   public JTree(Object[] paramArrayOfObject) {
/*  583 */     this(createTreeModel(paramArrayOfObject));
/*  584 */     setRootVisible(false);
/*  585 */     setShowsRootHandles(true);
/*  586 */     expandRoot();
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
/*      */   public JTree(Vector<?> paramVector) {
/*  599 */     this(createTreeModel(paramVector));
/*  600 */     setRootVisible(false);
/*  601 */     setShowsRootHandles(true);
/*  602 */     expandRoot();
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
/*      */   public JTree(Hashtable<?, ?> paramHashtable) {
/*  616 */     this(createTreeModel(paramHashtable));
/*  617 */     setRootVisible(false);
/*  618 */     setShowsRootHandles(true);
/*  619 */     expandRoot();
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
/*      */   public JTree(TreeNode paramTreeNode) {
/*  632 */     this(paramTreeNode, false);
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
/*      */   public JTree(TreeNode paramTreeNode, boolean paramBoolean) {
/*  648 */     this(new DefaultTreeModel(paramTreeNode, paramBoolean));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @ConstructorProperties({"model"})
/*      */   public JTree(TreeModel paramTreeModel) {
/*  660 */     this.expandedStack = new Stack<>();
/*  661 */     this.toggleClickCount = 2;
/*  662 */     this.expandedState = new Hashtable<>();
/*  663 */     setLayout((LayoutManager)null);
/*  664 */     this.rowHeight = 16;
/*  665 */     this.visibleRowCount = 20;
/*  666 */     this.rootVisible = true;
/*  667 */     this.selectionModel = new DefaultTreeSelectionModel();
/*  668 */     this.cellRenderer = null;
/*  669 */     this.scrollsOnExpand = true;
/*  670 */     setOpaque(true);
/*  671 */     this.expandsSelectedPaths = true;
/*  672 */     updateUI();
/*  673 */     setModel(paramTreeModel);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TreeUI getUI() {
/*  682 */     return (TreeUI)this.ui;
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
/*      */   public void setUI(TreeUI paramTreeUI) {
/*  699 */     if (this.ui != paramTreeUI) {
/*  700 */       this.settingUI = true;
/*  701 */       this.uiTreeExpansionListener = null;
/*      */       try {
/*  703 */         setUI(paramTreeUI);
/*      */       } finally {
/*      */         
/*  706 */         this.settingUI = false;
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
/*      */   public void updateUI() {
/*  719 */     setUI((TreeUI)UIManager.getUI(this));
/*      */     
/*  721 */     SwingUtilities.updateRendererOrEditorUI(getCellRenderer());
/*  722 */     SwingUtilities.updateRendererOrEditorUI(getCellEditor());
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
/*  734 */     return "TreeUI";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TreeCellRenderer getCellRenderer() {
/*  745 */     return this.cellRenderer;
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
/*      */   public void setCellRenderer(TreeCellRenderer paramTreeCellRenderer) {
/*  761 */     TreeCellRenderer treeCellRenderer = this.cellRenderer;
/*      */     
/*  763 */     this.cellRenderer = paramTreeCellRenderer;
/*  764 */     firePropertyChange("cellRenderer", treeCellRenderer, this.cellRenderer);
/*  765 */     invalidate();
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
/*      */   public void setEditable(boolean paramBoolean) {
/*  781 */     boolean bool = this.editable;
/*      */     
/*  783 */     this.editable = paramBoolean;
/*  784 */     firePropertyChange("editable", bool, paramBoolean);
/*  785 */     if (this.accessibleContext != null) {
/*  786 */       this.accessibleContext.firePropertyChange("AccessibleState", bool ? AccessibleState.EDITABLE : null, paramBoolean ? AccessibleState.EDITABLE : null);
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
/*      */   public boolean isEditable() {
/*  799 */     return this.editable;
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
/*      */   public void setCellEditor(TreeCellEditor paramTreeCellEditor) {
/*  817 */     TreeCellEditor treeCellEditor = this.cellEditor;
/*      */     
/*  819 */     this.cellEditor = paramTreeCellEditor;
/*  820 */     firePropertyChange("cellEditor", treeCellEditor, paramTreeCellEditor);
/*  821 */     invalidate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TreeCellEditor getCellEditor() {
/*  831 */     return this.cellEditor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TreeModel getModel() {
/*  840 */     return this.treeModel;
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
/*      */   public void setModel(TreeModel paramTreeModel) {
/*  854 */     clearSelection();
/*      */     
/*  856 */     TreeModel treeModel = this.treeModel;
/*      */     
/*  858 */     if (this.treeModel != null && this.treeModelListener != null) {
/*  859 */       this.treeModel.removeTreeModelListener(this.treeModelListener);
/*      */     }
/*  861 */     if (this.accessibleContext != null) {
/*  862 */       if (this.treeModel != null) {
/*  863 */         this.treeModel.removeTreeModelListener((TreeModelListener)this.accessibleContext);
/*      */       }
/*  865 */       if (paramTreeModel != null) {
/*  866 */         paramTreeModel.addTreeModelListener((TreeModelListener)this.accessibleContext);
/*      */       }
/*      */     } 
/*      */     
/*  870 */     this.treeModel = paramTreeModel;
/*  871 */     clearToggledPaths();
/*  872 */     if (this.treeModel != null) {
/*  873 */       if (this.treeModelListener == null)
/*  874 */         this.treeModelListener = createTreeModelListener(); 
/*  875 */       if (this.treeModelListener != null) {
/*  876 */         this.treeModel.addTreeModelListener(this.treeModelListener);
/*      */       }
/*  878 */       Object object = this.treeModel.getRoot();
/*  879 */       if (object != null && 
/*  880 */         !this.treeModel.isLeaf(object)) {
/*  881 */         this.expandedState.put(new TreePath(object), Boolean.TRUE);
/*      */       }
/*      */     } 
/*      */     
/*  885 */     firePropertyChange("model", treeModel, this.treeModel);
/*  886 */     invalidate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRootVisible() {
/*  896 */     return this.rootVisible;
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
/*      */   public void setRootVisible(boolean paramBoolean) {
/*  913 */     boolean bool = this.rootVisible;
/*      */     
/*  915 */     this.rootVisible = paramBoolean;
/*  916 */     firePropertyChange("rootVisible", bool, this.rootVisible);
/*  917 */     if (this.accessibleContext != null) {
/*  918 */       ((AccessibleJTree)this.accessibleContext).fireVisibleDataPropertyChange();
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
/*      */   public void setShowsRootHandles(boolean paramBoolean) {
/*  942 */     boolean bool = this.showsRootHandles;
/*  943 */     TreeModel treeModel = getModel();
/*      */     
/*  945 */     this.showsRootHandles = paramBoolean;
/*  946 */     this.showsRootHandlesSet = true;
/*  947 */     firePropertyChange("showsRootHandles", bool, this.showsRootHandles);
/*      */     
/*  949 */     if (this.accessibleContext != null) {
/*  950 */       ((AccessibleJTree)this.accessibleContext).fireVisibleDataPropertyChange();
/*      */     }
/*  952 */     invalidate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getShowsRootHandles() {
/*  963 */     return this.showsRootHandles;
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
/*      */   public void setRowHeight(int paramInt) {
/*  980 */     int i = this.rowHeight;
/*      */     
/*  982 */     this.rowHeight = paramInt;
/*  983 */     this.rowHeightSet = true;
/*  984 */     firePropertyChange("rowHeight", i, this.rowHeight);
/*  985 */     invalidate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRowHeight() {
/*  996 */     return this.rowHeight;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFixedRowHeight() {
/* 1006 */     return (this.rowHeight > 0);
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
/*      */   public void setLargeModel(boolean paramBoolean) {
/* 1024 */     boolean bool = this.largeModel;
/*      */     
/* 1026 */     this.largeModel = paramBoolean;
/* 1027 */     firePropertyChange("largeModel", bool, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isLargeModel() {
/* 1037 */     return this.largeModel;
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
/*      */   public void setInvokesStopCellEditing(boolean paramBoolean) {
/* 1058 */     boolean bool = this.invokesStopCellEditing;
/*      */     
/* 1060 */     this.invokesStopCellEditing = paramBoolean;
/* 1061 */     firePropertyChange("invokesStopCellEditing", bool, paramBoolean);
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
/*      */   public boolean getInvokesStopCellEditing() {
/* 1074 */     return this.invokesStopCellEditing;
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
/*      */   public void setScrollsOnExpand(boolean paramBoolean) {
/* 1099 */     boolean bool = this.scrollsOnExpand;
/*      */     
/* 1101 */     this.scrollsOnExpand = paramBoolean;
/* 1102 */     this.scrollsOnExpandSet = true;
/* 1103 */     firePropertyChange("scrollsOnExpand", bool, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getScrollsOnExpand() {
/* 1113 */     return this.scrollsOnExpand;
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
/*      */   public void setToggleClickCount(int paramInt) {
/* 1128 */     int i = this.toggleClickCount;
/*      */     
/* 1130 */     this.toggleClickCount = paramInt;
/* 1131 */     firePropertyChange("toggleClickCount", i, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getToggleClickCount() {
/* 1142 */     return this.toggleClickCount;
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
/*      */   public void setExpandsSelectedPaths(boolean paramBoolean) {
/* 1168 */     boolean bool = this.expandsSelectedPaths;
/*      */     
/* 1170 */     this.expandsSelectedPaths = paramBoolean;
/* 1171 */     firePropertyChange("expandsSelectedPaths", bool, paramBoolean);
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
/*      */   public boolean getExpandsSelectedPaths() {
/* 1183 */     return this.expandsSelectedPaths;
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
/*      */   public void setDragEnabled(boolean paramBoolean) {
/* 1220 */     if (paramBoolean && GraphicsEnvironment.isHeadless()) {
/* 1221 */       throw new HeadlessException();
/*      */     }
/* 1223 */     this.dragEnabled = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getDragEnabled() {
/* 1234 */     return this.dragEnabled;
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
/*      */   public final void setDropMode(DropMode paramDropMode) {
/* 1266 */     if (paramDropMode != null) {
/* 1267 */       switch (paramDropMode) {
/*      */         case USE_SELECTION:
/*      */         case ON:
/*      */         case INSERT:
/*      */         case ON_OR_INSERT:
/* 1272 */           this.dropMode = paramDropMode;
/*      */           return;
/*      */       } 
/*      */     
/*      */     }
/* 1277 */     throw new IllegalArgumentException(paramDropMode + ": Unsupported drop mode for tree");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final DropMode getDropMode() {
/* 1288 */     return this.dropMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   DropLocation dropLocationForPoint(Point paramPoint) {
/*      */     TreePath treePath2, treePath3;
/*      */     boolean bool2;
/*      */     SwingUtilities2.Section section;
/* 1299 */     DropLocation dropLocation = null;
/*      */     
/* 1301 */     int i = getClosestRowForLocation(paramPoint.x, paramPoint.y);
/* 1302 */     Rectangle rectangle = getRowBounds(i);
/* 1303 */     TreeModel treeModel = getModel();
/* 1304 */     Object object = (treeModel == null) ? null : treeModel.getRoot();
/* 1305 */     TreePath treePath1 = (object == null) ? null : new TreePath(object);
/*      */ 
/*      */ 
/*      */     
/* 1309 */     boolean bool1 = (i == -1 || paramPoint.y < rectangle.y || paramPoint.y >= rectangle.y + rectangle.height) ? true : false;
/*      */ 
/*      */ 
/*      */     
/* 1313 */     switch (this.dropMode) {
/*      */       case USE_SELECTION:
/*      */       case ON:
/* 1316 */         if (bool1) {
/* 1317 */           dropLocation = new DropLocation(paramPoint, null, -1); break;
/*      */         } 
/* 1319 */         dropLocation = new DropLocation(paramPoint, getPathForRow(i), -1);
/*      */         break;
/*      */ 
/*      */       
/*      */       case INSERT:
/*      */       case ON_OR_INSERT:
/* 1325 */         if (i == -1) {
/* 1326 */           if (object != null && !treeModel.isLeaf(object) && isExpanded(treePath1)) {
/* 1327 */             dropLocation = new DropLocation(paramPoint, treePath1, 0); break;
/*      */           } 
/* 1329 */           dropLocation = new DropLocation(paramPoint, null, -1);
/*      */ 
/*      */           
/*      */           break;
/*      */         } 
/*      */ 
/*      */         
/* 1336 */         bool2 = (this.dropMode == DropMode.ON_OR_INSERT || !treeModel.isLeaf(getPathForRow(i).getLastPathComponent())) ? true : false;
/*      */         
/* 1338 */         section = SwingUtilities2.liesInVertical(rectangle, paramPoint, bool2);
/* 1339 */         if (section == SwingUtilities2.Section.LEADING) {
/* 1340 */           treePath2 = getPathForRow(i);
/* 1341 */           treePath3 = treePath2.getParentPath();
/* 1342 */         } else if (section == SwingUtilities2.Section.TRAILING) {
/* 1343 */           int j = i + 1;
/* 1344 */           if (j >= getRowCount()) {
/* 1345 */             if (treeModel.isLeaf(object) || !isExpanded(treePath1)) {
/* 1346 */               dropLocation = new DropLocation(paramPoint, null, -1); break;
/*      */             } 
/* 1348 */             TreePath treePath = treePath1;
/* 1349 */             j = treeModel.getChildCount(object);
/* 1350 */             dropLocation = new DropLocation(paramPoint, treePath, j);
/*      */ 
/*      */             
/*      */             break;
/*      */           } 
/*      */           
/* 1356 */           treePath2 = getPathForRow(j);
/* 1357 */           treePath3 = treePath2.getParentPath();
/*      */         } else {
/* 1359 */           assert bool2;
/* 1360 */           dropLocation = new DropLocation(paramPoint, getPathForRow(i), -1);
/*      */           
/*      */           break;
/*      */         } 
/* 1364 */         if (treePath3 != null) {
/*      */           
/* 1366 */           dropLocation = new DropLocation(paramPoint, treePath3, treeModel.getIndexOfChild(treePath3.getLastPathComponent(), treePath2
/* 1367 */                 .getLastPathComponent())); break;
/* 1368 */         }  if (bool2 || !treeModel.isLeaf(object)) {
/* 1369 */           dropLocation = new DropLocation(paramPoint, treePath1, -1); break;
/*      */         } 
/* 1371 */         dropLocation = new DropLocation(paramPoint, null, -1);
/*      */         break;
/*      */ 
/*      */       
/*      */       default:
/* 1376 */         assert false : "Unexpected drop mode";
/*      */         break;
/*      */     } 
/* 1379 */     if (bool1 || i != this.expandRow) {
/* 1380 */       cancelDropTimer();
/*      */     }
/*      */     
/* 1383 */     if (!bool1 && i != this.expandRow && 
/* 1384 */       isCollapsed(i)) {
/* 1385 */       this.expandRow = i;
/* 1386 */       startDropTimer();
/*      */     } 
/*      */ 
/*      */     
/* 1390 */     return dropLocation;
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
/*      */   Object setDropLocation(TransferHandler.DropLocation paramDropLocation, Object paramObject, boolean paramBoolean) {
/* 1430 */     Object object = null;
/* 1431 */     DropLocation dropLocation1 = (DropLocation)paramDropLocation;
/*      */     
/* 1433 */     if (this.dropMode == DropMode.USE_SELECTION) {
/* 1434 */       if (dropLocation1 == null) {
/* 1435 */         if (!paramBoolean && paramObject != null) {
/* 1436 */           setSelectionPaths(((TreePath[][])paramObject)[0]);
/* 1437 */           setAnchorSelectionPath(((TreePath[][])paramObject)[1][0]);
/* 1438 */           setLeadSelectionPath(((TreePath[][])paramObject)[1][1]);
/*      */         } 
/*      */       } else {
/* 1441 */         if (this.dropLocation == null) {
/* 1442 */           TreePath[] arrayOfTreePath = getSelectionPaths();
/* 1443 */           if (arrayOfTreePath == null) {
/* 1444 */             arrayOfTreePath = new TreePath[0];
/*      */           }
/*      */ 
/*      */           
/* 1448 */           object = new TreePath[][] { arrayOfTreePath, { getAnchorSelectionPath(), getLeadSelectionPath() } };
/*      */         } else {
/* 1450 */           object = paramObject;
/*      */         } 
/*      */         
/* 1453 */         setSelectionPath(dropLocation1.getPath());
/*      */       } 
/*      */     }
/*      */     
/* 1457 */     DropLocation dropLocation2 = this.dropLocation;
/* 1458 */     this.dropLocation = dropLocation1;
/* 1459 */     firePropertyChange("dropLocation", dropLocation2, this.dropLocation);
/*      */     
/* 1461 */     return object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void dndDone() {
/* 1469 */     cancelDropTimer();
/* 1470 */     this.dropTimer = null;
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
/*      */   public final DropLocation getDropLocation() {
/* 1492 */     return this.dropLocation;
/*      */   }
/*      */   
/*      */   private void startDropTimer() {
/* 1496 */     if (this.dropTimer == null) {
/* 1497 */       this.dropTimer = new TreeTimer();
/*      */     }
/* 1499 */     this.dropTimer.start();
/*      */   }
/*      */   
/*      */   private void cancelDropTimer() {
/* 1503 */     if (this.dropTimer != null && this.dropTimer.isRunning()) {
/* 1504 */       this.expandRow = -1;
/* 1505 */       this.dropTimer.stop();
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
/*      */   public boolean isPathEditable(TreePath paramTreePath) {
/* 1519 */     return isEditable();
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
/*      */   public String getToolTipText(MouseEvent paramMouseEvent) {
/* 1539 */     String str = null;
/*      */     
/* 1541 */     if (paramMouseEvent != null) {
/* 1542 */       Point point = paramMouseEvent.getPoint();
/* 1543 */       int i = getRowForLocation(point.x, point.y);
/* 1544 */       TreeCellRenderer treeCellRenderer = getCellRenderer();
/*      */       
/* 1546 */       if (i != -1 && treeCellRenderer != null) {
/* 1547 */         TreePath treePath = getPathForRow(i);
/* 1548 */         Object object = treePath.getLastPathComponent();
/*      */         
/* 1550 */         Component component = treeCellRenderer.getTreeCellRendererComponent(this, object, isRowSelected(i), 
/* 1551 */             isExpanded(i), getModel().isLeaf(object), i, true);
/*      */ 
/*      */         
/* 1554 */         if (component instanceof JComponent) {
/*      */           
/* 1556 */           Rectangle rectangle = getPathBounds(treePath);
/*      */           
/* 1558 */           point.translate(-rectangle.x, -rectangle.y);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1566 */           MouseEvent mouseEvent = new MouseEvent(component, paramMouseEvent.getID(), paramMouseEvent.getWhen(), paramMouseEvent.getModifiers(), point.x, point.y, paramMouseEvent.getXOnScreen(), paramMouseEvent.getYOnScreen(), paramMouseEvent.getClickCount(), paramMouseEvent.isPopupTrigger(), 0);
/*      */ 
/*      */           
/* 1569 */           AWTAccessor.MouseEventAccessor mouseEventAccessor = AWTAccessor.getMouseEventAccessor();
/* 1570 */           mouseEventAccessor.setCausedByTouchEvent(mouseEvent, mouseEventAccessor
/* 1571 */               .isCausedByTouchEvent(paramMouseEvent));
/*      */           
/* 1573 */           str = ((JComponent)component).getToolTipText(mouseEvent);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1578 */     if (str == null) {
/* 1579 */       str = getToolTipText();
/*      */     }
/* 1581 */     return str;
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
/*      */   public String convertValueToText(Object paramObject, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt, boolean paramBoolean4) {
/* 1602 */     if (paramObject != null) {
/* 1603 */       String str = paramObject.toString();
/* 1604 */       if (str != null) {
/* 1605 */         return str;
/*      */       }
/*      */     } 
/* 1608 */     return "";
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
/*      */   public int getRowCount() {
/* 1625 */     TreeUI treeUI = getUI();
/*      */     
/* 1627 */     if (treeUI != null)
/* 1628 */       return treeUI.getRowCount(this); 
/* 1629 */     return 0;
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
/*      */   public void setSelectionPath(TreePath paramTreePath) {
/* 1641 */     getSelectionModel().setSelectionPath(paramTreePath);
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
/*      */   public void setSelectionPaths(TreePath[] paramArrayOfTreePath) {
/* 1654 */     getSelectionModel().setSelectionPaths(paramArrayOfTreePath);
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
/*      */   public void setLeadSelectionPath(TreePath paramTreePath) {
/* 1671 */     TreePath treePath = this.leadPath;
/*      */     
/* 1673 */     this.leadPath = paramTreePath;
/* 1674 */     firePropertyChange("leadSelectionPath", treePath, paramTreePath);
/*      */     
/* 1676 */     if (this.accessibleContext != null) {
/* 1677 */       ((AccessibleJTree)this.accessibleContext)
/* 1678 */         .fireActiveDescendantPropertyChange(treePath, paramTreePath);
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
/*      */   public void setAnchorSelectionPath(TreePath paramTreePath) {
/* 1696 */     TreePath treePath = this.anchorPath;
/*      */     
/* 1698 */     this.anchorPath = paramTreePath;
/* 1699 */     firePropertyChange("anchorSelectionPath", treePath, paramTreePath);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSelectionRow(int paramInt) {
/* 1709 */     int[] arrayOfInt = { paramInt };
/*      */     
/* 1711 */     setSelectionRows(arrayOfInt);
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
/*      */   public void setSelectionRows(int[] paramArrayOfint) {
/* 1727 */     TreeUI treeUI = getUI();
/*      */     
/* 1729 */     if (treeUI != null && paramArrayOfint != null) {
/* 1730 */       int i = paramArrayOfint.length;
/* 1731 */       TreePath[] arrayOfTreePath = new TreePath[i];
/*      */       
/* 1733 */       for (byte b = 0; b < i; b++) {
/* 1734 */         arrayOfTreePath[b] = treeUI.getPathForRow(this, paramArrayOfint[b]);
/*      */       }
/* 1736 */       setSelectionPaths(arrayOfTreePath);
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
/*      */   public void addSelectionPath(TreePath paramTreePath) {
/* 1753 */     getSelectionModel().addSelectionPath(paramTreePath);
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
/*      */   public void addSelectionPaths(TreePath[] paramArrayOfTreePath) {
/* 1770 */     getSelectionModel().addSelectionPaths(paramArrayOfTreePath);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSelectionRow(int paramInt) {
/* 1780 */     int[] arrayOfInt = { paramInt };
/*      */     
/* 1782 */     addSelectionRows(arrayOfInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSelectionRows(int[] paramArrayOfint) {
/* 1792 */     TreeUI treeUI = getUI();
/*      */     
/* 1794 */     if (treeUI != null && paramArrayOfint != null) {
/* 1795 */       int i = paramArrayOfint.length;
/* 1796 */       TreePath[] arrayOfTreePath = new TreePath[i];
/*      */       
/* 1798 */       for (byte b = 0; b < i; b++)
/* 1799 */         arrayOfTreePath[b] = treeUI.getPathForRow(this, paramArrayOfint[b]); 
/* 1800 */       addSelectionPaths(arrayOfTreePath);
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
/*      */   public Object getLastSelectedPathComponent() {
/* 1815 */     TreePath treePath = getSelectionModel().getSelectionPath();
/*      */     
/* 1817 */     if (treePath != null)
/* 1818 */       return treePath.getLastPathComponent(); 
/* 1819 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TreePath getLeadSelectionPath() {
/* 1827 */     return this.leadPath;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TreePath getAnchorSelectionPath() {
/* 1836 */     return this.anchorPath;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TreePath getSelectionPath() {
/* 1846 */     return getSelectionModel().getSelectionPath();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TreePath[] getSelectionPaths() {
/* 1856 */     TreePath[] arrayOfTreePath = getSelectionModel().getSelectionPaths();
/*      */     
/* 1858 */     return (arrayOfTreePath != null && arrayOfTreePath.length > 0) ? arrayOfTreePath : null;
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
/*      */   public int[] getSelectionRows() {
/* 1872 */     return getSelectionModel().getSelectionRows();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSelectionCount() {
/* 1881 */     return this.selectionModel.getSelectionCount();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinSelectionRow() {
/* 1891 */     return getSelectionModel().getMinSelectionRow();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxSelectionRow() {
/* 1901 */     return getSelectionModel().getMaxSelectionRow();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLeadSelectionRow() {
/* 1912 */     TreePath treePath = getLeadSelectionPath();
/*      */     
/* 1914 */     if (treePath != null) {
/* 1915 */       return getRowForPath(treePath);
/*      */     }
/* 1917 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPathSelected(TreePath paramTreePath) {
/* 1927 */     return getSelectionModel().isPathSelected(paramTreePath);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRowSelected(int paramInt) {
/* 1938 */     return getSelectionModel().isRowSelected(paramInt);
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
/*      */   public Enumeration<TreePath> getExpandedDescendants(TreePath paramTreePath) {
/* 1957 */     if (!isExpanded(paramTreePath)) {
/* 1958 */       return null;
/*      */     }
/* 1960 */     Enumeration<TreePath> enumeration = this.expandedState.keys();
/* 1961 */     Vector<TreePath> vector = null;
/*      */ 
/*      */ 
/*      */     
/* 1965 */     if (enumeration != null) {
/* 1966 */       while (enumeration.hasMoreElements()) {
/* 1967 */         TreePath treePath = enumeration.nextElement();
/* 1968 */         Boolean bool = (Boolean)this.expandedState.get(treePath);
/*      */ 
/*      */ 
/*      */         
/* 1972 */         if (treePath != paramTreePath && bool != null && ((Boolean)bool)
/* 1973 */           .booleanValue() && paramTreePath
/* 1974 */           .isDescendant(treePath) && isVisible(treePath)) {
/* 1975 */           if (vector == null) {
/* 1976 */             vector = new Vector();
/*      */           }
/* 1978 */           vector.addElement(treePath);
/*      */         } 
/*      */       } 
/*      */     }
/* 1982 */     if (vector == null) {
/* 1983 */       Set<?> set = Collections.emptySet();
/* 1984 */       return (Enumeration)Collections.enumeration(set);
/*      */     } 
/* 1986 */     return vector.elements();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasBeenExpanded(TreePath paramTreePath) {
/* 1995 */     return (paramTreePath != null && this.expandedState.get(paramTreePath) != null);
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
/*      */   public boolean isExpanded(TreePath paramTreePath) {
/* 2007 */     if (paramTreePath == null) {
/* 2008 */       return false;
/*      */     }
/*      */     
/*      */     do {
/* 2012 */       Boolean bool = (Boolean)this.expandedState.get(paramTreePath);
/* 2013 */       if (bool == null || !((Boolean)bool).booleanValue())
/* 2014 */         return false; 
/* 2015 */     } while ((paramTreePath = paramTreePath.getParentPath()) != null);
/*      */     
/* 2017 */     return true;
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
/*      */   public boolean isExpanded(int paramInt) {
/* 2029 */     TreeUI treeUI = getUI();
/*      */     
/* 2031 */     if (treeUI != null) {
/* 2032 */       TreePath treePath = treeUI.getPathForRow(this, paramInt);
/*      */       
/* 2034 */       if (treePath != null) {
/* 2035 */         Boolean bool = this.expandedState.get(treePath);
/*      */         
/* 2037 */         return (bool != null && bool.booleanValue());
/*      */       } 
/*      */     } 
/* 2040 */     return false;
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
/*      */   public boolean isCollapsed(TreePath paramTreePath) {
/* 2053 */     return !isExpanded(paramTreePath);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCollapsed(int paramInt) {
/* 2064 */     return !isExpanded(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void makeVisible(TreePath paramTreePath) {
/* 2073 */     if (paramTreePath != null) {
/* 2074 */       TreePath treePath = paramTreePath.getParentPath();
/*      */       
/* 2076 */       if (treePath != null) {
/* 2077 */         expandPath(treePath);
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
/*      */   public boolean isVisible(TreePath paramTreePath) {
/* 2090 */     if (paramTreePath != null) {
/* 2091 */       TreePath treePath = paramTreePath.getParentPath();
/*      */       
/* 2093 */       if (treePath != null) {
/* 2094 */         return isExpanded(treePath);
/*      */       }
/* 2096 */       return true;
/*      */     } 
/* 2098 */     return false;
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
/*      */   public Rectangle getPathBounds(TreePath paramTreePath) {
/* 2115 */     TreeUI treeUI = getUI();
/*      */     
/* 2117 */     if (treeUI != null)
/* 2118 */       return treeUI.getPathBounds(this, paramTreePath); 
/* 2119 */     return null;
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
/*      */   public Rectangle getRowBounds(int paramInt) {
/* 2131 */     return getPathBounds(getPathForRow(paramInt));
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
/*      */   public void scrollPathToVisible(TreePath paramTreePath) {
/* 2144 */     if (paramTreePath != null) {
/* 2145 */       makeVisible(paramTreePath);
/*      */       
/* 2147 */       Rectangle rectangle = getPathBounds(paramTreePath);
/*      */       
/* 2149 */       if (rectangle != null) {
/* 2150 */         scrollRectToVisible(rectangle);
/* 2151 */         if (this.accessibleContext != null) {
/* 2152 */           ((AccessibleJTree)this.accessibleContext).fireVisibleDataPropertyChange();
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
/*      */ 
/*      */   
/*      */   public void scrollRowToVisible(int paramInt) {
/* 2168 */     scrollPathToVisible(getPathForRow(paramInt));
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
/*      */   public TreePath getPathForRow(int paramInt) {
/* 2182 */     TreeUI treeUI = getUI();
/*      */     
/* 2184 */     if (treeUI != null)
/* 2185 */       return treeUI.getPathForRow(this, paramInt); 
/* 2186 */     return null;
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
/*      */   public int getRowForPath(TreePath paramTreePath) {
/* 2199 */     TreeUI treeUI = getUI();
/*      */     
/* 2201 */     if (treeUI != null)
/* 2202 */       return treeUI.getRowForPath(this, paramTreePath); 
/* 2203 */     return -1;
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
/*      */   public void expandPath(TreePath paramTreePath) {
/* 2215 */     TreeModel treeModel = getModel();
/*      */     
/* 2217 */     if (paramTreePath != null && treeModel != null && 
/* 2218 */       !treeModel.isLeaf(paramTreePath.getLastPathComponent())) {
/* 2219 */       setExpandedState(paramTreePath, true);
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
/*      */   public void expandRow(int paramInt) {
/* 2234 */     expandPath(getPathForRow(paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void collapsePath(TreePath paramTreePath) {
/* 2244 */     setExpandedState(paramTreePath, false);
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
/*      */   public void collapseRow(int paramInt) {
/* 2257 */     collapsePath(getPathForRow(paramInt));
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
/*      */   public TreePath getPathForLocation(int paramInt1, int paramInt2) {
/* 2270 */     TreePath treePath = getClosestPathForLocation(paramInt1, paramInt2);
/*      */     
/* 2272 */     if (treePath != null) {
/* 2273 */       Rectangle rectangle = getPathBounds(treePath);
/*      */       
/* 2275 */       if (rectangle != null && paramInt1 >= rectangle.x && paramInt1 < rectangle.x + rectangle.width && paramInt2 >= rectangle.y && paramInt2 < rectangle.y + rectangle.height)
/*      */       {
/*      */         
/* 2278 */         return treePath; } 
/*      */     } 
/* 2280 */     return null;
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
/*      */   public int getRowForLocation(int paramInt1, int paramInt2) {
/* 2295 */     return getRowForPath(getPathForLocation(paramInt1, paramInt2));
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
/*      */   public TreePath getClosestPathForLocation(int paramInt1, int paramInt2) {
/* 2316 */     TreeUI treeUI = getUI();
/*      */     
/* 2318 */     if (treeUI != null)
/* 2319 */       return treeUI.getClosestPathForLocation(this, paramInt1, paramInt2); 
/* 2320 */     return null;
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
/*      */   public int getClosestRowForLocation(int paramInt1, int paramInt2) {
/* 2341 */     return getRowForPath(getClosestPathForLocation(paramInt1, paramInt2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEditing() {
/* 2352 */     TreeUI treeUI = getUI();
/*      */     
/* 2354 */     if (treeUI != null)
/* 2355 */       return treeUI.isEditing(this); 
/* 2356 */     return false;
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
/*      */   public boolean stopEditing() {
/* 2375 */     TreeUI treeUI = getUI();
/*      */     
/* 2377 */     if (treeUI != null)
/* 2378 */       return treeUI.stopEditing(this); 
/* 2379 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cancelEditing() {
/* 2387 */     TreeUI treeUI = getUI();
/*      */     
/* 2389 */     if (treeUI != null) {
/* 2390 */       treeUI.cancelEditing(this);
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
/*      */   public void startEditingAtPath(TreePath paramTreePath) {
/* 2402 */     TreeUI treeUI = getUI();
/*      */     
/* 2404 */     if (treeUI != null) {
/* 2405 */       treeUI.startEditingAtPath(this, paramTreePath);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TreePath getEditingPath() {
/* 2414 */     TreeUI treeUI = getUI();
/*      */     
/* 2416 */     if (treeUI != null)
/* 2417 */       return treeUI.getEditingPath(this); 
/* 2418 */     return null;
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
/*      */   public void setSelectionModel(TreeSelectionModel paramTreeSelectionModel) {
/* 2445 */     if (paramTreeSelectionModel == null) {
/* 2446 */       paramTreeSelectionModel = EmptySelectionModel.sharedInstance();
/*      */     }
/* 2448 */     TreeSelectionModel treeSelectionModel = this.selectionModel;
/*      */     
/* 2450 */     if (this.selectionModel != null && this.selectionRedirector != null) {
/* 2451 */       this.selectionModel
/* 2452 */         .removeTreeSelectionListener(this.selectionRedirector);
/*      */     }
/* 2454 */     if (this.accessibleContext != null) {
/* 2455 */       this.selectionModel.removeTreeSelectionListener((TreeSelectionListener)this.accessibleContext);
/* 2456 */       paramTreeSelectionModel.addTreeSelectionListener((TreeSelectionListener)this.accessibleContext);
/*      */     } 
/*      */     
/* 2459 */     this.selectionModel = paramTreeSelectionModel;
/* 2460 */     if (this.selectionRedirector != null) {
/* 2461 */       this.selectionModel.addTreeSelectionListener(this.selectionRedirector);
/*      */     }
/* 2463 */     firePropertyChange("selectionModel", treeSelectionModel, this.selectionModel);
/*      */ 
/*      */     
/* 2466 */     if (this.accessibleContext != null) {
/* 2467 */       this.accessibleContext.firePropertyChange("AccessibleSelection", 
/*      */           
/* 2469 */           Boolean.valueOf(false), Boolean.valueOf(true));
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
/*      */   public TreeSelectionModel getSelectionModel() {
/* 2483 */     return this.selectionModel;
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
/*      */   protected TreePath[] getPathBetweenRows(int paramInt1, int paramInt2) {
/* 2513 */     TreeUI treeUI = getUI();
/* 2514 */     if (treeUI != null) {
/* 2515 */       int i = getRowCount();
/* 2516 */       if (i > 0 && (paramInt1 >= 0 || paramInt2 >= 0) && (paramInt1 < i || paramInt2 < i)) {
/*      */         
/* 2518 */         paramInt1 = Math.min(i - 1, Math.max(paramInt1, 0));
/* 2519 */         paramInt2 = Math.min(i - 1, Math.max(paramInt2, 0));
/* 2520 */         int j = Math.min(paramInt1, paramInt2);
/* 2521 */         int k = Math.max(paramInt1, paramInt2);
/* 2522 */         TreePath[] arrayOfTreePath = new TreePath[k - j + 1];
/*      */         
/* 2524 */         for (int m = j; m <= k; m++) {
/* 2525 */           arrayOfTreePath[m - j] = treeUI
/* 2526 */             .getPathForRow(this, m);
/*      */         }
/* 2528 */         return arrayOfTreePath;
/*      */       } 
/*      */     } 
/* 2531 */     return new TreePath[0];
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
/*      */   public void setSelectionInterval(int paramInt1, int paramInt2) {
/* 2555 */     TreePath[] arrayOfTreePath = getPathBetweenRows(paramInt1, paramInt2);
/*      */     
/* 2557 */     getSelectionModel().setSelectionPaths(arrayOfTreePath);
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
/*      */   public void addSelectionInterval(int paramInt1, int paramInt2) {
/* 2582 */     TreePath[] arrayOfTreePath = getPathBetweenRows(paramInt1, paramInt2);
/*      */     
/* 2584 */     if (arrayOfTreePath != null && arrayOfTreePath.length > 0) {
/* 2585 */       getSelectionModel().addSelectionPaths(arrayOfTreePath);
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
/*      */   public void removeSelectionInterval(int paramInt1, int paramInt2) {
/* 2610 */     TreePath[] arrayOfTreePath = getPathBetweenRows(paramInt1, paramInt2);
/*      */     
/* 2612 */     if (arrayOfTreePath != null && arrayOfTreePath.length > 0) {
/* 2613 */       getSelectionModel().removeSelectionPaths(arrayOfTreePath);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeSelectionPath(TreePath paramTreePath) {
/* 2624 */     getSelectionModel().removeSelectionPath(paramTreePath);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeSelectionPaths(TreePath[] paramArrayOfTreePath) {
/* 2635 */     getSelectionModel().removeSelectionPaths(paramArrayOfTreePath);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeSelectionRow(int paramInt) {
/* 2645 */     int[] arrayOfInt = { paramInt };
/*      */     
/* 2647 */     removeSelectionRows(arrayOfInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeSelectionRows(int[] paramArrayOfint) {
/* 2658 */     TreeUI treeUI = getUI();
/*      */     
/* 2660 */     if (treeUI != null && paramArrayOfint != null) {
/* 2661 */       int i = paramArrayOfint.length;
/* 2662 */       TreePath[] arrayOfTreePath = new TreePath[i];
/*      */       
/* 2664 */       for (byte b = 0; b < i; b++)
/* 2665 */         arrayOfTreePath[b] = treeUI.getPathForRow(this, paramArrayOfint[b]); 
/* 2666 */       removeSelectionPaths(arrayOfTreePath);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearSelection() {
/* 2674 */     getSelectionModel().clearSelection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSelectionEmpty() {
/* 2683 */     return getSelectionModel().isSelectionEmpty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addTreeExpansionListener(TreeExpansionListener paramTreeExpansionListener) {
/* 2694 */     if (this.settingUI) {
/* 2695 */       this.uiTreeExpansionListener = paramTreeExpansionListener;
/*      */     }
/* 2697 */     this.listenerList.add(TreeExpansionListener.class, paramTreeExpansionListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeTreeExpansionListener(TreeExpansionListener paramTreeExpansionListener) {
/* 2706 */     this.listenerList.remove(TreeExpansionListener.class, paramTreeExpansionListener);
/* 2707 */     if (this.uiTreeExpansionListener == paramTreeExpansionListener) {
/* 2708 */       this.uiTreeExpansionListener = null;
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
/*      */   public TreeExpansionListener[] getTreeExpansionListeners() {
/* 2721 */     return this.listenerList.<TreeExpansionListener>getListeners(TreeExpansionListener.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addTreeWillExpandListener(TreeWillExpandListener paramTreeWillExpandListener) {
/* 2732 */     this.listenerList.add(TreeWillExpandListener.class, paramTreeWillExpandListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeTreeWillExpandListener(TreeWillExpandListener paramTreeWillExpandListener) {
/* 2741 */     this.listenerList.remove(TreeWillExpandListener.class, paramTreeWillExpandListener);
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
/*      */   public TreeWillExpandListener[] getTreeWillExpandListeners() {
/* 2753 */     return this.listenerList.<TreeWillExpandListener>getListeners(TreeWillExpandListener.class);
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
/*      */   public void fireTreeExpanded(TreePath paramTreePath) {
/* 2767 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/* 2768 */     TreeExpansionEvent treeExpansionEvent = null;
/* 2769 */     if (this.uiTreeExpansionListener != null) {
/* 2770 */       treeExpansionEvent = new TreeExpansionEvent(this, paramTreePath);
/* 2771 */       this.uiTreeExpansionListener.treeExpanded(treeExpansionEvent);
/*      */     } 
/*      */ 
/*      */     
/* 2775 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 2776 */       if (arrayOfObject[i] == TreeExpansionListener.class && arrayOfObject[i + 1] != this.uiTreeExpansionListener) {
/*      */ 
/*      */         
/* 2779 */         if (treeExpansionEvent == null)
/* 2780 */           treeExpansionEvent = new TreeExpansionEvent(this, paramTreePath); 
/* 2781 */         ((TreeExpansionListener)arrayOfObject[i + 1])
/* 2782 */           .treeExpanded(treeExpansionEvent);
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
/*      */   public void fireTreeCollapsed(TreePath paramTreePath) {
/* 2798 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/* 2799 */     TreeExpansionEvent treeExpansionEvent = null;
/* 2800 */     if (this.uiTreeExpansionListener != null) {
/* 2801 */       treeExpansionEvent = new TreeExpansionEvent(this, paramTreePath);
/* 2802 */       this.uiTreeExpansionListener.treeCollapsed(treeExpansionEvent);
/*      */     } 
/*      */ 
/*      */     
/* 2806 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 2807 */       if (arrayOfObject[i] == TreeExpansionListener.class && arrayOfObject[i + 1] != this.uiTreeExpansionListener) {
/*      */ 
/*      */         
/* 2810 */         if (treeExpansionEvent == null)
/* 2811 */           treeExpansionEvent = new TreeExpansionEvent(this, paramTreePath); 
/* 2812 */         ((TreeExpansionListener)arrayOfObject[i + 1])
/* 2813 */           .treeCollapsed(treeExpansionEvent);
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
/*      */   public void fireTreeWillExpand(TreePath paramTreePath) throws ExpandVetoException {
/* 2829 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/* 2830 */     TreeExpansionEvent treeExpansionEvent = null;
/*      */ 
/*      */     
/* 2833 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 2834 */       if (arrayOfObject[i] == TreeWillExpandListener.class) {
/*      */         
/* 2836 */         if (treeExpansionEvent == null)
/* 2837 */           treeExpansionEvent = new TreeExpansionEvent(this, paramTreePath); 
/* 2838 */         ((TreeWillExpandListener)arrayOfObject[i + 1])
/* 2839 */           .treeWillExpand(treeExpansionEvent);
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
/*      */   public void fireTreeWillCollapse(TreePath paramTreePath) throws ExpandVetoException {
/* 2855 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/* 2856 */     TreeExpansionEvent treeExpansionEvent = null;
/*      */ 
/*      */     
/* 2859 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 2860 */       if (arrayOfObject[i] == TreeWillExpandListener.class) {
/*      */         
/* 2862 */         if (treeExpansionEvent == null)
/* 2863 */           treeExpansionEvent = new TreeExpansionEvent(this, paramTreePath); 
/* 2864 */         ((TreeWillExpandListener)arrayOfObject[i + 1])
/* 2865 */           .treeWillCollapse(treeExpansionEvent);
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
/*      */   public void addTreeSelectionListener(TreeSelectionListener paramTreeSelectionListener) {
/* 2878 */     this.listenerList.add(TreeSelectionListener.class, paramTreeSelectionListener);
/* 2879 */     if (this.listenerList.getListenerCount(TreeSelectionListener.class) != 0 && this.selectionRedirector == null) {
/*      */       
/* 2881 */       this.selectionRedirector = new TreeSelectionRedirector();
/* 2882 */       this.selectionModel.addTreeSelectionListener(this.selectionRedirector);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeTreeSelectionListener(TreeSelectionListener paramTreeSelectionListener) {
/* 2892 */     this.listenerList.remove(TreeSelectionListener.class, paramTreeSelectionListener);
/* 2893 */     if (this.listenerList.getListenerCount(TreeSelectionListener.class) == 0 && this.selectionRedirector != null) {
/*      */       
/* 2895 */       this.selectionModel
/* 2896 */         .removeTreeSelectionListener(this.selectionRedirector);
/* 2897 */       this.selectionRedirector = null;
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
/*      */   public TreeSelectionListener[] getTreeSelectionListeners() {
/* 2910 */     return this.listenerList.<TreeSelectionListener>getListeners(TreeSelectionListener.class);
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
/*      */   protected void fireValueChanged(TreeSelectionEvent paramTreeSelectionEvent) {
/* 2925 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*      */ 
/*      */     
/* 2928 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/*      */       
/* 2930 */       if (arrayOfObject[i] == TreeSelectionListener.class)
/*      */       {
/*      */ 
/*      */         
/* 2934 */         ((TreeSelectionListener)arrayOfObject[i + 1]).valueChanged(paramTreeSelectionEvent);
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
/*      */   public void treeDidChange() {
/* 2947 */     revalidate();
/* 2948 */     repaint();
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
/*      */   public void setVisibleRowCount(int paramInt) {
/* 2965 */     int i = this.visibleRowCount;
/*      */     
/* 2967 */     this.visibleRowCount = paramInt;
/* 2968 */     firePropertyChange("visibleRowCount", i, this.visibleRowCount);
/*      */     
/* 2970 */     invalidate();
/* 2971 */     if (this.accessibleContext != null) {
/* 2972 */       ((AccessibleJTree)this.accessibleContext).fireVisibleDataPropertyChange();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getVisibleRowCount() {
/* 2982 */     return this.visibleRowCount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void expandRoot() {
/* 2989 */     TreeModel treeModel = getModel();
/*      */     
/* 2991 */     if (treeModel != null && treeModel.getRoot() != null) {
/* 2992 */       expandPath(new TreePath(treeModel.getRoot()));
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
/*      */   public TreePath getNextMatch(String paramString, int paramInt, Position.Bias paramBias) {
/* 3015 */     int i = getRowCount();
/* 3016 */     if (paramString == null) {
/* 3017 */       throw new IllegalArgumentException();
/*      */     }
/* 3019 */     if (paramInt < 0 || paramInt >= i) {
/* 3020 */       throw new IllegalArgumentException();
/*      */     }
/* 3022 */     paramString = paramString.toUpperCase();
/*      */ 
/*      */ 
/*      */     
/* 3026 */     byte b = (paramBias == Position.Bias.Forward) ? 1 : -1;
/* 3027 */     int j = paramInt;
/*      */     while (true) {
/* 3029 */       TreePath treePath = getPathForRow(j);
/* 3030 */       String str = convertValueToText(treePath
/* 3031 */           .getLastPathComponent(), isRowSelected(j), 
/* 3032 */           isExpanded(j), true, j, false);
/*      */       
/* 3034 */       if (str.toUpperCase().startsWith(paramString)) {
/* 3035 */         return treePath;
/*      */       }
/* 3037 */       j = (j + b + i) % i;
/* 3038 */       if (j == paramInt)
/* 3039 */         return null; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 3044 */     Vector<String> vector = new Vector();
/*      */     
/* 3046 */     paramObjectOutputStream.defaultWriteObject();
/*      */     
/* 3048 */     if (this.cellRenderer != null && this.cellRenderer instanceof Serializable) {
/* 3049 */       vector.addElement("cellRenderer");
/* 3050 */       vector.addElement(this.cellRenderer);
/*      */     } 
/*      */     
/* 3053 */     if (this.cellEditor != null && this.cellEditor instanceof Serializable) {
/* 3054 */       vector.addElement("cellEditor");
/* 3055 */       vector.addElement(this.cellEditor);
/*      */     } 
/*      */     
/* 3058 */     if (this.treeModel != null && this.treeModel instanceof Serializable) {
/* 3059 */       vector.addElement("treeModel");
/* 3060 */       vector.addElement(this.treeModel);
/*      */     } 
/*      */     
/* 3063 */     if (this.selectionModel != null && this.selectionModel instanceof Serializable) {
/* 3064 */       vector.addElement("selectionModel");
/* 3065 */       vector.addElement(this.selectionModel);
/*      */     } 
/*      */     
/* 3068 */     Object object = getArchivableExpandedState();
/*      */     
/* 3070 */     if (object != null) {
/* 3071 */       vector.addElement("expandedState");
/* 3072 */       vector.addElement(object);
/*      */     } 
/*      */     
/* 3075 */     paramObjectOutputStream.writeObject(vector);
/* 3076 */     if (getUIClassID().equals("TreeUI")) {
/* 3077 */       byte b = JComponent.getWriteObjCounter(this);
/* 3078 */       b = (byte)(b - 1); JComponent.setWriteObjCounter(this, b);
/* 3079 */       if (b == 0 && this.ui != null) {
/* 3080 */         this.ui.installUI(this);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 3087 */     paramObjectInputStream.defaultReadObject();
/*      */ 
/*      */ 
/*      */     
/* 3091 */     this.expandedState = new Hashtable<>();
/*      */     
/* 3093 */     this.expandedStack = new Stack<>();
/*      */     
/* 3095 */     Vector<E> vector = (Vector)paramObjectInputStream.readObject();
/* 3096 */     byte b = 0;
/* 3097 */     int i = vector.size();
/*      */     
/* 3099 */     if (b < i && vector.elementAt(b)
/* 3100 */       .equals("cellRenderer")) {
/* 3101 */       this.cellRenderer = (TreeCellRenderer)vector.elementAt(++b);
/* 3102 */       b++;
/*      */     } 
/* 3104 */     if (b < i && vector.elementAt(b)
/* 3105 */       .equals("cellEditor")) {
/* 3106 */       this.cellEditor = (TreeCellEditor)vector.elementAt(++b);
/* 3107 */       b++;
/*      */     } 
/* 3109 */     if (b < i && vector.elementAt(b)
/* 3110 */       .equals("treeModel")) {
/* 3111 */       this.treeModel = (TreeModel)vector.elementAt(++b);
/* 3112 */       b++;
/*      */     } 
/* 3114 */     if (b < i && vector.elementAt(b)
/* 3115 */       .equals("selectionModel")) {
/* 3116 */       this.selectionModel = (TreeSelectionModel)vector.elementAt(++b);
/* 3117 */       b++;
/*      */     } 
/* 3119 */     if (b < i && vector.elementAt(b)
/* 3120 */       .equals("expandedState")) {
/* 3121 */       unarchiveExpandedState(vector.elementAt(++b));
/* 3122 */       b++;
/*      */     } 
/*      */     
/* 3125 */     if (this.listenerList.getListenerCount(TreeSelectionListener.class) != 0) {
/* 3126 */       this.selectionRedirector = new TreeSelectionRedirector();
/* 3127 */       this.selectionModel.addTreeSelectionListener(this.selectionRedirector);
/*      */     } 
/*      */     
/* 3130 */     if (this.treeModel != null) {
/* 3131 */       this.treeModelListener = createTreeModelListener();
/* 3132 */       if (this.treeModelListener != null) {
/* 3133 */         this.treeModel.addTreeModelListener(this.treeModelListener);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object getArchivableExpandedState() {
/* 3143 */     TreeModel treeModel = getModel();
/*      */     
/* 3145 */     if (treeModel != null) {
/* 3146 */       Enumeration<TreePath> enumeration = this.expandedState.keys();
/*      */       
/* 3148 */       if (enumeration != null) {
/* 3149 */         Vector vector = new Vector();
/*      */         
/* 3151 */         while (enumeration.hasMoreElements()) {
/* 3152 */           Object object; TreePath treePath = enumeration.nextElement();
/*      */ 
/*      */           
/*      */           try {
/* 3156 */             object = getModelIndexsForPath(treePath);
/* 3157 */           } catch (Error error) {
/* 3158 */             object = null;
/*      */           } 
/* 3160 */           if (object != null) {
/* 3161 */             vector.addElement(object);
/* 3162 */             vector.addElement(this.expandedState.get(treePath));
/*      */           } 
/*      */         } 
/* 3165 */         return vector;
/*      */       } 
/*      */     } 
/* 3168 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void unarchiveExpandedState(Object paramObject) {
/* 3176 */     if (paramObject instanceof Vector) {
/* 3177 */       Vector<Boolean> vector = (Vector)paramObject;
/*      */       
/* 3179 */       for (int i = vector.size() - 1; i >= 0; i--) {
/* 3180 */         Boolean bool = vector.elementAt(i--);
/*      */ 
/*      */         
/*      */         try {
/* 3184 */           TreePath treePath = getPathForIndexs((int[])vector.elementAt(i));
/* 3185 */           if (treePath != null)
/* 3186 */             this.expandedState.put(treePath, bool); 
/* 3187 */         } catch (Error error) {}
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
/*      */   private int[] getModelIndexsForPath(TreePath paramTreePath) {
/* 3199 */     if (paramTreePath != null) {
/* 3200 */       TreeModel treeModel = getModel();
/* 3201 */       int i = paramTreePath.getPathCount();
/* 3202 */       int[] arrayOfInt = new int[i - 1];
/* 3203 */       Object object = treeModel.getRoot();
/*      */       
/* 3205 */       for (byte b = 1; b < i; b++) {
/* 3206 */         arrayOfInt[b - 1] = treeModel
/* 3207 */           .getIndexOfChild(object, paramTreePath.getPathComponent(b));
/* 3208 */         object = paramTreePath.getPathComponent(b);
/* 3209 */         if (arrayOfInt[b - 1] < 0)
/* 3210 */           return null; 
/*      */       } 
/* 3212 */       return arrayOfInt;
/*      */     } 
/* 3214 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private TreePath getPathForIndexs(int[] paramArrayOfint) {
/* 3224 */     if (paramArrayOfint == null) {
/* 3225 */       return null;
/*      */     }
/* 3227 */     TreeModel treeModel = getModel();
/*      */     
/* 3229 */     if (treeModel == null) {
/* 3230 */       return null;
/*      */     }
/* 3232 */     int i = paramArrayOfint.length;
/* 3233 */     Object object = treeModel.getRoot();
/* 3234 */     if (object == null) {
/* 3235 */       return null;
/*      */     }
/* 3237 */     TreePath treePath = new TreePath(object);
/*      */     
/* 3239 */     for (byte b = 0; b < i; b++) {
/* 3240 */       object = treeModel.getChild(object, paramArrayOfint[b]);
/* 3241 */       if (object == null)
/* 3242 */         return null; 
/* 3243 */       treePath = treePath.pathByAddingChild(object);
/*      */     } 
/* 3245 */     return treePath;
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
/*      */   protected static class EmptySelectionModel
/*      */     extends DefaultTreeSelectionModel
/*      */   {
/* 3268 */     protected static final EmptySelectionModel sharedInstance = new EmptySelectionModel();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static EmptySelectionModel sharedInstance() {
/* 3277 */       return sharedInstance;
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
/*      */     public void setSelectionPaths(TreePath[] param1ArrayOfTreePath) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void addSelectionPaths(TreePath[] param1ArrayOfTreePath) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeSelectionPaths(TreePath[] param1ArrayOfTreePath) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setSelectionMode(int param1Int) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setRowMapper(RowMapper param1RowMapper) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void addTreeSelectionListener(TreeSelectionListener param1TreeSelectionListener) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeTreeSelectionListener(TreeSelectionListener param1TreeSelectionListener) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void addPropertyChangeListener(PropertyChangeListener param1PropertyChangeListener) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removePropertyChangeListener(PropertyChangeListener param1PropertyChangeListener) {}
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
/*      */   protected class TreeSelectionRedirector
/*      */     implements Serializable, TreeSelectionListener
/*      */   {
/*      */     public void valueChanged(TreeSelectionEvent param1TreeSelectionEvent) {
/* 3397 */       TreeSelectionEvent treeSelectionEvent = (TreeSelectionEvent)param1TreeSelectionEvent.cloneWithSource(JTree.this);
/* 3398 */       JTree.this.fireValueChanged(treeSelectionEvent);
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
/*      */   public Dimension getPreferredScrollableViewportSize() {
/* 3414 */     int i = (getPreferredSize()).width;
/* 3415 */     int j = getVisibleRowCount();
/* 3416 */     int k = -1;
/*      */     
/* 3418 */     if (isFixedRowHeight()) {
/* 3419 */       k = j * getRowHeight();
/*      */     } else {
/* 3421 */       TreeUI treeUI = getUI();
/*      */       
/* 3423 */       if (treeUI != null && j > 0) {
/* 3424 */         int m = treeUI.getRowCount(this);
/*      */         
/* 3426 */         if (m >= j) {
/* 3427 */           Rectangle rectangle = getRowBounds(j - 1);
/* 3428 */           if (rectangle != null) {
/* 3429 */             k = rectangle.y + rectangle.height;
/*      */           }
/*      */         }
/* 3432 */         else if (m > 0) {
/* 3433 */           Rectangle rectangle = getRowBounds(0);
/* 3434 */           if (rectangle != null) {
/* 3435 */             k = rectangle.height * j;
/*      */           }
/*      */         } 
/*      */       } 
/* 3439 */       if (k == -1) {
/* 3440 */         k = 16 * j;
/*      */       }
/*      */     } 
/* 3443 */     return new Dimension(i, k);
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
/*      */   public int getScrollableUnitIncrement(Rectangle paramRectangle, int paramInt1, int paramInt2) {
/* 3462 */     if (paramInt1 == 1) {
/*      */ 
/*      */       
/* 3465 */       int i = getClosestRowForLocation(0, paramRectangle.y);
/*      */       
/* 3467 */       if (i != -1) {
/* 3468 */         Rectangle rectangle = getRowBounds(i);
/* 3469 */         if (rectangle.y != paramRectangle.y) {
/* 3470 */           if (paramInt2 < 0)
/*      */           {
/* 3472 */             return Math.max(0, paramRectangle.y - rectangle.y);
/*      */           }
/* 3474 */           return rectangle.y + rectangle.height - paramRectangle.y;
/*      */         } 
/* 3476 */         if (paramInt2 < 0) {
/* 3477 */           if (i != 0) {
/* 3478 */             rectangle = getRowBounds(i - 1);
/* 3479 */             return rectangle.height;
/*      */           } 
/*      */         } else {
/*      */           
/* 3483 */           return rectangle.height;
/*      */         } 
/*      */       } 
/* 3486 */       return 0;
/*      */     } 
/* 3488 */     return 4;
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
/*      */   public int getScrollableBlockIncrement(Rectangle paramRectangle, int paramInt1, int paramInt2) {
/* 3506 */     return (paramInt1 == 1) ? paramRectangle.height : paramRectangle.width;
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
/*      */   public boolean getScrollableTracksViewportWidth() {
/* 3520 */     Container container = SwingUtilities.getUnwrappedParent(this);
/* 3521 */     if (container instanceof JViewport) {
/* 3522 */       return (container.getWidth() > (getPreferredSize()).width);
/*      */     }
/* 3524 */     return false;
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
/*      */   public boolean getScrollableTracksViewportHeight() {
/* 3537 */     Container container = SwingUtilities.getUnwrappedParent(this);
/* 3538 */     if (container instanceof JViewport) {
/* 3539 */       return (container.getHeight() > (getPreferredSize()).height);
/*      */     }
/* 3541 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setExpandedState(TreePath paramTreePath, boolean paramBoolean) {
/*      */     Stack<TreePath> stack;
/*      */     TreePath treePath;
/* 3554 */     if (paramTreePath != null) {
/*      */ 
/*      */       
/* 3557 */       treePath = paramTreePath.getParentPath();
/*      */       
/* 3559 */       if (this.expandedStack.size() == 0) {
/* 3560 */         stack = new Stack();
/*      */       } else {
/*      */         
/* 3563 */         stack = this.expandedStack.pop();
/*      */       } 
/*      */     } else {
/*      */       return;
/* 3567 */     }  while (treePath != null) {
/* 3568 */       if (isExpanded(treePath)) {
/* 3569 */         treePath = null;
/*      */         continue;
/*      */       } 
/* 3572 */       stack.push(treePath);
/* 3573 */       treePath = treePath.getParentPath();
/*      */     } 
/*      */     int i;
/* 3576 */     for (i = stack.size() - 1; i >= 0; i--)
/* 3577 */     { treePath = stack.pop();
/* 3578 */       if (!isExpanded(treePath))
/*      */       { 
/* 3580 */         try { fireTreeWillExpand(treePath); }
/* 3581 */         catch (ExpandVetoException expandVetoException)
/*      */         
/*      */         { 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3595 */           if (this.expandedStack.size() < TEMP_STACK_SIZE)
/* 3596 */           { stack.removeAllElements();
/* 3597 */             this.expandedStack.push(stack); }  return; }  this.expandedState.put(treePath, Boolean.TRUE); fireTreeExpanded(treePath); if (this.accessibleContext != null) ((AccessibleJTree)this.accessibleContext).fireVisibleDataPropertyChange();  }  }  if (this.expandedStack.size() < TEMP_STACK_SIZE) { stack.removeAllElements(); this.expandedStack.push(stack); }
/*      */ 
/*      */     
/* 3600 */     if (!paramBoolean) {
/*      */       
/* 3602 */       i = this.expandedState.get(paramTreePath);
/*      */       
/* 3604 */       if (i != null && ((Boolean)i).booleanValue()) {
/*      */         try {
/* 3606 */           fireTreeWillCollapse(paramTreePath);
/*      */         }
/* 3608 */         catch (ExpandVetoException expandVetoException) {
/*      */           return;
/*      */         } 
/* 3611 */         this.expandedState.put(paramTreePath, Boolean.FALSE);
/* 3612 */         fireTreeCollapsed(paramTreePath);
/* 3613 */         if (removeDescendantSelectedPaths(paramTreePath, false) && 
/* 3614 */           !isPathSelected(paramTreePath))
/*      */         {
/* 3616 */           addSelectionPath(paramTreePath);
/*      */         }
/* 3618 */         if (this.accessibleContext != null) {
/* 3619 */           ((AccessibleJTree)this.accessibleContext)
/* 3620 */             .fireVisibleDataPropertyChange();
/*      */         }
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 3626 */       i = this.expandedState.get(paramTreePath);
/*      */       
/* 3628 */       if (i == null || !((Boolean)i).booleanValue()) {
/*      */         try {
/* 3630 */           fireTreeWillExpand(paramTreePath);
/*      */         }
/* 3632 */         catch (ExpandVetoException expandVetoException) {
/*      */           return;
/*      */         } 
/* 3635 */         this.expandedState.put(paramTreePath, Boolean.TRUE);
/* 3636 */         fireTreeExpanded(paramTreePath);
/* 3637 */         if (this.accessibleContext != null) {
/* 3638 */           ((AccessibleJTree)this.accessibleContext)
/* 3639 */             .fireVisibleDataPropertyChange();
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
/*      */   
/*      */   protected Enumeration<TreePath> getDescendantToggledPaths(TreePath paramTreePath) {
/* 3654 */     if (paramTreePath == null) {
/* 3655 */       return null;
/*      */     }
/* 3657 */     Vector<TreePath> vector = new Vector();
/* 3658 */     Enumeration<TreePath> enumeration = this.expandedState.keys();
/*      */     
/* 3660 */     while (enumeration.hasMoreElements()) {
/* 3661 */       TreePath treePath = enumeration.nextElement();
/* 3662 */       if (paramTreePath.isDescendant(treePath))
/* 3663 */         vector.addElement(treePath); 
/*      */     } 
/* 3665 */     return vector.elements();
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
/*      */   protected void removeDescendantToggledPaths(Enumeration<TreePath> paramEnumeration) {
/* 3682 */     if (paramEnumeration != null) {
/* 3683 */       while (paramEnumeration.hasMoreElements()) {
/*      */         
/* 3685 */         Enumeration<TreePath> enumeration = getDescendantToggledPaths(paramEnumeration.nextElement());
/*      */         
/* 3687 */         if (enumeration != null) {
/* 3688 */           while (enumeration.hasMoreElements()) {
/* 3689 */             this.expandedState.remove(enumeration.nextElement());
/*      */           }
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void clearToggledPaths() {
/* 3701 */     this.expandedState.clear();
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
/*      */   protected TreeModelListener createTreeModelListener() {
/* 3714 */     return new TreeModelHandler();
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
/*      */   protected boolean removeDescendantSelectedPaths(TreePath paramTreePath, boolean paramBoolean) {
/* 3727 */     TreePath[] arrayOfTreePath = getDescendantSelectedPaths(paramTreePath, paramBoolean);
/*      */     
/* 3729 */     if (arrayOfTreePath != null) {
/* 3730 */       getSelectionModel().removeSelectionPaths(arrayOfTreePath);
/* 3731 */       return true;
/*      */     } 
/* 3733 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private TreePath[] getDescendantSelectedPaths(TreePath paramTreePath, boolean paramBoolean) {
/* 3742 */     TreeSelectionModel treeSelectionModel = getSelectionModel();
/* 3743 */     TreePath[] arrayOfTreePath = (treeSelectionModel != null) ? treeSelectionModel.getSelectionPaths() : null;
/*      */ 
/*      */     
/* 3746 */     if (arrayOfTreePath != null) {
/* 3747 */       boolean bool = false;
/*      */       
/* 3749 */       for (int i = arrayOfTreePath.length - 1; i >= 0; i--) {
/* 3750 */         if (arrayOfTreePath[i] != null && paramTreePath
/* 3751 */           .isDescendant(arrayOfTreePath[i]) && (
/* 3752 */           !paramTreePath.equals(arrayOfTreePath[i]) || paramBoolean)) {
/* 3753 */           bool = true;
/*      */         } else {
/* 3755 */           arrayOfTreePath[i] = null;
/*      */         } 
/* 3757 */       }  if (!bool) {
/* 3758 */         arrayOfTreePath = null;
/*      */       }
/* 3760 */       return arrayOfTreePath;
/*      */     } 
/* 3762 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void removeDescendantSelectedPaths(TreeModelEvent paramTreeModelEvent) {
/* 3770 */     TreePath treePath = SwingUtilities2.getTreePath(paramTreeModelEvent, getModel());
/* 3771 */     Object[] arrayOfObject = paramTreeModelEvent.getChildren();
/* 3772 */     TreeSelectionModel treeSelectionModel = getSelectionModel();
/*      */     
/* 3774 */     if (treeSelectionModel != null && treePath != null && arrayOfObject != null && arrayOfObject.length > 0)
/*      */     {
/* 3776 */       for (int i = arrayOfObject.length - 1; i >= 0; 
/* 3777 */         i--)
/*      */       {
/*      */         
/* 3780 */         removeDescendantSelectedPaths(treePath
/* 3781 */             .pathByAddingChild(arrayOfObject[i]), true);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected class TreeModelHandler
/*      */     implements TreeModelListener
/*      */   {
/*      */     public void treeNodesChanged(TreeModelEvent param1TreeModelEvent) {}
/*      */ 
/*      */     
/*      */     public void treeNodesInserted(TreeModelEvent param1TreeModelEvent) {}
/*      */ 
/*      */     
/*      */     public void treeStructureChanged(TreeModelEvent param1TreeModelEvent) {
/* 3797 */       if (param1TreeModelEvent == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3804 */       TreePath treePath = SwingUtilities2.getTreePath(param1TreeModelEvent, JTree.this.getModel());
/*      */       
/* 3806 */       if (treePath == null) {
/*      */         return;
/*      */       }
/* 3809 */       if (treePath.getPathCount() == 1) {
/*      */         
/* 3811 */         JTree.this.clearToggledPaths();
/* 3812 */         Object object = JTree.this.treeModel.getRoot();
/* 3813 */         if (object != null && 
/* 3814 */           !JTree.this.treeModel.isLeaf(object))
/*      */         {
/* 3816 */           JTree.this.expandedState.put(treePath, Boolean.TRUE);
/*      */         }
/*      */       }
/* 3819 */       else if (JTree.this.expandedState.get(treePath) != null) {
/* 3820 */         Vector<TreePath> vector = new Vector(1);
/* 3821 */         boolean bool = JTree.this.isExpanded(treePath);
/*      */         
/* 3823 */         vector.addElement(treePath);
/* 3824 */         JTree.this.removeDescendantToggledPaths(vector.elements());
/* 3825 */         if (bool) {
/* 3826 */           TreeModel treeModel = JTree.this.getModel();
/*      */           
/* 3828 */           if (treeModel == null || treeModel
/* 3829 */             .isLeaf(treePath.getLastPathComponent())) {
/* 3830 */             JTree.this.collapsePath(treePath);
/*      */           } else {
/* 3832 */             JTree.this.expandedState.put(treePath, Boolean.TRUE);
/*      */           } 
/*      */         } 
/* 3835 */       }  JTree.this.removeDescendantSelectedPaths(treePath, false);
/*      */     }
/*      */     
/*      */     public void treeNodesRemoved(TreeModelEvent param1TreeModelEvent) {
/* 3839 */       if (param1TreeModelEvent == null) {
/*      */         return;
/*      */       }
/* 3842 */       TreePath treePath = SwingUtilities2.getTreePath(param1TreeModelEvent, JTree.this.getModel());
/* 3843 */       Object[] arrayOfObject = param1TreeModelEvent.getChildren();
/*      */       
/* 3845 */       if (arrayOfObject == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/* 3850 */       Vector<TreePath> vector = new Vector(Math.max(1, arrayOfObject.length));
/*      */       
/* 3852 */       for (int i = arrayOfObject.length - 1; i >= 0; i--) {
/* 3853 */         TreePath treePath1 = treePath.pathByAddingChild(arrayOfObject[i]);
/* 3854 */         if (JTree.this.expandedState.get(treePath1) != null)
/* 3855 */           vector.addElement(treePath1); 
/*      */       } 
/* 3857 */       if (vector.size() > 0) {
/* 3858 */         JTree.this.removeDescendantToggledPaths(vector.elements());
/*      */       }
/* 3860 */       TreeModel treeModel = JTree.this.getModel();
/*      */       
/* 3862 */       if (treeModel == null || treeModel.isLeaf(treePath.getLastPathComponent())) {
/* 3863 */         JTree.this.expandedState.remove(treePath);
/*      */       }
/* 3865 */       JTree.this.removeDescendantSelectedPaths(param1TreeModelEvent);
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
/*      */   public static class DynamicUtilTreeNode
/*      */     extends DefaultMutableTreeNode
/*      */   {
/*      */     protected boolean hasChildren;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Object childValue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean loadedChildren;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static void createChildren(DefaultMutableTreeNode param1DefaultMutableTreeNode, Object param1Object) {
/* 3906 */       if (param1Object instanceof Vector) {
/* 3907 */         Vector vector = (Vector)param1Object;
/*      */         
/* 3909 */         byte b = 0; int i = vector.size();
/* 3910 */         for (; b < i; b++) {
/* 3911 */           param1DefaultMutableTreeNode.add(new DynamicUtilTreeNode(vector
/* 3912 */                 .elementAt(b), vector
/* 3913 */                 .elementAt(b)));
/*      */         }
/* 3915 */       } else if (param1Object instanceof Hashtable) {
/* 3916 */         Hashtable hashtable = (Hashtable)param1Object;
/* 3917 */         Enumeration<Object> enumeration = hashtable.keys();
/*      */ 
/*      */         
/* 3920 */         while (enumeration.hasMoreElements()) {
/* 3921 */           Object object = enumeration.nextElement();
/* 3922 */           param1DefaultMutableTreeNode.add(new DynamicUtilTreeNode(object, hashtable
/* 3923 */                 .get(object)));
/*      */         }
/*      */       
/* 3926 */       } else if (param1Object instanceof Object[]) {
/* 3927 */         Object[] arrayOfObject = (Object[])param1Object;
/*      */         
/* 3929 */         byte b = 0; int i = arrayOfObject.length;
/* 3930 */         for (; b < i; b++) {
/* 3931 */           param1DefaultMutableTreeNode.add(new DynamicUtilTreeNode(arrayOfObject[b], arrayOfObject[b]));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DynamicUtilTreeNode(Object param1Object1, Object param1Object2) {
/* 3954 */       super(param1Object1);
/* 3955 */       this.loadedChildren = false;
/* 3956 */       this.childValue = param1Object2;
/* 3957 */       if (param1Object2 != null) {
/* 3958 */         if (param1Object2 instanceof Vector) {
/* 3959 */           setAllowsChildren(true);
/* 3960 */         } else if (param1Object2 instanceof Hashtable) {
/* 3961 */           setAllowsChildren(true);
/* 3962 */         } else if (param1Object2 instanceof Object[]) {
/* 3963 */           setAllowsChildren(true);
/*      */         } else {
/* 3965 */           setAllowsChildren(false);
/*      */         } 
/*      */       } else {
/* 3968 */         setAllowsChildren(false);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isLeaf() {
/* 3979 */       return !getAllowsChildren();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getChildCount() {
/* 3988 */       if (!this.loadedChildren)
/* 3989 */         loadChildren(); 
/* 3990 */       return super.getChildCount();
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
/*      */     protected void loadChildren() {
/* 4002 */       this.loadedChildren = true;
/* 4003 */       createChildren(this, this.childValue);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TreeNode getChildAt(int param1Int) {
/* 4010 */       if (!this.loadedChildren)
/* 4011 */         loadChildren(); 
/* 4012 */       return super.getChildAt(param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Enumeration children() {
/* 4019 */       if (!this.loadedChildren)
/* 4020 */         loadChildren(); 
/* 4021 */       return super.children();
/*      */     }
/*      */   }
/*      */   
/*      */   void setUIProperty(String paramString, Object paramObject) {
/* 4026 */     if (paramString == "rowHeight") {
/* 4027 */       if (!this.rowHeightSet) {
/* 4028 */         setRowHeight(((Number)paramObject).intValue());
/* 4029 */         this.rowHeightSet = false;
/*      */       } 
/* 4031 */     } else if (paramString == "scrollsOnExpand") {
/* 4032 */       if (!this.scrollsOnExpandSet) {
/* 4033 */         setScrollsOnExpand(((Boolean)paramObject).booleanValue());
/* 4034 */         this.scrollsOnExpandSet = false;
/*      */       } 
/* 4036 */     } else if (paramString == "showsRootHandles") {
/* 4037 */       if (!this.showsRootHandlesSet) {
/* 4038 */         setShowsRootHandles(((Boolean)paramObject).booleanValue());
/* 4039 */         this.showsRootHandlesSet = false;
/*      */       } 
/*      */     } else {
/* 4042 */       super.setUIProperty(paramString, paramObject);
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
/* 4058 */     String str1 = this.rootVisible ? "true" : "false";
/*      */     
/* 4060 */     String str2 = this.showsRootHandles ? "true" : "false";
/*      */     
/* 4062 */     String str3 = this.editable ? "true" : "false";
/*      */     
/* 4064 */     String str4 = this.largeModel ? "true" : "false";
/*      */     
/* 4066 */     String str5 = this.invokesStopCellEditing ? "true" : "false";
/*      */     
/* 4068 */     String str6 = this.scrollsOnExpand ? "true" : "false";
/*      */ 
/*      */     
/* 4071 */     return super.paramString() + ",editable=" + str3 + ",invokesStopCellEditing=" + str5 + ",largeModel=" + str4 + ",rootVisible=" + str1 + ",rowHeight=" + this.rowHeight + ",scrollsOnExpand=" + str6 + ",showsRootHandles=" + str2 + ",toggleClickCount=" + this.toggleClickCount + ",visibleRowCount=" + this.visibleRowCount;
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
/*      */   public AccessibleContext getAccessibleContext() {
/* 4097 */     if (this.accessibleContext == null) {
/* 4098 */       this.accessibleContext = new AccessibleJTree();
/*      */     }
/* 4100 */     return this.accessibleContext;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class AccessibleJTree
/*      */     extends JComponent.AccessibleJComponent
/*      */     implements AccessibleSelection, TreeSelectionListener, TreeModelListener, TreeExpansionListener
/*      */   {
/*      */     TreePath leadSelectionPath;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Accessible leadSelectionAccessible;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleJTree() {
/* 4127 */       TreeModel treeModel = JTree.this.getModel();
/* 4128 */       if (treeModel != null) {
/* 4129 */         treeModel.addTreeModelListener(this);
/*      */       }
/* 4131 */       JTree.this.addTreeExpansionListener(this);
/* 4132 */       JTree.this.addTreeSelectionListener(this);
/* 4133 */       this.leadSelectionPath = JTree.this.getLeadSelectionPath();
/* 4134 */       this.leadSelectionAccessible = (this.leadSelectionPath != null) ? new AccessibleJTreeNode(JTree.this, this.leadSelectionPath, JTree.this) : null;
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
/*      */     public void valueChanged(TreeSelectionEvent param1TreeSelectionEvent) {
/* 4149 */       firePropertyChange("AccessibleSelection", 
/* 4150 */           Boolean.valueOf(false), Boolean.valueOf(true));
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
/*      */     public void fireVisibleDataPropertyChange() {
/* 4163 */       firePropertyChange("AccessibleVisibleData", 
/* 4164 */           Boolean.valueOf(false), Boolean.valueOf(true));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void treeNodesChanged(TreeModelEvent param1TreeModelEvent) {
/* 4175 */       fireVisibleDataPropertyChange();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void treeNodesInserted(TreeModelEvent param1TreeModelEvent) {
/* 4184 */       fireVisibleDataPropertyChange();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void treeNodesRemoved(TreeModelEvent param1TreeModelEvent) {
/* 4193 */       fireVisibleDataPropertyChange();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void treeStructureChanged(TreeModelEvent param1TreeModelEvent) {
/* 4202 */       fireVisibleDataPropertyChange();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void treeCollapsed(TreeExpansionEvent param1TreeExpansionEvent) {
/* 4211 */       fireVisibleDataPropertyChange();
/* 4212 */       TreePath treePath = param1TreeExpansionEvent.getPath();
/* 4213 */       if (treePath != null) {
/*      */ 
/*      */         
/* 4216 */         AccessibleJTreeNode accessibleJTreeNode = new AccessibleJTreeNode(JTree.this, treePath, null);
/*      */ 
/*      */         
/* 4219 */         PropertyChangeEvent propertyChangeEvent = new PropertyChangeEvent(accessibleJTreeNode, "AccessibleState", AccessibleState.EXPANDED, AccessibleState.COLLAPSED);
/*      */ 
/*      */ 
/*      */         
/* 4223 */         firePropertyChange("AccessibleState", null, propertyChangeEvent);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void treeExpanded(TreeExpansionEvent param1TreeExpansionEvent) {
/* 4234 */       fireVisibleDataPropertyChange();
/* 4235 */       TreePath treePath = param1TreeExpansionEvent.getPath();
/* 4236 */       if (treePath != null) {
/*      */ 
/*      */ 
/*      */         
/* 4240 */         AccessibleJTreeNode accessibleJTreeNode = new AccessibleJTreeNode(JTree.this, treePath, null);
/*      */ 
/*      */         
/* 4243 */         PropertyChangeEvent propertyChangeEvent = new PropertyChangeEvent(accessibleJTreeNode, "AccessibleState", AccessibleState.COLLAPSED, AccessibleState.EXPANDED);
/*      */ 
/*      */ 
/*      */         
/* 4247 */         firePropertyChange("AccessibleState", null, propertyChangeEvent);
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
/*      */     
/*      */     void fireActiveDescendantPropertyChange(TreePath param1TreePath1, TreePath param1TreePath2) {
/* 4264 */       if (param1TreePath1 != param1TreePath2) {
/* 4265 */         AccessibleJTreeNode accessibleJTreeNode1 = (param1TreePath1 != null) ? new AccessibleJTreeNode(JTree.this, param1TreePath1, null) : null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4271 */         AccessibleJTreeNode accessibleJTreeNode2 = (param1TreePath2 != null) ? new AccessibleJTreeNode(JTree.this, param1TreePath2, null) : null;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4276 */         firePropertyChange("AccessibleActiveDescendant", accessibleJTreeNode1, accessibleJTreeNode2);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private AccessibleContext getCurrentAccessibleContext() {
/* 4282 */       Component component = getCurrentComponent();
/* 4283 */       if (component instanceof Accessible) {
/* 4284 */         return component.getAccessibleContext();
/*      */       }
/* 4286 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Component getCurrentComponent() {
/* 4294 */       TreeModel treeModel = JTree.this.getModel();
/* 4295 */       if (treeModel == null) {
/* 4296 */         return null;
/*      */       }
/* 4298 */       Object object = treeModel.getRoot();
/* 4299 */       if (object == null) {
/* 4300 */         return null;
/*      */       }
/* 4302 */       TreePath treePath = new TreePath(object);
/* 4303 */       if (JTree.this.isVisible(treePath)) {
/* 4304 */         TreeCellRenderer treeCellRenderer = JTree.this.getCellRenderer();
/* 4305 */         TreeUI treeUI = JTree.this.getUI();
/* 4306 */         if (treeUI != null) {
/* 4307 */           int i = treeUI.getRowForPath(JTree.this, treePath);
/* 4308 */           int j = JTree.this.getLeadSelectionRow();
/* 4309 */           boolean bool = (JTree.this.isFocusOwner() && j == i) ? true : false;
/*      */           
/* 4311 */           boolean bool1 = JTree.this.isPathSelected(treePath);
/* 4312 */           boolean bool2 = JTree.this.isExpanded(treePath);
/*      */           
/* 4314 */           return treeCellRenderer.getTreeCellRendererComponent(JTree.this, object, bool1, bool2, treeModel
/*      */               
/* 4316 */               .isLeaf(object), i, bool);
/*      */         } 
/*      */       } 
/* 4319 */       return null;
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
/*      */     public AccessibleRole getAccessibleRole() {
/* 4332 */       return AccessibleRole.TREE;
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
/*      */     public Accessible getAccessibleAt(Point param1Point) {
/* 4345 */       TreePath treePath = JTree.this.getClosestPathForLocation(param1Point.x, param1Point.y);
/* 4346 */       if (treePath != null)
/*      */       {
/* 4348 */         return new AccessibleJTreeNode(JTree.this, treePath, null);
/*      */       }
/* 4350 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getAccessibleChildrenCount() {
/* 4361 */       TreeModel treeModel = JTree.this.getModel();
/* 4362 */       if (treeModel == null) {
/* 4363 */         return 0;
/*      */       }
/* 4365 */       if (JTree.this.isRootVisible()) {
/* 4366 */         return 1;
/*      */       }
/*      */       
/* 4369 */       Object object = treeModel.getRoot();
/* 4370 */       if (object == null) {
/* 4371 */         return 0;
/*      */       }
/* 4373 */       return treeModel.getChildCount(object);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Accessible getAccessibleChild(int param1Int) {
/* 4383 */       TreeModel treeModel = JTree.this.getModel();
/* 4384 */       if (treeModel == null) {
/* 4385 */         return null;
/*      */       }
/*      */       
/* 4388 */       Object object1 = treeModel.getRoot();
/* 4389 */       if (object1 == null) {
/* 4390 */         return null;
/*      */       }
/*      */       
/* 4393 */       if (JTree.this.isRootVisible()) {
/* 4394 */         if (param1Int == 0) {
/* 4395 */           Object[] arrayOfObject1 = { object1 };
/* 4396 */           if (arrayOfObject1[0] == null)
/* 4397 */             return null; 
/* 4398 */           TreePath treePath1 = new TreePath(arrayOfObject1);
/* 4399 */           return new AccessibleJTreeNode(JTree.this, treePath1, JTree.this);
/*      */         } 
/* 4401 */         return null;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 4406 */       int i = treeModel.getChildCount(object1);
/* 4407 */       if (param1Int < 0 || param1Int >= i) {
/* 4408 */         return null;
/*      */       }
/* 4410 */       Object object2 = treeModel.getChild(object1, param1Int);
/* 4411 */       if (object2 == null)
/* 4412 */         return null; 
/* 4413 */       Object[] arrayOfObject = { object1, object2 };
/* 4414 */       TreePath treePath = new TreePath(arrayOfObject);
/* 4415 */       return new AccessibleJTreeNode(JTree.this, treePath, JTree.this);
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
/*      */     public int getAccessibleIndexInParent() {
/* 4427 */       return super.getAccessibleIndexInParent();
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
/*      */     public AccessibleSelection getAccessibleSelection() {
/* 4440 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getAccessibleSelectionCount() {
/* 4450 */       Object[] arrayOfObject = new Object[1];
/* 4451 */       arrayOfObject[0] = JTree.this.treeModel.getRoot();
/* 4452 */       if (arrayOfObject[0] == null) {
/* 4453 */         return 0;
/*      */       }
/* 4455 */       TreePath treePath = new TreePath(arrayOfObject);
/* 4456 */       if (JTree.this.isPathSelected(treePath)) {
/* 4457 */         return 1;
/*      */       }
/* 4459 */       return 0;
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
/*      */     public Accessible getAccessibleSelection(int param1Int) {
/* 4474 */       if (param1Int == 0) {
/* 4475 */         Object[] arrayOfObject = new Object[1];
/* 4476 */         arrayOfObject[0] = JTree.this.treeModel.getRoot();
/* 4477 */         if (arrayOfObject[0] == null)
/* 4478 */           return null; 
/* 4479 */         TreePath treePath = new TreePath(arrayOfObject);
/* 4480 */         if (JTree.this.isPathSelected(treePath)) {
/* 4481 */           return new AccessibleJTreeNode(JTree.this, treePath, JTree.this);
/*      */         }
/*      */       } 
/* 4484 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isAccessibleChildSelected(int param1Int) {
/* 4495 */       if (param1Int == 0) {
/* 4496 */         Object[] arrayOfObject = new Object[1];
/* 4497 */         arrayOfObject[0] = JTree.this.treeModel.getRoot();
/* 4498 */         if (arrayOfObject[0] == null)
/* 4499 */           return false; 
/* 4500 */         TreePath treePath = new TreePath(arrayOfObject);
/* 4501 */         return JTree.this.isPathSelected(treePath);
/*      */       } 
/* 4503 */       return false;
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
/*      */     public void addAccessibleSelection(int param1Int) {
/* 4517 */       TreeModel treeModel = JTree.this.getModel();
/* 4518 */       if (treeModel != null && 
/* 4519 */         param1Int == 0) {
/* 4520 */         Object[] arrayOfObject = { treeModel.getRoot() };
/* 4521 */         if (arrayOfObject[0] == null)
/*      */           return; 
/* 4523 */         TreePath treePath = new TreePath(arrayOfObject);
/* 4524 */         JTree.this.addSelectionPath(treePath);
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
/*      */     public void removeAccessibleSelection(int param1Int) {
/* 4537 */       TreeModel treeModel = JTree.this.getModel();
/* 4538 */       if (treeModel != null && 
/* 4539 */         param1Int == 0) {
/* 4540 */         Object[] arrayOfObject = { treeModel.getRoot() };
/* 4541 */         if (arrayOfObject[0] == null)
/*      */           return; 
/* 4543 */         TreePath treePath = new TreePath(arrayOfObject);
/* 4544 */         JTree.this.removeSelectionPath(treePath);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void clearAccessibleSelection() {
/* 4554 */       int i = getAccessibleChildrenCount();
/* 4555 */       for (byte b = 0; b < i; b++) {
/* 4556 */         removeAccessibleSelection(b);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void selectAllAccessibleSelection() {
/* 4565 */       TreeModel treeModel = JTree.this.getModel();
/* 4566 */       if (treeModel != null) {
/* 4567 */         Object[] arrayOfObject = { treeModel.getRoot() };
/* 4568 */         if (arrayOfObject[0] == null)
/*      */           return; 
/* 4570 */         TreePath treePath = new TreePath(arrayOfObject);
/* 4571 */         JTree.this.addSelectionPath(treePath);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected class AccessibleJTreeNode
/*      */       extends AccessibleContext
/*      */       implements Accessible, AccessibleComponent, AccessibleSelection, AccessibleAction
/*      */     {
/* 4584 */       private JTree tree = null;
/* 4585 */       private TreeModel treeModel = null;
/* 4586 */       private Object obj = null;
/* 4587 */       private TreePath path = null;
/* 4588 */       private Accessible accessibleParent = null;
/* 4589 */       private int index = 0;
/*      */ 
/*      */       
/*      */       private boolean isLeaf = false;
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleJTreeNode(JTree param2JTree, TreePath param2TreePath, Accessible param2Accessible) {
/* 4597 */         this.tree = param2JTree;
/* 4598 */         this.path = param2TreePath;
/* 4599 */         this.accessibleParent = param2Accessible;
/* 4600 */         this.treeModel = param2JTree.getModel();
/* 4601 */         this.obj = param2TreePath.getLastPathComponent();
/* 4602 */         if (this.treeModel != null) {
/* 4603 */           this.isLeaf = this.treeModel.isLeaf(this.obj);
/*      */         }
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       private TreePath getChildTreePath(int param2Int) {
/* 4610 */         if (param2Int < 0 || param2Int >= getAccessibleChildrenCount()) {
/* 4611 */           return null;
/*      */         }
/* 4613 */         Object object = this.treeModel.getChild(this.obj, param2Int);
/* 4614 */         Object[] arrayOfObject1 = this.path.getPath();
/* 4615 */         Object[] arrayOfObject2 = new Object[arrayOfObject1.length + 1];
/* 4616 */         System.arraycopy(arrayOfObject1, 0, arrayOfObject2, 0, arrayOfObject1.length);
/* 4617 */         arrayOfObject2[arrayOfObject2.length - 1] = object;
/* 4618 */         return new TreePath(arrayOfObject2);
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
/*      */       public AccessibleContext getAccessibleContext() {
/* 4631 */         return this;
/*      */       }
/*      */       
/*      */       private AccessibleContext getCurrentAccessibleContext() {
/* 4635 */         Component component = getCurrentComponent();
/* 4636 */         if (component instanceof Accessible) {
/* 4637 */           return component.getAccessibleContext();
/*      */         }
/* 4639 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private Component getCurrentComponent() {
/* 4647 */         if (this.tree.isVisible(this.path)) {
/* 4648 */           TreeCellRenderer treeCellRenderer = this.tree.getCellRenderer();
/* 4649 */           if (treeCellRenderer == null) {
/* 4650 */             return null;
/*      */           }
/* 4652 */           TreeUI treeUI = this.tree.getUI();
/* 4653 */           if (treeUI != null) {
/* 4654 */             int i = treeUI.getRowForPath(JTree.this, this.path);
/* 4655 */             boolean bool1 = this.tree.isPathSelected(this.path);
/* 4656 */             boolean bool2 = this.tree.isExpanded(this.path);
/* 4657 */             boolean bool = false;
/* 4658 */             return treeCellRenderer.getTreeCellRendererComponent(this.tree, this.obj, bool1, bool2, this.isLeaf, i, bool);
/*      */           } 
/*      */         } 
/*      */         
/* 4662 */         return null;
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
/*      */       public String getAccessibleName() {
/* 4674 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 4675 */         if (accessibleContext != null) {
/* 4676 */           String str = accessibleContext.getAccessibleName();
/* 4677 */           if (str != null && str != "") {
/* 4678 */             return accessibleContext.getAccessibleName();
/*      */           }
/* 4680 */           return null;
/*      */         } 
/*      */         
/* 4683 */         if (this.accessibleName != null && this.accessibleName != "") {
/* 4684 */           return this.accessibleName;
/*      */         }
/*      */         
/* 4687 */         return (String)JTree.this.getClientProperty("AccessibleName");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setAccessibleName(String param2String) {
/* 4697 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 4698 */         if (accessibleContext != null) {
/* 4699 */           accessibleContext.setAccessibleName(param2String);
/*      */         } else {
/* 4701 */           super.setAccessibleName(param2String);
/*      */         } 
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
/*      */       public String getAccessibleDescription() {
/* 4715 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 4716 */         if (accessibleContext != null) {
/* 4717 */           return accessibleContext.getAccessibleDescription();
/*      */         }
/* 4719 */         return super.getAccessibleDescription();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setAccessibleDescription(String param2String) {
/* 4729 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 4730 */         if (accessibleContext != null) {
/* 4731 */           accessibleContext.setAccessibleDescription(param2String);
/*      */         } else {
/* 4733 */           super.setAccessibleDescription(param2String);
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleRole getAccessibleRole() {
/* 4744 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 4745 */         if (accessibleContext != null) {
/* 4746 */           return accessibleContext.getAccessibleRole();
/*      */         }
/* 4748 */         return AccessibleRole.UNKNOWN;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleStateSet getAccessibleStateSet() {
/*      */         AccessibleStateSet accessibleStateSet;
/* 4760 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/*      */         
/* 4762 */         if (accessibleContext != null) {
/* 4763 */           accessibleStateSet = accessibleContext.getAccessibleStateSet();
/*      */         } else {
/* 4765 */           accessibleStateSet = new AccessibleStateSet();
/*      */         } 
/*      */ 
/*      */         
/* 4769 */         if (isShowing()) {
/* 4770 */           accessibleStateSet.add(AccessibleState.SHOWING);
/* 4771 */         } else if (accessibleStateSet.contains(AccessibleState.SHOWING)) {
/* 4772 */           accessibleStateSet.remove(AccessibleState.SHOWING);
/*      */         } 
/* 4774 */         if (isVisible()) {
/* 4775 */           accessibleStateSet.add(AccessibleState.VISIBLE);
/* 4776 */         } else if (accessibleStateSet.contains(AccessibleState.VISIBLE)) {
/* 4777 */           accessibleStateSet.remove(AccessibleState.VISIBLE);
/*      */         } 
/* 4779 */         if (this.tree.isPathSelected(this.path)) {
/* 4780 */           accessibleStateSet.add(AccessibleState.SELECTED);
/*      */         }
/* 4782 */         if (this.path == JTree.this.getLeadSelectionPath()) {
/* 4783 */           accessibleStateSet.add(AccessibleState.ACTIVE);
/*      */         }
/* 4785 */         if (!this.isLeaf) {
/* 4786 */           accessibleStateSet.add(AccessibleState.EXPANDABLE);
/*      */         }
/* 4788 */         if (this.tree.isExpanded(this.path)) {
/* 4789 */           accessibleStateSet.add(AccessibleState.EXPANDED);
/*      */         } else {
/* 4791 */           accessibleStateSet.add(AccessibleState.COLLAPSED);
/*      */         } 
/* 4793 */         if (this.tree.isEditable()) {
/* 4794 */           accessibleStateSet.add(AccessibleState.EDITABLE);
/*      */         }
/* 4796 */         return accessibleStateSet;
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
/*      */       public Accessible getAccessibleParent() {
/* 4808 */         if (this.accessibleParent == null) {
/* 4809 */           Object[] arrayOfObject = this.path.getPath();
/* 4810 */           if (arrayOfObject.length > 1) {
/* 4811 */             Object object = arrayOfObject[arrayOfObject.length - 2];
/* 4812 */             if (this.treeModel != null) {
/* 4813 */               this.index = this.treeModel.getIndexOfChild(object, this.obj);
/*      */             }
/* 4815 */             Object[] arrayOfObject1 = new Object[arrayOfObject.length - 1];
/* 4816 */             System.arraycopy(arrayOfObject, 0, arrayOfObject1, 0, arrayOfObject.length - 1);
/*      */             
/* 4818 */             TreePath treePath = new TreePath(arrayOfObject1);
/* 4819 */             this.accessibleParent = new AccessibleJTreeNode(this.tree, treePath, null);
/*      */ 
/*      */             
/* 4822 */             setAccessibleParent(this.accessibleParent);
/* 4823 */           } else if (this.treeModel != null) {
/* 4824 */             this.accessibleParent = this.tree;
/* 4825 */             this.index = 0;
/* 4826 */             setAccessibleParent(this.accessibleParent);
/*      */           } 
/*      */         } 
/* 4829 */         return this.accessibleParent;
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
/*      */       public int getAccessibleIndexInParent() {
/* 4841 */         if (this.accessibleParent == null) {
/* 4842 */           getAccessibleParent();
/*      */         }
/* 4844 */         Object[] arrayOfObject = this.path.getPath();
/* 4845 */         if (arrayOfObject.length > 1) {
/* 4846 */           Object object = arrayOfObject[arrayOfObject.length - 2];
/* 4847 */           if (this.treeModel != null) {
/* 4848 */             this.index = this.treeModel.getIndexOfChild(object, this.obj);
/*      */           }
/*      */         } 
/* 4851 */         return this.index;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getAccessibleChildrenCount() {
/* 4862 */         return this.treeModel.getChildCount(this.obj);
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
/*      */       public Accessible getAccessibleChild(int param2Int) {
/* 4874 */         if (param2Int < 0 || param2Int >= getAccessibleChildrenCount()) {
/* 4875 */           return null;
/*      */         }
/* 4877 */         Object object = this.treeModel.getChild(this.obj, param2Int);
/* 4878 */         Object[] arrayOfObject1 = this.path.getPath();
/* 4879 */         Object[] arrayOfObject2 = new Object[arrayOfObject1.length + 1];
/* 4880 */         System.arraycopy(arrayOfObject1, 0, arrayOfObject2, 0, arrayOfObject1.length);
/* 4881 */         arrayOfObject2[arrayOfObject2.length - 1] = object;
/* 4882 */         TreePath treePath = new TreePath(arrayOfObject2);
/* 4883 */         return new AccessibleJTreeNode(JTree.this, treePath, this);
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
/*      */       public Locale getLocale() {
/* 4900 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 4901 */         if (accessibleContext != null) {
/* 4902 */           return accessibleContext.getLocale();
/*      */         }
/* 4904 */         return this.tree.getLocale();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void addPropertyChangeListener(PropertyChangeListener param2PropertyChangeListener) {
/* 4915 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 4916 */         if (accessibleContext != null) {
/* 4917 */           accessibleContext.addPropertyChangeListener(param2PropertyChangeListener);
/*      */         } else {
/* 4919 */           super.addPropertyChangeListener(param2PropertyChangeListener);
/*      */         } 
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
/* 4931 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 4932 */         if (accessibleContext != null) {
/* 4933 */           accessibleContext.removePropertyChangeListener(param2PropertyChangeListener);
/*      */         } else {
/* 4935 */           super.removePropertyChangeListener(param2PropertyChangeListener);
/*      */         } 
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
/*      */       public AccessibleAction getAccessibleAction() {
/* 4948 */         return this;
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
/*      */       public AccessibleComponent getAccessibleComponent() {
/* 4960 */         return this;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleSelection getAccessibleSelection() {
/* 4970 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 4971 */         if (accessibleContext != null && this.isLeaf) {
/* 4972 */           return accessibleContext.getAccessibleSelection();
/*      */         }
/* 4974 */         return this;
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
/* 4985 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 4986 */         if (accessibleContext != null) {
/* 4987 */           return accessibleContext.getAccessibleText();
/*      */         }
/* 4989 */         return null;
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
/* 5000 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 5001 */         if (accessibleContext != null) {
/* 5002 */           return accessibleContext.getAccessibleValue();
/*      */         }
/* 5004 */         return null;
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
/*      */       public Color getBackground() {
/* 5018 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 5019 */         if (accessibleContext instanceof AccessibleComponent) {
/* 5020 */           return ((AccessibleComponent)accessibleContext).getBackground();
/*      */         }
/* 5022 */         Component component = getCurrentComponent();
/* 5023 */         if (component != null) {
/* 5024 */           return component.getBackground();
/*      */         }
/* 5026 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setBackground(Color param2Color) {
/* 5037 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 5038 */         if (accessibleContext instanceof AccessibleComponent) {
/* 5039 */           ((AccessibleComponent)accessibleContext).setBackground(param2Color);
/*      */         } else {
/* 5041 */           Component component = getCurrentComponent();
/* 5042 */           if (component != null) {
/* 5043 */             component.setBackground(param2Color);
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
/*      */       
/*      */       public Color getForeground() {
/* 5056 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 5057 */         if (accessibleContext instanceof AccessibleComponent) {
/* 5058 */           return ((AccessibleComponent)accessibleContext).getForeground();
/*      */         }
/* 5060 */         Component component = getCurrentComponent();
/* 5061 */         if (component != null) {
/* 5062 */           return component.getForeground();
/*      */         }
/* 5064 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void setForeground(Color param2Color) {
/* 5070 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 5071 */         if (accessibleContext instanceof AccessibleComponent) {
/* 5072 */           ((AccessibleComponent)accessibleContext).setForeground(param2Color);
/*      */         } else {
/* 5074 */           Component component = getCurrentComponent();
/* 5075 */           if (component != null) {
/* 5076 */             component.setForeground(param2Color);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public Cursor getCursor() {
/* 5082 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 5083 */         if (accessibleContext instanceof AccessibleComponent) {
/* 5084 */           return ((AccessibleComponent)accessibleContext).getCursor();
/*      */         }
/* 5086 */         Component component = getCurrentComponent();
/* 5087 */         if (component != null) {
/* 5088 */           return component.getCursor();
/*      */         }
/* 5090 */         Accessible accessible = getAccessibleParent();
/* 5091 */         if (accessible instanceof AccessibleComponent) {
/* 5092 */           return ((AccessibleComponent)accessible).getCursor();
/*      */         }
/* 5094 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setCursor(Cursor param2Cursor) {
/* 5101 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 5102 */         if (accessibleContext instanceof AccessibleComponent) {
/* 5103 */           ((AccessibleComponent)accessibleContext).setCursor(param2Cursor);
/*      */         } else {
/* 5105 */           Component component = getCurrentComponent();
/* 5106 */           if (component != null) {
/* 5107 */             component.setCursor(param2Cursor);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public Font getFont() {
/* 5113 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 5114 */         if (accessibleContext instanceof AccessibleComponent) {
/* 5115 */           return ((AccessibleComponent)accessibleContext).getFont();
/*      */         }
/* 5117 */         Component component = getCurrentComponent();
/* 5118 */         if (component != null) {
/* 5119 */           return component.getFont();
/*      */         }
/* 5121 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void setFont(Font param2Font) {
/* 5127 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 5128 */         if (accessibleContext instanceof AccessibleComponent) {
/* 5129 */           ((AccessibleComponent)accessibleContext).setFont(param2Font);
/*      */         } else {
/* 5131 */           Component component = getCurrentComponent();
/* 5132 */           if (component != null) {
/* 5133 */             component.setFont(param2Font);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public FontMetrics getFontMetrics(Font param2Font) {
/* 5139 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 5140 */         if (accessibleContext instanceof AccessibleComponent) {
/* 5141 */           return ((AccessibleComponent)accessibleContext).getFontMetrics(param2Font);
/*      */         }
/* 5143 */         Component component = getCurrentComponent();
/* 5144 */         if (component != null) {
/* 5145 */           return component.getFontMetrics(param2Font);
/*      */         }
/* 5147 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean isEnabled() {
/* 5153 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 5154 */         if (accessibleContext instanceof AccessibleComponent) {
/* 5155 */           return ((AccessibleComponent)accessibleContext).isEnabled();
/*      */         }
/* 5157 */         Component component = getCurrentComponent();
/* 5158 */         if (component != null) {
/* 5159 */           return component.isEnabled();
/*      */         }
/* 5161 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void setEnabled(boolean param2Boolean) {
/* 5167 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 5168 */         if (accessibleContext instanceof AccessibleComponent) {
/* 5169 */           ((AccessibleComponent)accessibleContext).setEnabled(param2Boolean);
/*      */         } else {
/* 5171 */           Component component = getCurrentComponent();
/* 5172 */           if (component != null) {
/* 5173 */             component.setEnabled(param2Boolean);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public boolean isVisible() {
/* 5179 */         Rectangle rectangle1 = this.tree.getPathBounds(this.path);
/* 5180 */         Rectangle rectangle2 = this.tree.getVisibleRect();
/* 5181 */         return (rectangle1 != null && rectangle2 != null && rectangle2
/* 5182 */           .intersects(rectangle1));
/*      */       }
/*      */ 
/*      */       
/*      */       public void setVisible(boolean param2Boolean) {}
/*      */       
/*      */       public boolean isShowing() {
/* 5189 */         return (this.tree.isShowing() && isVisible());
/*      */       }
/*      */       
/*      */       public boolean contains(Point param2Point) {
/* 5193 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 5194 */         if (accessibleContext instanceof AccessibleComponent) {
/* 5195 */           Rectangle rectangle = ((AccessibleComponent)accessibleContext).getBounds();
/* 5196 */           return rectangle.contains(param2Point);
/*      */         } 
/* 5198 */         Component component = getCurrentComponent();
/* 5199 */         if (component != null) {
/* 5200 */           Rectangle rectangle = component.getBounds();
/* 5201 */           return rectangle.contains(param2Point);
/*      */         } 
/* 5203 */         return getBounds().contains(param2Point);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public Point getLocationOnScreen() {
/* 5209 */         if (this.tree != null) {
/* 5210 */           Point point = this.tree.getLocationOnScreen();
/* 5211 */           Rectangle rectangle = this.tree.getPathBounds(this.path);
/* 5212 */           if (point != null && rectangle != null) {
/* 5213 */             Point point1 = new Point(rectangle.x, rectangle.y);
/*      */             
/* 5215 */             point1.translate(point.x, point.y);
/* 5216 */             return point1;
/*      */           } 
/* 5218 */           return null;
/*      */         } 
/*      */         
/* 5221 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       protected Point getLocationInJTree() {
/* 5226 */         Rectangle rectangle = this.tree.getPathBounds(this.path);
/* 5227 */         if (rectangle != null) {
/* 5228 */           return rectangle.getLocation();
/*      */         }
/* 5230 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public Point getLocation() {
/* 5235 */         Rectangle rectangle = getBounds();
/* 5236 */         if (rectangle != null) {
/* 5237 */           return rectangle.getLocation();
/*      */         }
/* 5239 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public void setLocation(Point param2Point) {}
/*      */ 
/*      */       
/*      */       public Rectangle getBounds() {
/* 5247 */         Rectangle rectangle = this.tree.getPathBounds(this.path);
/* 5248 */         Accessible accessible = getAccessibleParent();
/* 5249 */         if (accessible != null && 
/* 5250 */           accessible instanceof AccessibleJTreeNode) {
/* 5251 */           Point point = ((AccessibleJTreeNode)accessible).getLocationInJTree();
/* 5252 */           if (point != null && rectangle != null) {
/* 5253 */             rectangle.translate(-point.x, -point.y);
/*      */           } else {
/* 5255 */             return null;
/*      */           } 
/*      */         } 
/*      */         
/* 5259 */         return rectangle;
/*      */       }
/*      */       
/*      */       public void setBounds(Rectangle param2Rectangle) {
/* 5263 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 5264 */         if (accessibleContext instanceof AccessibleComponent) {
/* 5265 */           ((AccessibleComponent)accessibleContext).setBounds(param2Rectangle);
/*      */         } else {
/* 5267 */           Component component = getCurrentComponent();
/* 5268 */           if (component != null) {
/* 5269 */             component.setBounds(param2Rectangle);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public Dimension getSize() {
/* 5275 */         return getBounds().getSize();
/*      */       }
/*      */       
/*      */       public void setSize(Dimension param2Dimension) {
/* 5279 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 5280 */         if (accessibleContext instanceof AccessibleComponent) {
/* 5281 */           ((AccessibleComponent)accessibleContext).setSize(param2Dimension);
/*      */         } else {
/* 5283 */           Component component = getCurrentComponent();
/* 5284 */           if (component != null) {
/* 5285 */             component.setSize(param2Dimension);
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
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Accessible getAccessibleAt(Point param2Point) {
/* 5301 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 5302 */         if (accessibleContext instanceof AccessibleComponent) {
/* 5303 */           return ((AccessibleComponent)accessibleContext).getAccessibleAt(param2Point);
/*      */         }
/* 5305 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean isFocusTraversable() {
/* 5311 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 5312 */         if (accessibleContext instanceof AccessibleComponent) {
/* 5313 */           return ((AccessibleComponent)accessibleContext).isFocusTraversable();
/*      */         }
/* 5315 */         Component component = getCurrentComponent();
/* 5316 */         if (component != null) {
/* 5317 */           return component.isFocusTraversable();
/*      */         }
/* 5319 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void requestFocus() {
/* 5325 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 5326 */         if (accessibleContext instanceof AccessibleComponent) {
/* 5327 */           ((AccessibleComponent)accessibleContext).requestFocus();
/*      */         } else {
/* 5329 */           Component component = getCurrentComponent();
/* 5330 */           if (component != null) {
/* 5331 */             component.requestFocus();
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public void addFocusListener(FocusListener param2FocusListener) {
/* 5337 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 5338 */         if (accessibleContext instanceof AccessibleComponent) {
/* 5339 */           ((AccessibleComponent)accessibleContext).addFocusListener(param2FocusListener);
/*      */         } else {
/* 5341 */           Component component = getCurrentComponent();
/* 5342 */           if (component != null) {
/* 5343 */             component.addFocusListener(param2FocusListener);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public void removeFocusListener(FocusListener param2FocusListener) {
/* 5349 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 5350 */         if (accessibleContext instanceof AccessibleComponent) {
/* 5351 */           ((AccessibleComponent)accessibleContext).removeFocusListener(param2FocusListener);
/*      */         } else {
/* 5353 */           Component component = getCurrentComponent();
/* 5354 */           if (component != null) {
/* 5355 */             component.removeFocusListener(param2FocusListener);
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
/*      */ 
/*      */       
/*      */       public int getAccessibleSelectionCount() {
/* 5369 */         byte b1 = 0;
/* 5370 */         int i = getAccessibleChildrenCount();
/* 5371 */         for (byte b2 = 0; b2 < i; b2++) {
/* 5372 */           TreePath treePath = getChildTreePath(b2);
/* 5373 */           if (this.tree.isPathSelected(treePath)) {
/* 5374 */             b1++;
/*      */           }
/*      */         } 
/* 5377 */         return b1;
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
/*      */       public Accessible getAccessibleSelection(int param2Int) {
/* 5390 */         int i = getAccessibleChildrenCount();
/* 5391 */         if (param2Int < 0 || param2Int >= i) {
/* 5392 */           return null;
/*      */         }
/* 5394 */         byte b1 = 0;
/* 5395 */         for (byte b2 = 0; b2 < i && param2Int >= b1; b2++) {
/* 5396 */           TreePath treePath = getChildTreePath(b2);
/* 5397 */           if (this.tree.isPathSelected(treePath)) {
/* 5398 */             if (b1 == param2Int) {
/* 5399 */               return new AccessibleJTreeNode(this.tree, treePath, this);
/*      */             }
/* 5401 */             b1++;
/*      */           } 
/*      */         } 
/*      */         
/* 5405 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean isAccessibleChildSelected(int param2Int) {
/* 5416 */         int i = getAccessibleChildrenCount();
/* 5417 */         if (param2Int < 0 || param2Int >= i) {
/* 5418 */           return false;
/*      */         }
/* 5420 */         TreePath treePath = getChildTreePath(param2Int);
/* 5421 */         return this.tree.isPathSelected(treePath);
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
/*      */       public void addAccessibleSelection(int param2Int) {
/* 5435 */         TreeModel treeModel = JTree.this.getModel();
/* 5436 */         if (treeModel != null && 
/* 5437 */           param2Int >= 0 && param2Int < getAccessibleChildrenCount()) {
/* 5438 */           TreePath treePath = getChildTreePath(param2Int);
/* 5439 */           JTree.this.addSelectionPath(treePath);
/*      */         } 
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
/*      */       public void removeAccessibleSelection(int param2Int) {
/* 5453 */         TreeModel treeModel = JTree.this.getModel();
/* 5454 */         if (treeModel != null && 
/* 5455 */           param2Int >= 0 && param2Int < getAccessibleChildrenCount()) {
/* 5456 */           TreePath treePath = getChildTreePath(param2Int);
/* 5457 */           JTree.this.removeSelectionPath(treePath);
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void clearAccessibleSelection() {
/* 5467 */         int i = getAccessibleChildrenCount();
/* 5468 */         for (byte b = 0; b < i; b++) {
/* 5469 */           removeAccessibleSelection(b);
/*      */         }
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void selectAllAccessibleSelection() {
/* 5478 */         TreeModel treeModel = JTree.this.getModel();
/* 5479 */         if (treeModel != null) {
/* 5480 */           int i = getAccessibleChildrenCount();
/*      */           
/* 5482 */           for (byte b = 0; b < i; b++) {
/* 5483 */             TreePath treePath = getChildTreePath(b);
/* 5484 */             JTree.this.addSelectionPath(treePath);
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
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int getAccessibleActionCount() {
/* 5500 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 5501 */         if (accessibleContext != null) {
/* 5502 */           AccessibleAction accessibleAction = accessibleContext.getAccessibleAction();
/* 5503 */           if (accessibleAction != null) {
/* 5504 */             return accessibleAction.getAccessibleActionCount() + (this.isLeaf ? 0 : 1);
/*      */           }
/*      */         } 
/* 5507 */         return this.isLeaf ? 0 : 1;
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
/*      */       public String getAccessibleActionDescription(int param2Int) {
/* 5520 */         if (param2Int < 0 || param2Int >= getAccessibleActionCount()) {
/* 5521 */           return null;
/*      */         }
/* 5523 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 5524 */         if (param2Int == 0)
/*      */         {
/* 5526 */           return AccessibleAction.TOGGLE_EXPAND; } 
/* 5527 */         if (accessibleContext != null) {
/* 5528 */           AccessibleAction accessibleAction = accessibleContext.getAccessibleAction();
/* 5529 */           if (accessibleAction != null) {
/* 5530 */             return accessibleAction.getAccessibleActionDescription(param2Int - 1);
/*      */           }
/*      */         } 
/* 5533 */         return null;
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
/*      */       public boolean doAccessibleAction(int param2Int) {
/* 5546 */         if (param2Int < 0 || param2Int >= getAccessibleActionCount()) {
/* 5547 */           return false;
/*      */         }
/* 5549 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 5550 */         if (param2Int == 0) {
/* 5551 */           if (JTree.this.isExpanded(this.path)) {
/* 5552 */             JTree.this.collapsePath(this.path);
/*      */           } else {
/* 5554 */             JTree.this.expandPath(this.path);
/*      */           } 
/* 5556 */           return true;
/* 5557 */         }  if (accessibleContext != null) {
/* 5558 */           AccessibleAction accessibleAction = accessibleContext.getAccessibleAction();
/* 5559 */           if (accessibleAction != null) {
/* 5560 */             return accessibleAction.doAccessibleAction(param2Int - 1);
/*      */           }
/*      */         } 
/* 5563 */         return false;
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JTree.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */