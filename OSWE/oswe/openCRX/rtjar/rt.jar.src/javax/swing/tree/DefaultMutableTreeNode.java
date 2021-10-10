/*      */ package javax.swing.tree;
/*      */ 
/*      */ import java.beans.Transient;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collections;
/*      */ import java.util.EmptyStackException;
/*      */ import java.util.Enumeration;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Stack;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DefaultMutableTreeNode
/*      */   implements Cloneable, MutableTreeNode, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -4298474751201349152L;
/*   98 */   public static final Enumeration<TreeNode> EMPTY_ENUMERATION = Collections.emptyEnumeration();
/*      */ 
/*      */ 
/*      */   
/*      */   protected MutableTreeNode parent;
/*      */ 
/*      */ 
/*      */   
/*      */   protected Vector children;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient Object userObject;
/*      */ 
/*      */   
/*      */   protected boolean allowsChildren;
/*      */ 
/*      */ 
/*      */   
/*      */   public DefaultMutableTreeNode() {
/*  118 */     this(null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DefaultMutableTreeNode(Object paramObject) {
/*  129 */     this(paramObject, true);
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
/*      */   public DefaultMutableTreeNode(Object paramObject, boolean paramBoolean) {
/*  144 */     this.parent = null;
/*  145 */     this.allowsChildren = paramBoolean;
/*  146 */     this.userObject = paramObject;
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
/*      */   public void insert(MutableTreeNode paramMutableTreeNode, int paramInt) {
/*  174 */     if (!this.allowsChildren)
/*  175 */       throw new IllegalStateException("node does not allow children"); 
/*  176 */     if (paramMutableTreeNode == null)
/*  177 */       throw new IllegalArgumentException("new child is null"); 
/*  178 */     if (isNodeAncestor(paramMutableTreeNode)) {
/*  179 */       throw new IllegalArgumentException("new child is an ancestor");
/*      */     }
/*      */     
/*  182 */     MutableTreeNode mutableTreeNode = (MutableTreeNode)paramMutableTreeNode.getParent();
/*      */     
/*  184 */     if (mutableTreeNode != null) {
/*  185 */       mutableTreeNode.remove(paramMutableTreeNode);
/*      */     }
/*  187 */     paramMutableTreeNode.setParent(this);
/*  188 */     if (this.children == null) {
/*  189 */       this.children = new Vector();
/*      */     }
/*  191 */     this.children.insertElementAt(paramMutableTreeNode, paramInt);
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
/*      */   public void remove(int paramInt) {
/*  205 */     MutableTreeNode mutableTreeNode = (MutableTreeNode)getChildAt(paramInt);
/*  206 */     this.children.removeElementAt(paramInt);
/*  207 */     mutableTreeNode.setParent(null);
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
/*      */   @Transient
/*      */   public void setParent(MutableTreeNode paramMutableTreeNode) {
/*  221 */     this.parent = paramMutableTreeNode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TreeNode getParent() {
/*  230 */     return this.parent;
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
/*      */   public TreeNode getChildAt(int paramInt) {
/*  242 */     if (this.children == null) {
/*  243 */       throw new ArrayIndexOutOfBoundsException("node has no children");
/*      */     }
/*  245 */     return this.children.elementAt(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getChildCount() {
/*  254 */     if (this.children == null) {
/*  255 */       return 0;
/*      */     }
/*  257 */     return this.children.size();
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
/*      */   public int getIndex(TreeNode paramTreeNode) {
/*  275 */     if (paramTreeNode == null) {
/*  276 */       throw new IllegalArgumentException("argument is null");
/*      */     }
/*      */     
/*  279 */     if (!isNodeChild(paramTreeNode)) {
/*  280 */       return -1;
/*      */     }
/*  282 */     return this.children.indexOf(paramTreeNode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Enumeration children() {
/*  293 */     if (this.children == null) {
/*  294 */       return EMPTY_ENUMERATION;
/*      */     }
/*  296 */     return this.children.elements();
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
/*      */   public void setAllowsChildren(boolean paramBoolean) {
/*  310 */     if (paramBoolean != this.allowsChildren) {
/*  311 */       this.allowsChildren = paramBoolean;
/*  312 */       if (!this.allowsChildren) {
/*  313 */         removeAllChildren();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAllowsChildren() {
/*  324 */     return this.allowsChildren;
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
/*      */   public void setUserObject(Object paramObject) {
/*  336 */     this.userObject = paramObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getUserObject() {
/*  347 */     return this.userObject;
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
/*      */   public void removeFromParent() {
/*  361 */     MutableTreeNode mutableTreeNode = (MutableTreeNode)getParent();
/*  362 */     if (mutableTreeNode != null) {
/*  363 */       mutableTreeNode.remove(this);
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
/*      */   public void remove(MutableTreeNode paramMutableTreeNode) {
/*  376 */     if (paramMutableTreeNode == null) {
/*  377 */       throw new IllegalArgumentException("argument is null");
/*      */     }
/*      */     
/*  380 */     if (!isNodeChild(paramMutableTreeNode)) {
/*  381 */       throw new IllegalArgumentException("argument is not a child");
/*      */     }
/*  383 */     remove(getIndex(paramMutableTreeNode));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAllChildren() {
/*  391 */     for (int i = getChildCount() - 1; i >= 0; i--) {
/*  392 */       remove(i);
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
/*      */   public void add(MutableTreeNode paramMutableTreeNode) {
/*  408 */     if (paramMutableTreeNode != null && paramMutableTreeNode.getParent() == this) {
/*  409 */       insert(paramMutableTreeNode, getChildCount() - 1);
/*      */     } else {
/*  411 */       insert(paramMutableTreeNode, getChildCount());
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
/*      */   public boolean isNodeAncestor(TreeNode paramTreeNode) {
/*  434 */     if (paramTreeNode == null) {
/*  435 */       return false;
/*      */     }
/*      */     
/*  438 */     DefaultMutableTreeNode defaultMutableTreeNode = this;
/*      */     TreeNode treeNode;
/*      */     do {
/*  441 */       if (defaultMutableTreeNode == paramTreeNode) {
/*  442 */         return true;
/*      */       }
/*  444 */     } while ((treeNode = defaultMutableTreeNode.getParent()) != null);
/*      */     
/*  446 */     return false;
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
/*      */   public boolean isNodeDescendant(DefaultMutableTreeNode paramDefaultMutableTreeNode) {
/*  463 */     if (paramDefaultMutableTreeNode == null) {
/*  464 */       return false;
/*      */     }
/*  466 */     return paramDefaultMutableTreeNode.isNodeAncestor(this);
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
/*      */   public TreeNode getSharedAncestor(DefaultMutableTreeNode paramDefaultMutableTreeNode) {
/*      */     int k;
/*      */     TreeNode treeNode1, treeNode2;
/*  482 */     if (paramDefaultMutableTreeNode == this)
/*  483 */       return this; 
/*  484 */     if (paramDefaultMutableTreeNode == null) {
/*  485 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  491 */     int i = getLevel();
/*  492 */     int j = paramDefaultMutableTreeNode.getLevel();
/*      */     
/*  494 */     if (j > i) {
/*  495 */       k = j - i;
/*  496 */       treeNode1 = paramDefaultMutableTreeNode;
/*  497 */       treeNode2 = this;
/*      */     } else {
/*  499 */       k = i - j;
/*  500 */       treeNode1 = this;
/*  501 */       treeNode2 = paramDefaultMutableTreeNode;
/*      */     } 
/*      */ 
/*      */     
/*  505 */     while (k > 0) {
/*  506 */       treeNode1 = treeNode1.getParent();
/*  507 */       k--;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     do {
/*  516 */       if (treeNode1 == treeNode2) {
/*  517 */         return treeNode1;
/*      */       }
/*  519 */       treeNode1 = treeNode1.getParent();
/*  520 */       treeNode2 = treeNode2.getParent();
/*  521 */     } while (treeNode1 != null);
/*      */ 
/*      */     
/*  524 */     if (treeNode1 != null || treeNode2 != null) {
/*  525 */       throw new Error("nodes should be null");
/*      */     }
/*      */     
/*  528 */     return null;
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
/*      */   public boolean isNodeRelated(DefaultMutableTreeNode paramDefaultMutableTreeNode) {
/*  542 */     return (paramDefaultMutableTreeNode != null && getRoot() == paramDefaultMutableTreeNode.getRoot());
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
/*      */   public int getDepth() {
/*  557 */     DefaultMutableTreeNode defaultMutableTreeNode = null;
/*  558 */     Enumeration<Object> enumeration = breadthFirstEnumeration();
/*      */     
/*  560 */     while (enumeration.hasMoreElements()) {
/*  561 */       defaultMutableTreeNode = (DefaultMutableTreeNode)enumeration.nextElement();
/*      */     }
/*      */     
/*  564 */     if (defaultMutableTreeNode == null) {
/*  565 */       throw new Error("nodes should be null");
/*      */     }
/*      */     
/*  568 */     return ((DefaultMutableTreeNode)defaultMutableTreeNode).getLevel() - getLevel();
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
/*      */   public int getLevel() {
/*  582 */     byte b = 0;
/*      */     
/*  584 */     DefaultMutableTreeNode defaultMutableTreeNode = this; TreeNode treeNode;
/*  585 */     while ((treeNode = defaultMutableTreeNode.getParent()) != null) {
/*  586 */       b++;
/*      */     }
/*      */     
/*  589 */     return b;
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
/*      */   public TreeNode[] getPath() {
/*  602 */     return getPathToRoot(this, 0);
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
/*      */   protected TreeNode[] getPathToRoot(TreeNode paramTreeNode, int paramInt) {
/*      */     TreeNode[] arrayOfTreeNode;
/*  622 */     if (paramTreeNode == null) {
/*  623 */       if (paramInt == 0) {
/*  624 */         return null;
/*      */       }
/*  626 */       arrayOfTreeNode = new TreeNode[paramInt];
/*      */     } else {
/*      */       
/*  629 */       paramInt++;
/*  630 */       arrayOfTreeNode = getPathToRoot(paramTreeNode.getParent(), paramInt);
/*  631 */       arrayOfTreeNode[arrayOfTreeNode.length - paramInt] = paramTreeNode;
/*      */     } 
/*  633 */     return arrayOfTreeNode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object[] getUserObjectPath() {
/*  642 */     TreeNode[] arrayOfTreeNode = getPath();
/*  643 */     Object[] arrayOfObject = new Object[arrayOfTreeNode.length];
/*      */     
/*  645 */     for (byte b = 0; b < arrayOfTreeNode.length; b++)
/*  646 */       arrayOfObject[b] = ((DefaultMutableTreeNode)arrayOfTreeNode[b])
/*  647 */         .getUserObject(); 
/*  648 */     return arrayOfObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TreeNode getRoot() {
/*      */     DefaultMutableTreeNode defaultMutableTreeNode;
/*  659 */     TreeNode treeNode = this;
/*      */ 
/*      */     
/*      */     do {
/*  663 */       defaultMutableTreeNode = (DefaultMutableTreeNode)treeNode;
/*  664 */       treeNode = treeNode.getParent();
/*  665 */     } while (treeNode != null);
/*      */     
/*  667 */     return defaultMutableTreeNode;
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
/*      */   public boolean isRoot() {
/*  679 */     return (getParent() == null);
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
/*      */   public DefaultMutableTreeNode getNextNode() {
/*  694 */     if (getChildCount() == 0) {
/*      */       
/*  696 */       DefaultMutableTreeNode defaultMutableTreeNode = getNextSibling();
/*      */       
/*  698 */       if (defaultMutableTreeNode == null) {
/*  699 */         DefaultMutableTreeNode defaultMutableTreeNode1 = (DefaultMutableTreeNode)getParent();
/*      */         
/*      */         while (true) {
/*  702 */           if (defaultMutableTreeNode1 == null) {
/*  703 */             return null;
/*      */           }
/*      */           
/*  706 */           defaultMutableTreeNode = defaultMutableTreeNode1.getNextSibling();
/*  707 */           if (defaultMutableTreeNode != null) {
/*  708 */             return defaultMutableTreeNode;
/*      */           }
/*      */           
/*  711 */           defaultMutableTreeNode1 = (DefaultMutableTreeNode)defaultMutableTreeNode1.getParent();
/*      */         } 
/*      */       } 
/*  714 */       return defaultMutableTreeNode;
/*      */     } 
/*      */     
/*  717 */     return (DefaultMutableTreeNode)getChildAt(0);
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
/*      */   public DefaultMutableTreeNode getPreviousNode() {
/*  735 */     DefaultMutableTreeNode defaultMutableTreeNode2 = (DefaultMutableTreeNode)getParent();
/*      */     
/*  737 */     if (defaultMutableTreeNode2 == null) {
/*  738 */       return null;
/*      */     }
/*      */     
/*  741 */     DefaultMutableTreeNode defaultMutableTreeNode1 = getPreviousSibling();
/*      */     
/*  743 */     if (defaultMutableTreeNode1 != null) {
/*  744 */       if (defaultMutableTreeNode1.getChildCount() == 0) {
/*  745 */         return defaultMutableTreeNode1;
/*      */       }
/*  747 */       return defaultMutableTreeNode1.getLastLeaf();
/*      */     } 
/*  749 */     return defaultMutableTreeNode2;
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
/*      */   public Enumeration preorderEnumeration() {
/*  765 */     return new PreorderEnumeration(this);
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
/*      */   public Enumeration postorderEnumeration() {
/*  782 */     return new PostorderEnumeration(this);
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
/*      */   public Enumeration breadthFirstEnumeration() {
/*  797 */     return new BreadthFirstEnumeration(this);
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
/*      */   public Enumeration depthFirstEnumeration() {
/*  814 */     return postorderEnumeration();
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
/*      */   public Enumeration pathFromAncestorEnumeration(TreeNode paramTreeNode) {
/*  838 */     return new PathBetweenNodesEnumeration(paramTreeNode, this);
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
/*      */   public boolean isNodeChild(TreeNode paramTreeNode) {
/*      */     boolean bool;
/*  856 */     if (paramTreeNode == null) {
/*  857 */       bool = false;
/*      */     }
/*  859 */     else if (getChildCount() == 0) {
/*  860 */       bool = false;
/*      */     } else {
/*  862 */       bool = (paramTreeNode.getParent() == this) ? true : false;
/*      */     } 
/*      */ 
/*      */     
/*  866 */     return bool;
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
/*      */   public TreeNode getFirstChild() {
/*  878 */     if (getChildCount() == 0) {
/*  879 */       throw new NoSuchElementException("node has no children");
/*      */     }
/*  881 */     return getChildAt(0);
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
/*      */   public TreeNode getLastChild() {
/*  893 */     if (getChildCount() == 0) {
/*  894 */       throw new NoSuchElementException("node has no children");
/*      */     }
/*  896 */     return getChildAt(getChildCount() - 1);
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
/*      */   public TreeNode getChildAfter(TreeNode paramTreeNode) {
/*  915 */     if (paramTreeNode == null) {
/*  916 */       throw new IllegalArgumentException("argument is null");
/*      */     }
/*      */     
/*  919 */     int i = getIndex(paramTreeNode);
/*      */     
/*  921 */     if (i == -1) {
/*  922 */       throw new IllegalArgumentException("node is not a child");
/*      */     }
/*      */     
/*  925 */     if (i < getChildCount() - 1) {
/*  926 */       return getChildAt(i + 1);
/*      */     }
/*  928 */     return null;
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
/*      */   public TreeNode getChildBefore(TreeNode paramTreeNode) {
/*  946 */     if (paramTreeNode == null) {
/*  947 */       throw new IllegalArgumentException("argument is null");
/*      */     }
/*      */     
/*  950 */     int i = getIndex(paramTreeNode);
/*      */     
/*  952 */     if (i == -1) {
/*  953 */       throw new IllegalArgumentException("argument is not a child");
/*      */     }
/*      */     
/*  956 */     if (i > 0) {
/*  957 */       return getChildAt(i - 1);
/*      */     }
/*  959 */     return null;
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
/*      */   public boolean isNodeSibling(TreeNode paramTreeNode) {
/*      */     boolean bool;
/*  980 */     if (paramTreeNode == null) {
/*  981 */       bool = false;
/*  982 */     } else if (paramTreeNode == this) {
/*  983 */       bool = true;
/*      */     } else {
/*  985 */       TreeNode treeNode = getParent();
/*  986 */       bool = (treeNode != null && treeNode == paramTreeNode.getParent()) ? true : false;
/*      */       
/*  988 */       if (bool && 
/*  989 */         !((DefaultMutableTreeNode)getParent()).isNodeChild(paramTreeNode)) {
/*  990 */         throw new Error("sibling has different parent");
/*      */       }
/*      */     } 
/*      */     
/*  994 */     return bool;
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
/*      */   public int getSiblingCount() {
/* 1006 */     TreeNode treeNode = getParent();
/*      */     
/* 1008 */     if (treeNode == null) {
/* 1009 */       return 1;
/*      */     }
/* 1011 */     return treeNode.getChildCount();
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
/*      */   public DefaultMutableTreeNode getNextSibling() {
/* 1029 */     DefaultMutableTreeNode defaultMutableTreeNode1, defaultMutableTreeNode2 = (DefaultMutableTreeNode)getParent();
/*      */     
/* 1031 */     if (defaultMutableTreeNode2 == null) {
/* 1032 */       defaultMutableTreeNode1 = null;
/*      */     } else {
/* 1034 */       defaultMutableTreeNode1 = (DefaultMutableTreeNode)defaultMutableTreeNode2.getChildAfter(this);
/*      */     } 
/*      */     
/* 1037 */     if (defaultMutableTreeNode1 != null && !isNodeSibling(defaultMutableTreeNode1)) {
/* 1038 */       throw new Error("child of parent is not a sibling");
/*      */     }
/*      */     
/* 1041 */     return defaultMutableTreeNode1;
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
/*      */   public DefaultMutableTreeNode getPreviousSibling() {
/* 1056 */     DefaultMutableTreeNode defaultMutableTreeNode1, defaultMutableTreeNode2 = (DefaultMutableTreeNode)getParent();
/*      */     
/* 1058 */     if (defaultMutableTreeNode2 == null) {
/* 1059 */       defaultMutableTreeNode1 = null;
/*      */     } else {
/* 1061 */       defaultMutableTreeNode1 = (DefaultMutableTreeNode)defaultMutableTreeNode2.getChildBefore(this);
/*      */     } 
/*      */     
/* 1064 */     if (defaultMutableTreeNode1 != null && !isNodeSibling(defaultMutableTreeNode1)) {
/* 1065 */       throw new Error("child of parent is not a sibling");
/*      */     }
/*      */     
/* 1068 */     return defaultMutableTreeNode1;
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
/*      */   public boolean isLeaf() {
/* 1087 */     return (getChildCount() == 0);
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
/*      */   public DefaultMutableTreeNode getFirstLeaf() {
/* 1101 */     DefaultMutableTreeNode defaultMutableTreeNode = this;
/*      */     
/* 1103 */     while (!defaultMutableTreeNode.isLeaf()) {
/* 1104 */       defaultMutableTreeNode = (DefaultMutableTreeNode)defaultMutableTreeNode.getFirstChild();
/*      */     }
/*      */     
/* 1107 */     return defaultMutableTreeNode;
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
/*      */   public DefaultMutableTreeNode getLastLeaf() {
/* 1121 */     DefaultMutableTreeNode defaultMutableTreeNode = this;
/*      */     
/* 1123 */     while (!defaultMutableTreeNode.isLeaf()) {
/* 1124 */       defaultMutableTreeNode = (DefaultMutableTreeNode)defaultMutableTreeNode.getLastChild();
/*      */     }
/*      */     
/* 1127 */     return defaultMutableTreeNode;
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
/*      */   public DefaultMutableTreeNode getNextLeaf() {
/* 1152 */     DefaultMutableTreeNode defaultMutableTreeNode2 = (DefaultMutableTreeNode)getParent();
/*      */     
/* 1154 */     if (defaultMutableTreeNode2 == null) {
/* 1155 */       return null;
/*      */     }
/* 1157 */     DefaultMutableTreeNode defaultMutableTreeNode1 = getNextSibling();
/*      */     
/* 1159 */     if (defaultMutableTreeNode1 != null) {
/* 1160 */       return defaultMutableTreeNode1.getFirstLeaf();
/*      */     }
/* 1162 */     return defaultMutableTreeNode2.getNextLeaf();
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
/*      */   public DefaultMutableTreeNode getPreviousLeaf() {
/* 1187 */     DefaultMutableTreeNode defaultMutableTreeNode2 = (DefaultMutableTreeNode)getParent();
/*      */     
/* 1189 */     if (defaultMutableTreeNode2 == null) {
/* 1190 */       return null;
/*      */     }
/* 1192 */     DefaultMutableTreeNode defaultMutableTreeNode1 = getPreviousSibling();
/*      */     
/* 1194 */     if (defaultMutableTreeNode1 != null) {
/* 1195 */       return defaultMutableTreeNode1.getLastLeaf();
/*      */     }
/* 1197 */     return defaultMutableTreeNode2.getPreviousLeaf();
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
/*      */   public int getLeafCount() {
/* 1210 */     byte b = 0;
/*      */ 
/*      */     
/* 1213 */     Enumeration<TreeNode> enumeration = breadthFirstEnumeration();
/*      */     
/* 1215 */     while (enumeration.hasMoreElements()) {
/* 1216 */       TreeNode treeNode = enumeration.nextElement();
/* 1217 */       if (treeNode.isLeaf()) {
/* 1218 */         b++;
/*      */       }
/*      */     } 
/*      */     
/* 1222 */     if (b < 1) {
/* 1223 */       throw new Error("tree has zero leaves");
/*      */     }
/*      */     
/* 1226 */     return b;
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
/*      */   public String toString() {
/* 1241 */     if (this.userObject == null) {
/* 1242 */       return "";
/*      */     }
/* 1244 */     return this.userObject.toString();
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
/*      */   public Object clone() {
/*      */     DefaultMutableTreeNode defaultMutableTreeNode;
/*      */     try {
/* 1259 */       defaultMutableTreeNode = (DefaultMutableTreeNode)super.clone();
/*      */ 
/*      */       
/* 1262 */       defaultMutableTreeNode.children = null;
/* 1263 */       defaultMutableTreeNode.parent = null;
/*      */     }
/* 1265 */     catch (CloneNotSupportedException cloneNotSupportedException) {
/*      */       
/* 1267 */       throw new Error(cloneNotSupportedException.toString());
/*      */     } 
/*      */     
/* 1270 */     return defaultMutableTreeNode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*      */     Object[] arrayOfObject;
/* 1278 */     paramObjectOutputStream.defaultWriteObject();
/*      */     
/* 1280 */     if (this.userObject != null && this.userObject instanceof Serializable) {
/* 1281 */       arrayOfObject = new Object[2];
/* 1282 */       arrayOfObject[0] = "userObject";
/* 1283 */       arrayOfObject[1] = this.userObject;
/*      */     } else {
/*      */       
/* 1286 */       arrayOfObject = new Object[0];
/* 1287 */     }  paramObjectOutputStream.writeObject(arrayOfObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1294 */     paramObjectInputStream.defaultReadObject();
/*      */     
/* 1296 */     Object[] arrayOfObject = (Object[])paramObjectInputStream.readObject();
/*      */     
/* 1298 */     if (arrayOfObject.length > 0 && arrayOfObject[0].equals("userObject"))
/* 1299 */       this.userObject = arrayOfObject[1]; 
/*      */   }
/*      */   
/*      */   private final class PreorderEnumeration implements Enumeration<TreeNode> {
/* 1303 */     private final Stack<Enumeration> stack = new Stack<>();
/*      */ 
/*      */     
/*      */     public PreorderEnumeration(TreeNode param1TreeNode) {
/* 1307 */       Vector<TreeNode> vector = new Vector(1);
/* 1308 */       vector.addElement(param1TreeNode);
/* 1309 */       this.stack.push(vector.elements());
/*      */     }
/*      */     
/*      */     public boolean hasMoreElements() {
/* 1313 */       return (!this.stack.empty() && ((Enumeration)this.stack.peek()).hasMoreElements());
/*      */     }
/*      */     
/*      */     public TreeNode nextElement() {
/* 1317 */       Enumeration<TreeNode> enumeration = this.stack.peek();
/* 1318 */       TreeNode treeNode = enumeration.nextElement();
/* 1319 */       Enumeration enumeration1 = treeNode.children();
/*      */       
/* 1321 */       if (!enumeration.hasMoreElements()) {
/* 1322 */         this.stack.pop();
/*      */       }
/* 1324 */       if (enumeration1.hasMoreElements()) {
/* 1325 */         this.stack.push(enumeration1);
/*      */       }
/* 1327 */       return treeNode;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   final class PostorderEnumeration
/*      */     implements Enumeration<TreeNode>
/*      */   {
/*      */     protected TreeNode root;
/*      */     
/*      */     protected Enumeration<TreeNode> children;
/*      */     protected Enumeration<TreeNode> subtree;
/*      */     
/*      */     public PostorderEnumeration(TreeNode param1TreeNode) {
/* 1341 */       this.root = param1TreeNode;
/* 1342 */       this.children = this.root.children();
/* 1343 */       this.subtree = DefaultMutableTreeNode.EMPTY_ENUMERATION;
/*      */     }
/*      */     
/*      */     public boolean hasMoreElements() {
/* 1347 */       return (this.root != null);
/*      */     }
/*      */ 
/*      */     
/*      */     public TreeNode nextElement() {
/*      */       TreeNode treeNode;
/* 1353 */       if (this.subtree.hasMoreElements()) {
/* 1354 */         treeNode = this.subtree.nextElement();
/* 1355 */       } else if (this.children.hasMoreElements()) {
/* 1356 */         this.subtree = new PostorderEnumeration(this.children.nextElement());
/* 1357 */         treeNode = this.subtree.nextElement();
/*      */       } else {
/* 1359 */         treeNode = this.root;
/* 1360 */         this.root = null;
/*      */       } 
/*      */       
/* 1363 */       return treeNode;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   final class BreadthFirstEnumeration
/*      */     implements Enumeration<TreeNode>
/*      */   {
/*      */     protected Queue queue;
/*      */ 
/*      */     
/*      */     public BreadthFirstEnumeration(TreeNode param1TreeNode) {
/* 1375 */       Vector<TreeNode> vector = new Vector(1);
/* 1376 */       vector.addElement(param1TreeNode);
/* 1377 */       this.queue = new Queue();
/* 1378 */       this.queue.enqueue(vector.elements());
/*      */     }
/*      */     
/*      */     public boolean hasMoreElements() {
/* 1382 */       return (!this.queue.isEmpty() && ((Enumeration)this.queue
/* 1383 */         .firstObject()).hasMoreElements());
/*      */     }
/*      */     
/*      */     public TreeNode nextElement() {
/* 1387 */       Enumeration<TreeNode> enumeration = (Enumeration)this.queue.firstObject();
/* 1388 */       TreeNode treeNode = enumeration.nextElement();
/* 1389 */       Enumeration enumeration1 = treeNode.children();
/*      */       
/* 1391 */       if (!enumeration.hasMoreElements()) {
/* 1392 */         this.queue.dequeue();
/*      */       }
/* 1394 */       if (enumeration1.hasMoreElements()) {
/* 1395 */         this.queue.enqueue(enumeration1);
/*      */       }
/* 1397 */       return treeNode;
/*      */     }
/*      */     
/*      */     final class Queue
/*      */     {
/*      */       QNode head;
/*      */       QNode tail;
/*      */       
/*      */       final class QNode {
/*      */         public Object object;
/*      */         public QNode next;
/*      */         
/*      */         public QNode(Object param3Object, QNode param3QNode) {
/* 1410 */           this.object = param3Object;
/* 1411 */           this.next = param3QNode;
/*      */         }
/*      */       }
/*      */       
/*      */       public void enqueue(Object param2Object) {
/* 1416 */         if (this.head == null) {
/* 1417 */           this.head = this.tail = new QNode(param2Object, null);
/*      */         } else {
/* 1419 */           this.tail.next = new QNode(param2Object, null);
/* 1420 */           this.tail = this.tail.next;
/*      */         } 
/*      */       }
/*      */       
/*      */       public Object dequeue() {
/* 1425 */         if (this.head == null) {
/* 1426 */           throw new NoSuchElementException("No more elements");
/*      */         }
/*      */         
/* 1429 */         Object object = this.head.object;
/* 1430 */         QNode qNode = this.head;
/* 1431 */         this.head = this.head.next;
/* 1432 */         if (this.head == null) {
/* 1433 */           this.tail = null;
/*      */         } else {
/* 1435 */           qNode.next = null;
/*      */         } 
/* 1437 */         return object;
/*      */       }
/*      */       
/*      */       public Object firstObject() {
/* 1441 */         if (this.head == null) {
/* 1442 */           throw new NoSuchElementException("No more elements");
/*      */         }
/*      */         
/* 1445 */         return this.head.object;
/*      */       }
/*      */       
/*      */       public boolean isEmpty() {
/* 1449 */         return (this.head == null);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final class PathBetweenNodesEnumeration
/*      */     implements Enumeration<TreeNode>
/*      */   {
/*      */     protected Stack<TreeNode> stack;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PathBetweenNodesEnumeration(TreeNode param1TreeNode1, TreeNode param1TreeNode2) {
/* 1466 */       if (param1TreeNode1 == null || param1TreeNode2 == null) {
/* 1467 */         throw new IllegalArgumentException("argument is null");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1472 */       this.stack = new Stack<>();
/* 1473 */       this.stack.push(param1TreeNode2);
/*      */       
/* 1475 */       TreeNode treeNode = param1TreeNode2;
/* 1476 */       while (treeNode != param1TreeNode1) {
/* 1477 */         treeNode = treeNode.getParent();
/* 1478 */         if (treeNode == null && param1TreeNode2 != param1TreeNode1) {
/* 1479 */           throw new IllegalArgumentException("node " + param1TreeNode1 + " is not an ancestor of " + param1TreeNode2);
/*      */         }
/*      */         
/* 1482 */         this.stack.push(treeNode);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean hasMoreElements() {
/* 1487 */       return (this.stack.size() > 0);
/*      */     }
/*      */     
/*      */     public TreeNode nextElement() {
/*      */       try {
/* 1492 */         return this.stack.pop();
/* 1493 */       } catch (EmptyStackException emptyStackException) {
/* 1494 */         throw new NoSuchElementException("No more elements");
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/tree/DefaultMutableTreeNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */