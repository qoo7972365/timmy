/*      */ package javax.swing.tree;
/*      */ 
/*      */ import java.awt.Rectangle;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Stack;
/*      */ import javax.swing.event.TreeModelEvent;
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
/*      */ public class FixedHeightLayoutCache
/*      */   extends AbstractLayoutCache
/*      */ {
/*      */   private FHTreeStateNode root;
/*      */   private int rowCount;
/*      */   private Rectangle boundsBuffer;
/*      */   private Hashtable<TreePath, FHTreeStateNode> treePathMapping;
/*      */   private SearchInfo info;
/*      */   private Stack<Stack<TreePath>> tempStacks;
/*      */   
/*      */   public FixedHeightLayoutCache() {
/*   80 */     this.tempStacks = new Stack<>();
/*   81 */     this.boundsBuffer = new Rectangle();
/*   82 */     this.treePathMapping = new Hashtable<>();
/*   83 */     this.info = new SearchInfo();
/*   84 */     setRowHeight(1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setModel(TreeModel paramTreeModel) {
/*   93 */     super.setModel(paramTreeModel);
/*   94 */     rebuild(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRootVisible(boolean paramBoolean) {
/*  105 */     if (isRootVisible() != paramBoolean) {
/*  106 */       super.setRootVisible(paramBoolean);
/*  107 */       if (this.root != null) {
/*  108 */         if (paramBoolean) {
/*  109 */           this.rowCount++;
/*  110 */           this.root.adjustRowBy(1);
/*      */         } else {
/*      */           
/*  113 */           this.rowCount--;
/*  114 */           this.root.adjustRowBy(-1);
/*      */         } 
/*  116 */         visibleNodesChanged();
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
/*      */   public void setRowHeight(int paramInt) {
/*  128 */     if (paramInt <= 0)
/*  129 */       throw new IllegalArgumentException("FixedHeightLayoutCache only supports row heights greater than 0"); 
/*  130 */     if (getRowHeight() != paramInt) {
/*  131 */       super.setRowHeight(paramInt);
/*  132 */       visibleNodesChanged();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRowCount() {
/*  140 */     return this.rowCount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void invalidatePathBounds(TreePath paramTreePath) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void invalidateSizes() {
/*  158 */     visibleNodesChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isExpanded(TreePath paramTreePath) {
/*  165 */     if (paramTreePath != null) {
/*  166 */       FHTreeStateNode fHTreeStateNode = getNodeForPath(paramTreePath, true, false);
/*      */       
/*  168 */       return (fHTreeStateNode != null && fHTreeStateNode.isExpanded());
/*      */     } 
/*  170 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle getBounds(TreePath paramTreePath, Rectangle paramRectangle) {
/*  181 */     if (paramTreePath == null) {
/*  182 */       return null;
/*      */     }
/*  184 */     FHTreeStateNode fHTreeStateNode = getNodeForPath(paramTreePath, true, false);
/*      */     
/*  186 */     if (fHTreeStateNode != null) {
/*  187 */       return getBounds(fHTreeStateNode, -1, paramRectangle);
/*      */     }
/*      */     
/*  190 */     TreePath treePath = paramTreePath.getParentPath();
/*      */     
/*  192 */     fHTreeStateNode = getNodeForPath(treePath, true, false);
/*  193 */     if (fHTreeStateNode != null && fHTreeStateNode.isExpanded()) {
/*      */       
/*  195 */       int i = this.treeModel.getIndexOfChild(treePath.getLastPathComponent(), paramTreePath
/*  196 */           .getLastPathComponent());
/*      */       
/*  198 */       if (i != -1)
/*  199 */         return getBounds(fHTreeStateNode, i, paramRectangle); 
/*      */     } 
/*  201 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TreePath getPathForRow(int paramInt) {
/*  209 */     if (paramInt >= 0 && paramInt < getRowCount() && 
/*  210 */       this.root.getPathForRow(paramInt, getRowCount(), this.info)) {
/*  211 */       return this.info.getPath();
/*      */     }
/*      */     
/*  214 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRowForPath(TreePath paramTreePath) {
/*  223 */     if (paramTreePath == null || this.root == null) {
/*  224 */       return -1;
/*      */     }
/*  226 */     FHTreeStateNode fHTreeStateNode = getNodeForPath(paramTreePath, true, false);
/*      */     
/*  228 */     if (fHTreeStateNode != null) {
/*  229 */       return fHTreeStateNode.getRow();
/*      */     }
/*  231 */     TreePath treePath = paramTreePath.getParentPath();
/*      */     
/*  233 */     fHTreeStateNode = getNodeForPath(treePath, true, false);
/*  234 */     if (fHTreeStateNode != null && fHTreeStateNode.isExpanded()) {
/*  235 */       return fHTreeStateNode.getRowToModelIndex(this.treeModel
/*  236 */           .getIndexOfChild(treePath.getLastPathComponent(), paramTreePath
/*  237 */             .getLastPathComponent()));
/*      */     }
/*  239 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TreePath getPathClosestTo(int paramInt1, int paramInt2) {
/*  250 */     if (getRowCount() == 0) {
/*  251 */       return null;
/*      */     }
/*  253 */     int i = getRowContainingYLocation(paramInt2);
/*      */     
/*  255 */     return getPathForRow(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getVisibleChildCount(TreePath paramTreePath) {
/*  262 */     FHTreeStateNode fHTreeStateNode = getNodeForPath(paramTreePath, true, false);
/*      */     
/*  264 */     if (fHTreeStateNode == null)
/*  265 */       return 0; 
/*  266 */     return fHTreeStateNode.getTotalChildCount();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Enumeration<TreePath> getVisiblePathsFrom(TreePath paramTreePath) {
/*  275 */     if (paramTreePath == null) {
/*  276 */       return null;
/*      */     }
/*  278 */     FHTreeStateNode fHTreeStateNode = getNodeForPath(paramTreePath, true, false);
/*      */     
/*  280 */     if (fHTreeStateNode != null) {
/*  281 */       return new VisibleFHTreeStateNodeEnumeration(fHTreeStateNode);
/*      */     }
/*  283 */     TreePath treePath = paramTreePath.getParentPath();
/*      */     
/*  285 */     fHTreeStateNode = getNodeForPath(treePath, true, false);
/*  286 */     if (fHTreeStateNode != null && fHTreeStateNode.isExpanded()) {
/*  287 */       return new VisibleFHTreeStateNodeEnumeration(fHTreeStateNode, this.treeModel
/*  288 */           .getIndexOfChild(treePath.getLastPathComponent(), paramTreePath
/*  289 */             .getLastPathComponent()));
/*      */     }
/*  291 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setExpandedState(TreePath paramTreePath, boolean paramBoolean) {
/*  299 */     if (paramBoolean) {
/*  300 */       ensurePathIsExpanded(paramTreePath, true);
/*  301 */     } else if (paramTreePath != null) {
/*  302 */       TreePath treePath = paramTreePath.getParentPath();
/*      */ 
/*      */       
/*  305 */       if (treePath != null) {
/*  306 */         FHTreeStateNode fHTreeStateNode1 = getNodeForPath(treePath, false, true);
/*      */         
/*  308 */         if (fHTreeStateNode1 != null) {
/*  309 */           fHTreeStateNode1.makeVisible();
/*      */         }
/*      */       } 
/*  312 */       FHTreeStateNode fHTreeStateNode = getNodeForPath(paramTreePath, true, false);
/*      */ 
/*      */       
/*  315 */       if (fHTreeStateNode != null) {
/*  316 */         fHTreeStateNode.collapse(true);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getExpandedState(TreePath paramTreePath) {
/*  324 */     FHTreeStateNode fHTreeStateNode = getNodeForPath(paramTreePath, true, false);
/*      */     
/*  326 */     return (fHTreeStateNode != null) ? ((fHTreeStateNode.isVisible() && fHTreeStateNode.isExpanded())) : false;
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
/*      */   public void treeNodesChanged(TreeModelEvent paramTreeModelEvent) {
/*  347 */     if (paramTreeModelEvent != null) {
/*      */ 
/*      */       
/*  350 */       FHTreeStateNode fHTreeStateNode = getNodeForPath(SwingUtilities2.getTreePath(paramTreeModelEvent, getModel()), false, false);
/*      */ 
/*      */       
/*  353 */       int[] arrayOfInt = paramTreeModelEvent.getChildIndices();
/*      */ 
/*      */ 
/*      */       
/*  357 */       if (fHTreeStateNode != null) {
/*  358 */         int i; if (arrayOfInt != null && (i = arrayOfInt.length) > 0) {
/*      */           
/*  360 */           Object object = fHTreeStateNode.getUserObject();
/*      */           
/*  362 */           for (byte b = 0; b < i; b++) {
/*      */             
/*  364 */             FHTreeStateNode fHTreeStateNode1 = fHTreeStateNode.getChildAtModelIndex(arrayOfInt[b]);
/*      */             
/*  366 */             if (fHTreeStateNode1 != null) {
/*  367 */               fHTreeStateNode1.setUserObject(this.treeModel.getChild(object, arrayOfInt[b]));
/*      */             }
/*      */           } 
/*      */           
/*  371 */           if (fHTreeStateNode.isVisible() && fHTreeStateNode.isExpanded()) {
/*  372 */             visibleNodesChanged();
/*      */           }
/*      */         }
/*  375 */         else if (fHTreeStateNode == this.root && fHTreeStateNode.isVisible() && fHTreeStateNode
/*  376 */           .isExpanded()) {
/*  377 */           visibleNodesChanged();
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
/*      */   public void treeNodesInserted(TreeModelEvent paramTreeModelEvent) {
/*  391 */     if (paramTreeModelEvent != null) {
/*      */ 
/*      */       
/*  394 */       FHTreeStateNode fHTreeStateNode = getNodeForPath(SwingUtilities2.getTreePath(paramTreeModelEvent, getModel()), false, false);
/*      */ 
/*      */       
/*  397 */       int[] arrayOfInt = paramTreeModelEvent.getChildIndices();
/*      */       
/*      */       int i;
/*      */       
/*  401 */       if (fHTreeStateNode != null && arrayOfInt != null && (i = arrayOfInt.length) > 0) {
/*      */ 
/*      */ 
/*      */         
/*  405 */         boolean bool = (fHTreeStateNode.isVisible() && fHTreeStateNode.isExpanded()) ? true : false;
/*      */         
/*  407 */         for (byte b = 0; b < i; b++) {
/*  408 */           fHTreeStateNode
/*  409 */             .childInsertedAtModelIndex(arrayOfInt[b], bool);
/*      */         }
/*  411 */         if (bool && this.treeSelectionModel != null)
/*  412 */           this.treeSelectionModel.resetRowSelection(); 
/*  413 */         if (fHTreeStateNode.isVisible()) {
/*  414 */           visibleNodesChanged();
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
/*      */   public void treeNodesRemoved(TreeModelEvent paramTreeModelEvent) {
/*  430 */     if (paramTreeModelEvent != null) {
/*      */ 
/*      */       
/*  433 */       TreePath treePath = SwingUtilities2.getTreePath(paramTreeModelEvent, getModel());
/*      */       
/*  435 */       FHTreeStateNode fHTreeStateNode = getNodeForPath(treePath, false, false);
/*      */       
/*  437 */       int[] arrayOfInt = paramTreeModelEvent.getChildIndices();
/*      */       
/*      */       int i;
/*  440 */       if (fHTreeStateNode != null && arrayOfInt != null && (i = arrayOfInt.length) > 0) {
/*      */         
/*  442 */         Object[] arrayOfObject = paramTreeModelEvent.getChildren();
/*      */ 
/*      */         
/*  445 */         boolean bool = (fHTreeStateNode.isVisible() && fHTreeStateNode.isExpanded()) ? true : false;
/*      */         
/*  447 */         for (int j = i - 1; j >= 0; j--) {
/*  448 */           fHTreeStateNode
/*  449 */             .removeChildAtModelIndex(arrayOfInt[j], bool);
/*      */         }
/*  451 */         if (bool) {
/*  452 */           if (this.treeSelectionModel != null)
/*  453 */             this.treeSelectionModel.resetRowSelection(); 
/*  454 */           if (this.treeModel.getChildCount(fHTreeStateNode
/*  455 */               .getUserObject()) == 0 && fHTreeStateNode
/*  456 */             .isLeaf())
/*      */           {
/*  458 */             fHTreeStateNode.collapse(false);
/*      */           }
/*  460 */           visibleNodesChanged();
/*      */         }
/*  462 */         else if (fHTreeStateNode.isVisible()) {
/*  463 */           visibleNodesChanged();
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
/*      */   public void treeStructureChanged(TreeModelEvent paramTreeModelEvent) {
/*  478 */     if (paramTreeModelEvent != null) {
/*  479 */       TreePath treePath = SwingUtilities2.getTreePath(paramTreeModelEvent, getModel());
/*      */       
/*  481 */       FHTreeStateNode fHTreeStateNode = getNodeForPath(treePath, false, false);
/*      */ 
/*      */ 
/*      */       
/*  485 */       if (fHTreeStateNode == this.root || (fHTreeStateNode == null && ((treePath == null && this.treeModel != null && this.treeModel
/*      */ 
/*      */         
/*  488 */         .getRoot() == null) || (treePath != null && treePath
/*  489 */         .getPathCount() <= 1)))) {
/*  490 */         rebuild(true);
/*      */       }
/*  492 */       else if (fHTreeStateNode != null) {
/*      */ 
/*      */         
/*  495 */         FHTreeStateNode fHTreeStateNode1 = (FHTreeStateNode)fHTreeStateNode.getParent();
/*      */         
/*  497 */         boolean bool1 = fHTreeStateNode.isExpanded();
/*  498 */         boolean bool2 = fHTreeStateNode.isVisible();
/*      */         
/*  500 */         int i = fHTreeStateNode1.getIndex(fHTreeStateNode);
/*  501 */         fHTreeStateNode.collapse(false);
/*  502 */         fHTreeStateNode1.remove(i);
/*      */         
/*  504 */         if (bool2 && bool1) {
/*  505 */           int j = fHTreeStateNode.getRow();
/*  506 */           fHTreeStateNode1.resetChildrenRowsFrom(j, i, fHTreeStateNode
/*  507 */               .getChildIndex());
/*  508 */           fHTreeStateNode = getNodeForPath(treePath, false, true);
/*  509 */           fHTreeStateNode.expand();
/*      */         } 
/*  511 */         if (this.treeSelectionModel != null && bool2 && bool1)
/*  512 */           this.treeSelectionModel.resetRowSelection(); 
/*  513 */         if (bool2) {
/*  514 */           visibleNodesChanged();
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
/*      */   private void visibleNodesChanged() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Rectangle getBounds(FHTreeStateNode paramFHTreeStateNode, int paramInt, Rectangle paramRectangle) {
/*      */     boolean bool;
/*      */     int i, j;
/*      */     Object object;
/*  539 */     if (paramInt == -1) {
/*      */       
/*  541 */       j = paramFHTreeStateNode.getRow();
/*  542 */       object = paramFHTreeStateNode.getUserObject();
/*  543 */       bool = paramFHTreeStateNode.isExpanded();
/*  544 */       i = paramFHTreeStateNode.getLevel();
/*      */     } else {
/*      */       
/*  547 */       j = paramFHTreeStateNode.getRowToModelIndex(paramInt);
/*  548 */       object = this.treeModel.getChild(paramFHTreeStateNode.getUserObject(), paramInt);
/*  549 */       bool = false;
/*  550 */       i = paramFHTreeStateNode.getLevel() + 1;
/*      */     } 
/*      */     
/*  553 */     Rectangle rectangle = getNodeDimensions(object, j, i, bool, this.boundsBuffer);
/*      */ 
/*      */     
/*  556 */     if (rectangle == null) {
/*  557 */       return null;
/*      */     }
/*  559 */     if (paramRectangle == null) {
/*  560 */       paramRectangle = new Rectangle();
/*      */     }
/*  562 */     paramRectangle.x = rectangle.x;
/*  563 */     paramRectangle.height = getRowHeight();
/*  564 */     paramRectangle.y = j * paramRectangle.height;
/*  565 */     paramRectangle.width = rectangle.width;
/*  566 */     return paramRectangle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void adjustRowCountBy(int paramInt) {
/*  574 */     this.rowCount += paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addMapping(FHTreeStateNode paramFHTreeStateNode) {
/*  581 */     this.treePathMapping.put(paramFHTreeStateNode.getTreePath(), paramFHTreeStateNode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void removeMapping(FHTreeStateNode paramFHTreeStateNode) {
/*  588 */     this.treePathMapping.remove(paramFHTreeStateNode.getTreePath());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FHTreeStateNode getMapping(TreePath paramTreePath) {
/*  596 */     return this.treePathMapping.get(paramTreePath);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void rebuild(boolean paramBoolean) {
/*  605 */     this.treePathMapping.clear(); Object object;
/*  606 */     if (this.treeModel != null && (object = this.treeModel.getRoot()) != null) {
/*  607 */       this.root = createNodeForValue(object, 0);
/*  608 */       this.root.path = new TreePath(object);
/*  609 */       addMapping(this.root);
/*  610 */       if (isRootVisible()) {
/*  611 */         this.rowCount = 1;
/*  612 */         this.root.row = 0;
/*      */       } else {
/*      */         
/*  615 */         this.rowCount = 0;
/*  616 */         this.root.row = -1;
/*      */       } 
/*  618 */       this.root.expand();
/*      */     } else {
/*      */       
/*  621 */       this.root = null;
/*  622 */       this.rowCount = 0;
/*      */     } 
/*  624 */     if (paramBoolean && this.treeSelectionModel != null) {
/*  625 */       this.treeSelectionModel.clearSelection();
/*      */     }
/*  627 */     visibleNodesChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getRowContainingYLocation(int paramInt) {
/*  636 */     if (getRowCount() == 0)
/*  637 */       return -1; 
/*  638 */     return Math.max(0, Math.min(getRowCount() - 1, paramInt / 
/*  639 */           getRowHeight()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean ensurePathIsExpanded(TreePath paramTreePath, boolean paramBoolean) {
/*  650 */     if (paramTreePath != null) {
/*      */       
/*  652 */       if (this.treeModel.isLeaf(paramTreePath.getLastPathComponent())) {
/*  653 */         paramTreePath = paramTreePath.getParentPath();
/*  654 */         paramBoolean = true;
/*      */       } 
/*  656 */       if (paramTreePath != null) {
/*  657 */         FHTreeStateNode fHTreeStateNode = getNodeForPath(paramTreePath, false, true);
/*      */ 
/*      */         
/*  660 */         if (fHTreeStateNode != null) {
/*  661 */           fHTreeStateNode.makeVisible();
/*  662 */           if (paramBoolean)
/*  663 */             fHTreeStateNode.expand(); 
/*  664 */           return true;
/*      */         } 
/*      */       } 
/*      */     } 
/*  668 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FHTreeStateNode createNodeForValue(Object paramObject, int paramInt) {
/*  675 */     return new FHTreeStateNode(paramObject, paramInt, -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FHTreeStateNode getNodeForPath(TreePath paramTreePath, boolean paramBoolean1, boolean paramBoolean2) {
/*  686 */     if (paramTreePath != null) {
/*      */       Stack<TreePath> stack;
/*      */       
/*  689 */       FHTreeStateNode fHTreeStateNode = getMapping(paramTreePath);
/*  690 */       if (fHTreeStateNode != null) {
/*  691 */         if (paramBoolean1 && !fHTreeStateNode.isVisible())
/*  692 */           return null; 
/*  693 */         return fHTreeStateNode;
/*      */       } 
/*  695 */       if (paramBoolean1) {
/*  696 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  701 */       if (this.tempStacks.size() == 0) {
/*  702 */         stack = new Stack();
/*      */       } else {
/*      */         
/*  705 */         stack = this.tempStacks.pop();
/*      */       } 
/*      */       
/*      */       try {
/*  709 */         stack.push(paramTreePath);
/*  710 */         paramTreePath = paramTreePath.getParentPath();
/*  711 */         fHTreeStateNode = null;
/*  712 */         while (paramTreePath != null) {
/*  713 */           fHTreeStateNode = getMapping(paramTreePath);
/*  714 */           if (fHTreeStateNode != null) {
/*      */ 
/*      */             
/*  717 */             while (fHTreeStateNode != null && stack.size() > 0) {
/*  718 */               paramTreePath = stack.pop();
/*  719 */               fHTreeStateNode = fHTreeStateNode.createChildFor(paramTreePath
/*  720 */                   .getLastPathComponent());
/*      */             } 
/*  722 */             return fHTreeStateNode;
/*      */           } 
/*  724 */           stack.push(paramTreePath);
/*  725 */           paramTreePath = paramTreePath.getParentPath();
/*      */         } 
/*      */       } finally {
/*      */         
/*  729 */         stack.removeAllElements();
/*  730 */         this.tempStacks.push(stack);
/*      */       } 
/*      */       
/*  733 */       return null;
/*      */     } 
/*  735 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class FHTreeStateNode
/*      */     extends DefaultMutableTreeNode
/*      */   {
/*      */     protected boolean isExpanded;
/*      */ 
/*      */ 
/*      */     
/*      */     protected int childIndex;
/*      */ 
/*      */ 
/*      */     
/*      */     protected int childCount;
/*      */ 
/*      */ 
/*      */     
/*      */     protected int row;
/*      */ 
/*      */     
/*      */     protected TreePath path;
/*      */ 
/*      */ 
/*      */     
/*      */     public FHTreeStateNode(Object param1Object, int param1Int1, int param1Int2) {
/*  764 */       super(param1Object);
/*  765 */       this.childIndex = param1Int1;
/*  766 */       this.row = param1Int2;
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
/*      */     public void setParent(MutableTreeNode param1MutableTreeNode) {
/*  778 */       super.setParent(param1MutableTreeNode);
/*  779 */       if (param1MutableTreeNode != null) {
/*  780 */         this
/*  781 */           .path = ((FHTreeStateNode)param1MutableTreeNode).getTreePath().pathByAddingChild(getUserObject());
/*  782 */         FixedHeightLayoutCache.this.addMapping(this);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void remove(int param1Int) {
/*  791 */       FHTreeStateNode fHTreeStateNode = (FHTreeStateNode)getChildAt(param1Int);
/*      */       
/*  793 */       fHTreeStateNode.removeFromMapping();
/*  794 */       super.remove(param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setUserObject(Object param1Object) {
/*  801 */       super.setUserObject(param1Object);
/*  802 */       if (this.path != null) {
/*  803 */         FHTreeStateNode fHTreeStateNode = (FHTreeStateNode)getParent();
/*      */         
/*  805 */         if (fHTreeStateNode != null) {
/*  806 */           resetChildrenPaths(fHTreeStateNode.getTreePath());
/*      */         } else {
/*  808 */           resetChildrenPaths((TreePath)null);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getChildIndex() {
/*  819 */       return this.childIndex;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TreePath getTreePath() {
/*  826 */       return this.path;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FHTreeStateNode getChildAtModelIndex(int param1Int) {
/*  836 */       for (int i = getChildCount() - 1; i >= 0; i--) {
/*  837 */         if (((FHTreeStateNode)getChildAt(i)).childIndex == param1Int)
/*  838 */           return (FHTreeStateNode)getChildAt(i); 
/*  839 */       }  return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isVisible() {
/*  847 */       FHTreeStateNode fHTreeStateNode = (FHTreeStateNode)getParent();
/*      */       
/*  849 */       if (fHTreeStateNode == null)
/*  850 */         return true; 
/*  851 */       return (fHTreeStateNode.isExpanded() && fHTreeStateNode.isVisible());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getRow() {
/*  858 */       return this.row;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getRowToModelIndex(int param1Int) {
/*  867 */       int i = getRow() + 1;
/*  868 */       int j = i;
/*      */ 
/*      */       
/*  871 */       byte b = 0; int k = getChildCount();
/*  872 */       for (; b < k; b++) {
/*  873 */         FHTreeStateNode fHTreeStateNode = (FHTreeStateNode)getChildAt(b);
/*  874 */         if (fHTreeStateNode.childIndex >= param1Int) {
/*  875 */           if (fHTreeStateNode.childIndex == param1Int)
/*  876 */             return fHTreeStateNode.row; 
/*  877 */           if (b == 0)
/*  878 */             return getRow() + 1 + param1Int; 
/*  879 */           return fHTreeStateNode.row - fHTreeStateNode.childIndex - param1Int;
/*      */         } 
/*      */       } 
/*      */       
/*  883 */       return getRow() + 1 + getTotalChildCount() - this.childCount - param1Int;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getTotalChildCount() {
/*  892 */       if (isExpanded()) {
/*  893 */         FHTreeStateNode fHTreeStateNode = (FHTreeStateNode)getParent();
/*      */         
/*      */         int i;
/*  896 */         if (fHTreeStateNode != null && (i = fHTreeStateNode.getIndex(this)) + 1 < fHTreeStateNode
/*  897 */           .getChildCount()) {
/*      */ 
/*      */ 
/*      */           
/*  901 */           FHTreeStateNode fHTreeStateNode1 = (FHTreeStateNode)fHTreeStateNode.getChildAt(i + 1);
/*      */           
/*  903 */           return fHTreeStateNode1.row - this.row - fHTreeStateNode1.childIndex - this.childIndex;
/*      */         } 
/*      */ 
/*      */         
/*  907 */         int j = this.childCount;
/*      */         
/*  909 */         for (int k = getChildCount() - 1; k >= 0; 
/*  910 */           k--) {
/*  911 */           j += ((FHTreeStateNode)getChildAt(k))
/*  912 */             .getTotalChildCount();
/*      */         }
/*  914 */         return j;
/*      */       } 
/*      */       
/*  917 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isExpanded() {
/*  924 */       return this.isExpanded;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getVisibleLevel() {
/*  931 */       if (FixedHeightLayoutCache.this.isRootVisible()) {
/*  932 */         return getLevel();
/*      */       }
/*  934 */       return getLevel() - 1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void resetChildrenPaths(TreePath param1TreePath) {
/*  942 */       FixedHeightLayoutCache.this.removeMapping(this);
/*  943 */       if (param1TreePath == null) {
/*  944 */         this.path = new TreePath(getUserObject());
/*      */       } else {
/*  946 */         this.path = param1TreePath.pathByAddingChild(getUserObject());
/*  947 */       }  FixedHeightLayoutCache.this.addMapping(this);
/*  948 */       for (int i = getChildCount() - 1; i >= 0; i--) {
/*  949 */         ((FHTreeStateNode)getChildAt(i))
/*  950 */           .resetChildrenPaths(this.path);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void removeFromMapping() {
/*  958 */       if (this.path != null) {
/*  959 */         FixedHeightLayoutCache.this.removeMapping(this);
/*  960 */         for (int i = getChildCount() - 1; i >= 0; i--) {
/*  961 */           ((FHTreeStateNode)getChildAt(i)).removeFromMapping();
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected FHTreeStateNode createChildFor(Object param1Object) {
/*      */       byte b;
/*  972 */       int i = FixedHeightLayoutCache.this.treeModel.getIndexOfChild(getUserObject(), param1Object);
/*      */       
/*  974 */       if (i < 0) {
/*  975 */         return null;
/*      */       }
/*      */       
/*  978 */       FHTreeStateNode fHTreeStateNode = FixedHeightLayoutCache.this.createNodeForValue(param1Object, i);
/*      */ 
/*      */ 
/*      */       
/*  982 */       if (isVisible()) {
/*  983 */         b = getRowToModelIndex(i);
/*      */       } else {
/*      */         
/*  986 */         b = -1;
/*      */       } 
/*  988 */       fHTreeStateNode.row = b;
/*  989 */       byte b1 = 0; int j = getChildCount();
/*  990 */       for (; b1 < j; b1++) {
/*  991 */         FHTreeStateNode fHTreeStateNode1 = (FHTreeStateNode)getChildAt(b1);
/*  992 */         if (fHTreeStateNode1.childIndex > i) {
/*  993 */           insert(fHTreeStateNode, b1);
/*  994 */           return fHTreeStateNode;
/*      */         } 
/*      */       } 
/*  997 */       add(fHTreeStateNode);
/*  998 */       return fHTreeStateNode;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void adjustRowBy(int param1Int) {
/* 1006 */       this.row += param1Int;
/* 1007 */       if (this.isExpanded) {
/* 1008 */         for (int i = getChildCount() - 1; i >= 0; 
/* 1009 */           i--) {
/* 1010 */           ((FHTreeStateNode)getChildAt(i)).adjustRowBy(param1Int);
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
/*      */     protected void adjustRowBy(int param1Int1, int param1Int2) {
/* 1022 */       if (this.isExpanded)
/*      */       {
/* 1024 */         for (int i = getChildCount() - 1; i >= param1Int2; 
/* 1025 */           i--) {
/* 1026 */           ((FHTreeStateNode)getChildAt(i)).adjustRowBy(param1Int1);
/*      */         }
/*      */       }
/* 1029 */       FHTreeStateNode fHTreeStateNode = (FHTreeStateNode)getParent();
/*      */       
/* 1031 */       if (fHTreeStateNode != null) {
/* 1032 */         fHTreeStateNode.adjustRowBy(param1Int1, fHTreeStateNode.getIndex(this) + 1);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void didExpand() {
/* 1041 */       int i = setRowAndChildren(this.row);
/* 1042 */       FHTreeStateNode fHTreeStateNode = (FHTreeStateNode)getParent();
/* 1043 */       int j = i - this.row - 1;
/*      */       
/* 1045 */       if (fHTreeStateNode != null) {
/* 1046 */         fHTreeStateNode.adjustRowBy(j, fHTreeStateNode.getIndex(this) + 1);
/*      */       }
/* 1048 */       FixedHeightLayoutCache.this.adjustRowCountBy(j);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected int setRowAndChildren(int param1Int) {
/* 1057 */       this.row = param1Int;
/*      */       
/* 1059 */       if (!isExpanded()) {
/* 1060 */         return this.row + 1;
/*      */       }
/* 1062 */       int i = this.row + 1;
/* 1063 */       int j = 0;
/*      */       
/* 1065 */       int k = getChildCount();
/*      */       
/* 1067 */       for (byte b = 0; b < k; b++) {
/* 1068 */         FHTreeStateNode fHTreeStateNode = (FHTreeStateNode)getChildAt(b);
/* 1069 */         i += fHTreeStateNode.childIndex - j;
/* 1070 */         j = fHTreeStateNode.childIndex + 1;
/* 1071 */         if (fHTreeStateNode.isExpanded) {
/* 1072 */           i = fHTreeStateNode.setRowAndChildren(i);
/*      */         } else {
/*      */           
/* 1075 */           fHTreeStateNode.row = i++;
/*      */         } 
/*      */       } 
/* 1078 */       return i + this.childCount - j;
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
/*      */     protected void resetChildrenRowsFrom(int param1Int1, int param1Int2, int param1Int3) {
/* 1095 */       int i = param1Int1;
/* 1096 */       int j = param1Int3;
/*      */       
/* 1098 */       int k = getChildCount();
/*      */       
/* 1100 */       for (int m = param1Int2; m < k; m++) {
/* 1101 */         FHTreeStateNode fHTreeStateNode1 = (FHTreeStateNode)getChildAt(m);
/* 1102 */         i += fHTreeStateNode1.childIndex - j;
/* 1103 */         j = fHTreeStateNode1.childIndex + 1;
/* 1104 */         if (fHTreeStateNode1.isExpanded) {
/* 1105 */           i = fHTreeStateNode1.setRowAndChildren(i);
/*      */         } else {
/*      */           
/* 1108 */           fHTreeStateNode1.row = i++;
/*      */         } 
/*      */       } 
/* 1111 */       i += this.childCount - j;
/* 1112 */       FHTreeStateNode fHTreeStateNode = (FHTreeStateNode)getParent();
/* 1113 */       if (fHTreeStateNode != null) {
/* 1114 */         fHTreeStateNode.resetChildrenRowsFrom(i, fHTreeStateNode.getIndex(this) + 1, this.childIndex + 1);
/*      */       }
/*      */       else {
/*      */         
/* 1118 */         FixedHeightLayoutCache.this.rowCount = i;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void makeVisible() {
/* 1127 */       FHTreeStateNode fHTreeStateNode = (FHTreeStateNode)getParent();
/*      */       
/* 1129 */       if (fHTreeStateNode != null) {
/* 1130 */         fHTreeStateNode.expandParentAndReceiver();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void expandParentAndReceiver() {
/* 1138 */       FHTreeStateNode fHTreeStateNode = (FHTreeStateNode)getParent();
/*      */       
/* 1140 */       if (fHTreeStateNode != null)
/* 1141 */         fHTreeStateNode.expandParentAndReceiver(); 
/* 1142 */       expand();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void expand() {
/* 1149 */       if (!this.isExpanded && !isLeaf()) {
/* 1150 */         boolean bool = isVisible();
/*      */         
/* 1152 */         this.isExpanded = true;
/* 1153 */         this.childCount = FixedHeightLayoutCache.this.treeModel.getChildCount(getUserObject());
/*      */         
/* 1155 */         if (bool) {
/* 1156 */           didExpand();
/*      */         }
/*      */ 
/*      */         
/* 1160 */         if (bool && FixedHeightLayoutCache.this.treeSelectionModel != null) {
/* 1161 */           FixedHeightLayoutCache.this.treeSelectionModel.resetRowSelection();
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void collapse(boolean param1Boolean) {
/* 1171 */       if (this.isExpanded) {
/* 1172 */         if (isVisible() && param1Boolean) {
/* 1173 */           int i = getTotalChildCount();
/*      */           
/* 1175 */           this.isExpanded = false;
/* 1176 */           FixedHeightLayoutCache.this.adjustRowCountBy(-i);
/*      */ 
/*      */           
/* 1179 */           adjustRowBy(-i, 0);
/*      */         } else {
/*      */           
/* 1182 */           this.isExpanded = false;
/*      */         } 
/* 1184 */         if (param1Boolean && isVisible() && FixedHeightLayoutCache.this.treeSelectionModel != null) {
/* 1185 */           FixedHeightLayoutCache.this.treeSelectionModel.resetRowSelection();
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isLeaf() {
/* 1193 */       TreeModel treeModel = FixedHeightLayoutCache.this.getModel();
/*      */       
/* 1195 */       return (treeModel != null) ? treeModel.isLeaf(getUserObject()) : true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void addNode(FHTreeStateNode param1FHTreeStateNode) {
/* 1204 */       boolean bool = false;
/* 1205 */       int i = param1FHTreeStateNode.getChildIndex();
/*      */       
/* 1207 */       int j = 0, k = getChildCount();
/* 1208 */       for (; j < k; j++) {
/* 1209 */         if (((FHTreeStateNode)getChildAt(j)).getChildIndex() > i) {
/*      */           
/* 1211 */           bool = true;
/* 1212 */           insert(param1FHTreeStateNode, j);
/* 1213 */           j = k;
/*      */         } 
/*      */       } 
/* 1216 */       if (!bool) {
/* 1217 */         add(param1FHTreeStateNode);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void removeChildAtModelIndex(int param1Int, boolean param1Boolean) {
/* 1227 */       FHTreeStateNode fHTreeStateNode = getChildAtModelIndex(param1Int);
/*      */       
/* 1229 */       if (fHTreeStateNode != null) {
/* 1230 */         int i = fHTreeStateNode.getRow();
/* 1231 */         int j = getIndex(fHTreeStateNode);
/*      */         
/* 1233 */         fHTreeStateNode.collapse(false);
/* 1234 */         remove(j);
/* 1235 */         adjustChildIndexs(j, -1);
/* 1236 */         this.childCount--;
/* 1237 */         if (param1Boolean)
/*      */         {
/* 1239 */           resetChildrenRowsFrom(i, j, param1Int);
/*      */         }
/*      */       } else {
/*      */         
/* 1243 */         int i = getChildCount();
/*      */ 
/*      */         
/* 1246 */         for (byte b = 0; b < i; b++) {
/* 1247 */           FHTreeStateNode fHTreeStateNode1 = (FHTreeStateNode)getChildAt(b);
/* 1248 */           if (fHTreeStateNode1.childIndex >= param1Int) {
/* 1249 */             if (param1Boolean) {
/* 1250 */               adjustRowBy(-1, b);
/* 1251 */               FixedHeightLayoutCache.this.adjustRowCountBy(-1);
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/* 1256 */             for (; b < i; b++) {
/* 1257 */               ((FHTreeStateNode)getChildAt(b)).childIndex--;
/*      */             }
/* 1259 */             this.childCount--;
/*      */             
/*      */             return;
/*      */           } 
/*      */         } 
/*      */         
/* 1265 */         if (param1Boolean) {
/* 1266 */           adjustRowBy(-1, i);
/* 1267 */           FixedHeightLayoutCache.this.adjustRowCountBy(-1);
/*      */         } 
/* 1269 */         this.childCount--;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void adjustChildIndexs(int param1Int1, int param1Int2) {
/* 1278 */       int i = param1Int1, j = getChildCount();
/* 1279 */       for (; i < j; i++) {
/* 1280 */         ((FHTreeStateNode)getChildAt(i)).childIndex += param1Int2;
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
/*      */     protected void childInsertedAtModelIndex(int param1Int, boolean param1Boolean) {
/* 1292 */       int i = getChildCount();
/*      */       
/* 1294 */       for (byte b = 0; b < i; b++) {
/* 1295 */         FHTreeStateNode fHTreeStateNode = (FHTreeStateNode)getChildAt(b);
/* 1296 */         if (fHTreeStateNode.childIndex >= param1Int) {
/* 1297 */           if (param1Boolean) {
/* 1298 */             adjustRowBy(1, b);
/* 1299 */             FixedHeightLayoutCache.this.adjustRowCountBy(1);
/*      */           } 
/*      */ 
/*      */           
/* 1303 */           for (; b < i; b++)
/* 1304 */             ((FHTreeStateNode)getChildAt(b)).childIndex++; 
/* 1305 */           this.childCount++;
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */       
/* 1311 */       if (param1Boolean) {
/* 1312 */         adjustRowBy(1, i);
/* 1313 */         FixedHeightLayoutCache.this.adjustRowCountBy(1);
/*      */       } 
/* 1315 */       this.childCount++;
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
/*      */     protected boolean getPathForRow(int param1Int1, int param1Int2, FixedHeightLayoutCache.SearchInfo param1SearchInfo) {
/* 1327 */       if (this.row == param1Int1) {
/* 1328 */         param1SearchInfo.node = this;
/* 1329 */         param1SearchInfo.isNodeParentNode = false;
/* 1330 */         param1SearchInfo.childIndex = this.childIndex;
/* 1331 */         return true;
/*      */       } 
/*      */ 
/*      */       
/* 1335 */       FHTreeStateNode fHTreeStateNode = null;
/*      */       
/* 1337 */       int i = 0, j = getChildCount();
/* 1338 */       for (; i < j; i++) {
/* 1339 */         FHTreeStateNode fHTreeStateNode1 = (FHTreeStateNode)getChildAt(i);
/* 1340 */         if (fHTreeStateNode1.row > param1Int1) {
/* 1341 */           if (i == 0) {
/*      */             
/* 1343 */             param1SearchInfo.node = this;
/* 1344 */             param1SearchInfo.isNodeParentNode = true;
/* 1345 */             param1SearchInfo.childIndex = param1Int1 - this.row - 1;
/* 1346 */             return true;
/*      */           } 
/*      */ 
/*      */           
/* 1350 */           int k = 1 + fHTreeStateNode1.row - fHTreeStateNode1.childIndex - fHTreeStateNode.childIndex;
/*      */ 
/*      */           
/* 1353 */           if (param1Int1 < k) {
/* 1354 */             return fHTreeStateNode.getPathForRow(param1Int1, k, param1SearchInfo);
/*      */           }
/*      */ 
/*      */           
/* 1358 */           param1SearchInfo.node = this;
/* 1359 */           param1SearchInfo.isNodeParentNode = true;
/* 1360 */           param1SearchInfo.childIndex = param1Int1 - k + fHTreeStateNode.childIndex + 1;
/*      */           
/* 1362 */           return true;
/*      */         } 
/*      */         
/* 1365 */         fHTreeStateNode = fHTreeStateNode1;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1370 */       if (fHTreeStateNode != null) {
/* 1371 */         i = param1Int2 - this.childCount - fHTreeStateNode.childIndex + 1;
/*      */ 
/*      */         
/* 1374 */         if (param1Int1 < i) {
/* 1375 */           return fHTreeStateNode.getPathForRow(param1Int1, i, param1SearchInfo);
/*      */         }
/*      */         
/* 1378 */         param1SearchInfo.node = this;
/* 1379 */         param1SearchInfo.isNodeParentNode = true;
/* 1380 */         param1SearchInfo.childIndex = param1Int1 - i + fHTreeStateNode.childIndex + 1;
/*      */         
/* 1382 */         return true;
/*      */       } 
/*      */ 
/*      */       
/* 1386 */       i = param1Int1 - this.row - 1;
/*      */       
/* 1388 */       if (i >= this.childCount) {
/* 1389 */         return false;
/*      */       }
/* 1391 */       param1SearchInfo.node = this;
/* 1392 */       param1SearchInfo.isNodeParentNode = true;
/* 1393 */       param1SearchInfo.childIndex = i;
/* 1394 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected int getCountTo(int param1Int) {
/* 1404 */       int i = param1Int + 1;
/*      */       
/* 1406 */       int j = 0, k = getChildCount();
/* 1407 */       for (; j < k; j++) {
/* 1408 */         FHTreeStateNode fHTreeStateNode = (FHTreeStateNode)getChildAt(j);
/* 1409 */         if (fHTreeStateNode.childIndex >= param1Int) {
/* 1410 */           j = k;
/*      */         } else {
/* 1412 */           i += fHTreeStateNode.getTotalChildCount();
/*      */         } 
/* 1414 */       }  if (this.parent != null)
/* 1415 */         return i + ((FHTreeStateNode)getParent())
/* 1416 */           .getCountTo(this.childIndex); 
/* 1417 */       if (!FixedHeightLayoutCache.this.isRootVisible())
/* 1418 */         return i - 1; 
/* 1419 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected int getNumExpandedChildrenTo(int param1Int) {
/* 1430 */       int i = param1Int;
/*      */       
/* 1432 */       byte b = 0; int j = getChildCount();
/* 1433 */       for (; b < j; b++) {
/* 1434 */         FHTreeStateNode fHTreeStateNode = (FHTreeStateNode)getChildAt(b);
/* 1435 */         if (fHTreeStateNode.childIndex >= param1Int) {
/* 1436 */           return i;
/*      */         }
/* 1438 */         i += fHTreeStateNode.getTotalChildCount();
/*      */       } 
/*      */       
/* 1441 */       return i;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void didAdjustTree() {}
/*      */   }
/*      */ 
/*      */   
/*      */   private class SearchInfo
/*      */   {
/*      */     protected FixedHeightLayoutCache.FHTreeStateNode node;
/*      */     
/*      */     protected boolean isNodeParentNode;
/*      */     
/*      */     protected int childIndex;
/*      */ 
/*      */     
/*      */     private SearchInfo() {}
/*      */ 
/*      */     
/*      */     protected TreePath getPath() {
/* 1462 */       if (this.node == null) {
/* 1463 */         return null;
/*      */       }
/* 1465 */       if (this.isNodeParentNode) {
/* 1466 */         return this.node.getTreePath().pathByAddingChild(FixedHeightLayoutCache.this.treeModel
/* 1467 */             .getChild(this.node.getUserObject(), this.childIndex));
/*      */       }
/* 1469 */       return this.node.path;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class VisibleFHTreeStateNodeEnumeration
/*      */     implements Enumeration<TreePath>
/*      */   {
/*      */     protected FixedHeightLayoutCache.FHTreeStateNode parent;
/*      */ 
/*      */ 
/*      */     
/*      */     protected int nextIndex;
/*      */ 
/*      */     
/*      */     protected int childCount;
/*      */ 
/*      */ 
/*      */     
/*      */     protected VisibleFHTreeStateNodeEnumeration(FixedHeightLayoutCache.FHTreeStateNode param1FHTreeStateNode) {
/* 1491 */       this(param1FHTreeStateNode, -1);
/*      */     }
/*      */ 
/*      */     
/*      */     protected VisibleFHTreeStateNodeEnumeration(FixedHeightLayoutCache.FHTreeStateNode param1FHTreeStateNode, int param1Int) {
/* 1496 */       this.parent = param1FHTreeStateNode;
/* 1497 */       this.nextIndex = param1Int;
/* 1498 */       this.childCount = FixedHeightLayoutCache.this.treeModel.getChildCount(this.parent
/* 1499 */           .getUserObject());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasMoreElements() {
/* 1506 */       return (this.parent != null);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public TreePath nextElement() {
/*      */       TreePath treePath;
/* 1513 */       if (!hasMoreElements()) {
/* 1514 */         throw new NoSuchElementException("No more visible paths");
/*      */       }
/*      */ 
/*      */       
/* 1518 */       if (this.nextIndex == -1) {
/* 1519 */         treePath = this.parent.getTreePath();
/*      */       } else {
/* 1521 */         FixedHeightLayoutCache.FHTreeStateNode fHTreeStateNode = this.parent.getChildAtModelIndex(this.nextIndex);
/*      */         
/* 1523 */         if (fHTreeStateNode == null) {
/*      */           
/* 1525 */           treePath = this.parent.getTreePath().pathByAddingChild(FixedHeightLayoutCache.this.treeModel.getChild(this.parent.getUserObject(), this.nextIndex));
/*      */         } else {
/*      */           
/* 1528 */           treePath = fHTreeStateNode.getTreePath();
/*      */         } 
/* 1530 */       }  updateNextObject();
/* 1531 */       return treePath;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void updateNextObject() {
/* 1539 */       if (!updateNextIndex()) {
/* 1540 */         findNextValidParent();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean findNextValidParent() {
/* 1549 */       if (this.parent == FixedHeightLayoutCache.this.root) {
/*      */         
/* 1551 */         this.parent = null;
/* 1552 */         return false;
/*      */       } 
/* 1554 */       while (this.parent != null) {
/*      */         
/* 1556 */         FixedHeightLayoutCache.FHTreeStateNode fHTreeStateNode = (FixedHeightLayoutCache.FHTreeStateNode)this.parent.getParent();
/*      */         
/* 1558 */         if (fHTreeStateNode != null) {
/* 1559 */           this.nextIndex = this.parent.childIndex;
/* 1560 */           this.parent = fHTreeStateNode;
/* 1561 */           this
/* 1562 */             .childCount = FixedHeightLayoutCache.this.treeModel.getChildCount(this.parent.getUserObject());
/* 1563 */           if (updateNextIndex())
/* 1564 */             return true; 
/*      */           continue;
/*      */         } 
/* 1567 */         this.parent = null;
/*      */       } 
/* 1569 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean updateNextIndex() {
/* 1579 */       if (this.nextIndex == -1 && !this.parent.isExpanded()) {
/* 1580 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1584 */       if (this.childCount == 0) {
/* 1585 */         return false;
/*      */       }
/*      */       
/* 1588 */       if (++this.nextIndex >= this.childCount) {
/* 1589 */         return false;
/*      */       }
/*      */       
/* 1592 */       FixedHeightLayoutCache.FHTreeStateNode fHTreeStateNode = this.parent.getChildAtModelIndex(this.nextIndex);
/*      */       
/* 1594 */       if (fHTreeStateNode != null && fHTreeStateNode.isExpanded()) {
/* 1595 */         this.parent = fHTreeStateNode;
/* 1596 */         this.nextIndex = -1;
/* 1597 */         this.childCount = FixedHeightLayoutCache.this.treeModel.getChildCount(fHTreeStateNode.getUserObject());
/*      */       } 
/* 1599 */       return true;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/tree/FixedHeightLayoutCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */