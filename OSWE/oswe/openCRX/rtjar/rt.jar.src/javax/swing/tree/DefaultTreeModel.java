/*     */ package javax.swing.tree;
/*     */ 
/*     */ import java.beans.ConstructorProperties;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Vector;
/*     */ import javax.swing.event.EventListenerList;
/*     */ import javax.swing.event.TreeModelEvent;
/*     */ import javax.swing.event.TreeModelListener;
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
/*     */ public class DefaultTreeModel
/*     */   implements Serializable, TreeModel
/*     */ {
/*     */   protected TreeNode root;
/*  56 */   protected EventListenerList listenerList = new EventListenerList();
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
/*     */   protected boolean asksAllowsChildren;
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
/*     */   @ConstructorProperties({"root"})
/*     */   public DefaultTreeModel(TreeNode paramTreeNode) {
/*  84 */     this(paramTreeNode, false);
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
/*     */   public DefaultTreeModel(TreeNode paramTreeNode, boolean paramBoolean) {
/*  99 */     this.root = paramTreeNode;
/* 100 */     this.asksAllowsChildren = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAsksAllowsChildren(boolean paramBoolean) {
/* 109 */     this.asksAllowsChildren = paramBoolean;
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
/*     */   public boolean asksAllowsChildren() {
/* 121 */     return this.asksAllowsChildren;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRoot(TreeNode paramTreeNode) {
/* 129 */     TreeNode treeNode = this.root;
/* 130 */     this.root = paramTreeNode;
/* 131 */     if (paramTreeNode == null && treeNode != null) {
/* 132 */       fireTreeStructureChanged(this, null);
/*     */     } else {
/*     */       
/* 135 */       nodeStructureChanged(paramTreeNode);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getRoot() {
/* 146 */     return this.root;
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
/*     */   public int getIndexOfChild(Object paramObject1, Object paramObject2) {
/* 158 */     if (paramObject1 == null || paramObject2 == null)
/* 159 */       return -1; 
/* 160 */     return ((TreeNode)paramObject1).getIndex((TreeNode)paramObject2);
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
/*     */   public Object getChild(Object paramObject, int paramInt) {
/* 174 */     return ((TreeNode)paramObject).getChildAt(paramInt);
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
/*     */   public int getChildCount(Object paramObject) {
/* 186 */     return ((TreeNode)paramObject).getChildCount();
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
/*     */   public boolean isLeaf(Object paramObject) {
/* 201 */     if (this.asksAllowsChildren)
/* 202 */       return !((TreeNode)paramObject).getAllowsChildren(); 
/* 203 */     return ((TreeNode)paramObject).isLeaf();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reload() {
/* 212 */     reload(this.root);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void valueForPathChanged(TreePath paramTreePath, Object paramObject) {
/* 222 */     MutableTreeNode mutableTreeNode = (MutableTreeNode)paramTreePath.getLastPathComponent();
/*     */     
/* 224 */     mutableTreeNode.setUserObject(paramObject);
/* 225 */     nodeChanged(mutableTreeNode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insertNodeInto(MutableTreeNode paramMutableTreeNode1, MutableTreeNode paramMutableTreeNode2, int paramInt) {
/* 236 */     paramMutableTreeNode2.insert(paramMutableTreeNode1, paramInt);
/*     */     
/* 238 */     int[] arrayOfInt = new int[1];
/*     */     
/* 240 */     arrayOfInt[0] = paramInt;
/* 241 */     nodesWereInserted(paramMutableTreeNode2, arrayOfInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeNodeFromParent(MutableTreeNode paramMutableTreeNode) {
/* 251 */     MutableTreeNode mutableTreeNode = (MutableTreeNode)paramMutableTreeNode.getParent();
/*     */     
/* 253 */     if (mutableTreeNode == null) {
/* 254 */       throw new IllegalArgumentException("node does not have a parent.");
/*     */     }
/* 256 */     int[] arrayOfInt = new int[1];
/* 257 */     Object[] arrayOfObject = new Object[1];
/*     */     
/* 259 */     arrayOfInt[0] = mutableTreeNode.getIndex(paramMutableTreeNode);
/* 260 */     mutableTreeNode.remove(arrayOfInt[0]);
/* 261 */     arrayOfObject[0] = paramMutableTreeNode;
/* 262 */     nodesWereRemoved(mutableTreeNode, arrayOfInt, arrayOfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void nodeChanged(TreeNode paramTreeNode) {
/* 270 */     if (this.listenerList != null && paramTreeNode != null) {
/* 271 */       TreeNode treeNode = paramTreeNode.getParent();
/*     */       
/* 273 */       if (treeNode != null) {
/* 274 */         int i = treeNode.getIndex(paramTreeNode);
/* 275 */         if (i != -1) {
/* 276 */           int[] arrayOfInt = new int[1];
/*     */           
/* 278 */           arrayOfInt[0] = i;
/* 279 */           nodesChanged(treeNode, arrayOfInt);
/*     */         }
/*     */       
/* 282 */       } else if (paramTreeNode == getRoot()) {
/* 283 */         nodesChanged(paramTreeNode, null);
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
/*     */   public void reload(TreeNode paramTreeNode) {
/* 296 */     if (paramTreeNode != null) {
/* 297 */       fireTreeStructureChanged(this, (Object[])getPathToRoot(paramTreeNode), null, null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void nodesWereInserted(TreeNode paramTreeNode, int[] paramArrayOfint) {
/* 307 */     if (this.listenerList != null && paramTreeNode != null && paramArrayOfint != null && paramArrayOfint.length > 0) {
/*     */       
/* 309 */       int i = paramArrayOfint.length;
/* 310 */       Object[] arrayOfObject = new Object[i];
/*     */       
/* 312 */       for (byte b = 0; b < i; b++)
/* 313 */         arrayOfObject[b] = paramTreeNode.getChildAt(paramArrayOfint[b]); 
/* 314 */       fireTreeNodesInserted(this, (Object[])getPathToRoot(paramTreeNode), paramArrayOfint, arrayOfObject);
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
/*     */   public void nodesWereRemoved(TreeNode paramTreeNode, int[] paramArrayOfint, Object[] paramArrayOfObject) {
/* 327 */     if (paramTreeNode != null && paramArrayOfint != null) {
/* 328 */       fireTreeNodesRemoved(this, (Object[])getPathToRoot(paramTreeNode), paramArrayOfint, paramArrayOfObject);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void nodesChanged(TreeNode paramTreeNode, int[] paramArrayOfint) {
/* 338 */     if (paramTreeNode != null) {
/* 339 */       if (paramArrayOfint != null) {
/* 340 */         int i = paramArrayOfint.length;
/*     */         
/* 342 */         if (i > 0) {
/* 343 */           Object[] arrayOfObject = new Object[i];
/*     */           
/* 345 */           for (byte b = 0; b < i; b++)
/* 346 */             arrayOfObject[b] = paramTreeNode
/* 347 */               .getChildAt(paramArrayOfint[b]); 
/* 348 */           fireTreeNodesChanged(this, (Object[])getPathToRoot(paramTreeNode), paramArrayOfint, arrayOfObject);
/*     */         }
/*     */       
/*     */       }
/* 352 */       else if (paramTreeNode == getRoot()) {
/* 353 */         fireTreeNodesChanged(this, (Object[])getPathToRoot(paramTreeNode), null, null);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void nodeStructureChanged(TreeNode paramTreeNode) {
/* 364 */     if (paramTreeNode != null) {
/* 365 */       fireTreeStructureChanged(this, (Object[])getPathToRoot(paramTreeNode), null, null);
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
/*     */   public TreeNode[] getPathToRoot(TreeNode paramTreeNode) {
/* 378 */     return getPathToRoot(paramTreeNode, 0);
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
/*     */   protected TreeNode[] getPathToRoot(TreeNode paramTreeNode, int paramInt) {
/*     */     TreeNode[] arrayOfTreeNode;
/* 401 */     if (paramTreeNode == null) {
/* 402 */       if (paramInt == 0) {
/* 403 */         return null;
/*     */       }
/* 405 */       arrayOfTreeNode = new TreeNode[paramInt];
/*     */     } else {
/*     */       
/* 408 */       paramInt++;
/* 409 */       if (paramTreeNode == this.root) {
/* 410 */         arrayOfTreeNode = new TreeNode[paramInt];
/*     */       } else {
/* 412 */         arrayOfTreeNode = getPathToRoot(paramTreeNode.getParent(), paramInt);
/* 413 */       }  arrayOfTreeNode[arrayOfTreeNode.length - paramInt] = paramTreeNode;
/*     */     } 
/* 415 */     return arrayOfTreeNode;
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
/*     */   public void addTreeModelListener(TreeModelListener paramTreeModelListener) {
/* 429 */     this.listenerList.add(TreeModelListener.class, paramTreeModelListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeTreeModelListener(TreeModelListener paramTreeModelListener) {
/* 439 */     this.listenerList.remove(TreeModelListener.class, paramTreeModelListener);
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
/*     */   public TreeModelListener[] getTreeModelListeners() {
/* 456 */     return this.listenerList.<TreeModelListener>getListeners(TreeModelListener.class);
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
/*     */   protected void fireTreeNodesChanged(Object paramObject, Object[] paramArrayOfObject1, int[] paramArrayOfint, Object[] paramArrayOfObject2) {
/* 476 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/* 477 */     TreeModelEvent treeModelEvent = null;
/*     */ 
/*     */     
/* 480 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 481 */       if (arrayOfObject[i] == TreeModelListener.class) {
/*     */         
/* 483 */         if (treeModelEvent == null) {
/* 484 */           treeModelEvent = new TreeModelEvent(paramObject, paramArrayOfObject1, paramArrayOfint, paramArrayOfObject2);
/*     */         }
/* 486 */         ((TreeModelListener)arrayOfObject[i + 1]).treeNodesChanged(treeModelEvent);
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
/*     */   
/*     */   protected void fireTreeNodesInserted(Object paramObject, Object[] paramArrayOfObject1, int[] paramArrayOfint, Object[] paramArrayOfObject2) {
/* 507 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/* 508 */     TreeModelEvent treeModelEvent = null;
/*     */ 
/*     */     
/* 511 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 512 */       if (arrayOfObject[i] == TreeModelListener.class) {
/*     */         
/* 514 */         if (treeModelEvent == null) {
/* 515 */           treeModelEvent = new TreeModelEvent(paramObject, paramArrayOfObject1, paramArrayOfint, paramArrayOfObject2);
/*     */         }
/* 517 */         ((TreeModelListener)arrayOfObject[i + 1]).treeNodesInserted(treeModelEvent);
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
/*     */   
/*     */   protected void fireTreeNodesRemoved(Object paramObject, Object[] paramArrayOfObject1, int[] paramArrayOfint, Object[] paramArrayOfObject2) {
/* 538 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/* 539 */     TreeModelEvent treeModelEvent = null;
/*     */ 
/*     */     
/* 542 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 543 */       if (arrayOfObject[i] == TreeModelListener.class) {
/*     */         
/* 545 */         if (treeModelEvent == null) {
/* 546 */           treeModelEvent = new TreeModelEvent(paramObject, paramArrayOfObject1, paramArrayOfint, paramArrayOfObject2);
/*     */         }
/* 548 */         ((TreeModelListener)arrayOfObject[i + 1]).treeNodesRemoved(treeModelEvent);
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
/*     */ 
/*     */   
/*     */   protected void fireTreeStructureChanged(Object paramObject, Object[] paramArrayOfObject1, int[] paramArrayOfint, Object[] paramArrayOfObject2) {
/* 570 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/* 571 */     TreeModelEvent treeModelEvent = null;
/*     */ 
/*     */     
/* 574 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 575 */       if (arrayOfObject[i] == TreeModelListener.class) {
/*     */         
/* 577 */         if (treeModelEvent == null) {
/* 578 */           treeModelEvent = new TreeModelEvent(paramObject, paramArrayOfObject1, paramArrayOfint, paramArrayOfObject2);
/*     */         }
/* 580 */         ((TreeModelListener)arrayOfObject[i + 1]).treeStructureChanged(treeModelEvent);
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
/*     */   private void fireTreeStructureChanged(Object paramObject, TreePath paramTreePath) {
/* 598 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/* 599 */     TreeModelEvent treeModelEvent = null;
/*     */ 
/*     */     
/* 602 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 603 */       if (arrayOfObject[i] == TreeModelListener.class) {
/*     */         
/* 605 */         if (treeModelEvent == null)
/* 606 */           treeModelEvent = new TreeModelEvent(paramObject, paramTreePath); 
/* 607 */         ((TreeModelListener)arrayOfObject[i + 1]).treeStructureChanged(treeModelEvent);
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
/*     */   public <T extends java.util.EventListener> T[] getListeners(Class<T> paramClass) {
/* 649 */     return this.listenerList.getListeners(paramClass);
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 654 */     Vector<String> vector = new Vector();
/*     */     
/* 656 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/* 658 */     if (this.root != null && this.root instanceof Serializable) {
/* 659 */       vector.addElement("root");
/* 660 */       vector.addElement(this.root);
/*     */     } 
/* 662 */     paramObjectOutputStream.writeObject(vector);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 667 */     paramObjectInputStream.defaultReadObject();
/*     */     
/* 669 */     Vector<E> vector = (Vector)paramObjectInputStream.readObject();
/* 670 */     byte b = 0;
/* 671 */     int i = vector.size();
/*     */     
/* 673 */     if (b < i && vector.elementAt(b)
/* 674 */       .equals("root")) {
/* 675 */       this.root = (TreeNode)vector.elementAt(++b);
/* 676 */       b++;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/tree/DefaultTreeModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */