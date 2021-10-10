/*      */ package javax.swing.tree;
/*      */ 
/*      */ import java.awt.Rectangle;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Stack;
/*      */ import java.util.Vector;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class VariableHeightLayoutCache
/*      */   extends AbstractLayoutCache
/*      */ {
/*   93 */   private Stack<Stack<TreePath>> tempStacks = new Stack<>();
/*   94 */   private Vector<Object> visibleNodes = new Vector();
/*   95 */   private Rectangle boundsBuffer = new Rectangle();
/*   96 */   private Hashtable<TreePath, TreeStateNode> treePathMapping = new Hashtable<>();
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean updateNodeSizes;
/*      */ 
/*      */   
/*      */   private TreeStateNode root;
/*      */ 
/*      */ 
/*      */   
/*      */   public void setModel(TreeModel paramTreeModel) {
/*  108 */     super.setModel(paramTreeModel);
/*  109 */     rebuild(false);
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
/*      */   public void setRootVisible(boolean paramBoolean) {
/*  124 */     if (isRootVisible() != paramBoolean && this.root != null) {
/*  125 */       if (paramBoolean) {
/*  126 */         this.root.updatePreferredSize(0);
/*  127 */         this.visibleNodes.insertElementAt(this.root, 0);
/*      */       }
/*  129 */       else if (this.visibleNodes.size() > 0) {
/*  130 */         this.visibleNodes.removeElementAt(0);
/*  131 */         if (this.treeSelectionModel != null)
/*  132 */           this.treeSelectionModel
/*  133 */             .removeSelectionPath(this.root.getTreePath()); 
/*      */       } 
/*  135 */       if (this.treeSelectionModel != null)
/*  136 */         this.treeSelectionModel.resetRowSelection(); 
/*  137 */       if (getRowCount() > 0)
/*  138 */         getNode(0).setYOrigin(0); 
/*  139 */       updateYLocationsFrom(0);
/*  140 */       visibleNodesChanged();
/*      */     } 
/*  142 */     super.setRootVisible(paramBoolean);
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
/*      */   public void setRowHeight(int paramInt) {
/*  156 */     if (paramInt != getRowHeight()) {
/*  157 */       super.setRowHeight(paramInt);
/*  158 */       invalidateSizes();
/*  159 */       visibleNodesChanged();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNodeDimensions(AbstractLayoutCache.NodeDimensions paramNodeDimensions) {
/*  168 */     super.setNodeDimensions(paramNodeDimensions);
/*  169 */     invalidateSizes();
/*  170 */     visibleNodesChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setExpandedState(TreePath paramTreePath, boolean paramBoolean) {
/*  180 */     if (paramTreePath != null) {
/*  181 */       if (paramBoolean) {
/*  182 */         ensurePathIsExpanded(paramTreePath, true);
/*      */       } else {
/*  184 */         TreeStateNode treeStateNode = getNodeForPath(paramTreePath, false, true);
/*      */         
/*  186 */         if (treeStateNode != null) {
/*  187 */           treeStateNode.makeVisible();
/*  188 */           treeStateNode.collapse();
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getExpandedState(TreePath paramTreePath) {
/*  199 */     TreeStateNode treeStateNode = getNodeForPath(paramTreePath, true, false);
/*      */     
/*  201 */     return (treeStateNode != null) ? ((treeStateNode.isVisible() && treeStateNode.isExpanded())) : false;
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
/*      */   public Rectangle getBounds(TreePath paramTreePath, Rectangle paramRectangle) {
/*  215 */     TreeStateNode treeStateNode = getNodeForPath(paramTreePath, true, false);
/*      */     
/*  217 */     if (treeStateNode != null) {
/*  218 */       if (this.updateNodeSizes)
/*  219 */         updateNodeSizes(false); 
/*  220 */       return treeStateNode.getNodeBounds(paramRectangle);
/*      */     } 
/*  222 */     return null;
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
/*      */   public TreePath getPathForRow(int paramInt) {
/*  234 */     if (paramInt >= 0 && paramInt < getRowCount()) {
/*  235 */       return getNode(paramInt).getTreePath();
/*      */     }
/*  237 */     return null;
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
/*      */   public int getRowForPath(TreePath paramTreePath) {
/*  249 */     if (paramTreePath == null) {
/*  250 */       return -1;
/*      */     }
/*  252 */     TreeStateNode treeStateNode = getNodeForPath(paramTreePath, true, false);
/*      */     
/*  254 */     if (treeStateNode != null)
/*  255 */       return treeStateNode.getRow(); 
/*  256 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRowCount() {
/*  264 */     return this.visibleNodes.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void invalidatePathBounds(TreePath paramTreePath) {
/*  274 */     TreeStateNode treeStateNode = getNodeForPath(paramTreePath, true, false);
/*      */     
/*  276 */     if (treeStateNode != null) {
/*  277 */       treeStateNode.markSizeInvalid();
/*  278 */       if (treeStateNode.isVisible()) {
/*  279 */         updateYLocationsFrom(treeStateNode.getRow());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPreferredHeight() {
/*  289 */     int i = getRowCount();
/*      */     
/*  291 */     if (i > 0) {
/*  292 */       TreeStateNode treeStateNode = getNode(i - 1);
/*      */       
/*  294 */       return treeStateNode.getYOrigin() + treeStateNode.getPreferredHeight();
/*      */     } 
/*  296 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPreferredWidth(Rectangle paramRectangle) {
/*  306 */     if (this.updateNodeSizes) {
/*  307 */       updateNodeSizes(false);
/*      */     }
/*  309 */     return getMaxNodeWidth();
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
/*      */   public TreePath getPathClosestTo(int paramInt1, int paramInt2) {
/*  325 */     if (getRowCount() == 0) {
/*  326 */       return null;
/*      */     }
/*  328 */     if (this.updateNodeSizes) {
/*  329 */       updateNodeSizes(false);
/*      */     }
/*  331 */     int i = getRowContainingYLocation(paramInt2);
/*      */     
/*  333 */     return getNode(i).getTreePath();
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
/*      */   public Enumeration<TreePath> getVisiblePathsFrom(TreePath paramTreePath) {
/*  346 */     TreeStateNode treeStateNode = getNodeForPath(paramTreePath, true, false);
/*      */     
/*  348 */     if (treeStateNode != null) {
/*  349 */       return new VisibleTreeStateNodeEnumeration(treeStateNode);
/*      */     }
/*  351 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getVisibleChildCount(TreePath paramTreePath) {
/*  359 */     TreeStateNode treeStateNode = getNodeForPath(paramTreePath, true, false);
/*      */     
/*  361 */     return (treeStateNode != null) ? treeStateNode.getVisibleChildCount() : 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void invalidateSizes() {
/*  369 */     if (this.root != null)
/*  370 */       this.root.deepMarkSizeInvalid(); 
/*  371 */     if (!isFixedRowHeight() && this.visibleNodes.size() > 0) {
/*  372 */       updateNodeSizes(true);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isExpanded(TreePath paramTreePath) {
/*  383 */     if (paramTreePath != null) {
/*  384 */       TreeStateNode treeStateNode = getNodeForPath(paramTreePath, true, false);
/*      */       
/*  386 */       return (treeStateNode != null && treeStateNode.isExpanded());
/*      */     } 
/*  388 */     return false;
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
/*      */   public void treeNodesChanged(TreeModelEvent paramTreeModelEvent) {
/*  412 */     if (paramTreeModelEvent != null) {
/*      */ 
/*      */ 
/*      */       
/*  416 */       int[] arrayOfInt = paramTreeModelEvent.getChildIndices();
/*  417 */       TreeStateNode treeStateNode = getNodeForPath(SwingUtilities2.getTreePath(paramTreeModelEvent, getModel()), false, false);
/*  418 */       if (treeStateNode != null) {
/*  419 */         Object object = treeStateNode.getValue();
/*      */ 
/*      */ 
/*      */         
/*  423 */         treeStateNode.updatePreferredSize();
/*  424 */         if (treeStateNode.hasBeenExpanded() && arrayOfInt != null) {
/*      */ 
/*      */ 
/*      */           
/*  428 */           for (byte b = 0; b < arrayOfInt.length; 
/*  429 */             b++)
/*      */           {
/*  431 */             TreeStateNode treeStateNode1 = (TreeStateNode)treeStateNode.getChildAt(arrayOfInt[b]);
/*      */             
/*  433 */             treeStateNode1
/*  434 */               .setUserObject(this.treeModel.getChild(object, arrayOfInt[b]));
/*      */             
/*  436 */             treeStateNode1.updatePreferredSize();
/*      */           }
/*      */         
/*  439 */         } else if (treeStateNode == this.root) {
/*      */           
/*  441 */           treeStateNode.updatePreferredSize();
/*      */         } 
/*  443 */         if (!isFixedRowHeight()) {
/*  444 */           int i = treeStateNode.getRow();
/*      */           
/*  446 */           if (i != -1)
/*  447 */             updateYLocationsFrom(i); 
/*      */         } 
/*  449 */         visibleNodesChanged();
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
/*      */   public void treeNodesInserted(TreeModelEvent paramTreeModelEvent) {
/*  465 */     if (paramTreeModelEvent != null) {
/*      */ 
/*      */ 
/*      */       
/*  469 */       int[] arrayOfInt = paramTreeModelEvent.getChildIndices();
/*  470 */       TreeStateNode treeStateNode = getNodeForPath(SwingUtilities2.getTreePath(paramTreeModelEvent, getModel()), false, false);
/*      */ 
/*      */ 
/*      */       
/*  474 */       if (treeStateNode != null && arrayOfInt != null && arrayOfInt.length > 0)
/*      */       {
/*  476 */         if (treeStateNode.hasBeenExpanded()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  482 */           int i = treeStateNode.getChildCount();
/*      */           
/*  484 */           Object object = treeStateNode.getValue();
/*      */ 
/*      */ 
/*      */           
/*  488 */           boolean bool = ((treeStateNode == this.root && !this.rootVisible) || (treeStateNode.getRow() != -1 && treeStateNode.isExpanded())) ? true : false;
/*  489 */           for (byte b = 0; b < arrayOfInt.length; b++)
/*      */           {
/*  491 */             TreeStateNode treeStateNode1 = createNodeAt(treeStateNode, arrayOfInt[b]);
/*      */           }
/*      */           
/*  494 */           if (i == 0)
/*      */           {
/*  496 */             treeStateNode.updatePreferredSize();
/*      */           }
/*  498 */           if (this.treeSelectionModel != null) {
/*  499 */             this.treeSelectionModel.resetRowSelection();
/*      */           }
/*      */           
/*  502 */           if (!isFixedRowHeight() && (bool || (i == 0 && treeStateNode
/*      */             
/*  504 */             .isVisible()))) {
/*  505 */             if (treeStateNode == this.root) {
/*  506 */               updateYLocationsFrom(0);
/*      */             } else {
/*  508 */               updateYLocationsFrom(treeStateNode
/*  509 */                   .getRow());
/*  510 */             }  visibleNodesChanged();
/*      */           }
/*  512 */           else if (bool) {
/*  513 */             visibleNodesChanged();
/*      */           } 
/*  515 */         } else if (this.treeModel.getChildCount(treeStateNode.getValue()) - arrayOfInt.length == 0) {
/*      */           
/*  517 */           treeStateNode.updatePreferredSize();
/*  518 */           if (!isFixedRowHeight() && treeStateNode.isVisible()) {
/*  519 */             updateYLocationsFrom(treeStateNode.getRow());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void treeNodesRemoved(TreeModelEvent paramTreeModelEvent) {
/*  539 */     if (paramTreeModelEvent != null) {
/*      */ 
/*      */ 
/*      */       
/*  543 */       int[] arrayOfInt = paramTreeModelEvent.getChildIndices();
/*  544 */       TreeStateNode treeStateNode = getNodeForPath(SwingUtilities2.getTreePath(paramTreeModelEvent, getModel()), false, false);
/*      */ 
/*      */       
/*  547 */       if (treeStateNode != null && arrayOfInt != null && arrayOfInt.length > 0)
/*      */       {
/*  549 */         if (treeStateNode.hasBeenExpanded()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  558 */           boolean bool = ((treeStateNode == this.root && !this.rootVisible) || (treeStateNode.getRow() != -1 && treeStateNode.isExpanded())) ? true : false;
/*  559 */           for (int i = arrayOfInt.length - 1; i >= 0; 
/*  560 */             i--) {
/*      */             
/*  562 */             TreeStateNode treeStateNode1 = (TreeStateNode)treeStateNode.getChildAt(arrayOfInt[i]);
/*  563 */             if (treeStateNode1.isExpanded()) {
/*  564 */               treeStateNode1.collapse(false);
/*      */             }
/*      */ 
/*      */             
/*  568 */             if (bool) {
/*  569 */               int j = treeStateNode1.getRow();
/*  570 */               if (j != -1) {
/*  571 */                 this.visibleNodes.removeElementAt(j);
/*      */               }
/*      */             } 
/*  574 */             treeStateNode.remove(arrayOfInt[i]);
/*      */           } 
/*  576 */           if (treeStateNode.getChildCount() == 0) {
/*      */             
/*  578 */             treeStateNode.updatePreferredSize();
/*  579 */             if (treeStateNode.isExpanded() && treeStateNode
/*  580 */               .isLeaf())
/*      */             {
/*  582 */               treeStateNode.collapse(false);
/*      */             }
/*      */           } 
/*  585 */           if (this.treeSelectionModel != null) {
/*  586 */             this.treeSelectionModel.resetRowSelection();
/*      */           }
/*      */           
/*  589 */           if (!isFixedRowHeight() && (bool || (treeStateNode
/*  590 */             .getChildCount() == 0 && treeStateNode
/*  591 */             .isVisible()))) {
/*  592 */             if (treeStateNode == this.root) {
/*      */ 
/*      */ 
/*      */               
/*  596 */               if (getRowCount() > 0)
/*  597 */                 getNode(0).setYOrigin(0); 
/*  598 */               updateYLocationsFrom(0);
/*      */             } else {
/*      */               
/*  601 */               updateYLocationsFrom(treeStateNode.getRow());
/*  602 */             }  visibleNodesChanged();
/*      */           }
/*  604 */           else if (bool) {
/*  605 */             visibleNodesChanged();
/*      */           } 
/*  607 */         } else if (this.treeModel.getChildCount(treeStateNode.getValue()) == 0) {
/*      */           
/*  609 */           treeStateNode.updatePreferredSize();
/*  610 */           if (!isFixedRowHeight() && treeStateNode.isVisible()) {
/*  611 */             updateYLocationsFrom(treeStateNode.getRow());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void treeStructureChanged(TreeModelEvent paramTreeModelEvent) {
/*  630 */     if (paramTreeModelEvent != null) {
/*      */       
/*  632 */       TreePath treePath = SwingUtilities2.getTreePath(paramTreeModelEvent, getModel());
/*      */ 
/*      */       
/*  635 */       TreeStateNode treeStateNode = getNodeForPath(treePath, false, false);
/*      */ 
/*      */ 
/*      */       
/*  639 */       if (treeStateNode == this.root || (treeStateNode == null && ((treePath == null && this.treeModel != null && this.treeModel
/*      */ 
/*      */         
/*  642 */         .getRoot() == null) || (treePath != null && treePath
/*  643 */         .getPathCount() == 1)))) {
/*  644 */         rebuild(true);
/*      */       }
/*  646 */       else if (treeStateNode != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  652 */         boolean bool = treeStateNode.isExpanded();
/*  653 */         boolean bool1 = (treeStateNode.getRow() != -1) ? true : false;
/*      */         
/*  655 */         TreeStateNode treeStateNode2 = (TreeStateNode)treeStateNode.getParent();
/*  656 */         int i = treeStateNode2.getIndex(treeStateNode);
/*  657 */         if (bool1 && bool) {
/*  658 */           treeStateNode.collapse(false);
/*      */         }
/*  660 */         if (bool1)
/*  661 */           this.visibleNodes.removeElement(treeStateNode); 
/*  662 */         treeStateNode.removeFromParent();
/*  663 */         createNodeAt(treeStateNode2, i);
/*  664 */         TreeStateNode treeStateNode1 = (TreeStateNode)treeStateNode2.getChildAt(i);
/*  665 */         if (bool1 && bool)
/*  666 */           treeStateNode1.expand(false); 
/*  667 */         int j = treeStateNode1.getRow();
/*  668 */         if (!isFixedRowHeight() && bool1) {
/*  669 */           if (j == 0) {
/*  670 */             updateYLocationsFrom(j);
/*      */           } else {
/*  672 */             updateYLocationsFrom(j - 1);
/*  673 */           }  visibleNodesChanged();
/*      */         }
/*  675 */         else if (bool1) {
/*  676 */           visibleNodesChanged();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
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
/*      */   private void addMapping(TreeStateNode paramTreeStateNode) {
/*  693 */     this.treePathMapping.put(paramTreeStateNode.getTreePath(), paramTreeStateNode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void removeMapping(TreeStateNode paramTreeStateNode) {
/*  700 */     this.treePathMapping.remove(paramTreeStateNode.getTreePath());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private TreeStateNode getMapping(TreePath paramTreePath) {
/*  708 */     return this.treePathMapping.get(paramTreePath);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Rectangle getBounds(int paramInt, Rectangle paramRectangle) {
/*  717 */     if (this.updateNodeSizes) {
/*  718 */       updateNodeSizes(false);
/*      */     }
/*  720 */     if (paramInt >= 0 && paramInt < getRowCount()) {
/*  721 */       return getNode(paramInt).getNodeBounds(paramRectangle);
/*      */     }
/*  723 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void rebuild(boolean paramBoolean) {
/*  733 */     this.treePathMapping.clear(); Object object;
/*  734 */     if (this.treeModel != null && (object = this.treeModel.getRoot()) != null) {
/*  735 */       this.root = createNodeForValue(object);
/*  736 */       this.root.path = new TreePath(object);
/*  737 */       addMapping(this.root);
/*  738 */       this.root.updatePreferredSize(0);
/*  739 */       this.visibleNodes.removeAllElements();
/*  740 */       if (isRootVisible())
/*  741 */         this.visibleNodes.addElement(this.root); 
/*  742 */       if (!this.root.isExpanded()) {
/*  743 */         this.root.expand();
/*      */       } else {
/*  745 */         Enumeration enumeration = this.root.children();
/*  746 */         while (enumeration.hasMoreElements()) {
/*  747 */           this.visibleNodes.addElement(enumeration.nextElement());
/*      */         }
/*  749 */         if (!isFixedRowHeight()) {
/*  750 */           updateYLocationsFrom(0);
/*      */         }
/*      */       } 
/*      */     } else {
/*  754 */       this.visibleNodes.removeAllElements();
/*  755 */       this.root = null;
/*      */     } 
/*  757 */     if (paramBoolean && this.treeSelectionModel != null) {
/*  758 */       this.treeSelectionModel.clearSelection();
/*      */     }
/*  760 */     visibleNodesChanged();
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
/*      */   private TreeStateNode createNodeAt(TreeStateNode paramTreeStateNode, int paramInt) {
/*  779 */     Object object = this.treeModel.getChild(paramTreeStateNode.getValue(), paramInt);
/*  780 */     TreeStateNode treeStateNode = createNodeForValue(object);
/*  781 */     paramTreeStateNode.insert(treeStateNode, paramInt);
/*  782 */     treeStateNode.updatePreferredSize(-1);
/*  783 */     boolean bool = (paramTreeStateNode == this.root) ? true : false;
/*  784 */     if (treeStateNode != null && paramTreeStateNode.isExpanded() && (paramTreeStateNode
/*  785 */       .getRow() != -1 || bool)) {
/*      */       int i;
/*      */ 
/*      */       
/*  789 */       if (paramInt == 0) {
/*  790 */         if (bool && !isRootVisible()) {
/*  791 */           i = 0;
/*      */         } else {
/*  793 */           i = paramTreeStateNode.getRow() + 1;
/*      */         } 
/*  795 */       } else if (paramInt == paramTreeStateNode.getChildCount()) {
/*  796 */         i = paramTreeStateNode.getLastVisibleNode().getRow() + 1;
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  801 */         TreeStateNode treeStateNode1 = (TreeStateNode)paramTreeStateNode.getChildAt(paramInt - 1);
/*  802 */         i = treeStateNode1.getLastVisibleNode().getRow() + 1;
/*      */       } 
/*  804 */       this.visibleNodes.insertElementAt(treeStateNode, i);
/*      */     } 
/*  806 */     return treeStateNode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private TreeStateNode getNodeForPath(TreePath paramTreePath, boolean paramBoolean1, boolean paramBoolean2) {
/*  817 */     if (paramTreePath != null) {
/*      */       Stack<TreePath> stack;
/*      */       
/*  820 */       TreeStateNode treeStateNode = getMapping(paramTreePath);
/*  821 */       if (treeStateNode != null) {
/*  822 */         if (paramBoolean1 && !treeStateNode.isVisible())
/*  823 */           return null; 
/*  824 */         return treeStateNode;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  830 */       if (this.tempStacks.size() == 0) {
/*  831 */         stack = new Stack();
/*      */       } else {
/*      */         
/*  834 */         stack = this.tempStacks.pop();
/*      */       } 
/*      */       
/*      */       try {
/*  838 */         stack.push(paramTreePath);
/*  839 */         paramTreePath = paramTreePath.getParentPath();
/*  840 */         treeStateNode = null;
/*  841 */         while (paramTreePath != null) {
/*  842 */           treeStateNode = getMapping(paramTreePath);
/*  843 */           if (treeStateNode != null) {
/*      */ 
/*      */             
/*  846 */             while (treeStateNode != null && stack.size() > 0) {
/*  847 */               paramTreePath = stack.pop();
/*  848 */               treeStateNode.getLoadedChildren(paramBoolean2);
/*      */ 
/*      */               
/*  851 */               int i = this.treeModel.getIndexOfChild(treeStateNode.getUserObject(), paramTreePath
/*  852 */                   .getLastPathComponent());
/*      */               
/*  854 */               if (i == -1 || i >= treeStateNode
/*  855 */                 .getChildCount() || (paramBoolean1 && 
/*  856 */                 !treeStateNode.isVisible())) {
/*  857 */                 treeStateNode = null;
/*      */                 
/*      */                 continue;
/*      */               } 
/*  861 */               treeStateNode = (TreeStateNode)treeStateNode.getChildAt(i);
/*      */             } 
/*  863 */             return treeStateNode;
/*      */           } 
/*  865 */           stack.push(paramTreePath);
/*  866 */           paramTreePath = paramTreePath.getParentPath();
/*      */         } 
/*      */       } finally {
/*      */         
/*  870 */         stack.removeAllElements();
/*  871 */         this.tempStacks.push(stack);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  876 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateYLocationsFrom(int paramInt) {
/*  884 */     if (paramInt >= 0 && paramInt < getRowCount()) {
/*      */ 
/*      */ 
/*      */       
/*  888 */       TreeStateNode treeStateNode = getNode(paramInt);
/*  889 */       int k = treeStateNode.getYOrigin() + treeStateNode.getPreferredHeight();
/*  890 */       int i = paramInt + 1, j = this.visibleNodes.size();
/*  891 */       for (; i < j; i++) {
/*      */         
/*  893 */         treeStateNode = (TreeStateNode)this.visibleNodes.elementAt(i);
/*  894 */         treeStateNode.setYOrigin(k);
/*  895 */         k += treeStateNode.getPreferredHeight();
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
/*      */   private void updateNodeSizes(boolean paramBoolean) {
/*  912 */     this.updateNodeSizes = false;
/*  913 */     byte b = 0; int i = b, j = this.visibleNodes.size();
/*  914 */     for (; b < j; b++) {
/*  915 */       TreeStateNode treeStateNode = (TreeStateNode)this.visibleNodes.elementAt(b);
/*  916 */       treeStateNode.setYOrigin(i);
/*  917 */       if (paramBoolean || !treeStateNode.hasValidSize())
/*  918 */         treeStateNode.updatePreferredSize(b); 
/*  919 */       i += treeStateNode.getPreferredHeight();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getRowContainingYLocation(int paramInt) {
/*  929 */     if (isFixedRowHeight()) {
/*  930 */       if (getRowCount() == 0)
/*  931 */         return -1; 
/*  932 */       return Math.max(0, Math.min(getRowCount() - 1, paramInt / 
/*  933 */             getRowHeight()));
/*      */     } 
/*      */ 
/*      */     
/*      */     int i;
/*      */     
/*  939 */     if ((i = getRowCount()) <= 0)
/*  940 */       return -1; 
/*  941 */     int k = 0, j = k;
/*  942 */     while (k < i) {
/*  943 */       j = (i - k) / 2 + k;
/*  944 */       TreeStateNode treeStateNode = (TreeStateNode)this.visibleNodes.elementAt(j);
/*  945 */       int n = treeStateNode.getYOrigin();
/*  946 */       int m = n + treeStateNode.getPreferredHeight();
/*  947 */       if (paramInt < n) {
/*  948 */         i = j - 1; continue;
/*      */       } 
/*  950 */       if (paramInt >= m) {
/*  951 */         k = j + 1;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  956 */     if (k == i) {
/*  957 */       j = k;
/*  958 */       if (j >= getRowCount())
/*  959 */         j = getRowCount() - 1; 
/*      */     } 
/*  961 */     return j;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void ensurePathIsExpanded(TreePath paramTreePath, boolean paramBoolean) {
/*  971 */     if (paramTreePath != null) {
/*      */       
/*  973 */       if (this.treeModel.isLeaf(paramTreePath.getLastPathComponent())) {
/*  974 */         paramTreePath = paramTreePath.getParentPath();
/*  975 */         paramBoolean = true;
/*      */       } 
/*  977 */       if (paramTreePath != null) {
/*  978 */         TreeStateNode treeStateNode = getNodeForPath(paramTreePath, false, true);
/*      */ 
/*      */         
/*  981 */         if (treeStateNode != null) {
/*  982 */           treeStateNode.makeVisible();
/*  983 */           if (paramBoolean) {
/*  984 */             treeStateNode.expand();
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private TreeStateNode getNode(int paramInt) {
/*  994 */     return (TreeStateNode)this.visibleNodes.elementAt(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getMaxNodeWidth() {
/* 1001 */     int i = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1006 */     for (int j = getRowCount() - 1; j >= 0; j--) {
/* 1007 */       TreeStateNode treeStateNode = getNode(j);
/* 1008 */       int k = treeStateNode.getPreferredWidth() + treeStateNode.getXOrigin();
/* 1009 */       if (k > i)
/* 1010 */         i = k; 
/*      */     } 
/* 1012 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private TreeStateNode createNodeForValue(Object paramObject) {
/* 1020 */     return new TreeStateNode(paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class TreeStateNode
/*      */     extends DefaultMutableTreeNode
/*      */   {
/*      */     protected int preferredWidth;
/*      */ 
/*      */     
/*      */     protected int preferredHeight;
/*      */ 
/*      */     
/*      */     protected int xOrigin;
/*      */ 
/*      */     
/*      */     protected int yOrigin;
/*      */ 
/*      */     
/*      */     protected boolean expanded;
/*      */ 
/*      */     
/*      */     protected boolean hasBeenExpanded;
/*      */ 
/*      */     
/*      */     protected TreePath path;
/*      */ 
/*      */ 
/*      */     
/*      */     public TreeStateNode(Object param1Object) {
/* 1051 */       super(param1Object);
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
/* 1063 */       super.setParent(param1MutableTreeNode);
/* 1064 */       if (param1MutableTreeNode != null) {
/* 1065 */         this
/* 1066 */           .path = ((TreeStateNode)param1MutableTreeNode).getTreePath().pathByAddingChild(getUserObject());
/* 1067 */         VariableHeightLayoutCache.this.addMapping(this);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void remove(int param1Int) {
/* 1076 */       TreeStateNode treeStateNode = (TreeStateNode)getChildAt(param1Int);
/*      */       
/* 1078 */       treeStateNode.removeFromMapping();
/* 1079 */       super.remove(param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setUserObject(Object param1Object) {
/* 1086 */       super.setUserObject(param1Object);
/* 1087 */       if (this.path != null) {
/* 1088 */         TreeStateNode treeStateNode = (TreeStateNode)getParent();
/*      */         
/* 1090 */         if (treeStateNode != null) {
/* 1091 */           resetChildrenPaths(treeStateNode.getTreePath());
/*      */         } else {
/* 1093 */           resetChildrenPaths((TreePath)null);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Enumeration children() {
/* 1103 */       if (!isExpanded()) {
/* 1104 */         return DefaultMutableTreeNode.EMPTY_ENUMERATION;
/*      */       }
/* 1106 */       return super.children();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isLeaf() {
/* 1114 */       return VariableHeightLayoutCache.this.getModel().isLeaf(getValue());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Rectangle getNodeBounds(Rectangle param1Rectangle) {
/* 1125 */       if (param1Rectangle == null) {
/*      */ 
/*      */         
/* 1128 */         param1Rectangle = new Rectangle(getXOrigin(), getYOrigin(), getPreferredWidth(), getPreferredHeight());
/*      */       } else {
/* 1130 */         param1Rectangle.x = getXOrigin();
/* 1131 */         param1Rectangle.y = getYOrigin();
/* 1132 */         param1Rectangle.width = getPreferredWidth();
/* 1133 */         param1Rectangle.height = getPreferredHeight();
/*      */       } 
/* 1135 */       return param1Rectangle;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getXOrigin() {
/* 1142 */       if (!hasValidSize())
/* 1143 */         updatePreferredSize(getRow()); 
/* 1144 */       return this.xOrigin;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getYOrigin() {
/* 1151 */       if (VariableHeightLayoutCache.this.isFixedRowHeight()) {
/* 1152 */         int i = getRow();
/*      */         
/* 1154 */         if (i == -1)
/* 1155 */           return -1; 
/* 1156 */         return VariableHeightLayoutCache.this.getRowHeight() * i;
/*      */       } 
/* 1158 */       return this.yOrigin;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getPreferredHeight() {
/* 1165 */       if (VariableHeightLayoutCache.this.isFixedRowHeight())
/* 1166 */         return VariableHeightLayoutCache.this.getRowHeight(); 
/* 1167 */       if (!hasValidSize())
/* 1168 */         updatePreferredSize(getRow()); 
/* 1169 */       return this.preferredHeight;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getPreferredWidth() {
/* 1176 */       if (!hasValidSize())
/* 1177 */         updatePreferredSize(getRow()); 
/* 1178 */       return this.preferredWidth;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasValidSize() {
/* 1185 */       return (this.preferredHeight != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getRow() {
/* 1192 */       return VariableHeightLayoutCache.this.visibleNodes.indexOf(this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasBeenExpanded() {
/* 1199 */       return this.hasBeenExpanded;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isExpanded() {
/* 1206 */       return this.expanded;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TreeStateNode getLastVisibleNode() {
/* 1214 */       TreeStateNode treeStateNode = this;
/*      */       
/* 1216 */       while (treeStateNode.isExpanded() && treeStateNode.getChildCount() > 0)
/* 1217 */         treeStateNode = (TreeStateNode)treeStateNode.getLastChild(); 
/* 1218 */       return treeStateNode;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isVisible() {
/* 1225 */       if (this == VariableHeightLayoutCache.this.root) {
/* 1226 */         return true;
/*      */       }
/* 1228 */       TreeStateNode treeStateNode = (TreeStateNode)getParent();
/*      */       
/* 1230 */       return (treeStateNode != null && treeStateNode.isExpanded() && treeStateNode
/* 1231 */         .isVisible());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getModelChildCount() {
/* 1239 */       if (this.hasBeenExpanded)
/* 1240 */         return getChildCount(); 
/* 1241 */       return VariableHeightLayoutCache.this.getModel().getChildCount(getValue());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getVisibleChildCount() {
/* 1249 */       int i = 0;
/*      */       
/* 1251 */       if (isExpanded()) {
/* 1252 */         int j = getChildCount();
/*      */         
/* 1254 */         i += j;
/* 1255 */         for (byte b = 0; b < j; b++)
/* 1256 */           i += ((TreeStateNode)getChildAt(b))
/* 1257 */             .getVisibleChildCount(); 
/*      */       } 
/* 1259 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void toggleExpanded() {
/* 1266 */       if (isExpanded()) {
/* 1267 */         collapse();
/*      */       } else {
/* 1269 */         expand();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void makeVisible() {
/* 1278 */       TreeStateNode treeStateNode = (TreeStateNode)getParent();
/*      */       
/* 1280 */       if (treeStateNode != null) {
/* 1281 */         treeStateNode.expandParentAndReceiver();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void expand() {
/* 1288 */       expand(true);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void collapse() {
/* 1295 */       collapse(true);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object getValue() {
/* 1303 */       return getUserObject();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TreePath getTreePath() {
/* 1310 */       return this.path;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void resetChildrenPaths(TreePath param1TreePath) {
/* 1321 */       VariableHeightLayoutCache.this.removeMapping(this);
/* 1322 */       if (param1TreePath == null) {
/* 1323 */         this.path = new TreePath(getUserObject());
/*      */       } else {
/* 1325 */         this.path = param1TreePath.pathByAddingChild(getUserObject());
/* 1326 */       }  VariableHeightLayoutCache.this.addMapping(this);
/* 1327 */       for (int i = getChildCount() - 1; i >= 0; i--) {
/* 1328 */         ((TreeStateNode)getChildAt(i)).resetChildrenPaths(this.path);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void setYOrigin(int param1Int) {
/* 1336 */       this.yOrigin = param1Int;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void shiftYOriginBy(int param1Int) {
/* 1343 */       this.yOrigin += param1Int;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void updatePreferredSize() {
/* 1351 */       updatePreferredSize(getRow());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void updatePreferredSize(int param1Int) {
/* 1360 */       Rectangle rectangle = VariableHeightLayoutCache.this.getNodeDimensions(getUserObject(), param1Int, 
/* 1361 */           getLevel(), 
/* 1362 */           isExpanded(), VariableHeightLayoutCache.this
/* 1363 */           .boundsBuffer);
/*      */       
/* 1365 */       if (rectangle == null) {
/* 1366 */         this.xOrigin = 0;
/* 1367 */         this.preferredWidth = this.preferredHeight = 0;
/* 1368 */         VariableHeightLayoutCache.this.updateNodeSizes = true;
/*      */       }
/* 1370 */       else if (rectangle.height == 0) {
/* 1371 */         this.xOrigin = 0;
/* 1372 */         this.preferredWidth = this.preferredHeight = 0;
/* 1373 */         VariableHeightLayoutCache.this.updateNodeSizes = true;
/*      */       } else {
/*      */         
/* 1376 */         this.xOrigin = rectangle.x;
/* 1377 */         this.preferredWidth = rectangle.width;
/* 1378 */         if (VariableHeightLayoutCache.this.isFixedRowHeight()) {
/* 1379 */           this.preferredHeight = VariableHeightLayoutCache.this.getRowHeight();
/*      */         } else {
/* 1381 */           this.preferredHeight = rectangle.height;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void markSizeInvalid() {
/* 1390 */       this.preferredHeight = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void deepMarkSizeInvalid() {
/* 1397 */       markSizeInvalid();
/* 1398 */       for (int i = getChildCount() - 1; i >= 0; i--) {
/* 1399 */         ((TreeStateNode)getChildAt(i)).deepMarkSizeInvalid();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Enumeration getLoadedChildren(boolean param1Boolean) {
/* 1409 */       if (!param1Boolean || this.hasBeenExpanded) {
/* 1410 */         return super.children();
/*      */       }
/*      */       
/* 1413 */       Object object = getValue();
/* 1414 */       TreeModel treeModel = VariableHeightLayoutCache.this.getModel();
/* 1415 */       int i = treeModel.getChildCount(object);
/*      */       
/* 1417 */       this.hasBeenExpanded = true;
/*      */       
/* 1419 */       int j = getRow();
/*      */       
/* 1421 */       if (j == -1) {
/* 1422 */         for (byte b = 0; b < i; b++) {
/* 1423 */           TreeStateNode treeStateNode = VariableHeightLayoutCache.this.createNodeForValue(treeModel
/* 1424 */               .getChild(object, b));
/* 1425 */           add(treeStateNode);
/* 1426 */           treeStateNode.updatePreferredSize(-1);
/*      */         } 
/*      */       } else {
/*      */         
/* 1430 */         j++;
/* 1431 */         for (byte b = 0; b < i; b++) {
/* 1432 */           TreeStateNode treeStateNode = VariableHeightLayoutCache.this.createNodeForValue(treeModel
/* 1433 */               .getChild(object, b));
/* 1434 */           add(treeStateNode);
/* 1435 */           treeStateNode.updatePreferredSize(j++);
/*      */         } 
/*      */       } 
/* 1438 */       return super.children();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void didAdjustTree() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void expandParentAndReceiver() {
/* 1453 */       TreeStateNode treeStateNode = (TreeStateNode)getParent();
/*      */       
/* 1455 */       if (treeStateNode != null)
/* 1456 */         treeStateNode.expandParentAndReceiver(); 
/* 1457 */       expand();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void expand(boolean param1Boolean) {
/* 1467 */       if (!isExpanded() && !isLeaf()) {
/* 1468 */         int m; boolean bool = VariableHeightLayoutCache.this.isFixedRowHeight();
/* 1469 */         int i = getPreferredHeight();
/* 1470 */         int j = getRow();
/*      */         
/* 1472 */         this.expanded = true;
/* 1473 */         updatePreferredSize(j);
/*      */         
/* 1475 */         if (!this.hasBeenExpanded) {
/*      */           
/* 1477 */           Object object = getValue();
/* 1478 */           TreeModel treeModel = VariableHeightLayoutCache.this.getModel();
/* 1479 */           int n = treeModel.getChildCount(object);
/*      */           
/* 1481 */           this.hasBeenExpanded = true;
/* 1482 */           if (j == -1) {
/* 1483 */             for (byte b = 0; b < n; b++) {
/* 1484 */               TreeStateNode treeStateNode = VariableHeightLayoutCache.this.createNodeForValue(treeModel
/* 1485 */                   .getChild(object, b));
/* 1486 */               add(treeStateNode);
/* 1487 */               treeStateNode.updatePreferredSize(-1);
/*      */             } 
/*      */           } else {
/*      */             
/* 1491 */             int i1 = j + 1;
/* 1492 */             for (byte b = 0; b < n; b++) {
/* 1493 */               TreeStateNode treeStateNode = VariableHeightLayoutCache.this.createNodeForValue(treeModel
/* 1494 */                   .getChild(object, b));
/* 1495 */               add(treeStateNode);
/* 1496 */               treeStateNode.updatePreferredSize(i1);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/* 1501 */         int k = j;
/* 1502 */         Enumeration<TreeStateNode> enumeration = preorderEnumeration();
/* 1503 */         enumeration.nextElement();
/*      */ 
/*      */ 
/*      */         
/* 1507 */         if (bool) {
/* 1508 */           m = 0;
/* 1509 */         } else if (this == VariableHeightLayoutCache.this.root && !VariableHeightLayoutCache.this.isRootVisible()) {
/* 1510 */           m = 0;
/*      */         } else {
/* 1512 */           m = getYOrigin() + getPreferredHeight();
/*      */         } 
/* 1514 */         if (!bool) {
/* 1515 */           while (enumeration.hasMoreElements()) {
/* 1516 */             TreeStateNode treeStateNode = enumeration.nextElement();
/* 1517 */             if (!VariableHeightLayoutCache.this.updateNodeSizes && !treeStateNode.hasValidSize())
/* 1518 */               treeStateNode.updatePreferredSize(k + 1); 
/* 1519 */             treeStateNode.setYOrigin(m);
/* 1520 */             m += treeStateNode.getPreferredHeight();
/* 1521 */             VariableHeightLayoutCache.this.visibleNodes.insertElementAt(treeStateNode, ++k);
/*      */           } 
/*      */         } else {
/*      */           
/* 1525 */           while (enumeration.hasMoreElements()) {
/* 1526 */             TreeStateNode treeStateNode = enumeration.nextElement();
/* 1527 */             VariableHeightLayoutCache.this.visibleNodes.insertElementAt(treeStateNode, ++k);
/*      */           } 
/*      */         } 
/*      */         
/* 1531 */         if (param1Boolean && (j != k || 
/* 1532 */           getPreferredHeight() != i)) {
/*      */           
/* 1534 */           if (!bool && ++k < VariableHeightLayoutCache.this.getRowCount()) {
/*      */ 
/*      */ 
/*      */             
/* 1538 */             int i1 = m - getYOrigin() + getPreferredHeight() + getPreferredHeight() - i;
/*      */             
/* 1540 */             for (int n = VariableHeightLayoutCache.this.visibleNodes.size() - 1; n >= k; 
/* 1541 */               n--)
/* 1542 */               ((TreeStateNode)VariableHeightLayoutCache.this.visibleNodes.elementAt(n))
/* 1543 */                 .shiftYOriginBy(i1); 
/*      */           } 
/* 1545 */           didAdjustTree();
/* 1546 */           VariableHeightLayoutCache.this.visibleNodesChanged();
/*      */         } 
/*      */ 
/*      */         
/* 1550 */         if (VariableHeightLayoutCache.this.treeSelectionModel != null) {
/* 1551 */           VariableHeightLayoutCache.this.treeSelectionModel.resetRowSelection();
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void collapse(boolean param1Boolean) {
/* 1561 */       if (isExpanded()) {
/* 1562 */         int i; Enumeration<TreeStateNode> enumeration = preorderEnumeration();
/* 1563 */         enumeration.nextElement();
/* 1564 */         byte b = 0;
/* 1565 */         boolean bool = VariableHeightLayoutCache.this.isFixedRowHeight();
/*      */         
/* 1567 */         if (bool) {
/* 1568 */           i = 0;
/*      */         } else {
/* 1570 */           i = getPreferredHeight() + getYOrigin();
/* 1571 */         }  int j = getPreferredHeight();
/* 1572 */         int k = i;
/* 1573 */         int m = getRow();
/*      */         
/* 1575 */         if (!bool) {
/* 1576 */           while (enumeration.hasMoreElements()) {
/*      */             
/* 1578 */             TreeStateNode treeStateNode = enumeration.nextElement();
/* 1579 */             if (treeStateNode.isVisible()) {
/* 1580 */               b++;
/*      */ 
/*      */               
/* 1583 */               i = treeStateNode.getYOrigin() + treeStateNode.getPreferredHeight();
/*      */             } 
/*      */           } 
/*      */         } else {
/*      */           
/* 1588 */           while (enumeration.hasMoreElements()) {
/*      */             
/* 1590 */             TreeStateNode treeStateNode = enumeration.nextElement();
/* 1591 */             if (treeStateNode.isVisible()) {
/* 1592 */               b++;
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/*      */         int n;
/*      */         
/* 1599 */         for (n = b + m; n > m; 
/* 1600 */           n--) {
/* 1601 */           VariableHeightLayoutCache.this.visibleNodes.removeElementAt(n);
/*      */         }
/*      */         
/* 1604 */         this.expanded = false;
/*      */         
/* 1606 */         if (m == -1) {
/* 1607 */           markSizeInvalid();
/* 1608 */         } else if (param1Boolean) {
/* 1609 */           updatePreferredSize(m);
/*      */         } 
/* 1611 */         if (m != -1 && param1Boolean && (b > 0 || j != 
/* 1612 */           getPreferredHeight())) {
/*      */           
/* 1614 */           k += getPreferredHeight() - j;
/* 1615 */           if (!bool && m + 1 < VariableHeightLayoutCache.this.getRowCount() && k != i) {
/*      */ 
/*      */ 
/*      */             
/* 1619 */             int i2 = k - i;
/* 1620 */             n = m + 1;
/* 1621 */             int i1 = VariableHeightLayoutCache.this.visibleNodes.size();
/* 1622 */             for (; n < i1; n++)
/* 1623 */               ((TreeStateNode)VariableHeightLayoutCache.this.visibleNodes.elementAt(n))
/* 1624 */                 .shiftYOriginBy(i2); 
/*      */           } 
/* 1626 */           didAdjustTree();
/* 1627 */           VariableHeightLayoutCache.this.visibleNodesChanged();
/*      */         } 
/* 1629 */         if (VariableHeightLayoutCache.this.treeSelectionModel != null && b > 0 && m != -1)
/*      */         {
/* 1631 */           VariableHeightLayoutCache.this.treeSelectionModel.resetRowSelection();
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void removeFromMapping() {
/* 1641 */       if (this.path != null) {
/* 1642 */         VariableHeightLayoutCache.this.removeMapping(this);
/* 1643 */         for (int i = getChildCount() - 1; i >= 0; i--) {
/* 1644 */           ((TreeStateNode)getChildAt(i)).removeFromMapping();
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class VisibleTreeStateNodeEnumeration
/*      */     implements Enumeration<TreePath>
/*      */   {
/*      */     protected VariableHeightLayoutCache.TreeStateNode parent;
/*      */ 
/*      */     
/*      */     protected int nextIndex;
/*      */     
/*      */     protected int childCount;
/*      */ 
/*      */     
/*      */     protected VisibleTreeStateNodeEnumeration(VariableHeightLayoutCache.TreeStateNode param1TreeStateNode) {
/* 1664 */       this(param1TreeStateNode, -1);
/*      */     }
/*      */ 
/*      */     
/*      */     protected VisibleTreeStateNodeEnumeration(VariableHeightLayoutCache.TreeStateNode param1TreeStateNode, int param1Int) {
/* 1669 */       this.parent = param1TreeStateNode;
/* 1670 */       this.nextIndex = param1Int;
/* 1671 */       this.childCount = this.parent.getChildCount();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasMoreElements() {
/* 1678 */       return (this.parent != null);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public TreePath nextElement() {
/*      */       TreePath treePath;
/* 1685 */       if (!hasMoreElements()) {
/* 1686 */         throw new NoSuchElementException("No more visible paths");
/*      */       }
/*      */ 
/*      */       
/* 1690 */       if (this.nextIndex == -1) {
/* 1691 */         treePath = this.parent.getTreePath();
/*      */       }
/*      */       else {
/*      */         
/* 1695 */         VariableHeightLayoutCache.TreeStateNode treeStateNode = (VariableHeightLayoutCache.TreeStateNode)this.parent.getChildAt(this.nextIndex);
/*      */         
/* 1697 */         treePath = treeStateNode.getTreePath();
/*      */       } 
/* 1699 */       updateNextObject();
/* 1700 */       return treePath;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void updateNextObject() {
/* 1708 */       if (!updateNextIndex()) {
/* 1709 */         findNextValidParent();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean findNextValidParent() {
/* 1718 */       if (this.parent == VariableHeightLayoutCache.this.root) {
/*      */         
/* 1720 */         this.parent = null;
/* 1721 */         return false;
/*      */       } 
/* 1723 */       while (this.parent != null) {
/*      */         
/* 1725 */         VariableHeightLayoutCache.TreeStateNode treeStateNode = (VariableHeightLayoutCache.TreeStateNode)this.parent.getParent();
/*      */         
/* 1727 */         if (treeStateNode != null) {
/* 1728 */           this.nextIndex = treeStateNode.getIndex(this.parent);
/* 1729 */           this.parent = treeStateNode;
/* 1730 */           this.childCount = this.parent.getChildCount();
/* 1731 */           if (updateNextIndex())
/* 1732 */             return true; 
/*      */           continue;
/*      */         } 
/* 1735 */         this.parent = null;
/*      */       } 
/* 1737 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean updateNextIndex() {
/* 1747 */       if (this.nextIndex == -1 && !this.parent.isExpanded()) {
/* 1748 */         return false;
/*      */       }
/*      */       
/* 1751 */       if (this.childCount == 0) {
/* 1752 */         return false;
/*      */       }
/* 1754 */       if (++this.nextIndex >= this.childCount) {
/* 1755 */         return false;
/*      */       }
/*      */       
/* 1758 */       VariableHeightLayoutCache.TreeStateNode treeStateNode = (VariableHeightLayoutCache.TreeStateNode)this.parent.getChildAt(this.nextIndex);
/*      */       
/* 1760 */       if (treeStateNode != null && treeStateNode.isExpanded()) {
/* 1761 */         this.parent = treeStateNode;
/* 1762 */         this.nextIndex = -1;
/* 1763 */         this.childCount = treeStateNode.getChildCount();
/*      */       } 
/* 1765 */       return true;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/tree/VariableHeightLayoutCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */