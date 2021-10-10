/*      */ package javax.swing.plaf.basic;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ComponentListener;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.KeyListener;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.MouseListener;
/*      */ import java.awt.event.MouseMotionListener;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import javax.swing.AbstractAction;
/*      */ import javax.swing.CellRendererPane;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.InputMap;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JScrollBar;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.JTree;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.LookAndFeel;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.Timer;
/*      */ import javax.swing.TransferHandler;
/*      */ import javax.swing.UIDefaults;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.event.CellEditorListener;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.TreeExpansionEvent;
/*      */ import javax.swing.event.TreeExpansionListener;
/*      */ import javax.swing.event.TreeModelEvent;
/*      */ import javax.swing.event.TreeModelListener;
/*      */ import javax.swing.event.TreeSelectionEvent;
/*      */ import javax.swing.event.TreeSelectionListener;
/*      */ import javax.swing.text.Position;
/*      */ import javax.swing.tree.AbstractLayoutCache;
/*      */ import javax.swing.tree.DefaultTreeCellEditor;
/*      */ import javax.swing.tree.DefaultTreeCellRenderer;
/*      */ import javax.swing.tree.TreeCellEditor;
/*      */ import javax.swing.tree.TreeCellRenderer;
/*      */ import javax.swing.tree.TreeModel;
/*      */ import javax.swing.tree.TreePath;
/*      */ import javax.swing.tree.TreeSelectionModel;
/*      */ import sun.swing.DefaultLookup;
/*      */ import sun.swing.SwingUtilities2;
/*      */ 
/*      */ public class BasicTreeUI extends TreeUI {
/*   61 */   private static final StringBuilder BASELINE_COMPONENT_KEY = new StringBuilder("Tree.baselineComponent");
/*      */ 
/*      */ 
/*      */   
/*   65 */   private static final Actions SHARED_ACTION = new Actions();
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient Icon collapsedIcon;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient Icon expandedIcon;
/*      */ 
/*      */ 
/*      */   
/*      */   private Color hashColor;
/*      */ 
/*      */ 
/*      */   
/*      */   protected int leftChildIndent;
/*      */ 
/*      */ 
/*      */   
/*      */   protected int rightChildIndent;
/*      */ 
/*      */ 
/*      */   
/*      */   protected int totalChildIndent;
/*      */ 
/*      */ 
/*      */   
/*      */   protected Dimension preferredMinSize;
/*      */ 
/*      */   
/*      */   protected int lastSelectedRow;
/*      */ 
/*      */   
/*      */   protected JTree tree;
/*      */ 
/*      */   
/*      */   protected transient TreeCellRenderer currentCellRenderer;
/*      */ 
/*      */   
/*      */   protected boolean createdRenderer;
/*      */ 
/*      */   
/*      */   protected transient TreeCellEditor cellEditor;
/*      */ 
/*      */   
/*      */   protected boolean createdCellEditor;
/*      */ 
/*      */   
/*      */   protected boolean stopEditingInCompleteEditing;
/*      */ 
/*      */   
/*      */   protected CellRendererPane rendererPane;
/*      */ 
/*      */   
/*      */   protected Dimension preferredSize;
/*      */ 
/*      */   
/*      */   protected boolean validCachedPreferredSize;
/*      */ 
/*      */   
/*      */   protected AbstractLayoutCache treeState;
/*      */ 
/*      */   
/*      */   protected Hashtable<TreePath, Boolean> drawingCache;
/*      */ 
/*      */   
/*      */   protected boolean largeModel;
/*      */ 
/*      */   
/*      */   protected AbstractLayoutCache.NodeDimensions nodeDimensions;
/*      */ 
/*      */   
/*      */   protected TreeModel treeModel;
/*      */ 
/*      */   
/*      */   protected TreeSelectionModel treeSelectionModel;
/*      */ 
/*      */   
/*      */   protected int depthOffset;
/*      */ 
/*      */   
/*      */   protected Component editingComponent;
/*      */ 
/*      */   
/*      */   protected TreePath editingPath;
/*      */ 
/*      */   
/*      */   protected int editingRow;
/*      */ 
/*      */   
/*      */   protected boolean editorHasDifferentSize;
/*      */ 
/*      */   
/*      */   private int leadRow;
/*      */ 
/*      */   
/*      */   private boolean ignoreLAChange;
/*      */ 
/*      */   
/*      */   private boolean leftToRight;
/*      */ 
/*      */   
/*      */   private PropertyChangeListener propertyChangeListener;
/*      */ 
/*      */   
/*      */   private PropertyChangeListener selectionModelPropertyChangeListener;
/*      */ 
/*      */   
/*      */   private MouseListener mouseListener;
/*      */ 
/*      */   
/*      */   private FocusListener focusListener;
/*      */ 
/*      */   
/*      */   private KeyListener keyListener;
/*      */ 
/*      */   
/*      */   private ComponentListener componentListener;
/*      */ 
/*      */   
/*      */   private CellEditorListener cellEditorListener;
/*      */ 
/*      */   
/*      */   private TreeSelectionListener treeSelectionListener;
/*      */ 
/*      */   
/*      */   private TreeModelListener treeModelListener;
/*      */ 
/*      */   
/*      */   private TreeExpansionListener treeExpansionListener;
/*      */ 
/*      */   
/*      */   private boolean paintLines = true;
/*      */ 
/*      */   
/*      */   private boolean lineTypeDashed;
/*      */ 
/*      */   
/*  204 */   private long timeFactor = 1000L;
/*      */ 
/*      */   
/*      */   private Handler handler;
/*      */ 
/*      */   
/*      */   private MouseEvent releaseEvent;
/*      */ 
/*      */ 
/*      */   
/*      */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  215 */     return new BasicTreeUI();
/*      */   }
/*      */ 
/*      */   
/*      */   static void loadActionMap(LazyActionMap paramLazyActionMap) {
/*  220 */     paramLazyActionMap.put(new Actions("selectPrevious"));
/*  221 */     paramLazyActionMap.put(new Actions("selectPreviousChangeLead"));
/*  222 */     paramLazyActionMap.put(new Actions("selectPreviousExtendSelection"));
/*      */     
/*  224 */     paramLazyActionMap.put(new Actions("selectNext"));
/*  225 */     paramLazyActionMap.put(new Actions("selectNextChangeLead"));
/*  226 */     paramLazyActionMap.put(new Actions("selectNextExtendSelection"));
/*      */     
/*  228 */     paramLazyActionMap.put(new Actions("selectChild"));
/*  229 */     paramLazyActionMap.put(new Actions("selectChildChangeLead"));
/*      */     
/*  231 */     paramLazyActionMap.put(new Actions("selectParent"));
/*  232 */     paramLazyActionMap.put(new Actions("selectParentChangeLead"));
/*      */     
/*  234 */     paramLazyActionMap.put(new Actions("scrollUpChangeSelection"));
/*  235 */     paramLazyActionMap.put(new Actions("scrollUpChangeLead"));
/*  236 */     paramLazyActionMap.put(new Actions("scrollUpExtendSelection"));
/*      */     
/*  238 */     paramLazyActionMap.put(new Actions("scrollDownChangeSelection"));
/*  239 */     paramLazyActionMap.put(new Actions("scrollDownExtendSelection"));
/*  240 */     paramLazyActionMap.put(new Actions("scrollDownChangeLead"));
/*      */     
/*  242 */     paramLazyActionMap.put(new Actions("selectFirst"));
/*  243 */     paramLazyActionMap.put(new Actions("selectFirstChangeLead"));
/*  244 */     paramLazyActionMap.put(new Actions("selectFirstExtendSelection"));
/*      */     
/*  246 */     paramLazyActionMap.put(new Actions("selectLast"));
/*  247 */     paramLazyActionMap.put(new Actions("selectLastChangeLead"));
/*  248 */     paramLazyActionMap.put(new Actions("selectLastExtendSelection"));
/*      */     
/*  250 */     paramLazyActionMap.put(new Actions("toggle"));
/*      */     
/*  252 */     paramLazyActionMap.put(new Actions("cancel"));
/*      */     
/*  254 */     paramLazyActionMap.put(new Actions("startEditing"));
/*      */     
/*  256 */     paramLazyActionMap.put(new Actions("selectAll"));
/*      */     
/*  258 */     paramLazyActionMap.put(new Actions("clearSelection"));
/*      */     
/*  260 */     paramLazyActionMap.put(new Actions("scrollLeft"));
/*  261 */     paramLazyActionMap.put(new Actions("scrollRight"));
/*      */     
/*  263 */     paramLazyActionMap.put(new Actions("scrollLeftExtendSelection"));
/*  264 */     paramLazyActionMap.put(new Actions("scrollRightExtendSelection"));
/*      */     
/*  266 */     paramLazyActionMap.put(new Actions("scrollRightChangeLead"));
/*  267 */     paramLazyActionMap.put(new Actions("scrollLeftChangeLead"));
/*      */     
/*  269 */     paramLazyActionMap.put(new Actions("expand"));
/*  270 */     paramLazyActionMap.put(new Actions("collapse"));
/*  271 */     paramLazyActionMap.put(new Actions("moveSelectionToParent"));
/*      */     
/*  273 */     paramLazyActionMap.put(new Actions("addToSelection"));
/*  274 */     paramLazyActionMap.put(new Actions("toggleAndAnchor"));
/*  275 */     paramLazyActionMap.put(new Actions("extendTo"));
/*  276 */     paramLazyActionMap.put(new Actions("moveSelectionTo"));
/*      */     
/*  278 */     paramLazyActionMap.put(TransferHandler.getCutAction());
/*  279 */     paramLazyActionMap.put(TransferHandler.getCopyAction());
/*  280 */     paramLazyActionMap.put(TransferHandler.getPasteAction());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Color getHashColor() {
/*  289 */     return this.hashColor;
/*      */   }
/*      */   
/*      */   protected void setHashColor(Color paramColor) {
/*  293 */     this.hashColor = paramColor;
/*      */   }
/*      */   
/*      */   public void setLeftChildIndent(int paramInt) {
/*  297 */     this.leftChildIndent = paramInt;
/*  298 */     this.totalChildIndent = this.leftChildIndent + this.rightChildIndent;
/*  299 */     if (this.treeState != null)
/*  300 */       this.treeState.invalidateSizes(); 
/*  301 */     updateSize();
/*      */   }
/*      */   
/*      */   public int getLeftChildIndent() {
/*  305 */     return this.leftChildIndent;
/*      */   }
/*      */   
/*      */   public void setRightChildIndent(int paramInt) {
/*  309 */     this.rightChildIndent = paramInt;
/*  310 */     this.totalChildIndent = this.leftChildIndent + this.rightChildIndent;
/*  311 */     if (this.treeState != null)
/*  312 */       this.treeState.invalidateSizes(); 
/*  313 */     updateSize();
/*      */   }
/*      */   
/*      */   public int getRightChildIndent() {
/*  317 */     return this.rightChildIndent;
/*      */   }
/*      */   
/*      */   public void setExpandedIcon(Icon paramIcon) {
/*  321 */     this.expandedIcon = paramIcon;
/*      */   }
/*      */   
/*      */   public Icon getExpandedIcon() {
/*  325 */     return this.expandedIcon;
/*      */   }
/*      */   
/*      */   public void setCollapsedIcon(Icon paramIcon) {
/*  329 */     this.collapsedIcon = paramIcon;
/*      */   }
/*      */   
/*      */   public Icon getCollapsedIcon() {
/*  333 */     return this.collapsedIcon;
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
/*      */   protected void setLargeModel(boolean paramBoolean) {
/*  346 */     if (getRowHeight() < 1)
/*  347 */       paramBoolean = false; 
/*  348 */     if (this.largeModel != paramBoolean) {
/*  349 */       completeEditing();
/*  350 */       this.largeModel = paramBoolean;
/*  351 */       this.treeState = createLayoutCache();
/*  352 */       configureLayoutCache();
/*  353 */       updateLayoutCacheExpandedNodesIfNecessary();
/*  354 */       updateSize();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean isLargeModel() {
/*  359 */     return this.largeModel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setRowHeight(int paramInt) {
/*  366 */     completeEditing();
/*  367 */     if (this.treeState != null) {
/*  368 */       setLargeModel(this.tree.isLargeModel());
/*  369 */       this.treeState.setRowHeight(paramInt);
/*  370 */       updateSize();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected int getRowHeight() {
/*  375 */     return (this.tree == null) ? -1 : this.tree.getRowHeight();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setCellRenderer(TreeCellRenderer paramTreeCellRenderer) {
/*  383 */     completeEditing();
/*  384 */     updateRenderer();
/*  385 */     if (this.treeState != null) {
/*  386 */       this.treeState.invalidateSizes();
/*  387 */       updateSize();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected TreeCellRenderer getCellRenderer() {
/*  396 */     return this.currentCellRenderer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setModel(TreeModel paramTreeModel) {
/*  403 */     completeEditing();
/*  404 */     if (this.treeModel != null && this.treeModelListener != null)
/*  405 */       this.treeModel.removeTreeModelListener(this.treeModelListener); 
/*  406 */     this.treeModel = paramTreeModel;
/*  407 */     if (this.treeModel != null && 
/*  408 */       this.treeModelListener != null) {
/*  409 */       this.treeModel.addTreeModelListener(this.treeModelListener);
/*      */     }
/*  411 */     if (this.treeState != null) {
/*  412 */       this.treeState.setModel(paramTreeModel);
/*  413 */       updateLayoutCacheExpandedNodesIfNecessary();
/*  414 */       updateSize();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected TreeModel getModel() {
/*  419 */     return this.treeModel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setRootVisible(boolean paramBoolean) {
/*  426 */     completeEditing();
/*  427 */     updateDepthOffset();
/*  428 */     if (this.treeState != null) {
/*  429 */       this.treeState.setRootVisible(paramBoolean);
/*  430 */       this.treeState.invalidateSizes();
/*  431 */       updateSize();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean isRootVisible() {
/*  436 */     return (this.tree != null) ? this.tree.isRootVisible() : false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setShowsRootHandles(boolean paramBoolean) {
/*  443 */     completeEditing();
/*  444 */     updateDepthOffset();
/*  445 */     if (this.treeState != null) {
/*  446 */       this.treeState.invalidateSizes();
/*  447 */       updateSize();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean getShowsRootHandles() {
/*  452 */     return (this.tree != null) ? this.tree.getShowsRootHandles() : false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setCellEditor(TreeCellEditor paramTreeCellEditor) {
/*  459 */     updateCellEditor();
/*      */   }
/*      */   
/*      */   protected TreeCellEditor getCellEditor() {
/*  463 */     return (this.tree != null) ? this.tree.getCellEditor() : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setEditable(boolean paramBoolean) {
/*  470 */     updateCellEditor();
/*      */   }
/*      */   
/*      */   protected boolean isEditable() {
/*  474 */     return (this.tree != null) ? this.tree.isEditable() : false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setSelectionModel(TreeSelectionModel paramTreeSelectionModel) {
/*  482 */     completeEditing();
/*  483 */     if (this.selectionModelPropertyChangeListener != null && this.treeSelectionModel != null)
/*      */     {
/*  485 */       this.treeSelectionModel
/*  486 */         .removePropertyChangeListener(this.selectionModelPropertyChangeListener); } 
/*  487 */     if (this.treeSelectionListener != null && this.treeSelectionModel != null)
/*  488 */       this.treeSelectionModel
/*  489 */         .removeTreeSelectionListener(this.treeSelectionListener); 
/*  490 */     this.treeSelectionModel = paramTreeSelectionModel;
/*  491 */     if (this.treeSelectionModel != null) {
/*  492 */       if (this.selectionModelPropertyChangeListener != null)
/*  493 */         this.treeSelectionModel
/*  494 */           .addPropertyChangeListener(this.selectionModelPropertyChangeListener); 
/*  495 */       if (this.treeSelectionListener != null)
/*  496 */         this.treeSelectionModel
/*  497 */           .addTreeSelectionListener(this.treeSelectionListener); 
/*  498 */       if (this.treeState != null) {
/*  499 */         this.treeState.setSelectionModel(this.treeSelectionModel);
/*      */       }
/*  501 */     } else if (this.treeState != null) {
/*  502 */       this.treeState.setSelectionModel(null);
/*  503 */     }  if (this.tree != null)
/*  504 */       this.tree.repaint(); 
/*      */   }
/*      */   
/*      */   protected TreeSelectionModel getSelectionModel() {
/*  508 */     return this.treeSelectionModel;
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
/*      */   public Rectangle getPathBounds(JTree paramJTree, TreePath paramTreePath) {
/*  521 */     if (paramJTree != null && this.treeState != null) {
/*  522 */       return getPathBounds(paramTreePath, paramJTree.getInsets(), new Rectangle());
/*      */     }
/*  524 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private Rectangle getPathBounds(TreePath paramTreePath, Insets paramInsets, Rectangle paramRectangle) {
/*  529 */     paramRectangle = this.treeState.getBounds(paramTreePath, paramRectangle);
/*  530 */     if (paramRectangle != null) {
/*  531 */       if (this.leftToRight) {
/*  532 */         paramRectangle.x += paramInsets.left;
/*      */       } else {
/*  534 */         paramRectangle.x = this.tree.getWidth() - paramRectangle.x + paramRectangle.width - paramInsets.right;
/*      */       } 
/*      */       
/*  537 */       paramRectangle.y += paramInsets.top;
/*      */     } 
/*  539 */     return paramRectangle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TreePath getPathForRow(JTree paramJTree, int paramInt) {
/*  547 */     return (this.treeState != null) ? this.treeState.getPathForRow(paramInt) : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRowForPath(JTree paramJTree, TreePath paramTreePath) {
/*  556 */     return (this.treeState != null) ? this.treeState.getRowForPath(paramTreePath) : -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRowCount(JTree paramJTree) {
/*  563 */     return (this.treeState != null) ? this.treeState.getRowCount() : 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TreePath getClosestPathForLocation(JTree paramJTree, int paramInt1, int paramInt2) {
/*  574 */     if (paramJTree != null && this.treeState != null) {
/*      */ 
/*      */       
/*  577 */       paramInt2 -= (paramJTree.getInsets()).top;
/*  578 */       return this.treeState.getPathClosestTo(paramInt1, paramInt2);
/*      */     } 
/*  580 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEditing(JTree paramJTree) {
/*  588 */     return (this.editingComponent != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean stopEditing(JTree paramJTree) {
/*  597 */     if (this.editingComponent != null && this.cellEditor.stopCellEditing()) {
/*  598 */       completeEditing(false, false, true);
/*  599 */       return true;
/*      */     } 
/*  601 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cancelEditing(JTree paramJTree) {
/*  608 */     if (this.editingComponent != null) {
/*  609 */       completeEditing(false, true, false);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startEditingAtPath(JTree paramJTree, TreePath paramTreePath) {
/*  618 */     paramJTree.scrollPathToVisible(paramTreePath);
/*  619 */     if (paramTreePath != null && paramJTree.isVisible(paramTreePath)) {
/*  620 */       startEditing(paramTreePath, null);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public TreePath getEditingPath(JTree paramJTree) {
/*  627 */     return this.editingPath;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void installUI(JComponent paramJComponent) {
/*  635 */     if (paramJComponent == null) {
/*  636 */       throw new NullPointerException("null component passed to BasicTreeUI.installUI()");
/*      */     }
/*      */     
/*  639 */     this.tree = (JTree)paramJComponent;
/*      */     
/*  641 */     prepareForUIInstall();
/*      */ 
/*      */     
/*  644 */     installDefaults();
/*  645 */     installKeyboardActions();
/*  646 */     installComponents();
/*  647 */     installListeners();
/*      */     
/*  649 */     completeUIInstall();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void prepareForUIInstall() {
/*  657 */     this.drawingCache = new Hashtable<>(7);
/*      */ 
/*      */     
/*  660 */     this.leftToRight = BasicGraphicsUtils.isLeftToRight(this.tree);
/*  661 */     this.stopEditingInCompleteEditing = true;
/*  662 */     this.lastSelectedRow = -1;
/*  663 */     this.leadRow = -1;
/*  664 */     this.preferredSize = new Dimension();
/*      */     
/*  666 */     this.largeModel = this.tree.isLargeModel();
/*  667 */     if (getRowHeight() <= 0)
/*  668 */       this.largeModel = false; 
/*  669 */     setModel(this.tree.getModel());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void completeUIInstall() {
/*  679 */     setShowsRootHandles(this.tree.getShowsRootHandles());
/*      */     
/*  681 */     updateRenderer();
/*      */     
/*  683 */     updateDepthOffset();
/*      */     
/*  685 */     setSelectionModel(this.tree.getSelectionModel());
/*      */ 
/*      */     
/*  688 */     this.treeState = createLayoutCache();
/*  689 */     configureLayoutCache();
/*      */     
/*  691 */     updateSize();
/*      */   }
/*      */   
/*      */   protected void installDefaults() {
/*  695 */     if (this.tree.getBackground() == null || this.tree
/*  696 */       .getBackground() instanceof UIResource) {
/*  697 */       this.tree.setBackground(UIManager.getColor("Tree.background"));
/*      */     }
/*  699 */     if (getHashColor() == null || getHashColor() instanceof UIResource) {
/*  700 */       setHashColor(UIManager.getColor("Tree.hash"));
/*      */     }
/*  702 */     if (this.tree.getFont() == null || this.tree.getFont() instanceof UIResource) {
/*  703 */       this.tree.setFont(UIManager.getFont("Tree.font"));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  712 */     setExpandedIcon((Icon)UIManager.get("Tree.expandedIcon"));
/*  713 */     setCollapsedIcon((Icon)UIManager.get("Tree.collapsedIcon"));
/*      */     
/*  715 */     setLeftChildIndent(((Integer)UIManager.get("Tree.leftChildIndent"))
/*  716 */         .intValue());
/*  717 */     setRightChildIndent(((Integer)UIManager.get("Tree.rightChildIndent"))
/*  718 */         .intValue());
/*      */     
/*  720 */     LookAndFeel.installProperty(this.tree, "rowHeight", 
/*  721 */         UIManager.get("Tree.rowHeight"));
/*      */     
/*  723 */     this.largeModel = (this.tree.isLargeModel() && this.tree.getRowHeight() > 0);
/*      */     
/*  725 */     Object object1 = UIManager.get("Tree.scrollsOnExpand");
/*  726 */     if (object1 != null) {
/*  727 */       LookAndFeel.installProperty(this.tree, "scrollsOnExpand", object1);
/*      */     }
/*      */     
/*  730 */     this.paintLines = UIManager.getBoolean("Tree.paintLines");
/*  731 */     this.lineTypeDashed = UIManager.getBoolean("Tree.lineTypeDashed");
/*      */     
/*  733 */     Long long_ = (Long)UIManager.get("Tree.timeFactor");
/*  734 */     this.timeFactor = (long_ != null) ? long_.longValue() : 1000L;
/*      */     
/*  736 */     Object object2 = UIManager.get("Tree.showsRootHandles");
/*  737 */     if (object2 != null) {
/*  738 */       LookAndFeel.installProperty(this.tree, "showsRootHandles", object2);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void installListeners() {
/*  744 */     if ((this.propertyChangeListener = createPropertyChangeListener()) != null)
/*      */     {
/*  746 */       this.tree.addPropertyChangeListener(this.propertyChangeListener);
/*      */     }
/*  748 */     if ((this.mouseListener = createMouseListener()) != null) {
/*  749 */       this.tree.addMouseListener(this.mouseListener);
/*  750 */       if (this.mouseListener instanceof MouseMotionListener) {
/*  751 */         this.tree.addMouseMotionListener((MouseMotionListener)this.mouseListener);
/*      */       }
/*      */     } 
/*  754 */     if ((this.focusListener = createFocusListener()) != null) {
/*  755 */       this.tree.addFocusListener(this.focusListener);
/*      */     }
/*  757 */     if ((this.keyListener = createKeyListener()) != null) {
/*  758 */       this.tree.addKeyListener(this.keyListener);
/*      */     }
/*  760 */     if ((this.treeExpansionListener = createTreeExpansionListener()) != null) {
/*  761 */       this.tree.addTreeExpansionListener(this.treeExpansionListener);
/*      */     }
/*  763 */     if ((this.treeModelListener = createTreeModelListener()) != null && this.treeModel != null)
/*      */     {
/*  765 */       this.treeModel.addTreeModelListener(this.treeModelListener);
/*      */     }
/*  767 */     if ((this
/*  768 */       .selectionModelPropertyChangeListener = createSelectionModelPropertyChangeListener()) != null && this.treeSelectionModel != null)
/*      */     {
/*  770 */       this.treeSelectionModel
/*  771 */         .addPropertyChangeListener(this.selectionModelPropertyChangeListener);
/*      */     }
/*  773 */     if ((this.treeSelectionListener = createTreeSelectionListener()) != null && this.treeSelectionModel != null)
/*      */     {
/*  775 */       this.treeSelectionModel.addTreeSelectionListener(this.treeSelectionListener);
/*      */     }
/*      */     
/*  778 */     TransferHandler transferHandler = this.tree.getTransferHandler();
/*  779 */     if (transferHandler == null || transferHandler instanceof UIResource) {
/*  780 */       this.tree.setTransferHandler(defaultTransferHandler);
/*      */ 
/*      */       
/*  783 */       if (this.tree.getDropTarget() instanceof UIResource) {
/*  784 */         this.tree.setDropTarget(null);
/*      */       }
/*      */     } 
/*      */     
/*  788 */     LookAndFeel.installProperty(this.tree, "opaque", Boolean.TRUE);
/*      */   }
/*      */   
/*      */   protected void installKeyboardActions() {
/*  792 */     InputMap inputMap = getInputMap(1);
/*      */ 
/*      */     
/*  795 */     SwingUtilities.replaceUIInputMap(this.tree, 1, inputMap);
/*      */ 
/*      */     
/*  798 */     inputMap = getInputMap(0);
/*  799 */     SwingUtilities.replaceUIInputMap(this.tree, 0, inputMap);
/*      */     
/*  801 */     LazyActionMap.installLazyActionMap(this.tree, BasicTreeUI.class, "Tree.actionMap");
/*      */   }
/*      */ 
/*      */   
/*      */   InputMap getInputMap(int paramInt) {
/*  806 */     if (paramInt == 1) {
/*  807 */       return (InputMap)DefaultLookup.get(this.tree, this, "Tree.ancestorInputMap");
/*      */     }
/*      */     
/*  810 */     if (paramInt == 0) {
/*  811 */       InputMap inputMap1 = (InputMap)DefaultLookup.get(this.tree, this, "Tree.focusInputMap");
/*      */       
/*      */       InputMap inputMap2;
/*      */       
/*  815 */       if (this.tree.getComponentOrientation().isLeftToRight() || (
/*  816 */         inputMap2 = (InputMap)DefaultLookup.get(this.tree, this, "Tree.focusInputMap.RightToLeft")) == null)
/*      */       {
/*  818 */         return inputMap1;
/*      */       }
/*  820 */       inputMap2.setParent(inputMap1);
/*  821 */       return inputMap2;
/*      */     } 
/*      */     
/*  824 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void installComponents() {
/*  831 */     if ((this.rendererPane = createCellRendererPane()) != null) {
/*  832 */       this.tree.add(this.rendererPane);
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
/*      */   protected AbstractLayoutCache.NodeDimensions createNodeDimensions() {
/*  845 */     return new NodeDimensionsHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PropertyChangeListener createPropertyChangeListener() {
/*  853 */     return getHandler();
/*      */   }
/*      */   
/*      */   private Handler getHandler() {
/*  857 */     if (this.handler == null) {
/*  858 */       this.handler = new Handler();
/*      */     }
/*  860 */     return this.handler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected MouseListener createMouseListener() {
/*  868 */     return getHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected FocusListener createFocusListener() {
/*  876 */     return getHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected KeyListener createKeyListener() {
/*  884 */     return getHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PropertyChangeListener createSelectionModelPropertyChangeListener() {
/*  892 */     return getHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected TreeSelectionListener createTreeSelectionListener() {
/*  900 */     return getHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected CellEditorListener createCellEditorListener() {
/*  907 */     return getHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ComponentListener createComponentListener() {
/*  916 */     return new ComponentHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected TreeExpansionListener createTreeExpansionListener() {
/*  924 */     return getHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AbstractLayoutCache createLayoutCache() {
/*  932 */     if (isLargeModel() && getRowHeight() > 0) {
/*  933 */       return new FixedHeightLayoutCache();
/*      */     }
/*  935 */     return new VariableHeightLayoutCache();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected CellRendererPane createCellRendererPane() {
/*  942 */     return new CellRendererPane();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected TreeCellEditor createDefaultCellEditor() {
/*  949 */     if (this.currentCellRenderer != null && this.currentCellRenderer instanceof DefaultTreeCellRenderer)
/*      */     {
/*  951 */       return new DefaultTreeCellEditor(this.tree, (DefaultTreeCellRenderer)this.currentCellRenderer);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  956 */     return new DefaultTreeCellEditor(this.tree, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected TreeCellRenderer createDefaultCellRenderer() {
/*  964 */     return new DefaultTreeCellRenderer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected TreeModelListener createTreeModelListener() {
/*  971 */     return getHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void uninstallUI(JComponent paramJComponent) {
/*  979 */     completeEditing();
/*      */     
/*  981 */     prepareForUIUninstall();
/*      */     
/*  983 */     uninstallDefaults();
/*  984 */     uninstallListeners();
/*  985 */     uninstallKeyboardActions();
/*  986 */     uninstallComponents();
/*      */     
/*  988 */     completeUIUninstall();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void prepareForUIUninstall() {}
/*      */   
/*      */   protected void completeUIUninstall() {
/*  995 */     if (this.createdRenderer) {
/*  996 */       this.tree.setCellRenderer((TreeCellRenderer)null);
/*      */     }
/*  998 */     if (this.createdCellEditor) {
/*  999 */       this.tree.setCellEditor((TreeCellEditor)null);
/*      */     }
/* 1001 */     this.cellEditor = null;
/* 1002 */     this.currentCellRenderer = null;
/* 1003 */     this.rendererPane = null;
/* 1004 */     this.componentListener = null;
/* 1005 */     this.propertyChangeListener = null;
/* 1006 */     this.mouseListener = null;
/* 1007 */     this.focusListener = null;
/* 1008 */     this.keyListener = null;
/* 1009 */     setSelectionModel(null);
/* 1010 */     this.treeState = null;
/* 1011 */     this.drawingCache = null;
/* 1012 */     this.selectionModelPropertyChangeListener = null;
/* 1013 */     this.tree = null;
/* 1014 */     this.treeModel = null;
/* 1015 */     this.treeSelectionModel = null;
/* 1016 */     this.treeSelectionListener = null;
/* 1017 */     this.treeExpansionListener = null;
/*      */   }
/*      */   
/*      */   protected void uninstallDefaults() {
/* 1021 */     if (this.tree.getTransferHandler() instanceof UIResource) {
/* 1022 */       this.tree.setTransferHandler((TransferHandler)null);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void uninstallListeners() {
/* 1027 */     if (this.componentListener != null) {
/* 1028 */       this.tree.removeComponentListener(this.componentListener);
/*      */     }
/* 1030 */     if (this.propertyChangeListener != null) {
/* 1031 */       this.tree.removePropertyChangeListener(this.propertyChangeListener);
/*      */     }
/* 1033 */     if (this.mouseListener != null) {
/* 1034 */       this.tree.removeMouseListener(this.mouseListener);
/* 1035 */       if (this.mouseListener instanceof MouseMotionListener) {
/* 1036 */         this.tree.removeMouseMotionListener((MouseMotionListener)this.mouseListener);
/*      */       }
/*      */     } 
/* 1039 */     if (this.focusListener != null) {
/* 1040 */       this.tree.removeFocusListener(this.focusListener);
/*      */     }
/* 1042 */     if (this.keyListener != null) {
/* 1043 */       this.tree.removeKeyListener(this.keyListener);
/*      */     }
/* 1045 */     if (this.treeExpansionListener != null) {
/* 1046 */       this.tree.removeTreeExpansionListener(this.treeExpansionListener);
/*      */     }
/* 1048 */     if (this.treeModel != null && this.treeModelListener != null) {
/* 1049 */       this.treeModel.removeTreeModelListener(this.treeModelListener);
/*      */     }
/* 1051 */     if (this.selectionModelPropertyChangeListener != null && this.treeSelectionModel != null)
/*      */     {
/* 1053 */       this.treeSelectionModel
/* 1054 */         .removePropertyChangeListener(this.selectionModelPropertyChangeListener);
/*      */     }
/* 1056 */     if (this.treeSelectionListener != null && this.treeSelectionModel != null) {
/* 1057 */       this.treeSelectionModel
/* 1058 */         .removeTreeSelectionListener(this.treeSelectionListener);
/*      */     }
/* 1060 */     this.handler = null;
/*      */   }
/*      */   
/*      */   protected void uninstallKeyboardActions() {
/* 1064 */     SwingUtilities.replaceUIActionMap(this.tree, null);
/* 1065 */     SwingUtilities.replaceUIInputMap(this.tree, 1, null);
/*      */ 
/*      */     
/* 1068 */     SwingUtilities.replaceUIInputMap(this.tree, 0, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void uninstallComponents() {
/* 1075 */     if (this.rendererPane != null) {
/* 1076 */       this.tree.remove(this.rendererPane);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void redoTheLayout() {
/* 1084 */     if (this.treeState != null) {
/* 1085 */       this.treeState.invalidateSizes();
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
/*      */   public int getBaseline(JComponent paramJComponent, int paramInt1, int paramInt2) {
/*      */     int j;
/* 1098 */     super.getBaseline(paramJComponent, paramInt1, paramInt2);
/* 1099 */     UIDefaults uIDefaults = UIManager.getLookAndFeelDefaults();
/* 1100 */     Component component = (Component)uIDefaults.get(BASELINE_COMPONENT_KEY);
/*      */     
/* 1102 */     if (component == null) {
/* 1103 */       TreeCellRenderer treeCellRenderer = createDefaultCellRenderer();
/* 1104 */       component = treeCellRenderer.getTreeCellRendererComponent(this.tree, "a", false, false, false, -1, false);
/*      */       
/* 1106 */       uIDefaults.put(BASELINE_COMPONENT_KEY, component);
/*      */     } 
/* 1108 */     int i = this.tree.getRowHeight();
/*      */     
/* 1110 */     if (i > 0) {
/* 1111 */       j = component.getBaseline(2147483647, i);
/*      */     } else {
/*      */       
/* 1114 */       Dimension dimension = component.getPreferredSize();
/* 1115 */       j = component.getBaseline(dimension.width, dimension.height);
/*      */     } 
/* 1117 */     return j + (this.tree.getInsets()).top;
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
/* 1130 */     super.getBaselineResizeBehavior(paramJComponent);
/* 1131 */     return Component.BaselineResizeBehavior.CONSTANT_ASCENT;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 1139 */     if (this.tree != paramJComponent) {
/* 1140 */       throw new InternalError("incorrect component");
/*      */     }
/*      */ 
/*      */     
/* 1144 */     if (this.treeState == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1148 */     Rectangle rectangle = paramGraphics.getClipBounds();
/* 1149 */     Insets insets = this.tree.getInsets();
/*      */     
/* 1151 */     TreePath treePath = getClosestPathForLocation(this.tree, 0, rectangle.y);
/*      */     
/* 1153 */     Enumeration<TreePath> enumeration = this.treeState.getVisiblePathsFrom(treePath);
/* 1154 */     int i = this.treeState.getRowForPath(treePath);
/* 1155 */     int j = rectangle.y + rectangle.height;
/*      */     
/* 1157 */     this.drawingCache.clear();
/*      */     
/* 1159 */     if (treePath != null && enumeration != null) {
/* 1160 */       TreePath treePath1 = treePath;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1165 */       treePath1 = treePath1.getParentPath();
/* 1166 */       while (treePath1 != null) {
/* 1167 */         paintVerticalPartOfLeg(paramGraphics, rectangle, insets, treePath1);
/* 1168 */         this.drawingCache.put(treePath1, Boolean.TRUE);
/* 1169 */         treePath1 = treePath1.getParentPath();
/*      */       } 
/*      */       
/* 1172 */       boolean bool = false;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1177 */       Rectangle rectangle1 = new Rectangle();
/*      */ 
/*      */       
/* 1180 */       boolean bool1 = isRootVisible();
/*      */       
/* 1182 */       while (!bool && enumeration.hasMoreElements()) {
/* 1183 */         TreePath treePath2 = enumeration.nextElement();
/* 1184 */         if (treePath2 != null) {
/* 1185 */           boolean bool2, bool3, bool4 = this.treeModel.isLeaf(treePath2.getLastPathComponent());
/* 1186 */           if (bool4) {
/* 1187 */             bool2 = bool3 = false;
/*      */           } else {
/* 1189 */             bool2 = this.treeState.getExpandedState(treePath2);
/* 1190 */             bool3 = this.tree.hasBeenExpanded(treePath2);
/*      */           } 
/* 1192 */           Rectangle rectangle2 = getPathBounds(treePath2, insets, rectangle1);
/* 1193 */           if (rectangle2 == null) {
/*      */             return;
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1200 */           treePath1 = treePath2.getParentPath();
/* 1201 */           if (treePath1 != null) {
/* 1202 */             if (this.drawingCache.get(treePath1) == null) {
/* 1203 */               paintVerticalPartOfLeg(paramGraphics, rectangle, insets, treePath1);
/*      */               
/* 1205 */               this.drawingCache.put(treePath1, Boolean.TRUE);
/*      */             } 
/* 1207 */             paintHorizontalPartOfLeg(paramGraphics, rectangle, insets, rectangle2, treePath2, i, bool2, bool3, bool4);
/*      */ 
/*      */ 
/*      */           
/*      */           }
/* 1212 */           else if (bool1 && i == 0) {
/* 1213 */             paintHorizontalPartOfLeg(paramGraphics, rectangle, insets, rectangle2, treePath2, i, bool2, bool3, bool4);
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1218 */           if (shouldPaintExpandControl(treePath2, i, bool2, bool3, bool4))
/*      */           {
/* 1220 */             paintExpandControl(paramGraphics, rectangle, insets, rectangle2, treePath2, i, bool2, bool3, bool4);
/*      */           }
/*      */ 
/*      */           
/* 1224 */           paintRow(paramGraphics, rectangle, insets, rectangle2, treePath2, i, bool2, bool3, bool4);
/*      */           
/* 1226 */           if (rectangle2.y + rectangle2.height >= j) {
/* 1227 */             bool = true;
/*      */           }
/*      */         } else {
/* 1230 */           bool = true;
/*      */         } 
/* 1232 */         i++;
/*      */       } 
/*      */     } 
/*      */     
/* 1236 */     paintDropLine(paramGraphics);
/*      */ 
/*      */     
/* 1239 */     this.rendererPane.removeAll();
/*      */     
/* 1241 */     this.drawingCache.clear();
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
/*      */   protected boolean isDropLine(JTree.DropLocation paramDropLocation) {
/* 1254 */     return (paramDropLocation != null && paramDropLocation.getPath() != null && paramDropLocation.getChildIndex() != -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void paintDropLine(Graphics paramGraphics) {
/* 1264 */     JTree.DropLocation dropLocation = this.tree.getDropLocation();
/* 1265 */     if (!isDropLine(dropLocation)) {
/*      */       return;
/*      */     }
/*      */     
/* 1269 */     Color color = UIManager.getColor("Tree.dropLineColor");
/* 1270 */     if (color != null) {
/* 1271 */       paramGraphics.setColor(color);
/* 1272 */       Rectangle rectangle = getDropLineRect(dropLocation);
/* 1273 */       paramGraphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
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
/*      */   protected Rectangle getDropLineRect(JTree.DropLocation paramDropLocation) {
/*      */     Rectangle rectangle;
/* 1286 */     TreePath treePath = paramDropLocation.getPath();
/* 1287 */     int i = paramDropLocation.getChildIndex();
/* 1288 */     boolean bool = this.leftToRight;
/*      */     
/* 1290 */     Insets insets = this.tree.getInsets();
/*      */     
/* 1292 */     if (this.tree.getRowCount() == 0) {
/*      */ 
/*      */       
/* 1295 */       rectangle = new Rectangle(insets.left, insets.top, this.tree.getWidth() - insets.left - insets.right, 0);
/*      */     } else {
/*      */       
/* 1298 */       TreeModel treeModel = getModel();
/* 1299 */       Object object = treeModel.getRoot();
/*      */       
/* 1301 */       if (treePath.getLastPathComponent() == object && i >= treeModel
/* 1302 */         .getChildCount(object)) {
/*      */         Rectangle rectangle1;
/* 1304 */         rectangle = this.tree.getRowBounds(this.tree.getRowCount() - 1);
/* 1305 */         rectangle.y += rectangle.height;
/*      */ 
/*      */         
/* 1308 */         if (!this.tree.isRootVisible()) {
/* 1309 */           rectangle1 = this.tree.getRowBounds(0);
/* 1310 */         } else if (treeModel.getChildCount(object) == 0) {
/* 1311 */           rectangle1 = this.tree.getRowBounds(0);
/* 1312 */           rectangle1.x += this.totalChildIndent;
/* 1313 */           rectangle1.width -= this.totalChildIndent + this.totalChildIndent;
/*      */         } else {
/* 1315 */           TreePath treePath1 = treePath.pathByAddingChild(treeModel
/* 1316 */               .getChild(object, treeModel.getChildCount(object) - 1));
/* 1317 */           rectangle1 = this.tree.getPathBounds(treePath1);
/*      */         } 
/*      */         
/* 1320 */         rectangle.x = rectangle1.x;
/* 1321 */         rectangle.width = rectangle1.width;
/*      */       } else {
/* 1323 */         rectangle = this.tree.getPathBounds(treePath.pathByAddingChild(treeModel
/* 1324 */               .getChild(treePath.getLastPathComponent(), i)));
/*      */       } 
/*      */     } 
/*      */     
/* 1328 */     if (rectangle.y != 0) {
/* 1329 */       rectangle.y--;
/*      */     }
/*      */     
/* 1332 */     if (!bool) {
/* 1333 */       rectangle.x = rectangle.x + rectangle.width - 100;
/*      */     }
/*      */     
/* 1336 */     rectangle.width = 100;
/* 1337 */     rectangle.height = 2;
/*      */     
/* 1339 */     return rectangle;
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
/*      */   protected void paintHorizontalPartOfLeg(Graphics paramGraphics, Rectangle paramRectangle1, Insets paramInsets, Rectangle paramRectangle2, TreePath paramTreePath, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
/* 1353 */     if (!this.paintLines) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1358 */     int i = paramTreePath.getPathCount() - 1;
/* 1359 */     if ((i == 0 || (i == 1 && !isRootVisible())) && 
/* 1360 */       !getShowsRootHandles()) {
/*      */       return;
/*      */     }
/*      */     
/* 1364 */     int j = paramRectangle1.x;
/* 1365 */     int k = paramRectangle1.x + paramRectangle1.width;
/* 1366 */     int m = paramRectangle1.y;
/* 1367 */     int n = paramRectangle1.y + paramRectangle1.height;
/* 1368 */     int i1 = paramRectangle2.y + paramRectangle2.height / 2;
/*      */     
/* 1370 */     if (this.leftToRight) {
/* 1371 */       int i2 = paramRectangle2.x - getRightChildIndent();
/* 1372 */       int i3 = paramRectangle2.x - getHorizontalLegBuffer();
/*      */       
/* 1374 */       if (i1 >= m && i1 < n && i3 >= j && i2 < k && i2 < i3) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1380 */         paramGraphics.setColor(getHashColor());
/* 1381 */         paintHorizontalLine(paramGraphics, this.tree, i1, i2, i3 - 1);
/*      */       } 
/*      */     } else {
/* 1384 */       int i2 = paramRectangle2.x + paramRectangle2.width + getHorizontalLegBuffer();
/* 1385 */       int i3 = paramRectangle2.x + paramRectangle2.width + getRightChildIndent();
/*      */       
/* 1387 */       if (i1 >= m && i1 < n && i3 >= j && i2 < k && i2 < i3) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1393 */         paramGraphics.setColor(getHashColor());
/* 1394 */         paintHorizontalLine(paramGraphics, this.tree, i1, i2, i3 - 1);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void paintVerticalPartOfLeg(Graphics paramGraphics, Rectangle paramRectangle, Insets paramInsets, TreePath paramTreePath) {
/* 1405 */     if (!this.paintLines) {
/*      */       return;
/*      */     }
/*      */     
/* 1409 */     int i = paramTreePath.getPathCount() - 1;
/* 1410 */     if (i == 0 && !getShowsRootHandles() && !isRootVisible()) {
/*      */       return;
/*      */     }
/* 1413 */     int j = getRowX(-1, i + 1);
/* 1414 */     if (this.leftToRight) {
/* 1415 */       j = j - getRightChildIndent() + paramInsets.left;
/*      */     }
/*      */     else {
/*      */       
/* 1419 */       j = this.tree.getWidth() - j - paramInsets.right + getRightChildIndent() - 1;
/*      */     } 
/* 1421 */     int k = paramRectangle.x;
/* 1422 */     int m = paramRectangle.x + paramRectangle.width - 1;
/*      */     
/* 1424 */     if (j >= k && j <= m) {
/* 1425 */       int i2, n = paramRectangle.y;
/* 1426 */       int i1 = paramRectangle.y + paramRectangle.height;
/* 1427 */       Rectangle rectangle1 = getPathBounds(this.tree, paramTreePath);
/* 1428 */       Rectangle rectangle2 = getPathBounds(this.tree, 
/* 1429 */           getLastChildPath(paramTreePath));
/*      */       
/* 1431 */       if (rectangle2 == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1440 */       if (rectangle1 == null) {
/* 1441 */         i2 = Math.max(paramInsets.top + getVerticalLegBuffer(), n);
/*      */       }
/*      */       else {
/*      */         
/* 1445 */         i2 = Math.max(rectangle1.y + rectangle1.height + 
/* 1446 */             getVerticalLegBuffer(), n);
/* 1447 */       }  if (i == 0 && !isRootVisible()) {
/* 1448 */         TreeModel treeModel = getModel();
/*      */         
/* 1450 */         if (treeModel != null) {
/* 1451 */           Object object = treeModel.getRoot();
/*      */           
/* 1453 */           if (treeModel.getChildCount(object) > 0) {
/* 1454 */             rectangle1 = getPathBounds(this.tree, paramTreePath
/* 1455 */                 .pathByAddingChild(treeModel.getChild(object, 0)));
/* 1456 */             if (rectangle1 != null) {
/* 1457 */               i2 = Math.max(paramInsets.top + getVerticalLegBuffer(), rectangle1.y + rectangle1.height / 2);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1464 */       int i3 = Math.min(rectangle2.y + rectangle2.height / 2, i1);
/*      */ 
/*      */       
/* 1467 */       if (i2 <= i3) {
/* 1468 */         paramGraphics.setColor(getHashColor());
/* 1469 */         paintVerticalLine(paramGraphics, this.tree, j, i2, i3);
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
/*      */   protected void paintExpandControl(Graphics paramGraphics, Rectangle paramRectangle1, Insets paramInsets, Rectangle paramRectangle2, TreePath paramTreePath, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
/* 1484 */     Object object = paramTreePath.getLastPathComponent();
/*      */ 
/*      */ 
/*      */     
/* 1488 */     if (!paramBoolean3 && (!paramBoolean2 || this.treeModel
/* 1489 */       .getChildCount(object) > 0)) {
/*      */       int i;
/* 1491 */       if (this.leftToRight) {
/* 1492 */         i = paramRectangle2.x - getRightChildIndent() + 1;
/*      */       } else {
/* 1494 */         i = paramRectangle2.x + paramRectangle2.width + getRightChildIndent() - 1;
/*      */       } 
/* 1496 */       int j = paramRectangle2.y + paramRectangle2.height / 2;
/*      */       
/* 1498 */       if (paramBoolean1) {
/* 1499 */         Icon icon = getExpandedIcon();
/* 1500 */         if (icon != null) {
/* 1501 */           drawCentered(this.tree, paramGraphics, icon, i, j);
/*      */         }
/*      */       } else {
/*      */         
/* 1505 */         Icon icon = getCollapsedIcon();
/* 1506 */         if (icon != null) {
/* 1507 */           drawCentered(this.tree, paramGraphics, icon, i, j);
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
/*      */   protected void paintRow(Graphics paramGraphics, Rectangle paramRectangle1, Insets paramInsets, Rectangle paramRectangle2, TreePath paramTreePath, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
/*      */     byte b;
/* 1522 */     if (this.editingComponent != null && this.editingRow == paramInt) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1527 */     if (this.tree.hasFocus()) {
/* 1528 */       b = getLeadSelectionRow();
/*      */     } else {
/*      */       
/* 1531 */       b = -1;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1536 */     Component component = this.currentCellRenderer.getTreeCellRendererComponent(this.tree, paramTreePath.getLastPathComponent(), this.tree
/* 1537 */         .isRowSelected(paramInt), paramBoolean1, paramBoolean3, paramInt, (b == paramInt));
/*      */ 
/*      */     
/* 1540 */     this.rendererPane.paintComponent(paramGraphics, component, this.tree, paramRectangle2.x, paramRectangle2.y, paramRectangle2.width, paramRectangle2.height, true);
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
/*      */   protected boolean shouldPaintExpandControl(TreePath paramTreePath, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
/* 1552 */     if (paramBoolean3) {
/* 1553 */       return false;
/*      */     }
/* 1555 */     int i = paramTreePath.getPathCount() - 1;
/*      */     
/* 1557 */     if ((i == 0 || (i == 1 && !isRootVisible())) && 
/* 1558 */       !getShowsRootHandles())
/* 1559 */       return false; 
/* 1560 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void paintVerticalLine(Graphics paramGraphics, JComponent paramJComponent, int paramInt1, int paramInt2, int paramInt3) {
/* 1568 */     if (this.lineTypeDashed) {
/* 1569 */       drawDashedVerticalLine(paramGraphics, paramInt1, paramInt2, paramInt3);
/*      */     } else {
/* 1571 */       paramGraphics.drawLine(paramInt1, paramInt2, paramInt1, paramInt3);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void paintHorizontalLine(Graphics paramGraphics, JComponent paramJComponent, int paramInt1, int paramInt2, int paramInt3) {
/* 1580 */     if (this.lineTypeDashed) {
/* 1581 */       drawDashedHorizontalLine(paramGraphics, paramInt1, paramInt2, paramInt3);
/*      */     } else {
/* 1583 */       paramGraphics.drawLine(paramInt2, paramInt1, paramInt3, paramInt1);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getVerticalLegBuffer() {
/* 1592 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getHorizontalLegBuffer() {
/* 1601 */     return 0;
/*      */   }
/*      */   
/*      */   private int findCenteredX(int paramInt1, int paramInt2) {
/* 1605 */     return this.leftToRight ? (paramInt1 - 
/* 1606 */       (int)Math.ceil(paramInt2 / 2.0D)) : (paramInt1 - 
/* 1607 */       (int)Math.floor(paramInt2 / 2.0D));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawCentered(Component paramComponent, Graphics paramGraphics, Icon paramIcon, int paramInt1, int paramInt2) {
/* 1617 */     paramIcon.paintIcon(paramComponent, paramGraphics, 
/* 1618 */         findCenteredX(paramInt1, paramIcon.getIconWidth()), paramInt2 - paramIcon
/* 1619 */         .getIconHeight() / 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawDashedHorizontalLine(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3) {
/* 1628 */     paramInt2 += paramInt2 % 2;
/*      */     
/* 1630 */     for (int i = paramInt2; i <= paramInt3; i += 2) {
/* 1631 */       paramGraphics.drawLine(i, paramInt1, i, paramInt1);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawDashedVerticalLine(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3) {
/* 1641 */     paramInt2 += paramInt2 % 2;
/*      */     
/* 1643 */     for (int i = paramInt2; i <= paramInt3; i += 2) {
/* 1644 */       paramGraphics.drawLine(paramInt1, i, paramInt1, i);
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
/*      */   protected int getRowX(int paramInt1, int paramInt2) {
/* 1665 */     return this.totalChildIndent * (paramInt2 + this.depthOffset);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateLayoutCacheExpandedNodes() {
/* 1673 */     if (this.treeModel != null && this.treeModel.getRoot() != null)
/* 1674 */       updateExpandedDescendants(new TreePath(this.treeModel.getRoot())); 
/*      */   }
/*      */   
/*      */   private void updateLayoutCacheExpandedNodesIfNecessary() {
/* 1678 */     if (this.treeModel != null && this.treeModel.getRoot() != null) {
/* 1679 */       TreePath treePath = new TreePath(this.treeModel.getRoot());
/* 1680 */       if (this.tree.isExpanded(treePath)) {
/* 1681 */         updateLayoutCacheExpandedNodes();
/*      */       } else {
/* 1683 */         this.treeState.setExpandedState(treePath, false);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateExpandedDescendants(TreePath paramTreePath) {
/* 1694 */     completeEditing();
/* 1695 */     if (this.treeState != null) {
/* 1696 */       this.treeState.setExpandedState(paramTreePath, true);
/*      */       
/* 1698 */       Enumeration<TreePath> enumeration = this.tree.getExpandedDescendants(paramTreePath);
/*      */       
/* 1700 */       if (enumeration != null) {
/* 1701 */         while (enumeration.hasMoreElements()) {
/* 1702 */           paramTreePath = enumeration.nextElement();
/* 1703 */           this.treeState.setExpandedState(paramTreePath, true);
/*      */         } 
/*      */       }
/* 1706 */       updateLeadSelectionRow();
/* 1707 */       updateSize();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected TreePath getLastChildPath(TreePath paramTreePath) {
/* 1715 */     if (this.treeModel != null) {
/*      */       
/* 1717 */       int i = this.treeModel.getChildCount(paramTreePath.getLastPathComponent());
/*      */       
/* 1719 */       if (i > 0)
/* 1720 */         return paramTreePath.pathByAddingChild(this.treeModel
/* 1721 */             .getChild(paramTreePath.getLastPathComponent(), i - 1)); 
/*      */     } 
/* 1723 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateDepthOffset() {
/* 1730 */     if (isRootVisible()) {
/* 1731 */       if (getShowsRootHandles()) {
/* 1732 */         this.depthOffset = 1;
/*      */       } else {
/* 1734 */         this.depthOffset = 0;
/*      */       } 
/* 1736 */     } else if (!getShowsRootHandles()) {
/* 1737 */       this.depthOffset = -1;
/*      */     } else {
/* 1739 */       this.depthOffset = 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateCellEditor() {
/*      */     TreeCellEditor treeCellEditor;
/* 1750 */     completeEditing();
/* 1751 */     if (this.tree == null) {
/* 1752 */       treeCellEditor = null;
/*      */     }
/* 1754 */     else if (this.tree.isEditable()) {
/* 1755 */       treeCellEditor = this.tree.getCellEditor();
/* 1756 */       if (treeCellEditor == null) {
/* 1757 */         treeCellEditor = createDefaultCellEditor();
/* 1758 */         if (treeCellEditor != null) {
/* 1759 */           this.tree.setCellEditor(treeCellEditor);
/* 1760 */           this.createdCellEditor = true;
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1765 */       treeCellEditor = null;
/*      */     } 
/* 1767 */     if (treeCellEditor != this.cellEditor) {
/* 1768 */       if (this.cellEditor != null && this.cellEditorListener != null)
/* 1769 */         this.cellEditor.removeCellEditorListener(this.cellEditorListener); 
/* 1770 */       this.cellEditor = treeCellEditor;
/* 1771 */       if (this.cellEditorListener == null)
/* 1772 */         this.cellEditorListener = createCellEditorListener(); 
/* 1773 */       if (treeCellEditor != null && this.cellEditorListener != null)
/* 1774 */         treeCellEditor.addCellEditorListener(this.cellEditorListener); 
/* 1775 */       this.createdCellEditor = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateRenderer() {
/* 1783 */     if (this.tree != null) {
/*      */ 
/*      */       
/* 1786 */       TreeCellRenderer treeCellRenderer = this.tree.getCellRenderer();
/* 1787 */       if (treeCellRenderer == null) {
/* 1788 */         this.tree.setCellRenderer(createDefaultCellRenderer());
/* 1789 */         this.createdRenderer = true;
/*      */       } else {
/*      */         
/* 1792 */         this.createdRenderer = false;
/* 1793 */         this.currentCellRenderer = treeCellRenderer;
/* 1794 */         if (this.createdCellEditor) {
/* 1795 */           this.tree.setCellEditor((TreeCellEditor)null);
/*      */         }
/*      */       } 
/*      */     } else {
/*      */       
/* 1800 */       this.createdRenderer = false;
/* 1801 */       this.currentCellRenderer = null;
/*      */     } 
/* 1803 */     updateCellEditor();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void configureLayoutCache() {
/* 1811 */     if (this.treeState != null && this.tree != null) {
/* 1812 */       if (this.nodeDimensions == null)
/* 1813 */         this.nodeDimensions = createNodeDimensions(); 
/* 1814 */       this.treeState.setNodeDimensions(this.nodeDimensions);
/* 1815 */       this.treeState.setRootVisible(this.tree.isRootVisible());
/* 1816 */       this.treeState.setRowHeight(this.tree.getRowHeight());
/* 1817 */       this.treeState.setSelectionModel(getSelectionModel());
/*      */ 
/*      */       
/* 1820 */       if (this.treeState.getModel() != this.tree.getModel())
/* 1821 */         this.treeState.setModel(this.tree.getModel()); 
/* 1822 */       updateLayoutCacheExpandedNodesIfNecessary();
/*      */ 
/*      */       
/* 1825 */       if (isLargeModel()) {
/* 1826 */         if (this.componentListener == null) {
/* 1827 */           this.componentListener = createComponentListener();
/* 1828 */           if (this.componentListener != null) {
/* 1829 */             this.tree.addComponentListener(this.componentListener);
/*      */           }
/*      */         } 
/* 1832 */       } else if (this.componentListener != null) {
/* 1833 */         this.tree.removeComponentListener(this.componentListener);
/* 1834 */         this.componentListener = null;
/*      */       }
/*      */     
/* 1837 */     } else if (this.componentListener != null) {
/* 1838 */       this.tree.removeComponentListener(this.componentListener);
/* 1839 */       this.componentListener = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateSize() {
/* 1848 */     this.validCachedPreferredSize = false;
/* 1849 */     this.tree.treeDidChange();
/*      */   }
/*      */   
/*      */   private void updateSize0() {
/* 1853 */     this.validCachedPreferredSize = false;
/* 1854 */     this.tree.revalidate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateCachedPreferredSize() {
/* 1865 */     if (this.treeState != null) {
/* 1866 */       Insets insets = this.tree.getInsets();
/*      */       
/* 1868 */       if (isLargeModel()) {
/* 1869 */         Rectangle rectangle = this.tree.getVisibleRect();
/*      */         
/* 1871 */         if (rectangle.x == 0 && rectangle.y == 0 && rectangle.width == 0 && rectangle.height == 0 && this.tree
/*      */           
/* 1873 */           .getVisibleRowCount() > 0) {
/*      */ 
/*      */           
/* 1876 */           rectangle.width = 1;
/* 1877 */           rectangle
/* 1878 */             .height = this.tree.getRowHeight() * this.tree.getVisibleRowCount();
/*      */         } else {
/* 1880 */           rectangle.x -= insets.left;
/* 1881 */           rectangle.y -= insets.top;
/*      */         } 
/*      */         
/* 1884 */         Container container = SwingUtilities.getUnwrappedParent(this.tree);
/* 1885 */         if (container instanceof javax.swing.JViewport) {
/* 1886 */           container = container.getParent();
/* 1887 */           if (container instanceof JScrollPane) {
/* 1888 */             JScrollPane jScrollPane = (JScrollPane)container;
/* 1889 */             JScrollBar jScrollBar = jScrollPane.getHorizontalScrollBar();
/* 1890 */             if (jScrollBar != null && jScrollBar.isVisible()) {
/* 1891 */               int i = jScrollBar.getHeight();
/* 1892 */               rectangle.y -= i;
/* 1893 */               rectangle.height += i;
/*      */             } 
/*      */           } 
/*      */         } 
/* 1897 */         this.preferredSize.width = this.treeState.getPreferredWidth(rectangle);
/*      */       } else {
/*      */         
/* 1900 */         this.preferredSize.width = this.treeState.getPreferredWidth(null);
/*      */       } 
/* 1902 */       this.preferredSize.height = this.treeState.getPreferredHeight();
/* 1903 */       this.preferredSize.width += insets.left + insets.right;
/* 1904 */       this.preferredSize.height += insets.top + insets.bottom;
/*      */     } 
/* 1906 */     this.validCachedPreferredSize = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void pathWasExpanded(TreePath paramTreePath) {
/* 1913 */     if (this.tree != null) {
/* 1914 */       this.tree.fireTreeExpanded(paramTreePath);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void pathWasCollapsed(TreePath paramTreePath) {
/* 1922 */     if (this.tree != null) {
/* 1923 */       this.tree.fireTreeCollapsed(paramTreePath);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void ensureRowsAreVisible(int paramInt1, int paramInt2) {
/* 1932 */     if (this.tree != null && paramInt1 >= 0 && paramInt2 < getRowCount(this.tree)) {
/* 1933 */       boolean bool = DefaultLookup.getBoolean(this.tree, this, "Tree.scrollsHorizontallyAndVertically", false);
/*      */       
/* 1935 */       if (paramInt1 == paramInt2) {
/* 1936 */         Rectangle rectangle = getPathBounds(this.tree, 
/* 1937 */             getPathForRow(this.tree, paramInt1));
/*      */         
/* 1939 */         if (rectangle != null) {
/* 1940 */           if (!bool) {
/* 1941 */             rectangle.x = (this.tree.getVisibleRect()).x;
/* 1942 */             rectangle.width = 1;
/*      */           } 
/* 1944 */           this.tree.scrollRectToVisible(rectangle);
/*      */         } 
/*      */       } else {
/*      */         
/* 1948 */         Rectangle rectangle = getPathBounds(this.tree, 
/* 1949 */             getPathForRow(this.tree, paramInt1));
/* 1950 */         if (rectangle != null) {
/* 1951 */           Rectangle rectangle1 = this.tree.getVisibleRect();
/* 1952 */           Rectangle rectangle2 = rectangle;
/* 1953 */           int i = rectangle.y;
/* 1954 */           int j = i + rectangle1.height;
/*      */           
/* 1956 */           for (int k = paramInt1 + 1; k <= paramInt2; k++) {
/* 1957 */             rectangle2 = getPathBounds(this.tree, 
/* 1958 */                 getPathForRow(this.tree, k));
/* 1959 */             if (rectangle2 == null) {
/*      */               return;
/*      */             }
/* 1962 */             if (rectangle2.y + rectangle2.height > j)
/* 1963 */               k = paramInt2; 
/*      */           } 
/* 1965 */           this.tree.scrollRectToVisible(new Rectangle(rectangle1.x, i, 1, rectangle2.y + rectangle2.height - i));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPreferredMinSize(Dimension paramDimension) {
/* 1976 */     this.preferredMinSize = paramDimension;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getPreferredMinSize() {
/* 1982 */     if (this.preferredMinSize == null)
/* 1983 */       return null; 
/* 1984 */     return new Dimension(this.preferredMinSize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 1991 */     return getPreferredSize(paramJComponent, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getPreferredSize(JComponent paramJComponent, boolean paramBoolean) {
/* 2000 */     Dimension dimension = getPreferredMinSize();
/*      */     
/* 2002 */     if (!this.validCachedPreferredSize)
/* 2003 */       updateCachedPreferredSize(); 
/* 2004 */     if (this.tree != null) {
/* 2005 */       if (dimension != null)
/* 2006 */         return new Dimension(Math.max(dimension.width, this.preferredSize.width), 
/*      */             
/* 2008 */             Math.max(dimension.height, this.preferredSize.height)); 
/* 2009 */       return new Dimension(this.preferredSize.width, this.preferredSize.height);
/*      */     } 
/* 2011 */     if (dimension != null) {
/* 2012 */       return dimension;
/*      */     }
/* 2014 */     return new Dimension(0, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getMinimumSize(JComponent paramJComponent) {
/* 2022 */     if (getPreferredMinSize() != null)
/* 2023 */       return getPreferredMinSize(); 
/* 2024 */     return new Dimension(0, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getMaximumSize(JComponent paramJComponent) {
/* 2032 */     if (this.tree != null)
/* 2033 */       return getPreferredSize(this.tree); 
/* 2034 */     if (getPreferredMinSize() != null)
/* 2035 */       return getPreferredMinSize(); 
/* 2036 */     return new Dimension(0, 0);
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
/*      */   protected void completeEditing() {
/* 2050 */     if (this.tree.getInvokesStopCellEditing() && this.stopEditingInCompleteEditing && this.editingComponent != null)
/*      */     {
/* 2052 */       this.cellEditor.stopCellEditing();
/*      */     }
/*      */ 
/*      */     
/* 2056 */     completeEditing(false, true, false);
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
/*      */   protected void completeEditing(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
/* 2068 */     if (this.stopEditingInCompleteEditing && this.editingComponent != null) {
/* 2069 */       Component component = this.editingComponent;
/* 2070 */       TreePath treePath = this.editingPath;
/* 2071 */       TreeCellEditor treeCellEditor = this.cellEditor;
/* 2072 */       Object object = treeCellEditor.getCellEditorValue();
/* 2073 */       Rectangle rectangle = getPathBounds(this.tree, this.editingPath);
/*      */ 
/*      */ 
/*      */       
/* 2077 */       boolean bool = (this.tree != null && (this.tree.hasFocus() || SwingUtilities.findFocusOwner(this.editingComponent) != null)) ? true : false;
/*      */       
/* 2079 */       this.editingComponent = null;
/* 2080 */       this.editingPath = null;
/* 2081 */       if (paramBoolean1) {
/* 2082 */         treeCellEditor.stopCellEditing();
/* 2083 */       } else if (paramBoolean2) {
/* 2084 */         treeCellEditor.cancelCellEditing();
/* 2085 */       }  this.tree.remove(component);
/* 2086 */       if (this.editorHasDifferentSize) {
/* 2087 */         this.treeState.invalidatePathBounds(treePath);
/* 2088 */         updateSize();
/*      */       }
/* 2090 */       else if (rectangle != null) {
/* 2091 */         rectangle.x = 0;
/* 2092 */         rectangle.width = (this.tree.getSize()).width;
/* 2093 */         this.tree.repaint(rectangle);
/*      */       } 
/* 2095 */       if (bool)
/* 2096 */         this.tree.requestFocus(); 
/* 2097 */       if (paramBoolean3) {
/* 2098 */         this.treeModel.valueForPathChanged(treePath, object);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean startEditingOnRelease(TreePath paramTreePath, MouseEvent paramMouseEvent1, MouseEvent paramMouseEvent2) {
/* 2107 */     this.releaseEvent = paramMouseEvent2;
/*      */     try {
/* 2109 */       return startEditing(paramTreePath, paramMouseEvent1);
/*      */     } finally {
/* 2111 */       this.releaseEvent = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean startEditing(TreePath paramTreePath, MouseEvent paramMouseEvent) {
/* 2121 */     if (isEditing(this.tree) && this.tree.getInvokesStopCellEditing() && 
/* 2122 */       !stopEditing(this.tree)) {
/* 2123 */       return false;
/*      */     }
/* 2125 */     completeEditing();
/* 2126 */     if (this.cellEditor != null && this.tree.isPathEditable(paramTreePath)) {
/* 2127 */       int i = getRowForPath(this.tree, paramTreePath);
/*      */       
/* 2129 */       if (this.cellEditor.isCellEditable(paramMouseEvent)) {
/* 2130 */         this
/* 2131 */           .editingComponent = this.cellEditor.getTreeCellEditorComponent(this.tree, paramTreePath.getLastPathComponent(), this.tree
/* 2132 */             .isPathSelected(paramTreePath), this.tree.isExpanded(paramTreePath), this.treeModel
/* 2133 */             .isLeaf(paramTreePath.getLastPathComponent()), i);
/* 2134 */         Rectangle rectangle = getPathBounds(this.tree, paramTreePath);
/* 2135 */         if (rectangle == null) {
/* 2136 */           return false;
/*      */         }
/*      */         
/* 2139 */         this.editingRow = i;
/*      */         
/* 2141 */         Dimension dimension = this.editingComponent.getPreferredSize();
/*      */ 
/*      */         
/* 2144 */         if (dimension.height != rectangle.height && 
/* 2145 */           getRowHeight() > 0) {
/* 2146 */           dimension.height = getRowHeight();
/*      */         }
/* 2148 */         if (dimension.width != rectangle.width || dimension.height != rectangle.height) {
/*      */ 
/*      */ 
/*      */           
/* 2152 */           this.editorHasDifferentSize = true;
/* 2153 */           this.treeState.invalidatePathBounds(paramTreePath);
/* 2154 */           updateSize();
/*      */ 
/*      */           
/* 2157 */           rectangle = getPathBounds(this.tree, paramTreePath);
/* 2158 */           if (rectangle == null) {
/* 2159 */             return false;
/*      */           }
/*      */         } else {
/*      */           
/* 2163 */           this.editorHasDifferentSize = false;
/* 2164 */         }  this.tree.add(this.editingComponent);
/* 2165 */         this.editingComponent.setBounds(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */ 
/*      */         
/* 2168 */         this.editingPath = paramTreePath;
/* 2169 */         AWTAccessor.getComponentAccessor().revalidateSynchronously(this.editingComponent);
/* 2170 */         this.editingComponent.repaint();
/* 2171 */         if (this.cellEditor.shouldSelectCell(paramMouseEvent)) {
/* 2172 */           this.stopEditingInCompleteEditing = false;
/* 2173 */           this.tree.setSelectionRow(i);
/* 2174 */           this.stopEditingInCompleteEditing = true;
/*      */         } 
/*      */ 
/*      */         
/* 2178 */         Component component = SwingUtilities2.compositeRequestFocus(this.editingComponent);
/* 2179 */         boolean bool = true;
/*      */         
/* 2181 */         if (paramMouseEvent != null) {
/*      */ 
/*      */ 
/*      */           
/* 2185 */           Point point = SwingUtilities.convertPoint(this.tree, new Point(paramMouseEvent.getX(), paramMouseEvent.getY()), this.editingComponent);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2194 */           Component component1 = SwingUtilities.getDeepestComponentAt(this.editingComponent, point.x, point.y);
/*      */           
/* 2196 */           if (component1 != null) {
/* 2197 */             MouseInputHandler mouseInputHandler = new MouseInputHandler(this.tree, component1, paramMouseEvent, component);
/*      */ 
/*      */ 
/*      */             
/* 2201 */             if (this.releaseEvent != null) {
/* 2202 */               mouseInputHandler.mouseReleased(this.releaseEvent);
/*      */             }
/*      */             
/* 2205 */             bool = false;
/*      */           } 
/*      */         } 
/* 2208 */         if (bool && component instanceof JTextField) {
/* 2209 */           ((JTextField)component).selectAll();
/*      */         }
/* 2211 */         return true;
/*      */       } 
/*      */       
/* 2214 */       this.editingComponent = null;
/*      */     } 
/* 2216 */     return false;
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
/*      */   protected void checkForClickInExpandControl(TreePath paramTreePath, int paramInt1, int paramInt2) {
/* 2230 */     if (isLocationInExpandControl(paramTreePath, paramInt1, paramInt2)) {
/* 2231 */       handleExpandControlClick(paramTreePath, paramInt1, paramInt2);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isLocationInExpandControl(TreePath paramTreePath, int paramInt1, int paramInt2) {
/* 2242 */     if (paramTreePath != null && !this.treeModel.isLeaf(paramTreePath.getLastPathComponent())) {
/*      */       byte b;
/* 2244 */       Insets insets = this.tree.getInsets();
/*      */       
/* 2246 */       if (getExpandedIcon() != null) {
/* 2247 */         b = getExpandedIcon().getIconWidth();
/*      */       } else {
/* 2249 */         b = 8;
/*      */       } 
/* 2251 */       int i = getRowX(this.tree.getRowForPath(paramTreePath), paramTreePath
/* 2252 */           .getPathCount() - 1);
/*      */       
/* 2254 */       if (this.leftToRight) {
/* 2255 */         i = i + insets.left - getRightChildIndent() + 1;
/*      */       } else {
/* 2257 */         i = this.tree.getWidth() - i - insets.right + getRightChildIndent() - 1;
/*      */       } 
/*      */       
/* 2260 */       i = findCenteredX(i, b);
/*      */       
/* 2262 */       return (paramInt1 >= i && paramInt1 < i + b);
/*      */     } 
/* 2264 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleExpandControlClick(TreePath paramTreePath, int paramInt1, int paramInt2) {
/* 2273 */     toggleExpandState(paramTreePath);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void toggleExpandState(TreePath paramTreePath) {
/* 2283 */     if (!this.tree.isExpanded(paramTreePath)) {
/* 2284 */       int i = getRowForPath(this.tree, paramTreePath);
/*      */       
/* 2286 */       this.tree.expandPath(paramTreePath);
/* 2287 */       updateSize();
/* 2288 */       if (i != -1) {
/* 2289 */         if (this.tree.getScrollsOnExpand()) {
/* 2290 */           ensureRowsAreVisible(i, i + this.treeState
/* 2291 */               .getVisibleChildCount(paramTreePath));
/*      */         } else {
/* 2293 */           ensureRowsAreVisible(i, i);
/*      */         } 
/*      */       }
/*      */     } else {
/* 2297 */       this.tree.collapsePath(paramTreePath);
/* 2298 */       updateSize();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isToggleSelectionEvent(MouseEvent paramMouseEvent) {
/* 2307 */     return (SwingUtilities.isLeftMouseButton(paramMouseEvent) && 
/* 2308 */       BasicGraphicsUtils.isMenuShortcutKeyDown(paramMouseEvent));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isMultiSelectEvent(MouseEvent paramMouseEvent) {
/* 2316 */     return (SwingUtilities.isLeftMouseButton(paramMouseEvent) && paramMouseEvent
/* 2317 */       .isShiftDown());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isToggleEvent(MouseEvent paramMouseEvent) {
/* 2326 */     if (!SwingUtilities.isLeftMouseButton(paramMouseEvent)) {
/* 2327 */       return false;
/*      */     }
/* 2329 */     int i = this.tree.getToggleClickCount();
/*      */     
/* 2331 */     if (i <= 0) {
/* 2332 */       return false;
/*      */     }
/* 2334 */     return (paramMouseEvent.getClickCount() % i == 0);
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
/*      */   protected void selectPathForEvent(TreePath paramTreePath, MouseEvent paramMouseEvent) {
/* 2347 */     if (isMultiSelectEvent(paramMouseEvent)) {
/* 2348 */       TreePath treePath = getAnchorSelectionPath();
/*      */       
/* 2350 */       byte b = (treePath == null) ? -1 : getRowForPath(this.tree, treePath);
/*      */       
/* 2352 */       if (b == -1 || this.tree.getSelectionModel()
/* 2353 */         .getSelectionMode() == 1) {
/*      */         
/* 2355 */         this.tree.setSelectionPath(paramTreePath);
/*      */       } else {
/*      */         
/* 2358 */         int i = getRowForPath(this.tree, paramTreePath);
/* 2359 */         TreePath treePath1 = treePath;
/*      */         
/* 2361 */         if (isToggleSelectionEvent(paramMouseEvent)) {
/* 2362 */           if (this.tree.isRowSelected(b)) {
/* 2363 */             this.tree.addSelectionInterval(b, i);
/*      */           } else {
/* 2365 */             this.tree.removeSelectionInterval(b, i);
/* 2366 */             this.tree.addSelectionInterval(i, i);
/*      */           } 
/* 2368 */         } else if (i < b) {
/* 2369 */           this.tree.setSelectionInterval(i, b);
/*      */         } else {
/* 2371 */           this.tree.setSelectionInterval(b, i);
/*      */         } 
/* 2373 */         this.lastSelectedRow = i;
/* 2374 */         setAnchorSelectionPath(treePath1);
/* 2375 */         setLeadSelectionPath(paramTreePath);
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/* 2381 */     else if (isToggleSelectionEvent(paramMouseEvent)) {
/* 2382 */       if (this.tree.isPathSelected(paramTreePath)) {
/* 2383 */         this.tree.removeSelectionPath(paramTreePath);
/*      */       } else {
/* 2385 */         this.tree.addSelectionPath(paramTreePath);
/* 2386 */       }  this.lastSelectedRow = getRowForPath(this.tree, paramTreePath);
/* 2387 */       setAnchorSelectionPath(paramTreePath);
/* 2388 */       setLeadSelectionPath(paramTreePath);
/*      */ 
/*      */     
/*      */     }
/* 2392 */     else if (SwingUtilities.isLeftMouseButton(paramMouseEvent)) {
/* 2393 */       this.tree.setSelectionPath(paramTreePath);
/* 2394 */       if (isToggleEvent(paramMouseEvent)) {
/* 2395 */         toggleExpandState(paramTreePath);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isLeaf(int paramInt) {
/* 2404 */     TreePath treePath = getPathForRow(this.tree, paramInt);
/*      */     
/* 2406 */     if (treePath != null) {
/* 2407 */       return this.treeModel.isLeaf(treePath.getLastPathComponent());
/*      */     }
/* 2409 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setAnchorSelectionPath(TreePath paramTreePath) {
/* 2417 */     this.ignoreLAChange = true;
/*      */     try {
/* 2419 */       this.tree.setAnchorSelectionPath(paramTreePath);
/*      */     } finally {
/* 2421 */       this.ignoreLAChange = false;
/*      */     } 
/*      */   }
/*      */   
/*      */   private TreePath getAnchorSelectionPath() {
/* 2426 */     return this.tree.getAnchorSelectionPath();
/*      */   }
/*      */   
/*      */   private void setLeadSelectionPath(TreePath paramTreePath) {
/* 2430 */     setLeadSelectionPath(paramTreePath, false);
/*      */   }
/*      */ 
/*      */   
/*      */   private void setLeadSelectionPath(TreePath paramTreePath, boolean paramBoolean) {
/* 2435 */     Rectangle rectangle = paramBoolean ? getPathBounds(this.tree, getLeadSelectionPath()) : null;
/*      */     
/* 2437 */     this.ignoreLAChange = true;
/*      */     try {
/* 2439 */       this.tree.setLeadSelectionPath(paramTreePath);
/*      */     } finally {
/* 2441 */       this.ignoreLAChange = false;
/*      */     } 
/* 2443 */     this.leadRow = getRowForPath(this.tree, paramTreePath);
/*      */     
/* 2445 */     if (paramBoolean) {
/* 2446 */       if (rectangle != null) {
/* 2447 */         this.tree.repaint(getRepaintPathBounds(rectangle));
/*      */       }
/* 2449 */       rectangle = getPathBounds(this.tree, paramTreePath);
/* 2450 */       if (rectangle != null) {
/* 2451 */         this.tree.repaint(getRepaintPathBounds(rectangle));
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private Rectangle getRepaintPathBounds(Rectangle paramRectangle) {
/* 2457 */     if (UIManager.getBoolean("Tree.repaintWholeRow")) {
/* 2458 */       paramRectangle.x = 0;
/* 2459 */       paramRectangle.width = this.tree.getWidth();
/*      */     } 
/* 2461 */     return paramRectangle;
/*      */   }
/*      */   
/*      */   private TreePath getLeadSelectionPath() {
/* 2465 */     return this.tree.getLeadSelectionPath();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateLeadSelectionRow() {
/* 2473 */     this.leadRow = getRowForPath(this.tree, getLeadSelectionPath());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getLeadSelectionRow() {
/* 2483 */     return this.leadRow;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void extendSelection(TreePath paramTreePath) {
/* 2491 */     TreePath treePath = getAnchorSelectionPath();
/*      */     
/* 2493 */     byte b = (treePath == null) ? -1 : getRowForPath(this.tree, treePath);
/* 2494 */     int i = getRowForPath(this.tree, paramTreePath);
/*      */     
/* 2496 */     if (b == -1) {
/* 2497 */       this.tree.setSelectionRow(i);
/*      */     } else {
/*      */       
/* 2500 */       if (b < i) {
/* 2501 */         this.tree.setSelectionInterval(b, i);
/*      */       } else {
/*      */         
/* 2504 */         this.tree.setSelectionInterval(i, b);
/*      */       } 
/* 2506 */       setAnchorSelectionPath(treePath);
/* 2507 */       setLeadSelectionPath(paramTreePath);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void repaintPath(TreePath paramTreePath) {
/* 2516 */     if (paramTreePath != null) {
/* 2517 */       Rectangle rectangle = getPathBounds(this.tree, paramTreePath);
/* 2518 */       if (rectangle != null) {
/* 2519 */         this.tree.repaint(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
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
/*      */   public class TreeExpansionHandler
/*      */     implements TreeExpansionListener
/*      */   {
/*      */     public void treeExpanded(TreeExpansionEvent param1TreeExpansionEvent) {
/* 2537 */       BasicTreeUI.this.getHandler().treeExpanded(param1TreeExpansionEvent);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void treeCollapsed(TreeExpansionEvent param1TreeExpansionEvent) {
/* 2544 */       BasicTreeUI.this.getHandler().treeCollapsed(param1TreeExpansionEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public class ComponentHandler
/*      */     extends ComponentAdapter
/*      */     implements ActionListener
/*      */   {
/*      */     protected Timer timer;
/*      */ 
/*      */     
/*      */     protected JScrollBar scrollBar;
/*      */ 
/*      */     
/*      */     public void componentMoved(ComponentEvent param1ComponentEvent) {
/* 2561 */       if (this.timer == null) {
/* 2562 */         JScrollPane jScrollPane = getScrollPane();
/*      */         
/* 2564 */         if (jScrollPane == null) {
/* 2565 */           BasicTreeUI.this.updateSize();
/*      */         } else {
/* 2567 */           this.scrollBar = jScrollPane.getVerticalScrollBar();
/* 2568 */           if (this.scrollBar == null || 
/* 2569 */             !this.scrollBar.getValueIsAdjusting()) {
/*      */             
/* 2571 */             if ((this.scrollBar = jScrollPane.getHorizontalScrollBar()) != null && this.scrollBar
/* 2572 */               .getValueIsAdjusting()) {
/* 2573 */               startTimer();
/*      */             } else {
/* 2575 */               BasicTreeUI.this.updateSize();
/*      */             } 
/*      */           } else {
/* 2578 */             startTimer();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void startTimer() {
/* 2588 */       if (this.timer == null) {
/* 2589 */         this.timer = new Timer(200, this);
/* 2590 */         this.timer.setRepeats(true);
/*      */       } 
/* 2592 */       this.timer.start();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected JScrollPane getScrollPane() {
/* 2600 */       Container container = BasicTreeUI.this.tree.getParent();
/*      */       
/* 2602 */       while (container != null && !(container instanceof JScrollPane))
/* 2603 */         container = container.getParent(); 
/* 2604 */       if (container instanceof JScrollPane)
/* 2605 */         return (JScrollPane)container; 
/* 2606 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2614 */       if (this.scrollBar == null || !this.scrollBar.getValueIsAdjusting()) {
/* 2615 */         if (this.timer != null)
/* 2616 */           this.timer.stop(); 
/* 2617 */         BasicTreeUI.this.updateSize();
/* 2618 */         this.timer = null;
/* 2619 */         this.scrollBar = null;
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
/*      */   public class TreeModelHandler
/*      */     implements TreeModelListener
/*      */   {
/*      */     public void treeNodesChanged(TreeModelEvent param1TreeModelEvent) {
/* 2636 */       BasicTreeUI.this.getHandler().treeNodesChanged(param1TreeModelEvent);
/*      */     }
/*      */     
/*      */     public void treeNodesInserted(TreeModelEvent param1TreeModelEvent) {
/* 2640 */       BasicTreeUI.this.getHandler().treeNodesInserted(param1TreeModelEvent);
/*      */     }
/*      */     
/*      */     public void treeNodesRemoved(TreeModelEvent param1TreeModelEvent) {
/* 2644 */       BasicTreeUI.this.getHandler().treeNodesRemoved(param1TreeModelEvent);
/*      */     }
/*      */     
/*      */     public void treeStructureChanged(TreeModelEvent param1TreeModelEvent) {
/* 2648 */       BasicTreeUI.this.getHandler().treeStructureChanged(param1TreeModelEvent);
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
/*      */   public class TreeSelectionHandler
/*      */     implements TreeSelectionListener
/*      */   {
/*      */     public void valueChanged(TreeSelectionEvent param1TreeSelectionEvent) {
/* 2669 */       BasicTreeUI.this.getHandler().valueChanged(param1TreeSelectionEvent);
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
/*      */   public class CellEditorHandler
/*      */     implements CellEditorListener
/*      */   {
/*      */     public void editingStopped(ChangeEvent param1ChangeEvent) {
/* 2687 */       BasicTreeUI.this.getHandler().editingStopped(param1ChangeEvent);
/*      */     }
/*      */ 
/*      */     
/*      */     public void editingCanceled(ChangeEvent param1ChangeEvent) {
/* 2692 */       BasicTreeUI.this.getHandler().editingCanceled(param1ChangeEvent);
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
/*      */   public class KeyHandler
/*      */     extends KeyAdapter
/*      */   {
/*      */     protected Action repeatKeyAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean isKeyDown;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void keyTyped(KeyEvent param1KeyEvent) {
/* 2728 */       BasicTreeUI.this.getHandler().keyTyped(param1KeyEvent);
/*      */     }
/*      */     
/*      */     public void keyPressed(KeyEvent param1KeyEvent) {
/* 2732 */       BasicTreeUI.this.getHandler().keyPressed(param1KeyEvent);
/*      */     }
/*      */     
/*      */     public void keyReleased(KeyEvent param1KeyEvent) {
/* 2736 */       BasicTreeUI.this.getHandler().keyReleased(param1KeyEvent);
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
/*      */   public class FocusHandler
/*      */     implements FocusListener
/*      */   {
/*      */     public void focusGained(FocusEvent param1FocusEvent) {
/* 2755 */       BasicTreeUI.this.getHandler().focusGained(param1FocusEvent);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void focusLost(FocusEvent param1FocusEvent) {
/* 2763 */       BasicTreeUI.this.getHandler().focusLost(param1FocusEvent);
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
/*      */   public class NodeDimensionsHandler
/*      */     extends AbstractLayoutCache.NodeDimensions
/*      */   {
/*      */     public Rectangle getNodeDimensions(Object param1Object, int param1Int1, int param1Int2, boolean param1Boolean, Rectangle param1Rectangle) {
/* 2784 */       if (BasicTreeUI.this.editingComponent != null && BasicTreeUI.this.editingRow == param1Int1) {
/*      */         
/* 2786 */         Dimension dimension = BasicTreeUI.this.editingComponent.getPreferredSize();
/* 2787 */         int i = BasicTreeUI.this.getRowHeight();
/*      */         
/* 2789 */         if (i > 0 && i != dimension.height)
/* 2790 */           dimension.height = i; 
/* 2791 */         if (param1Rectangle != null) {
/* 2792 */           param1Rectangle.x = getRowX(param1Int1, param1Int2);
/* 2793 */           param1Rectangle.width = dimension.width;
/* 2794 */           param1Rectangle.height = dimension.height;
/*      */         } else {
/*      */           
/* 2797 */           param1Rectangle = new Rectangle(getRowX(param1Int1, param1Int2), 0, dimension.width, dimension.height);
/*      */         } 
/*      */         
/* 2800 */         return param1Rectangle;
/*      */       } 
/*      */       
/* 2803 */       if (BasicTreeUI.this.currentCellRenderer != null) {
/*      */ 
/*      */ 
/*      */         
/* 2807 */         Component component = BasicTreeUI.this.currentCellRenderer.getTreeCellRendererComponent(BasicTreeUI.this.tree, param1Object, BasicTreeUI.this.tree.isRowSelected(param1Int1), param1Boolean, BasicTreeUI.this.treeModel
/* 2808 */             .isLeaf(param1Object), param1Int1, false);
/*      */         
/* 2810 */         if (BasicTreeUI.this.tree != null) {
/*      */           
/* 2812 */           BasicTreeUI.this.rendererPane.add(component);
/* 2813 */           component.validate();
/*      */         } 
/* 2815 */         Dimension dimension = component.getPreferredSize();
/*      */         
/* 2817 */         if (param1Rectangle != null) {
/* 2818 */           param1Rectangle.x = getRowX(param1Int1, param1Int2);
/* 2819 */           param1Rectangle.width = dimension.width;
/* 2820 */           param1Rectangle.height = dimension.height;
/*      */         } else {
/*      */           
/* 2823 */           param1Rectangle = new Rectangle(getRowX(param1Int1, param1Int2), 0, dimension.width, dimension.height);
/*      */         } 
/*      */         
/* 2826 */         return param1Rectangle;
/*      */       } 
/* 2828 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected int getRowX(int param1Int1, int param1Int2) {
/* 2835 */       return BasicTreeUI.this.getRowX(param1Int1, param1Int2);
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
/*      */   public class MouseHandler
/*      */     extends MouseAdapter
/*      */     implements MouseMotionListener
/*      */   {
/*      */     public void mousePressed(MouseEvent param1MouseEvent) {
/* 2856 */       BasicTreeUI.this.getHandler().mousePressed(param1MouseEvent);
/*      */     }
/*      */     
/*      */     public void mouseDragged(MouseEvent param1MouseEvent) {
/* 2860 */       BasicTreeUI.this.getHandler().mouseDragged(param1MouseEvent);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseMoved(MouseEvent param1MouseEvent) {
/* 2869 */       BasicTreeUI.this.getHandler().mouseMoved(param1MouseEvent);
/*      */     }
/*      */     
/*      */     public void mouseReleased(MouseEvent param1MouseEvent) {
/* 2873 */       BasicTreeUI.this.getHandler().mouseReleased(param1MouseEvent);
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
/*      */   public class PropertyChangeHandler
/*      */     implements PropertyChangeListener
/*      */   {
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 2891 */       BasicTreeUI.this.getHandler().propertyChange(param1PropertyChangeEvent);
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
/*      */   public class SelectionModelPropertyChangeHandler
/*      */     implements PropertyChangeListener
/*      */   {
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 2909 */       BasicTreeUI.this.getHandler().propertyChange(param1PropertyChangeEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class TreeTraverseAction
/*      */     extends AbstractAction
/*      */   {
/*      */     protected int direction;
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean changeSelection;
/*      */ 
/*      */ 
/*      */     
/*      */     public TreeTraverseAction(int param1Int, String param1String) {
/* 2928 */       this(param1Int, param1String, true);
/*      */     }
/*      */ 
/*      */     
/*      */     private TreeTraverseAction(int param1Int, String param1String, boolean param1Boolean) {
/* 2933 */       this.direction = param1Int;
/* 2934 */       this.changeSelection = param1Boolean;
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2938 */       if (BasicTreeUI.this.tree != null) {
/* 2939 */         BasicTreeUI.SHARED_ACTION.traverse(BasicTreeUI.this.tree, BasicTreeUI.this, this.direction, this.changeSelection);
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean isEnabled() {
/* 2944 */       return (BasicTreeUI.this.tree != null && BasicTreeUI.this.tree
/* 2945 */         .isEnabled());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public class TreePageAction
/*      */     extends AbstractAction
/*      */   {
/*      */     protected int direction;
/*      */     
/*      */     private boolean addToSelection;
/*      */     private boolean changeSelection;
/*      */     
/*      */     public TreePageAction(int param1Int, String param1String) {
/* 2959 */       this(param1Int, param1String, false, true);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private TreePageAction(int param1Int, String param1String, boolean param1Boolean1, boolean param1Boolean2) {
/* 2965 */       this.direction = param1Int;
/* 2966 */       this.addToSelection = param1Boolean1;
/* 2967 */       this.changeSelection = param1Boolean2;
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2971 */       if (BasicTreeUI.this.tree != null) {
/* 2972 */         BasicTreeUI.SHARED_ACTION.page(BasicTreeUI.this.tree, BasicTreeUI.this, this.direction, this.addToSelection, this.changeSelection);
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean isEnabled() {
/* 2977 */       return (BasicTreeUI.this.tree != null && BasicTreeUI.this.tree
/* 2978 */         .isEnabled());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public class TreeIncrementAction
/*      */     extends AbstractAction
/*      */   {
/*      */     protected int direction;
/*      */     
/*      */     private boolean addToSelection;
/*      */     
/*      */     private boolean changeSelection;
/*      */ 
/*      */     
/*      */     public TreeIncrementAction(int param1Int, String param1String) {
/* 2995 */       this(param1Int, param1String, false, true);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private TreeIncrementAction(int param1Int, String param1String, boolean param1Boolean1, boolean param1Boolean2) {
/* 3001 */       this.direction = param1Int;
/* 3002 */       this.addToSelection = param1Boolean1;
/* 3003 */       this.changeSelection = param1Boolean2;
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 3007 */       if (BasicTreeUI.this.tree != null) {
/* 3008 */         BasicTreeUI.SHARED_ACTION.increment(BasicTreeUI.this.tree, BasicTreeUI.this, this.direction, this.addToSelection, this.changeSelection);
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean isEnabled() {
/* 3013 */       return (BasicTreeUI.this.tree != null && BasicTreeUI.this.tree
/* 3014 */         .isEnabled());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public class TreeHomeAction
/*      */     extends AbstractAction
/*      */   {
/*      */     protected int direction;
/*      */     
/*      */     private boolean addToSelection;
/*      */     
/*      */     private boolean changeSelection;
/*      */ 
/*      */     
/*      */     public TreeHomeAction(int param1Int, String param1String) {
/* 3030 */       this(param1Int, param1String, false, true);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private TreeHomeAction(int param1Int, String param1String, boolean param1Boolean1, boolean param1Boolean2) {
/* 3036 */       this.direction = param1Int;
/* 3037 */       this.changeSelection = param1Boolean2;
/* 3038 */       this.addToSelection = param1Boolean1;
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 3042 */       if (BasicTreeUI.this.tree != null) {
/* 3043 */         BasicTreeUI.SHARED_ACTION.home(BasicTreeUI.this.tree, BasicTreeUI.this, this.direction, this.addToSelection, this.changeSelection);
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean isEnabled() {
/* 3048 */       return (BasicTreeUI.this.tree != null && BasicTreeUI.this.tree
/* 3049 */         .isEnabled());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public class TreeToggleAction
/*      */     extends AbstractAction
/*      */   {
/*      */     public TreeToggleAction(String param1String) {}
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 3062 */       if (BasicTreeUI.this.tree != null)
/* 3063 */         BasicTreeUI.SHARED_ACTION.toggle(BasicTreeUI.this.tree, BasicTreeUI.this); 
/*      */     }
/*      */     
/*      */     public boolean isEnabled() {
/* 3067 */       return (BasicTreeUI.this.tree != null && BasicTreeUI.this.tree
/* 3068 */         .isEnabled());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public class TreeCancelEditingAction
/*      */     extends AbstractAction
/*      */   {
/*      */     public TreeCancelEditingAction(String param1String) {}
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 3081 */       if (BasicTreeUI.this.tree != null)
/* 3082 */         BasicTreeUI.SHARED_ACTION.cancelEditing(BasicTreeUI.this.tree, BasicTreeUI.this); 
/*      */     }
/*      */     
/*      */     public boolean isEnabled() {
/* 3086 */       return (BasicTreeUI.this.tree != null && BasicTreeUI.this.tree
/* 3087 */         .isEnabled() && BasicTreeUI.this
/* 3088 */         .isEditing(BasicTreeUI.this.tree));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public class MouseInputHandler
/*      */     implements MouseInputListener
/*      */   {
/*      */     protected Component source;
/*      */ 
/*      */     
/*      */     protected Component destination;
/*      */ 
/*      */     
/*      */     private Component focusComponent;
/*      */ 
/*      */     
/*      */     private boolean dispatchedEvent;
/*      */ 
/*      */     
/*      */     public MouseInputHandler(Component param1Component1, Component param1Component2, MouseEvent param1MouseEvent) {
/* 3110 */       this(param1Component1, param1Component2, param1MouseEvent, null);
/*      */     }
/*      */ 
/*      */     
/*      */     MouseInputHandler(Component param1Component1, Component param1Component2, MouseEvent param1MouseEvent, Component param1Component3) {
/* 3115 */       this.source = param1Component1;
/* 3116 */       this.destination = param1Component2;
/* 3117 */       this.source.addMouseListener(this);
/* 3118 */       this.source.addMouseMotionListener(this);
/*      */       
/* 3120 */       SwingUtilities2.setSkipClickCount(param1Component2, param1MouseEvent
/* 3121 */           .getClickCount() - 1);
/*      */ 
/*      */       
/* 3124 */       param1Component2.dispatchEvent(
/* 3125 */           SwingUtilities.convertMouseEvent(param1Component1, param1MouseEvent, param1Component2));
/* 3126 */       this.focusComponent = param1Component3;
/*      */     }
/*      */     
/*      */     public void mouseClicked(MouseEvent param1MouseEvent) {
/* 3130 */       if (this.destination != null) {
/* 3131 */         this.dispatchedEvent = true;
/* 3132 */         this.destination.dispatchEvent(
/* 3133 */             SwingUtilities.convertMouseEvent(this.source, param1MouseEvent, this.destination));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void mousePressed(MouseEvent param1MouseEvent) {}
/*      */     
/*      */     public void mouseReleased(MouseEvent param1MouseEvent) {
/* 3141 */       if (this.destination != null)
/* 3142 */         this.destination.dispatchEvent(
/* 3143 */             SwingUtilities.convertMouseEvent(this.source, param1MouseEvent, this.destination)); 
/* 3144 */       removeFromSource();
/*      */     }
/*      */     
/*      */     public void mouseEntered(MouseEvent param1MouseEvent) {
/* 3148 */       if (!SwingUtilities.isLeftMouseButton(param1MouseEvent)) {
/* 3149 */         removeFromSource();
/*      */       }
/*      */     }
/*      */     
/*      */     public void mouseExited(MouseEvent param1MouseEvent) {
/* 3154 */       if (!SwingUtilities.isLeftMouseButton(param1MouseEvent)) {
/* 3155 */         removeFromSource();
/*      */       }
/*      */     }
/*      */     
/*      */     public void mouseDragged(MouseEvent param1MouseEvent) {
/* 3160 */       if (this.destination != null) {
/* 3161 */         this.dispatchedEvent = true;
/* 3162 */         this.destination.dispatchEvent(
/* 3163 */             SwingUtilities.convertMouseEvent(this.source, param1MouseEvent, this.destination));
/*      */       } 
/*      */     }
/*      */     
/*      */     public void mouseMoved(MouseEvent param1MouseEvent) {
/* 3168 */       removeFromSource();
/*      */     }
/*      */     
/*      */     protected void removeFromSource() {
/* 3172 */       if (this.source != null) {
/* 3173 */         this.source.removeMouseListener(this);
/* 3174 */         this.source.removeMouseMotionListener(this);
/* 3175 */         if (this.focusComponent != null && this.focusComponent == this.destination && !this.dispatchedEvent && this.focusComponent instanceof JTextField)
/*      */         {
/*      */           
/* 3178 */           ((JTextField)this.focusComponent).selectAll();
/*      */         }
/*      */       } 
/* 3181 */       this.source = this.destination = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/* 3186 */   private static final TransferHandler defaultTransferHandler = new TreeTransferHandler();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class TreeTransferHandler
/*      */     extends TransferHandler
/*      */     implements UIResource, Comparator<TreePath>
/*      */   {
/*      */     private JTree tree;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Transferable createTransferable(JComponent param1JComponent) {
/* 3202 */       if (param1JComponent instanceof JTree) {
/* 3203 */         this.tree = (JTree)param1JComponent;
/* 3204 */         TreePath[] arrayOfTreePath1 = this.tree.getSelectionPaths();
/*      */         
/* 3206 */         if (arrayOfTreePath1 == null || arrayOfTreePath1.length == 0) {
/* 3207 */           return null;
/*      */         }
/*      */         
/* 3210 */         StringBuffer stringBuffer1 = new StringBuffer();
/* 3211 */         StringBuffer stringBuffer2 = new StringBuffer();
/*      */         
/* 3213 */         stringBuffer2.append("<html>\n<body>\n<ul>\n");
/*      */         
/* 3215 */         TreeModel treeModel = this.tree.getModel();
/* 3216 */         Object object = null;
/* 3217 */         TreePath[] arrayOfTreePath2 = getDisplayOrderPaths(arrayOfTreePath1);
/*      */         
/* 3219 */         for (TreePath treePath : arrayOfTreePath2) {
/* 3220 */           Object object1 = treePath.getLastPathComponent();
/* 3221 */           boolean bool = treeModel.isLeaf(object1);
/* 3222 */           String str = getDisplayString(treePath, true, bool);
/*      */           
/* 3224 */           stringBuffer1.append(str + "\n");
/* 3225 */           stringBuffer2.append("  <li>" + str + "\n");
/*      */         } 
/*      */ 
/*      */         
/* 3229 */         stringBuffer1.deleteCharAt(stringBuffer1.length() - 1);
/* 3230 */         stringBuffer2.append("</ul>\n</body>\n</html>");
/*      */         
/* 3232 */         this.tree = null;
/*      */         
/* 3234 */         return new BasicTransferable(stringBuffer1.toString(), stringBuffer2.toString());
/*      */       } 
/*      */       
/* 3237 */       return null;
/*      */     }
/*      */     
/*      */     public int compare(TreePath param1TreePath1, TreePath param1TreePath2) {
/* 3241 */       int i = this.tree.getRowForPath(param1TreePath1);
/* 3242 */       int j = this.tree.getRowForPath(param1TreePath2);
/* 3243 */       return i - j;
/*      */     }
/*      */     
/*      */     String getDisplayString(TreePath param1TreePath, boolean param1Boolean1, boolean param1Boolean2) {
/* 3247 */       int i = this.tree.getRowForPath(param1TreePath);
/* 3248 */       boolean bool = (this.tree.getLeadSelectionRow() == i) ? true : false;
/* 3249 */       Object object = param1TreePath.getLastPathComponent();
/* 3250 */       return this.tree.convertValueToText(object, param1Boolean1, this.tree.isExpanded(i), param1Boolean2, i, bool);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     TreePath[] getDisplayOrderPaths(TreePath[] param1ArrayOfTreePath) {
/* 3261 */       ArrayList<TreePath> arrayList = new ArrayList();
/* 3262 */       for (TreePath treePath : param1ArrayOfTreePath) {
/* 3263 */         arrayList.add(treePath);
/*      */       }
/* 3265 */       Collections.sort(arrayList, this);
/* 3266 */       int i = arrayList.size();
/* 3267 */       TreePath[] arrayOfTreePath = new TreePath[i];
/* 3268 */       for (byte b = 0; b < i; b++) {
/* 3269 */         arrayOfTreePath[b] = arrayList.get(b);
/*      */       }
/* 3271 */       return arrayOfTreePath;
/*      */     }
/*      */     
/*      */     public int getSourceActions(JComponent param1JComponent) {
/* 3275 */       return 1;
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
/*      */   private class Handler
/*      */     implements CellEditorListener, FocusListener, KeyListener, MouseListener, MouseMotionListener, PropertyChangeListener, TreeExpansionListener, TreeModelListener, TreeSelectionListener, DragRecognitionSupport.BeforeDrag
/*      */   {
/* 3289 */     private String prefix = "";
/* 3290 */     private String typedString = "";
/* 3291 */     private long lastTime = 0L;
/*      */ 
/*      */     
/*      */     private boolean dragPressDidSelection;
/*      */     
/*      */     private boolean dragStarted;
/*      */     
/*      */     private TreePath pressedPath;
/*      */     
/*      */     private MouseEvent pressedEvent;
/*      */     
/*      */     private boolean valueChangedOnPress;
/*      */ 
/*      */     
/*      */     public void keyTyped(KeyEvent param1KeyEvent) {
/* 3306 */       if (BasicTreeUI.this.tree != null && BasicTreeUI.this.tree.getRowCount() > 0 && BasicTreeUI.this.tree.hasFocus() && BasicTreeUI.this.tree
/* 3307 */         .isEnabled()) {
/* 3308 */         if (param1KeyEvent.isAltDown() || BasicGraphicsUtils.isMenuShortcutKeyDown(param1KeyEvent) || 
/* 3309 */           isNavigationKey(param1KeyEvent)) {
/*      */           return;
/*      */         }
/* 3312 */         boolean bool = true;
/*      */         
/* 3314 */         char c = param1KeyEvent.getKeyChar();
/*      */         
/* 3316 */         long l = param1KeyEvent.getWhen();
/* 3317 */         int i = BasicTreeUI.this.tree.getLeadSelectionRow();
/* 3318 */         if (l - this.lastTime < BasicTreeUI.this.timeFactor) {
/* 3319 */           this.typedString += c;
/* 3320 */           if (this.prefix.length() == 1 && c == this.prefix.charAt(0)) {
/*      */ 
/*      */             
/* 3323 */             i++;
/*      */           } else {
/* 3325 */             this.prefix = this.typedString;
/*      */           } 
/*      */         } else {
/* 3328 */           i++;
/* 3329 */           this.typedString = "" + c;
/* 3330 */           this.prefix = this.typedString;
/*      */         } 
/* 3332 */         this.lastTime = l;
/*      */         
/* 3334 */         if (i < 0 || i >= BasicTreeUI.this.tree.getRowCount()) {
/* 3335 */           bool = false;
/* 3336 */           i = 0;
/*      */         } 
/* 3338 */         TreePath treePath = BasicTreeUI.this.tree.getNextMatch(this.prefix, i, Position.Bias.Forward);
/*      */         
/* 3340 */         if (treePath != null) {
/* 3341 */           BasicTreeUI.this.tree.setSelectionPath(treePath);
/* 3342 */           int j = BasicTreeUI.this.getRowForPath(BasicTreeUI.this.tree, treePath);
/* 3343 */           BasicTreeUI.this.ensureRowsAreVisible(j, j);
/* 3344 */         } else if (bool) {
/* 3345 */           treePath = BasicTreeUI.this.tree.getNextMatch(this.prefix, 0, Position.Bias.Forward);
/*      */           
/* 3347 */           if (treePath != null) {
/* 3348 */             BasicTreeUI.this.tree.setSelectionPath(treePath);
/* 3349 */             int j = BasicTreeUI.this.getRowForPath(BasicTreeUI.this.tree, treePath);
/* 3350 */             BasicTreeUI.this.ensureRowsAreVisible(j, j);
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
/*      */     public void keyPressed(KeyEvent param1KeyEvent) {
/* 3363 */       if (BasicTreeUI.this.tree != null && isNavigationKey(param1KeyEvent)) {
/* 3364 */         this.prefix = "";
/* 3365 */         this.typedString = "";
/* 3366 */         this.lastTime = 0L;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void keyReleased(KeyEvent param1KeyEvent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean isNavigationKey(KeyEvent param1KeyEvent) {
/* 3379 */       InputMap inputMap = BasicTreeUI.this.tree.getInputMap(1);
/* 3380 */       KeyStroke keyStroke = KeyStroke.getKeyStrokeForEvent(param1KeyEvent);
/*      */       
/* 3382 */       return (inputMap != null && inputMap.get(keyStroke) != null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 3390 */       if (param1PropertyChangeEvent.getSource() == BasicTreeUI.this.treeSelectionModel) {
/* 3391 */         BasicTreeUI.this.treeSelectionModel.resetRowSelection();
/*      */       }
/* 3393 */       else if (param1PropertyChangeEvent.getSource() == BasicTreeUI.this.tree) {
/* 3394 */         String str = param1PropertyChangeEvent.getPropertyName();
/*      */         
/* 3396 */         if (str == "leadSelectionPath") {
/* 3397 */           if (!BasicTreeUI.this.ignoreLAChange) {
/* 3398 */             BasicTreeUI.this.updateLeadSelectionRow();
/* 3399 */             BasicTreeUI.this.repaintPath((TreePath)param1PropertyChangeEvent.getOldValue());
/* 3400 */             BasicTreeUI.this.repaintPath((TreePath)param1PropertyChangeEvent.getNewValue());
/*      */           }
/*      */         
/* 3403 */         } else if (str == "anchorSelectionPath" && 
/* 3404 */           !BasicTreeUI.this.ignoreLAChange) {
/* 3405 */           BasicTreeUI.this.repaintPath((TreePath)param1PropertyChangeEvent.getOldValue());
/* 3406 */           BasicTreeUI.this.repaintPath((TreePath)param1PropertyChangeEvent.getNewValue());
/*      */         } 
/*      */         
/* 3409 */         if (str == "cellRenderer") {
/* 3410 */           BasicTreeUI.this.setCellRenderer((TreeCellRenderer)param1PropertyChangeEvent.getNewValue());
/* 3411 */           BasicTreeUI.this.redoTheLayout();
/*      */         }
/* 3413 */         else if (str == "model") {
/* 3414 */           BasicTreeUI.this.setModel((TreeModel)param1PropertyChangeEvent.getNewValue());
/*      */         }
/* 3416 */         else if (str == "rootVisible") {
/* 3417 */           BasicTreeUI.this.setRootVisible(((Boolean)param1PropertyChangeEvent.getNewValue())
/* 3418 */               .booleanValue());
/*      */         }
/* 3420 */         else if (str == "showsRootHandles") {
/* 3421 */           BasicTreeUI.this.setShowsRootHandles(((Boolean)param1PropertyChangeEvent.getNewValue())
/* 3422 */               .booleanValue());
/*      */         }
/* 3424 */         else if (str == "rowHeight") {
/* 3425 */           BasicTreeUI.this.setRowHeight(((Integer)param1PropertyChangeEvent.getNewValue())
/* 3426 */               .intValue());
/*      */         }
/* 3428 */         else if (str == "cellEditor") {
/* 3429 */           BasicTreeUI.this.setCellEditor((TreeCellEditor)param1PropertyChangeEvent.getNewValue());
/*      */         }
/* 3431 */         else if (str == "editable") {
/* 3432 */           BasicTreeUI.this.setEditable(((Boolean)param1PropertyChangeEvent.getNewValue()).booleanValue());
/*      */         }
/* 3434 */         else if (str == "largeModel") {
/* 3435 */           BasicTreeUI.this.setLargeModel(BasicTreeUI.this.tree.isLargeModel());
/*      */         }
/* 3437 */         else if (str == "selectionModel") {
/* 3438 */           BasicTreeUI.this.setSelectionModel(BasicTreeUI.this.tree.getSelectionModel());
/*      */         }
/* 3440 */         else if (str == "font") {
/* 3441 */           BasicTreeUI.this.completeEditing();
/* 3442 */           if (BasicTreeUI.this.treeState != null)
/* 3443 */             BasicTreeUI.this.treeState.invalidateSizes(); 
/* 3444 */           BasicTreeUI.this.updateSize();
/*      */         }
/* 3446 */         else if (str == "componentOrientation") {
/* 3447 */           if (BasicTreeUI.this.tree != null) {
/* 3448 */             BasicTreeUI.this.leftToRight = BasicGraphicsUtils.isLeftToRight(BasicTreeUI.this.tree);
/* 3449 */             BasicTreeUI.this.redoTheLayout();
/* 3450 */             BasicTreeUI.this.tree.treeDidChange();
/*      */             
/* 3452 */             InputMap inputMap = BasicTreeUI.this.getInputMap(0);
/* 3453 */             SwingUtilities.replaceUIInputMap(BasicTreeUI.this.tree, 0, inputMap);
/*      */           }
/*      */         
/* 3456 */         } else if ("dropLocation" == str) {
/* 3457 */           JTree.DropLocation dropLocation = (JTree.DropLocation)param1PropertyChangeEvent.getOldValue();
/* 3458 */           repaintDropLocation(dropLocation);
/* 3459 */           repaintDropLocation(BasicTreeUI.this.tree.getDropLocation());
/*      */         } 
/*      */       } 
/*      */     }
/*      */     private void repaintDropLocation(JTree.DropLocation param1DropLocation) {
/*      */       Rectangle rectangle;
/* 3465 */       if (param1DropLocation == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 3471 */       if (BasicTreeUI.this.isDropLine(param1DropLocation)) {
/* 3472 */         rectangle = BasicTreeUI.this.getDropLineRect(param1DropLocation);
/*      */       } else {
/* 3474 */         rectangle = BasicTreeUI.this.tree.getPathBounds(param1DropLocation.getPath());
/*      */       } 
/*      */       
/* 3477 */       if (rectangle != null) {
/* 3478 */         BasicTreeUI.this.tree.repaint(rectangle);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean isActualPath(TreePath param1TreePath, int param1Int1, int param1Int2) {
/* 3505 */       if (param1TreePath == null) {
/* 3506 */         return false;
/*      */       }
/*      */       
/* 3509 */       Rectangle rectangle = BasicTreeUI.this.getPathBounds(BasicTreeUI.this.tree, param1TreePath);
/* 3510 */       if (rectangle == null || param1Int2 > rectangle.y + rectangle.height) {
/* 3511 */         return false;
/*      */       }
/*      */       
/* 3514 */       return (param1Int1 >= rectangle.x && param1Int1 <= rectangle.x + rectangle.width);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseClicked(MouseEvent param1MouseEvent) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseEntered(MouseEvent param1MouseEvent) {}
/*      */ 
/*      */     
/*      */     public void mouseExited(MouseEvent param1MouseEvent) {}
/*      */ 
/*      */     
/*      */     public void mousePressed(MouseEvent param1MouseEvent) {
/* 3530 */       if (SwingUtilities2.shouldIgnore(param1MouseEvent, BasicTreeUI.this.tree)) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/* 3535 */       if (BasicTreeUI.this.isEditing(BasicTreeUI.this.tree) && BasicTreeUI.this.tree.getInvokesStopCellEditing() && 
/* 3536 */         !BasicTreeUI.this.stopEditing(BasicTreeUI.this.tree)) {
/*      */         return;
/*      */       }
/*      */       
/* 3540 */       BasicTreeUI.this.completeEditing();
/*      */       
/* 3542 */       this.pressedPath = BasicTreeUI.this.getClosestPathForLocation(BasicTreeUI.this.tree, param1MouseEvent.getX(), param1MouseEvent.getY());
/*      */       
/* 3544 */       if (BasicTreeUI.this.tree.getDragEnabled()) {
/* 3545 */         mousePressedDND(param1MouseEvent);
/*      */       } else {
/* 3547 */         SwingUtilities2.adjustFocus(BasicTreeUI.this.tree);
/* 3548 */         handleSelection(param1MouseEvent);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void mousePressedDND(MouseEvent param1MouseEvent) {
/* 3553 */       this.pressedEvent = param1MouseEvent;
/* 3554 */       boolean bool = true;
/* 3555 */       this.dragStarted = false;
/* 3556 */       this.valueChangedOnPress = false;
/*      */ 
/*      */       
/* 3559 */       if (isActualPath(this.pressedPath, param1MouseEvent.getX(), param1MouseEvent.getY()) && 
/* 3560 */         DragRecognitionSupport.mousePressed(param1MouseEvent)) {
/*      */         
/* 3562 */         this.dragPressDidSelection = false;
/*      */         
/* 3564 */         if (BasicGraphicsUtils.isMenuShortcutKeyDown(param1MouseEvent)) {
/*      */           return;
/*      */         }
/*      */         
/* 3568 */         if (!param1MouseEvent.isShiftDown() && BasicTreeUI.this.tree.isPathSelected(this.pressedPath)) {
/*      */ 
/*      */           
/* 3571 */           BasicTreeUI.this.setAnchorSelectionPath(this.pressedPath);
/* 3572 */           BasicTreeUI.this.setLeadSelectionPath(this.pressedPath, true);
/*      */           
/*      */           return;
/*      */         } 
/* 3576 */         this.dragPressDidSelection = true;
/*      */ 
/*      */         
/* 3579 */         bool = false;
/*      */       } 
/*      */       
/* 3582 */       if (bool) {
/* 3583 */         SwingUtilities2.adjustFocus(BasicTreeUI.this.tree);
/*      */       }
/*      */       
/* 3586 */       handleSelection(param1MouseEvent);
/*      */     }
/*      */     
/*      */     void handleSelection(MouseEvent param1MouseEvent) {
/* 3590 */       if (this.pressedPath != null) {
/* 3591 */         Rectangle rectangle = BasicTreeUI.this.getPathBounds(BasicTreeUI.this.tree, this.pressedPath);
/*      */         
/* 3593 */         if (rectangle == null || param1MouseEvent.getY() >= rectangle.y + rectangle.height) {
/*      */           return;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 3599 */         if (SwingUtilities.isLeftMouseButton(param1MouseEvent)) {
/* 3600 */           BasicTreeUI.this.checkForClickInExpandControl(this.pressedPath, param1MouseEvent.getX(), param1MouseEvent.getY());
/*      */         }
/*      */         
/* 3603 */         int i = param1MouseEvent.getX();
/*      */ 
/*      */ 
/*      */         
/* 3607 */         if (i >= rectangle.x && i < rectangle.x + rectangle.width && (
/* 3608 */           BasicTreeUI.this.tree.getDragEnabled() || !BasicTreeUI.this.startEditing(this.pressedPath, param1MouseEvent))) {
/* 3609 */           BasicTreeUI.this.selectPathForEvent(this.pressedPath, param1MouseEvent);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void dragStarting(MouseEvent param1MouseEvent) {
/* 3616 */       this.dragStarted = true;
/*      */       
/* 3618 */       if (BasicGraphicsUtils.isMenuShortcutKeyDown(param1MouseEvent)) {
/* 3619 */         BasicTreeUI.this.tree.addSelectionPath(this.pressedPath);
/* 3620 */         BasicTreeUI.this.setAnchorSelectionPath(this.pressedPath);
/* 3621 */         BasicTreeUI.this.setLeadSelectionPath(this.pressedPath, true);
/*      */       } 
/*      */       
/* 3624 */       this.pressedEvent = null;
/* 3625 */       this.pressedPath = null;
/*      */     }
/*      */     
/*      */     public void mouseDragged(MouseEvent param1MouseEvent) {
/* 3629 */       if (SwingUtilities2.shouldIgnore(param1MouseEvent, BasicTreeUI.this.tree)) {
/*      */         return;
/*      */       }
/*      */       
/* 3633 */       if (BasicTreeUI.this.tree.getDragEnabled()) {
/* 3634 */         DragRecognitionSupport.mouseDragged(param1MouseEvent, this);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseMoved(MouseEvent param1MouseEvent) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseReleased(MouseEvent param1MouseEvent) {
/* 3646 */       if (SwingUtilities2.shouldIgnore(param1MouseEvent, BasicTreeUI.this.tree)) {
/*      */         return;
/*      */       }
/*      */       
/* 3650 */       if (BasicTreeUI.this.tree.getDragEnabled()) {
/* 3651 */         mouseReleasedDND(param1MouseEvent);
/*      */       }
/*      */       
/* 3654 */       this.pressedEvent = null;
/* 3655 */       this.pressedPath = null;
/*      */     }
/*      */     
/*      */     private void mouseReleasedDND(MouseEvent param1MouseEvent) {
/* 3659 */       MouseEvent mouseEvent = DragRecognitionSupport.mouseReleased(param1MouseEvent);
/* 3660 */       if (mouseEvent != null) {
/* 3661 */         SwingUtilities2.adjustFocus(BasicTreeUI.this.tree);
/* 3662 */         if (!this.dragPressDidSelection) {
/* 3663 */           handleSelection(mouseEvent);
/*      */         }
/*      */       } 
/*      */       
/* 3667 */       if (!this.dragStarted)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3678 */         if (this.pressedPath != null && !this.valueChangedOnPress && 
/* 3679 */           isActualPath(this.pressedPath, this.pressedEvent.getX(), this.pressedEvent.getY()))
/*      */         {
/* 3681 */           BasicTreeUI.this.startEditingOnRelease(this.pressedPath, this.pressedEvent, param1MouseEvent);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void focusGained(FocusEvent param1FocusEvent) {
/* 3690 */       if (BasicTreeUI.this.tree != null) {
/*      */ 
/*      */         
/* 3693 */         Rectangle rectangle = BasicTreeUI.this.getPathBounds(BasicTreeUI.this.tree, BasicTreeUI.this.tree.getLeadSelectionPath());
/* 3694 */         if (rectangle != null)
/* 3695 */           BasicTreeUI.this.tree.repaint(BasicTreeUI.this.getRepaintPathBounds(rectangle)); 
/* 3696 */         rectangle = BasicTreeUI.this.getPathBounds(BasicTreeUI.this.tree, BasicTreeUI.this.getLeadSelectionPath());
/* 3697 */         if (rectangle != null)
/* 3698 */           BasicTreeUI.this.tree.repaint(BasicTreeUI.this.getRepaintPathBounds(rectangle)); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void focusLost(FocusEvent param1FocusEvent) {
/* 3703 */       focusGained(param1FocusEvent);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void editingStopped(ChangeEvent param1ChangeEvent) {
/* 3710 */       BasicTreeUI.this.completeEditing(false, false, true);
/*      */     }
/*      */ 
/*      */     
/*      */     public void editingCanceled(ChangeEvent param1ChangeEvent) {
/* 3715 */       BasicTreeUI.this.completeEditing(false, false, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void valueChanged(TreeSelectionEvent param1TreeSelectionEvent) {
/* 3723 */       this.valueChangedOnPress = true;
/*      */ 
/*      */       
/* 3726 */       BasicTreeUI.this.completeEditing();
/*      */ 
/*      */       
/* 3729 */       if (BasicTreeUI.this.tree.getExpandsSelectedPaths() && BasicTreeUI.this.treeSelectionModel != null) {
/*      */         
/* 3731 */         TreePath[] arrayOfTreePath1 = BasicTreeUI.this.treeSelectionModel.getSelectionPaths();
/*      */         
/* 3733 */         if (arrayOfTreePath1 != null) {
/* 3734 */           for (int j = arrayOfTreePath1.length - 1; j >= 0; 
/* 3735 */             j--) {
/* 3736 */             TreePath treePath = arrayOfTreePath1[j].getParentPath();
/* 3737 */             boolean bool1 = true;
/*      */             
/* 3739 */             while (treePath != null) {
/*      */ 
/*      */               
/* 3742 */               if (BasicTreeUI.this.treeModel.isLeaf(treePath.getLastPathComponent())) {
/* 3743 */                 bool1 = false;
/* 3744 */                 treePath = null;
/*      */                 continue;
/*      */               } 
/* 3747 */               treePath = treePath.getParentPath();
/*      */             } 
/*      */             
/* 3750 */             if (bool1) {
/* 3751 */               BasicTreeUI.this.tree.makeVisible(arrayOfTreePath1[j]);
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */       
/* 3757 */       TreePath treePath1 = BasicTreeUI.this.getLeadSelectionPath();
/* 3758 */       BasicTreeUI.this.lastSelectedRow = BasicTreeUI.this.tree.getMinSelectionRow();
/* 3759 */       TreePath treePath2 = BasicTreeUI.this.tree.getSelectionModel().getLeadSelectionPath();
/* 3760 */       BasicTreeUI.this.setAnchorSelectionPath(treePath2);
/* 3761 */       BasicTreeUI.this.setLeadSelectionPath(treePath2);
/*      */       
/* 3763 */       TreePath[] arrayOfTreePath = param1TreeSelectionEvent.getPaths();
/*      */       
/* 3765 */       Rectangle rectangle = BasicTreeUI.this.tree.getVisibleRect();
/* 3766 */       boolean bool = true;
/* 3767 */       int i = BasicTreeUI.this.tree.getWidth();
/*      */       
/* 3769 */       if (arrayOfTreePath != null) {
/* 3770 */         int j = arrayOfTreePath.length;
/*      */         
/* 3772 */         if (j > 4) {
/* 3773 */           BasicTreeUI.this.tree.repaint();
/* 3774 */           bool = false;
/*      */         } else {
/*      */           
/* 3777 */           for (byte b = 0; b < j; b++) {
/* 3778 */             Rectangle rectangle1 = BasicTreeUI.this.getPathBounds(BasicTreeUI.this.tree, arrayOfTreePath[b]);
/*      */             
/* 3780 */             if (rectangle1 != null && rectangle
/* 3781 */               .intersects(rectangle1)) {
/* 3782 */               BasicTreeUI.this.tree.repaint(0, rectangle1.y, i, rectangle1.height);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/* 3787 */       if (bool) {
/* 3788 */         Rectangle rectangle1 = BasicTreeUI.this.getPathBounds(BasicTreeUI.this.tree, treePath1);
/* 3789 */         if (rectangle1 != null && rectangle.intersects(rectangle1))
/* 3790 */           BasicTreeUI.this.tree.repaint(0, rectangle1.y, i, rectangle1.height); 
/* 3791 */         rectangle1 = BasicTreeUI.this.getPathBounds(BasicTreeUI.this.tree, treePath2);
/* 3792 */         if (rectangle1 != null && rectangle.intersects(rectangle1)) {
/* 3793 */           BasicTreeUI.this.tree.repaint(0, rectangle1.y, i, rectangle1.height);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void treeExpanded(TreeExpansionEvent param1TreeExpansionEvent) {
/* 3802 */       if (param1TreeExpansionEvent != null && BasicTreeUI.this.tree != null) {
/* 3803 */         TreePath treePath = param1TreeExpansionEvent.getPath();
/*      */         
/* 3805 */         BasicTreeUI.this.updateExpandedDescendants(treePath);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void treeCollapsed(TreeExpansionEvent param1TreeExpansionEvent) {
/* 3810 */       if (param1TreeExpansionEvent != null && BasicTreeUI.this.tree != null) {
/* 3811 */         TreePath treePath = param1TreeExpansionEvent.getPath();
/*      */         
/* 3813 */         BasicTreeUI.this.completeEditing();
/* 3814 */         if (treePath != null && BasicTreeUI.this.tree.isVisible(treePath)) {
/* 3815 */           BasicTreeUI.this.treeState.setExpandedState(treePath, false);
/* 3816 */           BasicTreeUI.this.updateLeadSelectionRow();
/* 3817 */           BasicTreeUI.this.updateSize();
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void treeNodesChanged(TreeModelEvent param1TreeModelEvent) {
/* 3826 */       if (BasicTreeUI.this.treeState != null && param1TreeModelEvent != null) {
/* 3827 */         TreePath treePath = SwingUtilities2.getTreePath(param1TreeModelEvent, BasicTreeUI.this.getModel());
/* 3828 */         int[] arrayOfInt = param1TreeModelEvent.getChildIndices();
/* 3829 */         if (arrayOfInt == null || arrayOfInt.length == 0) {
/*      */           
/* 3831 */           BasicTreeUI.this.treeState.treeNodesChanged(param1TreeModelEvent);
/* 3832 */           BasicTreeUI.this.updateSize();
/*      */         }
/* 3834 */         else if (BasicTreeUI.this.treeState.isExpanded(treePath)) {
/*      */ 
/*      */ 
/*      */           
/* 3838 */           int i = arrayOfInt[0];
/* 3839 */           for (int j = arrayOfInt.length - 1; j > 0; j--) {
/* 3840 */             i = Math.min(arrayOfInt[j], i);
/*      */           }
/* 3842 */           Object object = BasicTreeUI.this.treeModel.getChild(treePath
/* 3843 */               .getLastPathComponent(), i);
/* 3844 */           TreePath treePath1 = treePath.pathByAddingChild(object);
/* 3845 */           Rectangle rectangle1 = BasicTreeUI.this.getPathBounds(BasicTreeUI.this.tree, treePath1);
/*      */ 
/*      */           
/* 3848 */           BasicTreeUI.this.treeState.treeNodesChanged(param1TreeModelEvent);
/*      */ 
/*      */           
/* 3851 */           BasicTreeUI.this.updateSize0();
/*      */ 
/*      */           
/* 3854 */           Rectangle rectangle2 = BasicTreeUI.this.getPathBounds(BasicTreeUI.this.tree, treePath1);
/* 3855 */           if (rectangle1 == null || rectangle2 == null) {
/*      */             return;
/*      */           }
/*      */           
/* 3859 */           if (arrayOfInt.length == 1 && rectangle2.height == rectangle1.height)
/*      */           {
/* 3861 */             BasicTreeUI.this.tree.repaint(0, rectangle1.y, BasicTreeUI.this.tree.getWidth(), rectangle1.height);
/*      */           }
/*      */           else
/*      */           {
/* 3865 */             BasicTreeUI.this.tree.repaint(0, rectangle1.y, BasicTreeUI.this.tree.getWidth(), BasicTreeUI.this.tree
/* 3866 */                 .getHeight() - rectangle1.y);
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 3871 */           BasicTreeUI.this.treeState.treeNodesChanged(param1TreeModelEvent);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void treeNodesInserted(TreeModelEvent param1TreeModelEvent) {
/* 3877 */       if (BasicTreeUI.this.treeState != null && param1TreeModelEvent != null) {
/* 3878 */         BasicTreeUI.this.treeState.treeNodesInserted(param1TreeModelEvent);
/*      */         
/* 3880 */         BasicTreeUI.this.updateLeadSelectionRow();
/*      */         
/* 3882 */         TreePath treePath = SwingUtilities2.getTreePath(param1TreeModelEvent, BasicTreeUI.this.getModel());
/*      */         
/* 3884 */         if (BasicTreeUI.this.treeState.isExpanded(treePath)) {
/* 3885 */           BasicTreeUI.this.updateSize();
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/* 3891 */           int[] arrayOfInt = param1TreeModelEvent.getChildIndices();
/*      */           
/* 3893 */           int i = BasicTreeUI.this.treeModel.getChildCount(treePath.getLastPathComponent());
/*      */           
/* 3895 */           if (arrayOfInt != null && i - arrayOfInt.length == 0)
/* 3896 */             BasicTreeUI.this.updateSize(); 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void treeNodesRemoved(TreeModelEvent param1TreeModelEvent) {
/* 3902 */       if (BasicTreeUI.this.treeState != null && param1TreeModelEvent != null) {
/* 3903 */         BasicTreeUI.this.treeState.treeNodesRemoved(param1TreeModelEvent);
/*      */         
/* 3905 */         BasicTreeUI.this.updateLeadSelectionRow();
/*      */         
/* 3907 */         TreePath treePath = SwingUtilities2.getTreePath(param1TreeModelEvent, BasicTreeUI.this.getModel());
/*      */         
/* 3909 */         if (BasicTreeUI.this.treeState.isExpanded(treePath) || BasicTreeUI.this.treeModel
/* 3910 */           .getChildCount(treePath.getLastPathComponent()) == 0)
/* 3911 */           BasicTreeUI.this.updateSize(); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void treeStructureChanged(TreeModelEvent param1TreeModelEvent) {
/* 3916 */       if (BasicTreeUI.this.treeState != null && param1TreeModelEvent != null) {
/* 3917 */         BasicTreeUI.this.treeState.treeStructureChanged(param1TreeModelEvent);
/*      */         
/* 3919 */         BasicTreeUI.this.updateLeadSelectionRow();
/*      */         
/* 3921 */         TreePath treePath = SwingUtilities2.getTreePath(param1TreeModelEvent, BasicTreeUI.this.getModel());
/*      */         
/* 3923 */         if (treePath != null) {
/* 3924 */           treePath = treePath.getParentPath();
/*      */         }
/* 3926 */         if (treePath == null || BasicTreeUI.this.treeState.isExpanded(treePath)) {
/* 3927 */           BasicTreeUI.this.updateSize();
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private Handler() {}
/*      */   }
/*      */ 
/*      */   
/*      */   private static class Actions
/*      */     extends UIAction
/*      */   {
/*      */     private static final String SELECT_PREVIOUS = "selectPrevious";
/*      */     
/*      */     private static final String SELECT_PREVIOUS_CHANGE_LEAD = "selectPreviousChangeLead";
/*      */     
/*      */     private static final String SELECT_PREVIOUS_EXTEND_SELECTION = "selectPreviousExtendSelection";
/*      */     
/*      */     private static final String SELECT_NEXT = "selectNext";
/*      */     
/*      */     private static final String SELECT_NEXT_CHANGE_LEAD = "selectNextChangeLead";
/*      */     
/*      */     private static final String SELECT_NEXT_EXTEND_SELECTION = "selectNextExtendSelection";
/*      */     
/*      */     private static final String SELECT_CHILD = "selectChild";
/*      */     
/*      */     private static final String SELECT_CHILD_CHANGE_LEAD = "selectChildChangeLead";
/*      */     
/*      */     private static final String SELECT_PARENT = "selectParent";
/*      */     
/*      */     private static final String SELECT_PARENT_CHANGE_LEAD = "selectParentChangeLead";
/*      */     
/*      */     private static final String SCROLL_UP_CHANGE_SELECTION = "scrollUpChangeSelection";
/*      */     
/*      */     private static final String SCROLL_UP_CHANGE_LEAD = "scrollUpChangeLead";
/*      */     
/*      */     private static final String SCROLL_UP_EXTEND_SELECTION = "scrollUpExtendSelection";
/*      */     
/*      */     private static final String SCROLL_DOWN_CHANGE_SELECTION = "scrollDownChangeSelection";
/*      */     
/*      */     private static final String SCROLL_DOWN_EXTEND_SELECTION = "scrollDownExtendSelection";
/*      */     
/*      */     private static final String SCROLL_DOWN_CHANGE_LEAD = "scrollDownChangeLead";
/*      */     
/*      */     private static final String SELECT_FIRST = "selectFirst";
/*      */     
/*      */     private static final String SELECT_FIRST_CHANGE_LEAD = "selectFirstChangeLead";
/*      */     
/*      */     private static final String SELECT_FIRST_EXTEND_SELECTION = "selectFirstExtendSelection";
/*      */     
/*      */     private static final String SELECT_LAST = "selectLast";
/*      */     
/*      */     private static final String SELECT_LAST_CHANGE_LEAD = "selectLastChangeLead";
/*      */     
/*      */     private static final String SELECT_LAST_EXTEND_SELECTION = "selectLastExtendSelection";
/*      */     
/*      */     private static final String TOGGLE = "toggle";
/*      */     
/*      */     private static final String CANCEL_EDITING = "cancel";
/*      */     
/*      */     private static final String START_EDITING = "startEditing";
/*      */     private static final String SELECT_ALL = "selectAll";
/*      */     private static final String CLEAR_SELECTION = "clearSelection";
/*      */     private static final String SCROLL_LEFT = "scrollLeft";
/*      */     private static final String SCROLL_RIGHT = "scrollRight";
/*      */     private static final String SCROLL_LEFT_EXTEND_SELECTION = "scrollLeftExtendSelection";
/*      */     private static final String SCROLL_RIGHT_EXTEND_SELECTION = "scrollRightExtendSelection";
/*      */     private static final String SCROLL_RIGHT_CHANGE_LEAD = "scrollRightChangeLead";
/*      */     private static final String SCROLL_LEFT_CHANGE_LEAD = "scrollLeftChangeLead";
/*      */     private static final String EXPAND = "expand";
/*      */     private static final String COLLAPSE = "collapse";
/*      */     private static final String MOVE_SELECTION_TO_PARENT = "moveSelectionToParent";
/*      */     private static final String ADD_TO_SELECTION = "addToSelection";
/*      */     private static final String TOGGLE_AND_ANCHOR = "toggleAndAnchor";
/*      */     private static final String EXTEND_TO = "extendTo";
/*      */     private static final String MOVE_SELECTION_TO = "moveSelectionTo";
/*      */     
/*      */     Actions() {
/* 4006 */       super(null);
/*      */     }
/*      */     
/*      */     Actions(String param1String) {
/* 4010 */       super(param1String);
/*      */     }
/*      */     
/*      */     public boolean isEnabled(Object param1Object) {
/* 4014 */       if (param1Object instanceof JTree && 
/* 4015 */         getName() == "cancel") {
/* 4016 */         return ((JTree)param1Object).isEditing();
/*      */       }
/*      */       
/* 4019 */       return true;
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 4023 */       JTree jTree = (JTree)param1ActionEvent.getSource();
/* 4024 */       BasicTreeUI basicTreeUI = (BasicTreeUI)BasicLookAndFeel.getUIOfType(jTree
/* 4025 */           .getUI(), BasicTreeUI.class);
/* 4026 */       if (basicTreeUI == null) {
/*      */         return;
/*      */       }
/* 4029 */       String str = getName();
/* 4030 */       if (str == "selectPrevious") {
/* 4031 */         increment(jTree, basicTreeUI, -1, false, true);
/*      */       }
/* 4033 */       else if (str == "selectPreviousChangeLead") {
/* 4034 */         increment(jTree, basicTreeUI, -1, false, false);
/*      */       }
/* 4036 */       else if (str == "selectPreviousExtendSelection") {
/* 4037 */         increment(jTree, basicTreeUI, -1, true, true);
/*      */       }
/* 4039 */       else if (str == "selectNext") {
/* 4040 */         increment(jTree, basicTreeUI, 1, false, true);
/*      */       }
/* 4042 */       else if (str == "selectNextChangeLead") {
/* 4043 */         increment(jTree, basicTreeUI, 1, false, false);
/*      */       }
/* 4045 */       else if (str == "selectNextExtendSelection") {
/* 4046 */         increment(jTree, basicTreeUI, 1, true, true);
/*      */       }
/* 4048 */       else if (str == "selectChild") {
/* 4049 */         traverse(jTree, basicTreeUI, 1, true);
/*      */       }
/* 4051 */       else if (str == "selectChildChangeLead") {
/* 4052 */         traverse(jTree, basicTreeUI, 1, false);
/*      */       }
/* 4054 */       else if (str == "selectParent") {
/* 4055 */         traverse(jTree, basicTreeUI, -1, true);
/*      */       }
/* 4057 */       else if (str == "selectParentChangeLead") {
/* 4058 */         traverse(jTree, basicTreeUI, -1, false);
/*      */       }
/* 4060 */       else if (str == "scrollUpChangeSelection") {
/* 4061 */         page(jTree, basicTreeUI, -1, false, true);
/*      */       }
/* 4063 */       else if (str == "scrollUpChangeLead") {
/* 4064 */         page(jTree, basicTreeUI, -1, false, false);
/*      */       }
/* 4066 */       else if (str == "scrollUpExtendSelection") {
/* 4067 */         page(jTree, basicTreeUI, -1, true, true);
/*      */       }
/* 4069 */       else if (str == "scrollDownChangeSelection") {
/* 4070 */         page(jTree, basicTreeUI, 1, false, true);
/*      */       }
/* 4072 */       else if (str == "scrollDownExtendSelection") {
/* 4073 */         page(jTree, basicTreeUI, 1, true, true);
/*      */       }
/* 4075 */       else if (str == "scrollDownChangeLead") {
/* 4076 */         page(jTree, basicTreeUI, 1, false, false);
/*      */       }
/* 4078 */       else if (str == "selectFirst") {
/* 4079 */         home(jTree, basicTreeUI, -1, false, true);
/*      */       }
/* 4081 */       else if (str == "selectFirstChangeLead") {
/* 4082 */         home(jTree, basicTreeUI, -1, false, false);
/*      */       }
/* 4084 */       else if (str == "selectFirstExtendSelection") {
/* 4085 */         home(jTree, basicTreeUI, -1, true, true);
/*      */       }
/* 4087 */       else if (str == "selectLast") {
/* 4088 */         home(jTree, basicTreeUI, 1, false, true);
/*      */       }
/* 4090 */       else if (str == "selectLastChangeLead") {
/* 4091 */         home(jTree, basicTreeUI, 1, false, false);
/*      */       }
/* 4093 */       else if (str == "selectLastExtendSelection") {
/* 4094 */         home(jTree, basicTreeUI, 1, true, true);
/*      */       }
/* 4096 */       else if (str == "toggle") {
/* 4097 */         toggle(jTree, basicTreeUI);
/*      */       }
/* 4099 */       else if (str == "cancel") {
/* 4100 */         cancelEditing(jTree, basicTreeUI);
/*      */       }
/* 4102 */       else if (str == "startEditing") {
/* 4103 */         startEditing(jTree, basicTreeUI);
/*      */       }
/* 4105 */       else if (str == "selectAll") {
/* 4106 */         selectAll(jTree, basicTreeUI, true);
/*      */       }
/* 4108 */       else if (str == "clearSelection") {
/* 4109 */         selectAll(jTree, basicTreeUI, false);
/*      */       }
/* 4111 */       else if (str == "addToSelection") {
/* 4112 */         if (basicTreeUI.getRowCount(jTree) > 0) {
/* 4113 */           int i = basicTreeUI.getLeadSelectionRow();
/* 4114 */           if (!jTree.isRowSelected(i)) {
/* 4115 */             TreePath treePath = basicTreeUI.getAnchorSelectionPath();
/* 4116 */             jTree.addSelectionRow(i);
/* 4117 */             basicTreeUI.setAnchorSelectionPath(treePath);
/*      */           }
/*      */         
/*      */         } 
/* 4121 */       } else if (str == "toggleAndAnchor") {
/* 4122 */         if (basicTreeUI.getRowCount(jTree) > 0) {
/* 4123 */           int i = basicTreeUI.getLeadSelectionRow();
/* 4124 */           TreePath treePath = basicTreeUI.getLeadSelectionPath();
/* 4125 */           if (!jTree.isRowSelected(i)) {
/* 4126 */             jTree.addSelectionRow(i);
/*      */           } else {
/* 4128 */             jTree.removeSelectionRow(i);
/* 4129 */             basicTreeUI.setLeadSelectionPath(treePath);
/*      */           } 
/* 4131 */           basicTreeUI.setAnchorSelectionPath(treePath);
/*      */         }
/*      */       
/* 4134 */       } else if (str == "extendTo") {
/* 4135 */         extendSelection(jTree, basicTreeUI);
/*      */       }
/* 4137 */       else if (str == "moveSelectionTo") {
/* 4138 */         if (basicTreeUI.getRowCount(jTree) > 0) {
/* 4139 */           int i = basicTreeUI.getLeadSelectionRow();
/* 4140 */           jTree.setSelectionInterval(i, i);
/*      */         }
/*      */       
/* 4143 */       } else if (str == "scrollLeft") {
/* 4144 */         scroll(jTree, basicTreeUI, 0, -10);
/*      */       }
/* 4146 */       else if (str == "scrollRight") {
/* 4147 */         scroll(jTree, basicTreeUI, 0, 10);
/*      */       }
/* 4149 */       else if (str == "scrollLeftExtendSelection") {
/* 4150 */         scrollChangeSelection(jTree, basicTreeUI, -1, true, true);
/*      */       }
/* 4152 */       else if (str == "scrollRightExtendSelection") {
/* 4153 */         scrollChangeSelection(jTree, basicTreeUI, 1, true, true);
/*      */       }
/* 4155 */       else if (str == "scrollRightChangeLead") {
/* 4156 */         scrollChangeSelection(jTree, basicTreeUI, 1, false, false);
/*      */       }
/* 4158 */       else if (str == "scrollLeftChangeLead") {
/* 4159 */         scrollChangeSelection(jTree, basicTreeUI, -1, false, false);
/*      */       }
/* 4161 */       else if (str == "expand") {
/* 4162 */         expand(jTree, basicTreeUI);
/*      */       }
/* 4164 */       else if (str == "collapse") {
/* 4165 */         collapse(jTree, basicTreeUI);
/*      */       }
/* 4167 */       else if (str == "moveSelectionToParent") {
/* 4168 */         moveSelectionToParent(jTree, basicTreeUI);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void scrollChangeSelection(JTree param1JTree, BasicTreeUI param1BasicTreeUI, int param1Int, boolean param1Boolean1, boolean param1Boolean2) {
/*      */       int i;
/* 4177 */       if ((i = param1BasicTreeUI.getRowCount(param1JTree)) > 0 && param1BasicTreeUI.treeSelectionModel != null) {
/*      */         TreePath treePath;
/*      */         
/* 4180 */         Rectangle rectangle = param1JTree.getVisibleRect();
/*      */         
/* 4182 */         if (param1Int == -1) {
/* 4183 */           treePath = param1BasicTreeUI.getClosestPathForLocation(param1JTree, rectangle.x, rectangle.y);
/*      */           
/* 4185 */           rectangle.x = Math.max(0, rectangle.x - rectangle.width);
/*      */         } else {
/*      */           
/* 4188 */           rectangle.x = Math.min(Math.max(0, param1JTree.getWidth() - rectangle.width), rectangle.x + rectangle.width);
/*      */           
/* 4190 */           treePath = param1BasicTreeUI.getClosestPathForLocation(param1JTree, rectangle.x, rectangle.y + rectangle.height);
/*      */         } 
/*      */ 
/*      */         
/* 4194 */         param1JTree.scrollRectToVisible(rectangle);
/*      */         
/* 4196 */         if (param1Boolean1) {
/* 4197 */           param1BasicTreeUI.extendSelection(treePath);
/*      */         }
/* 4199 */         else if (param1Boolean2) {
/* 4200 */           param1JTree.setSelectionPath(treePath);
/*      */         } else {
/*      */           
/* 4203 */           param1BasicTreeUI.setLeadSelectionPath(treePath, true);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void scroll(JTree param1JTree, BasicTreeUI param1BasicTreeUI, int param1Int1, int param1Int2) {
/* 4210 */       Rectangle rectangle = param1JTree.getVisibleRect();
/* 4211 */       Dimension dimension = param1JTree.getSize();
/* 4212 */       if (param1Int1 == 0) {
/* 4213 */         rectangle.x += param1Int2;
/* 4214 */         rectangle.x = Math.max(0, rectangle.x);
/* 4215 */         rectangle.x = Math.min(Math.max(0, dimension.width - rectangle.width), rectangle.x);
/*      */       }
/*      */       else {
/*      */         
/* 4219 */         rectangle.y += param1Int2;
/* 4220 */         rectangle.y = Math.max(0, rectangle.y);
/* 4221 */         rectangle.y = Math.min(Math.max(0, dimension.width - rectangle.height), rectangle.y);
/*      */       } 
/*      */       
/* 4224 */       param1JTree.scrollRectToVisible(rectangle);
/*      */     }
/*      */     
/*      */     private void extendSelection(JTree param1JTree, BasicTreeUI param1BasicTreeUI) {
/* 4228 */       if (param1BasicTreeUI.getRowCount(param1JTree) > 0) {
/* 4229 */         int i = param1BasicTreeUI.getLeadSelectionRow();
/*      */         
/* 4231 */         if (i != -1) {
/* 4232 */           TreePath treePath1 = param1BasicTreeUI.getLeadSelectionPath();
/* 4233 */           TreePath treePath2 = param1BasicTreeUI.getAnchorSelectionPath();
/* 4234 */           int j = param1BasicTreeUI.getRowForPath(param1JTree, treePath2);
/*      */           
/* 4236 */           if (j == -1)
/* 4237 */             j = 0; 
/* 4238 */           param1JTree.setSelectionInterval(j, i);
/* 4239 */           param1BasicTreeUI.setLeadSelectionPath(treePath1);
/* 4240 */           param1BasicTreeUI.setAnchorSelectionPath(treePath2);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     private void selectAll(JTree param1JTree, BasicTreeUI param1BasicTreeUI, boolean param1Boolean) {
/* 4246 */       int i = param1BasicTreeUI.getRowCount(param1JTree);
/*      */       
/* 4248 */       if (i > 0) {
/* 4249 */         if (param1Boolean) {
/* 4250 */           if (param1JTree.getSelectionModel().getSelectionMode() == 1) {
/*      */ 
/*      */             
/* 4253 */             int j = param1BasicTreeUI.getLeadSelectionRow();
/* 4254 */             if (j != -1) {
/* 4255 */               param1JTree.setSelectionRow(j);
/* 4256 */             } else if (param1JTree.getMinSelectionRow() == -1) {
/* 4257 */               param1JTree.setSelectionRow(0);
/* 4258 */               param1BasicTreeUI.ensureRowsAreVisible(0, 0);
/*      */             } 
/*      */             
/*      */             return;
/*      */           } 
/* 4263 */           TreePath treePath1 = param1BasicTreeUI.getLeadSelectionPath();
/* 4264 */           TreePath treePath2 = param1BasicTreeUI.getAnchorSelectionPath();
/*      */           
/* 4266 */           if (treePath1 != null && !param1JTree.isVisible(treePath1)) {
/* 4267 */             treePath1 = null;
/*      */           }
/* 4269 */           param1JTree.setSelectionInterval(0, i - 1);
/* 4270 */           if (treePath1 != null) {
/* 4271 */             param1BasicTreeUI.setLeadSelectionPath(treePath1);
/*      */           }
/* 4273 */           if (treePath2 != null && param1JTree.isVisible(treePath2)) {
/* 4274 */             param1BasicTreeUI.setAnchorSelectionPath(treePath2);
/*      */           }
/*      */         } else {
/*      */           
/* 4278 */           TreePath treePath1 = param1BasicTreeUI.getLeadSelectionPath();
/* 4279 */           TreePath treePath2 = param1BasicTreeUI.getAnchorSelectionPath();
/*      */           
/* 4281 */           param1JTree.clearSelection();
/* 4282 */           param1BasicTreeUI.setAnchorSelectionPath(treePath2);
/* 4283 */           param1BasicTreeUI.setLeadSelectionPath(treePath1);
/*      */         } 
/*      */       }
/*      */     }
/*      */     
/*      */     private void startEditing(JTree param1JTree, BasicTreeUI param1BasicTreeUI) {
/* 4289 */       TreePath treePath = param1BasicTreeUI.getLeadSelectionPath();
/*      */       
/* 4291 */       boolean bool = (treePath != null) ? param1BasicTreeUI.getRowForPath(param1JTree, treePath) : true;
/*      */       
/* 4293 */       if (bool != -1) {
/* 4294 */         param1JTree.startEditingAtPath(treePath);
/*      */       }
/*      */     }
/*      */     
/*      */     private void cancelEditing(JTree param1JTree, BasicTreeUI param1BasicTreeUI) {
/* 4299 */       param1JTree.cancelEditing();
/*      */     }
/*      */     
/*      */     private void toggle(JTree param1JTree, BasicTreeUI param1BasicTreeUI) {
/* 4303 */       int i = param1BasicTreeUI.getLeadSelectionRow();
/*      */       
/* 4305 */       if (i != -1 && !param1BasicTreeUI.isLeaf(i)) {
/* 4306 */         TreePath treePath1 = param1BasicTreeUI.getAnchorSelectionPath();
/* 4307 */         TreePath treePath2 = param1BasicTreeUI.getLeadSelectionPath();
/*      */         
/* 4309 */         param1BasicTreeUI.toggleExpandState(param1BasicTreeUI.getPathForRow(param1JTree, i));
/* 4310 */         param1BasicTreeUI.setAnchorSelectionPath(treePath1);
/* 4311 */         param1BasicTreeUI.setLeadSelectionPath(treePath2);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void expand(JTree param1JTree, BasicTreeUI param1BasicTreeUI) {
/* 4316 */       int i = param1BasicTreeUI.getLeadSelectionRow();
/* 4317 */       param1JTree.expandRow(i);
/*      */     }
/*      */     
/*      */     private void collapse(JTree param1JTree, BasicTreeUI param1BasicTreeUI) {
/* 4321 */       int i = param1BasicTreeUI.getLeadSelectionRow();
/* 4322 */       param1JTree.collapseRow(i);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void increment(JTree param1JTree, BasicTreeUI param1BasicTreeUI, int param1Int, boolean param1Boolean1, boolean param1Boolean2) {
/* 4330 */       if (!param1Boolean1 && !param1Boolean2 && param1JTree
/* 4331 */         .getSelectionModel().getSelectionMode() != 4)
/*      */       {
/* 4333 */         param1Boolean2 = true;
/*      */       }
/*      */       
/*      */       int i;
/*      */       
/* 4338 */       if (param1BasicTreeUI.treeSelectionModel != null && (
/* 4339 */         i = param1JTree.getRowCount()) > 0) {
/* 4340 */         int k, j = param1BasicTreeUI.getLeadSelectionRow();
/*      */ 
/*      */         
/* 4343 */         if (j == -1) {
/* 4344 */           if (param1Int == 1) {
/* 4345 */             k = 0;
/*      */           } else {
/* 4347 */             k = i - 1;
/*      */           } 
/*      */         } else {
/*      */           
/* 4351 */           k = Math.min(i - 1, 
/* 4352 */               Math.max(0, j + param1Int));
/* 4353 */         }  if (param1Boolean1 && param1BasicTreeUI.treeSelectionModel
/* 4354 */           .getSelectionMode() != 1) {
/*      */           
/* 4356 */           param1BasicTreeUI.extendSelection(param1JTree.getPathForRow(k));
/*      */         }
/* 4358 */         else if (param1Boolean2) {
/* 4359 */           param1JTree.setSelectionInterval(k, k);
/*      */         } else {
/*      */           
/* 4362 */           param1BasicTreeUI.setLeadSelectionPath(param1JTree.getPathForRow(k), true);
/*      */         } 
/* 4364 */         param1BasicTreeUI.ensureRowsAreVisible(k, k);
/* 4365 */         param1BasicTreeUI.lastSelectedRow = k;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void traverse(JTree param1JTree, BasicTreeUI param1BasicTreeUI, int param1Int, boolean param1Boolean) {
/* 4373 */       if (!param1Boolean && param1JTree
/* 4374 */         .getSelectionModel().getSelectionMode() != 4)
/*      */       {
/* 4376 */         param1Boolean = true;
/*      */       }
/*      */       
/*      */       int i;
/*      */       
/* 4381 */       if ((i = param1JTree.getRowCount()) > 0) {
/* 4382 */         byte b; int j = param1BasicTreeUI.getLeadSelectionRow();
/*      */ 
/*      */         
/* 4385 */         if (j == -1) {
/* 4386 */           b = 0;
/*      */ 
/*      */         
/*      */         }
/* 4390 */         else if (param1Int == 1) {
/* 4391 */           TreePath treePath = param1BasicTreeUI.getPathForRow(param1JTree, j);
/*      */           
/* 4393 */           int k = param1JTree.getModel().getChildCount(treePath.getLastPathComponent());
/* 4394 */           b = -1;
/* 4395 */           if (!param1BasicTreeUI.isLeaf(j)) {
/* 4396 */             if (!param1JTree.isExpanded(j)) {
/* 4397 */               param1BasicTreeUI.toggleExpandState(treePath);
/*      */             }
/* 4399 */             else if (k > 0) {
/* 4400 */               b = Math.min(j + 1, i - 1);
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */         }
/* 4406 */         else if (!param1BasicTreeUI.isLeaf(j) && param1JTree
/* 4407 */           .isExpanded(j)) {
/* 4408 */           param1BasicTreeUI.toggleExpandState(param1BasicTreeUI
/* 4409 */               .getPathForRow(param1JTree, j));
/* 4410 */           b = -1;
/*      */         } else {
/*      */           
/* 4413 */           TreePath treePath = param1BasicTreeUI.getPathForRow(param1JTree, j);
/*      */ 
/*      */           
/* 4416 */           if (treePath != null && treePath.getPathCount() > 1) {
/* 4417 */             b = param1BasicTreeUI.getRowForPath(param1JTree, treePath
/* 4418 */                 .getParentPath());
/*      */           } else {
/*      */             
/* 4421 */             b = -1;
/*      */           } 
/*      */         } 
/*      */         
/* 4425 */         if (b != -1) {
/* 4426 */           if (param1Boolean) {
/* 4427 */             param1JTree.setSelectionInterval(b, b);
/*      */           } else {
/*      */             
/* 4430 */             param1BasicTreeUI.setLeadSelectionPath(param1BasicTreeUI.getPathForRow(param1JTree, b), true);
/*      */           } 
/*      */           
/* 4433 */           param1BasicTreeUI.ensureRowsAreVisible(b, b);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     private void moveSelectionToParent(JTree param1JTree, BasicTreeUI param1BasicTreeUI) {
/* 4439 */       int i = param1BasicTreeUI.getLeadSelectionRow();
/* 4440 */       TreePath treePath = param1BasicTreeUI.getPathForRow(param1JTree, i);
/* 4441 */       if (treePath != null && treePath.getPathCount() > 1) {
/* 4442 */         int j = param1BasicTreeUI.getRowForPath(param1JTree, treePath.getParentPath());
/* 4443 */         if (j != -1) {
/* 4444 */           param1JTree.setSelectionInterval(j, j);
/* 4445 */           param1BasicTreeUI.ensureRowsAreVisible(j, j);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void page(JTree param1JTree, BasicTreeUI param1BasicTreeUI, int param1Int, boolean param1Boolean1, boolean param1Boolean2) {
/* 4454 */       if (!param1Boolean1 && !param1Boolean2 && param1JTree
/* 4455 */         .getSelectionModel().getSelectionMode() != 4)
/*      */       {
/* 4457 */         param1Boolean2 = true;
/*      */       }
/*      */       
/*      */       int i;
/*      */       
/* 4462 */       if ((i = param1BasicTreeUI.getRowCount(param1JTree)) > 0 && param1BasicTreeUI.treeSelectionModel != null) {
/*      */         TreePath treePath2;
/* 4464 */         Dimension dimension = param1JTree.getSize();
/* 4465 */         TreePath treePath1 = param1BasicTreeUI.getLeadSelectionPath();
/*      */         
/* 4467 */         Rectangle rectangle1 = param1JTree.getVisibleRect();
/*      */         
/* 4469 */         if (param1Int == -1) {
/*      */           
/* 4471 */           treePath2 = param1BasicTreeUI.getClosestPathForLocation(param1JTree, rectangle1.x, rectangle1.y);
/*      */           
/* 4473 */           if (treePath2.equals(treePath1)) {
/* 4474 */             rectangle1.y = Math.max(0, rectangle1.y - rectangle1.height);
/* 4475 */             treePath2 = param1JTree.getClosestPathForLocation(rectangle1.x, rectangle1.y);
/*      */           }
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 4481 */           rectangle1.y = Math.min(dimension.height, rectangle1.y + rectangle1.height - 1);
/*      */           
/* 4483 */           treePath2 = param1JTree.getClosestPathForLocation(rectangle1.x, rectangle1.y);
/*      */           
/* 4485 */           if (treePath2.equals(treePath1)) {
/* 4486 */             rectangle1.y = Math.min(dimension.height, rectangle1.y + rectangle1.height - 1);
/*      */             
/* 4488 */             treePath2 = param1JTree.getClosestPathForLocation(rectangle1.x, rectangle1.y);
/*      */           } 
/*      */         } 
/*      */         
/* 4492 */         Rectangle rectangle2 = param1BasicTreeUI.getPathBounds(param1JTree, treePath2);
/* 4493 */         if (rectangle2 != null) {
/* 4494 */           rectangle2.x = rectangle1.x;
/* 4495 */           rectangle2.width = rectangle1.width;
/* 4496 */           if (param1Int == -1) {
/* 4497 */             rectangle2.height = rectangle1.height;
/*      */           } else {
/*      */             
/* 4500 */             rectangle2.y -= rectangle1.height - rectangle2.height;
/* 4501 */             rectangle2.height = rectangle1.height;
/*      */           } 
/*      */           
/* 4504 */           if (param1Boolean1) {
/* 4505 */             param1BasicTreeUI.extendSelection(treePath2);
/*      */           }
/* 4507 */           else if (param1Boolean2) {
/* 4508 */             param1JTree.setSelectionPath(treePath2);
/*      */           } else {
/*      */             
/* 4511 */             param1BasicTreeUI.setLeadSelectionPath(treePath2, true);
/*      */           } 
/* 4513 */           param1JTree.scrollRectToVisible(rectangle2);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void home(JTree param1JTree, final BasicTreeUI ui, int param1Int, boolean param1Boolean1, boolean param1Boolean2) {
/* 4522 */       if (!param1Boolean1 && !param1Boolean2 && param1JTree
/* 4523 */         .getSelectionModel().getSelectionMode() != 4)
/*      */       {
/* 4525 */         param1Boolean2 = true;
/*      */       }
/*      */       
/* 4528 */       final int rowCount = ui.getRowCount(param1JTree);
/*      */       
/* 4530 */       if (i > 0)
/* 4531 */         if (param1Int == -1) {
/* 4532 */           ui.ensureRowsAreVisible(0, 0);
/* 4533 */           if (param1Boolean1) {
/* 4534 */             TreePath treePath = ui.getAnchorSelectionPath();
/*      */             
/* 4536 */             boolean bool = (treePath == null) ? true : ui.getRowForPath(param1JTree, treePath);
/*      */             
/* 4538 */             if (bool == -1) {
/* 4539 */               param1JTree.setSelectionInterval(0, 0);
/*      */             } else {
/*      */               
/* 4542 */               param1JTree.setSelectionInterval(0, bool);
/* 4543 */               ui.setAnchorSelectionPath(treePath);
/* 4544 */               ui.setLeadSelectionPath(ui.getPathForRow(param1JTree, 0));
/*      */             }
/*      */           
/* 4547 */           } else if (param1Boolean2) {
/* 4548 */             param1JTree.setSelectionInterval(0, 0);
/*      */           } else {
/*      */             
/* 4551 */             ui.setLeadSelectionPath(ui.getPathForRow(param1JTree, 0), true);
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 4556 */           ui.ensureRowsAreVisible(i - 1, i - 1);
/* 4557 */           if (param1Boolean1) {
/* 4558 */             TreePath treePath = ui.getAnchorSelectionPath();
/*      */             
/* 4560 */             boolean bool = (treePath == null) ? true : ui.getRowForPath(param1JTree, treePath);
/*      */             
/* 4562 */             if (bool == -1) {
/* 4563 */               param1JTree.setSelectionInterval(i - 1, i - 1);
/*      */             }
/*      */             else {
/*      */               
/* 4567 */               param1JTree.setSelectionInterval(bool, i - 1);
/* 4568 */               ui.setAnchorSelectionPath(treePath);
/* 4569 */               ui.setLeadSelectionPath(ui.getPathForRow(param1JTree, i - 1));
/*      */             }
/*      */           
/*      */           }
/* 4573 */           else if (param1Boolean2) {
/* 4574 */             param1JTree.setSelectionInterval(i - 1, i - 1);
/*      */           } else {
/*      */             
/* 4577 */             ui.setLeadSelectionPath(ui.getPathForRow(param1JTree, i - 1), true);
/*      */           } 
/*      */           
/* 4580 */           if (ui.isLargeModel())
/* 4581 */             SwingUtilities.invokeLater(new Runnable() {
/*      */                   public void run() {
/* 4583 */                     ui.ensureRowsAreVisible(rowCount - 1, rowCount - 1);
/*      */                   }
/*      */                 }); 
/*      */         }  
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicTreeUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */