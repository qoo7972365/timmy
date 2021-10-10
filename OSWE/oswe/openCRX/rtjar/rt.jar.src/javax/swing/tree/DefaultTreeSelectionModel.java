/*      */ package javax.swing.tree;
/*      */ 
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.BitSet;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Vector;
/*      */ import javax.swing.DefaultListSelectionModel;
/*      */ import javax.swing.event.EventListenerList;
/*      */ import javax.swing.event.SwingPropertyChangeSupport;
/*      */ import javax.swing.event.TreeSelectionEvent;
/*      */ import javax.swing.event.TreeSelectionListener;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DefaultTreeSelectionModel
/*      */   implements Cloneable, Serializable, TreeSelectionModel
/*      */ {
/*      */   public static final String SELECTION_MODE_PROPERTY = "selectionMode";
/*      */   protected SwingPropertyChangeSupport changeSupport;
/*      */   protected TreePath[] selection;
/*   78 */   protected EventListenerList listenerList = new EventListenerList();
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient RowMapper rowMapper;
/*      */ 
/*      */ 
/*      */   
/*      */   protected DefaultListSelectionModel listSelectionModel;
/*      */ 
/*      */   
/*      */   protected int selectionMode;
/*      */ 
/*      */   
/*      */   protected TreePath leadPath;
/*      */ 
/*      */   
/*      */   protected int leadIndex;
/*      */ 
/*      */   
/*      */   protected int leadRow;
/*      */ 
/*      */   
/*      */   private Hashtable<TreePath, Boolean> uniquePaths;
/*      */ 
/*      */   
/*      */   private Hashtable<TreePath, Boolean> lastPaths;
/*      */ 
/*      */   
/*      */   private TreePath[] tempPaths;
/*      */ 
/*      */ 
/*      */   
/*      */   public DefaultTreeSelectionModel() {
/*  112 */     this.listSelectionModel = new DefaultListSelectionModel();
/*  113 */     this.selectionMode = 4;
/*  114 */     this.leadIndex = this.leadRow = -1;
/*  115 */     this.uniquePaths = new Hashtable<>();
/*  116 */     this.lastPaths = new Hashtable<>();
/*  117 */     this.tempPaths = new TreePath[1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRowMapper(RowMapper paramRowMapper) {
/*  125 */     this.rowMapper = paramRowMapper;
/*  126 */     resetRowSelection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RowMapper getRowMapper() {
/*  134 */     return this.rowMapper;
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
/*      */   public void setSelectionMode(int paramInt) {
/*  152 */     int i = this.selectionMode;
/*      */     
/*  154 */     this.selectionMode = paramInt;
/*  155 */     if (this.selectionMode != 1 && this.selectionMode != 2 && this.selectionMode != 4)
/*      */     {
/*      */       
/*  158 */       this.selectionMode = 4; } 
/*  159 */     if (i != this.selectionMode && this.changeSupport != null) {
/*  160 */       this.changeSupport.firePropertyChange("selectionMode", 
/*  161 */           Integer.valueOf(i), 
/*  162 */           Integer.valueOf(this.selectionMode));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSelectionMode() {
/*  171 */     return this.selectionMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSelectionPath(TreePath paramTreePath) {
/*  182 */     if (paramTreePath == null) {
/*  183 */       setSelectionPaths(null);
/*      */     } else {
/*  185 */       TreePath[] arrayOfTreePath = new TreePath[1];
/*      */       
/*  187 */       arrayOfTreePath[0] = paramTreePath;
/*  188 */       setSelectionPaths(arrayOfTreePath);
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
/*      */   public void setSelectionPaths(TreePath[] paramArrayOfTreePath) {
/*      */     int i, j;
/*  217 */     TreePath[] arrayOfTreePath = paramArrayOfTreePath;
/*      */     
/*  219 */     if (arrayOfTreePath == null) {
/*  220 */       i = 0;
/*      */     } else {
/*  222 */       i = arrayOfTreePath.length;
/*  223 */     }  if (this.selection == null) {
/*  224 */       j = 0;
/*      */     } else {
/*  226 */       j = this.selection.length;
/*  227 */     }  if (i + j != 0) {
/*  228 */       if (this.selectionMode == 1) {
/*      */ 
/*      */         
/*  231 */         if (i > 1) {
/*  232 */           arrayOfTreePath = new TreePath[1];
/*  233 */           arrayOfTreePath[0] = paramArrayOfTreePath[0];
/*  234 */           i = 1;
/*      */         }
/*      */       
/*  237 */       } else if (this.selectionMode == 2) {
/*      */ 
/*      */ 
/*      */         
/*  241 */         if (i > 0 && !arePathsContiguous(arrayOfTreePath)) {
/*  242 */           arrayOfTreePath = new TreePath[1];
/*  243 */           arrayOfTreePath[0] = paramArrayOfTreePath[0];
/*  244 */           i = 1;
/*      */         } 
/*      */       } 
/*      */       
/*  248 */       TreePath treePath = this.leadPath;
/*  249 */       Vector<PathPlaceHolder> vector = new Vector(i + j);
/*  250 */       ArrayList<TreePath> arrayList = new ArrayList(i);
/*      */ 
/*      */       
/*  253 */       this.lastPaths.clear();
/*  254 */       this.leadPath = null;
/*      */       
/*  256 */       for (byte b1 = 0; b1 < i; b1++) {
/*  257 */         TreePath treePath1 = arrayOfTreePath[b1];
/*  258 */         if (treePath1 != null && this.lastPaths.get(treePath1) == null) {
/*  259 */           this.lastPaths.put(treePath1, Boolean.TRUE);
/*  260 */           if (this.uniquePaths.get(treePath1) == null) {
/*  261 */             vector.addElement(new PathPlaceHolder(treePath1, true));
/*      */           }
/*  263 */           this.leadPath = treePath1;
/*  264 */           arrayList.add(treePath1);
/*      */         } 
/*      */       } 
/*      */       
/*  268 */       TreePath[] arrayOfTreePath1 = arrayList.<TreePath>toArray(
/*  269 */           new TreePath[arrayList.size()]);
/*      */ 
/*      */       
/*  272 */       for (byte b2 = 0; b2 < j; b2++) {
/*  273 */         if (this.selection[b2] != null && this.lastPaths
/*  274 */           .get(this.selection[b2]) == null) {
/*  275 */           vector.addElement(new PathPlaceHolder(this.selection[b2], false));
/*      */         }
/*      */       } 
/*  278 */       this.selection = arrayOfTreePath1;
/*      */       
/*  280 */       Hashtable<TreePath, Boolean> hashtable = this.uniquePaths;
/*      */       
/*  282 */       this.uniquePaths = this.lastPaths;
/*  283 */       this.lastPaths = hashtable;
/*  284 */       this.lastPaths.clear();
/*      */ 
/*      */       
/*  287 */       insureUniqueness();
/*      */       
/*  289 */       updateLeadIndex();
/*      */       
/*  291 */       resetRowSelection();
/*      */       
/*  293 */       if (vector.size() > 0) {
/*  294 */         notifyPathChange(vector, treePath);
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
/*      */   public void addSelectionPath(TreePath paramTreePath) {
/*  306 */     if (paramTreePath != null) {
/*  307 */       TreePath[] arrayOfTreePath = new TreePath[1];
/*      */       
/*  309 */       arrayOfTreePath[0] = paramTreePath;
/*  310 */       addSelectionPaths(arrayOfTreePath);
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
/*      */   public void addSelectionPaths(TreePath[] paramArrayOfTreePath) {
/*  330 */     byte b = (paramArrayOfTreePath == null) ? 0 : paramArrayOfTreePath.length;
/*      */     
/*  332 */     if (b) {
/*  333 */       if (this.selectionMode == 1) {
/*  334 */         setSelectionPaths(paramArrayOfTreePath);
/*      */       }
/*  336 */       else if (this.selectionMode == 2 && 
/*  337 */         !canPathsBeAdded(paramArrayOfTreePath)) {
/*  338 */         if (arePathsContiguous(paramArrayOfTreePath)) {
/*  339 */           setSelectionPaths(paramArrayOfTreePath);
/*      */         } else {
/*      */           
/*  342 */           TreePath[] arrayOfTreePath = new TreePath[1];
/*      */           
/*  344 */           arrayOfTreePath[0] = paramArrayOfTreePath[0];
/*  345 */           setSelectionPaths(arrayOfTreePath);
/*      */         } 
/*      */       } else {
/*      */         int j;
/*      */ 
/*      */         
/*  351 */         TreePath treePath = this.leadPath;
/*  352 */         Vector<PathPlaceHolder> vector = null;
/*      */         
/*  354 */         if (this.selection == null) {
/*  355 */           j = 0;
/*      */         } else {
/*  357 */           j = this.selection.length;
/*      */         } 
/*      */         
/*  360 */         this.lastPaths.clear(); int i; byte b1;
/*  361 */         for (i = 0, b1 = 0; i < b; 
/*  362 */           i++) {
/*  363 */           if (paramArrayOfTreePath[i] != null) {
/*  364 */             if (this.uniquePaths.get(paramArrayOfTreePath[i]) == null) {
/*  365 */               b1++;
/*  366 */               if (vector == null)
/*  367 */                 vector = new Vector(); 
/*  368 */               vector.addElement(new PathPlaceHolder(paramArrayOfTreePath[i], true));
/*      */               
/*  370 */               this.uniquePaths.put(paramArrayOfTreePath[i], Boolean.TRUE);
/*  371 */               this.lastPaths.put(paramArrayOfTreePath[i], Boolean.TRUE);
/*      */             } 
/*  373 */             this.leadPath = paramArrayOfTreePath[i];
/*      */           } 
/*      */         } 
/*      */         
/*  377 */         if (this.leadPath == null) {
/*  378 */           this.leadPath = treePath;
/*      */         }
/*      */         
/*  381 */         if (b1 > 0) {
/*  382 */           TreePath[] arrayOfTreePath = new TreePath[j + b1];
/*      */ 
/*      */ 
/*      */           
/*  386 */           if (j > 0) {
/*  387 */             System.arraycopy(this.selection, 0, arrayOfTreePath, 0, j);
/*      */           }
/*  389 */           if (b1 != paramArrayOfTreePath.length) {
/*      */ 
/*      */             
/*  392 */             Enumeration<TreePath> enumeration = this.lastPaths.keys();
/*      */             
/*  394 */             i = j;
/*  395 */             while (enumeration.hasMoreElements()) {
/*  396 */               arrayOfTreePath[i++] = enumeration.nextElement();
/*      */             }
/*      */           } else {
/*      */             
/*  400 */             System.arraycopy(paramArrayOfTreePath, 0, arrayOfTreePath, j, b1);
/*      */           } 
/*      */ 
/*      */           
/*  404 */           this.selection = arrayOfTreePath;
/*      */           
/*  406 */           insureUniqueness();
/*      */           
/*  408 */           updateLeadIndex();
/*      */           
/*  410 */           resetRowSelection();
/*      */           
/*  412 */           notifyPathChange(vector, treePath);
/*      */         } else {
/*      */           
/*  415 */           this.leadPath = treePath;
/*  416 */         }  this.lastPaths.clear();
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
/*      */   public void removeSelectionPath(TreePath paramTreePath) {
/*  429 */     if (paramTreePath != null) {
/*  430 */       TreePath[] arrayOfTreePath = new TreePath[1];
/*      */       
/*  432 */       arrayOfTreePath[0] = paramTreePath;
/*  433 */       removeSelectionPaths(arrayOfTreePath);
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
/*      */   public void removeSelectionPaths(TreePath[] paramArrayOfTreePath) {
/*  445 */     if (paramArrayOfTreePath != null && this.selection != null && paramArrayOfTreePath.length > 0) {
/*  446 */       if (!canPathsBeRemoved(paramArrayOfTreePath)) {
/*      */         
/*  448 */         clearSelection();
/*      */       } else {
/*      */         
/*  451 */         Vector<PathPlaceHolder> vector = null;
/*      */         
/*      */         int i;
/*  454 */         for (i = paramArrayOfTreePath.length - 1; i >= 0; 
/*  455 */           i--) {
/*  456 */           if (paramArrayOfTreePath[i] != null && 
/*  457 */             this.uniquePaths.get(paramArrayOfTreePath[i]) != null) {
/*  458 */             if (vector == null)
/*  459 */               vector = new Vector(paramArrayOfTreePath.length); 
/*  460 */             this.uniquePaths.remove(paramArrayOfTreePath[i]);
/*  461 */             vector.addElement(new PathPlaceHolder(paramArrayOfTreePath[i], false));
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  466 */         if (vector != null) {
/*  467 */           i = vector.size();
/*  468 */           TreePath treePath = this.leadPath;
/*      */           
/*  470 */           if (i == this.selection.length) {
/*  471 */             this.selection = null;
/*      */           } else {
/*      */             
/*  474 */             Enumeration<TreePath> enumeration = this.uniquePaths.keys();
/*  475 */             byte b = 0;
/*      */             
/*  477 */             this.selection = new TreePath[this.selection.length - i];
/*      */             
/*  479 */             while (enumeration.hasMoreElements()) {
/*  480 */               this.selection[b++] = enumeration.nextElement();
/*      */             }
/*      */           } 
/*  483 */           if (this.leadPath != null && this.uniquePaths
/*  484 */             .get(this.leadPath) == null) {
/*  485 */             if (this.selection != null) {
/*  486 */               this.leadPath = this.selection[this.selection.length - 1];
/*      */             } else {
/*      */               
/*  489 */               this.leadPath = null;
/*      */             }
/*      */           
/*  492 */           } else if (this.selection != null) {
/*  493 */             this.leadPath = this.selection[this.selection.length - 1];
/*      */           } else {
/*      */             
/*  496 */             this.leadPath = null;
/*      */           } 
/*  498 */           updateLeadIndex();
/*      */           
/*  500 */           resetRowSelection();
/*      */           
/*  502 */           notifyPathChange(vector, treePath);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TreePath getSelectionPath() {
/*  513 */     if (this.selection != null && this.selection.length > 0) {
/*  514 */       return this.selection[0];
/*      */     }
/*  516 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TreePath[] getSelectionPaths() {
/*  525 */     if (this.selection != null) {
/*  526 */       int i = this.selection.length;
/*  527 */       TreePath[] arrayOfTreePath = new TreePath[i];
/*      */       
/*  529 */       System.arraycopy(this.selection, 0, arrayOfTreePath, 0, i);
/*  530 */       return arrayOfTreePath;
/*      */     } 
/*  532 */     return new TreePath[0];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSelectionCount() {
/*  539 */     return (this.selection == null) ? 0 : this.selection.length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPathSelected(TreePath paramTreePath) {
/*  547 */     return (paramTreePath != null) ? ((this.uniquePaths.get(paramTreePath) != null)) : false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSelectionEmpty() {
/*  554 */     return (this.selection == null || this.selection.length == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearSelection() {
/*  562 */     if (this.selection != null && this.selection.length > 0) {
/*  563 */       int i = this.selection.length;
/*  564 */       boolean[] arrayOfBoolean = new boolean[i];
/*      */       
/*  566 */       for (byte b = 0; b < i; b++) {
/*  567 */         arrayOfBoolean[b] = false;
/*      */       }
/*  569 */       TreeSelectionEvent treeSelectionEvent = new TreeSelectionEvent(this, this.selection, arrayOfBoolean, this.leadPath, null);
/*      */ 
/*      */       
/*  572 */       this.leadPath = null;
/*  573 */       this.leadIndex = this.leadRow = -1;
/*  574 */       this.uniquePaths.clear();
/*  575 */       this.selection = null;
/*  576 */       resetRowSelection();
/*  577 */       fireValueChanged(treeSelectionEvent);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addTreeSelectionListener(TreeSelectionListener paramTreeSelectionListener) {
/*  588 */     this.listenerList.add(TreeSelectionListener.class, paramTreeSelectionListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeTreeSelectionListener(TreeSelectionListener paramTreeSelectionListener) {
/*  598 */     this.listenerList.remove(TreeSelectionListener.class, paramTreeSelectionListener);
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
/*      */   public TreeSelectionListener[] getTreeSelectionListeners() {
/*  615 */     return this.listenerList.<TreeSelectionListener>getListeners(TreeSelectionListener.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fireValueChanged(TreeSelectionEvent paramTreeSelectionEvent) {
/*  626 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*      */ 
/*      */ 
/*      */     
/*  630 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/*  631 */       if (arrayOfObject[i] == TreeSelectionListener.class)
/*      */       {
/*      */ 
/*      */         
/*  635 */         ((TreeSelectionListener)arrayOfObject[i + 1]).valueChanged(paramTreeSelectionEvent);
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
/*      */   public <T extends java.util.EventListener> T[] getListeners(Class<T> paramClass) {
/*  678 */     return this.listenerList.getListeners(paramClass);
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
/*      */   public int[] getSelectionRows() {
/*  698 */     if (this.rowMapper != null && this.selection != null && this.selection.length > 0) {
/*  699 */       int[] arrayOfInt = this.rowMapper.getRowsForPaths(this.selection);
/*      */       
/*  701 */       if (arrayOfInt != null) {
/*  702 */         byte b = 0;
/*      */         
/*  704 */         for (int i = arrayOfInt.length - 1; i >= 0; i--) {
/*  705 */           if (arrayOfInt[i] == -1) {
/*  706 */             b++;
/*      */           }
/*      */         } 
/*  709 */         if (b > 0) {
/*  710 */           if (b == arrayOfInt.length) {
/*  711 */             arrayOfInt = null;
/*      */           } else {
/*      */             
/*  714 */             int[] arrayOfInt1 = new int[arrayOfInt.length - b];
/*      */             
/*  716 */             int j = arrayOfInt.length - 1; byte b1 = 0;
/*  717 */             for (; j >= 0; j--) {
/*  718 */               if (arrayOfInt[j] != -1) {
/*  719 */                 arrayOfInt1[b1++] = arrayOfInt[j];
/*      */               }
/*      */             } 
/*  722 */             arrayOfInt = arrayOfInt1;
/*      */           } 
/*      */         }
/*      */       } 
/*  726 */       return arrayOfInt;
/*      */     } 
/*  728 */     return new int[0];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinSelectionRow() {
/*  737 */     return this.listSelectionModel.getMinSelectionIndex();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxSelectionRow() {
/*  746 */     return this.listSelectionModel.getMaxSelectionIndex();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRowSelected(int paramInt) {
/*  753 */     return this.listSelectionModel.isSelectedIndex(paramInt);
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
/*      */   public void resetRowSelection() {
/*  768 */     this.listSelectionModel.clearSelection();
/*  769 */     if (this.selection != null && this.rowMapper != null) {
/*      */       
/*  771 */       boolean bool = false;
/*  772 */       int[] arrayOfInt = this.rowMapper.getRowsForPaths(this.selection);
/*      */       
/*  774 */       byte b = 0; int i = this.selection.length;
/*  775 */       for (; b < i; b++) {
/*  776 */         int j = arrayOfInt[b];
/*  777 */         if (j != -1) {
/*  778 */           this.listSelectionModel.addSelectionInterval(j, j);
/*      */         }
/*      */       } 
/*  781 */       if (this.leadIndex != -1 && arrayOfInt != null) {
/*  782 */         this.leadRow = arrayOfInt[this.leadIndex];
/*      */       }
/*  784 */       else if (this.leadPath != null) {
/*      */         
/*  786 */         this.tempPaths[0] = this.leadPath;
/*  787 */         arrayOfInt = this.rowMapper.getRowsForPaths(this.tempPaths);
/*  788 */         this.leadRow = (arrayOfInt != null) ? arrayOfInt[0] : -1;
/*      */       } else {
/*      */         
/*  791 */         this.leadRow = -1;
/*      */       } 
/*  793 */       insureRowContinuity();
/*      */     }
/*      */     else {
/*      */       
/*  797 */       this.leadRow = -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLeadSelectionRow() {
/*  805 */     return this.leadRow;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TreePath getLeadSelectionPath() {
/*  813 */     return this.leadPath;
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
/*      */   public synchronized void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/*  827 */     if (this.changeSupport == null) {
/*  828 */       this.changeSupport = new SwingPropertyChangeSupport(this);
/*      */     }
/*  830 */     this.changeSupport.addPropertyChangeListener(paramPropertyChangeListener);
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
/*      */   public synchronized void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/*  843 */     if (this.changeSupport == null) {
/*      */       return;
/*      */     }
/*  846 */     this.changeSupport.removePropertyChangeListener(paramPropertyChangeListener);
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
/*      */   public PropertyChangeListener[] getPropertyChangeListeners() {
/*  863 */     if (this.changeSupport == null) {
/*  864 */       return new PropertyChangeListener[0];
/*      */     }
/*  866 */     return this.changeSupport.getPropertyChangeListeners();
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
/*      */   protected void insureRowContinuity() {
/*  884 */     if (this.selectionMode == 2 && this.selection != null && this.rowMapper != null) {
/*      */       
/*  886 */       DefaultListSelectionModel defaultListSelectionModel = this.listSelectionModel;
/*  887 */       int i = defaultListSelectionModel.getMinSelectionIndex();
/*      */       
/*  889 */       if (i != -1) {
/*  890 */         int j = i;
/*  891 */         int k = defaultListSelectionModel.getMaxSelectionIndex();
/*  892 */         for (; j <= k; j++) {
/*  893 */           if (!defaultListSelectionModel.isSelectedIndex(j)) {
/*  894 */             if (j == i) {
/*  895 */               clearSelection();
/*      */             } else {
/*      */               
/*  898 */               TreePath[] arrayOfTreePath = new TreePath[j - i];
/*  899 */               int[] arrayOfInt = this.rowMapper.getRowsForPaths(this.selection);
/*      */ 
/*      */               
/*  902 */               for (byte b = 0; b < arrayOfInt.length; b++) {
/*  903 */                 if (arrayOfInt[b] < j) {
/*  904 */                   arrayOfTreePath[arrayOfInt[b] - i] = this.selection[b];
/*      */                 }
/*      */               } 
/*  907 */               setSelectionPaths(arrayOfTreePath);
/*      */               
/*      */               break;
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } 
/*  914 */     } else if (this.selectionMode == 1 && this.selection != null && this.selection.length > 1) {
/*      */       
/*  916 */       setSelectionPath(this.selection[0]);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean arePathsContiguous(TreePath[] paramArrayOfTreePath) {
/*  925 */     if (this.rowMapper == null || paramArrayOfTreePath.length < 2) {
/*  926 */       return true;
/*      */     }
/*  928 */     BitSet bitSet = new BitSet(32);
/*      */     
/*  930 */     int k = paramArrayOfTreePath.length;
/*  931 */     byte b = 0;
/*  932 */     TreePath[] arrayOfTreePath = new TreePath[1];
/*      */     
/*  934 */     arrayOfTreePath[0] = paramArrayOfTreePath[0];
/*  935 */     int j = this.rowMapper.getRowsForPaths(arrayOfTreePath)[0]; int i;
/*  936 */     for (i = 0; i < k; i++) {
/*  937 */       if (paramArrayOfTreePath[i] != null) {
/*  938 */         arrayOfTreePath[0] = paramArrayOfTreePath[i];
/*  939 */         int[] arrayOfInt = this.rowMapper.getRowsForPaths(arrayOfTreePath);
/*  940 */         if (arrayOfInt == null) {
/*  941 */           return false;
/*      */         }
/*  943 */         int n = arrayOfInt[0];
/*  944 */         if (n == -1 || n < j - k || n > j + k)
/*      */         {
/*  946 */           return false; } 
/*  947 */         if (n < j)
/*  948 */           j = n; 
/*  949 */         if (!bitSet.get(n)) {
/*  950 */           bitSet.set(n);
/*  951 */           b++;
/*      */         } 
/*      */       } 
/*      */     } 
/*  955 */     int m = b + j;
/*      */     
/*  957 */     for (i = j; i < m; i++) {
/*  958 */       if (!bitSet.get(i))
/*  959 */         return false; 
/*      */     } 
/*  961 */     return true;
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
/*      */   protected boolean canPathsBeAdded(TreePath[] paramArrayOfTreePath) {
/*  973 */     if (paramArrayOfTreePath == null || paramArrayOfTreePath.length == 0 || this.rowMapper == null || this.selection == null || this.selectionMode == 4)
/*      */     {
/*      */       
/*  976 */       return true;
/*      */     }
/*  978 */     BitSet bitSet = new BitSet();
/*  979 */     DefaultListSelectionModel defaultListSelectionModel = this.listSelectionModel;
/*      */ 
/*      */     
/*  982 */     int j = defaultListSelectionModel.getMinSelectionIndex();
/*  983 */     int k = defaultListSelectionModel.getMaxSelectionIndex();
/*  984 */     TreePath[] arrayOfTreePath = new TreePath[1];
/*      */     
/*  986 */     if (j != -1) {
/*  987 */       for (int m = j; m <= k; m++) {
/*  988 */         if (defaultListSelectionModel.isSelectedIndex(m)) {
/*  989 */           bitSet.set(m);
/*      */         }
/*      */       } 
/*      */     } else {
/*  993 */       arrayOfTreePath[0] = paramArrayOfTreePath[0];
/*  994 */       j = k = this.rowMapper.getRowsForPaths(arrayOfTreePath)[0];
/*      */     }  int i;
/*  996 */     for (i = paramArrayOfTreePath.length - 1; i >= 0; i--) {
/*  997 */       if (paramArrayOfTreePath[i] != null) {
/*  998 */         arrayOfTreePath[0] = paramArrayOfTreePath[i];
/*  999 */         int[] arrayOfInt = this.rowMapper.getRowsForPaths(arrayOfTreePath);
/* 1000 */         if (arrayOfInt == null) {
/* 1001 */           return false;
/*      */         }
/* 1003 */         int m = arrayOfInt[0];
/* 1004 */         j = Math.min(m, j);
/* 1005 */         k = Math.max(m, k);
/* 1006 */         if (m == -1)
/* 1007 */           return false; 
/* 1008 */         bitSet.set(m);
/*      */       } 
/*      */     } 
/* 1011 */     for (i = j; i <= k; i++) {
/* 1012 */       if (!bitSet.get(i))
/* 1013 */         return false; 
/*      */     } 
/* 1015 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canPathsBeRemoved(TreePath[] paramArrayOfTreePath) {
/* 1024 */     if (this.rowMapper == null || this.selection == null || this.selectionMode == 4)
/*      */     {
/* 1026 */       return true;
/*      */     }
/* 1028 */     BitSet bitSet = new BitSet();
/*      */     
/* 1030 */     int j = paramArrayOfTreePath.length;
/*      */     
/* 1032 */     int k = -1;
/* 1033 */     byte b = 0;
/* 1034 */     TreePath[] arrayOfTreePath = new TreePath[1];
/*      */ 
/*      */ 
/*      */     
/* 1038 */     this.lastPaths.clear(); int i;
/* 1039 */     for (i = 0; i < j; i++) {
/* 1040 */       if (paramArrayOfTreePath[i] != null) {
/* 1041 */         this.lastPaths.put(paramArrayOfTreePath[i], Boolean.TRUE);
/*      */       }
/*      */     } 
/* 1044 */     for (i = this.selection.length - 1; i >= 0; i--) {
/* 1045 */       if (this.lastPaths.get(this.selection[i]) == null) {
/* 1046 */         arrayOfTreePath[0] = this.selection[i];
/* 1047 */         int[] arrayOfInt = this.rowMapper.getRowsForPaths(arrayOfTreePath);
/* 1048 */         if (arrayOfInt != null && arrayOfInt[0] != -1 && !bitSet.get(arrayOfInt[0])) {
/* 1049 */           b++;
/* 1050 */           if (k == -1) {
/* 1051 */             k = arrayOfInt[0];
/*      */           } else {
/* 1053 */             k = Math.min(k, arrayOfInt[0]);
/* 1054 */           }  bitSet.set(arrayOfInt[0]);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1058 */     this.lastPaths.clear();
/*      */     
/* 1060 */     if (b > 1)
/* 1061 */       for (i = k + b - 1; i >= k; 
/* 1062 */         i--) {
/* 1063 */         if (!bitSet.get(i)) {
/* 1064 */           return false;
/*      */         }
/*      */       }  
/* 1067 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected void notifyPathChange(Vector<?> paramVector, TreePath paramTreePath) {
/* 1079 */     int i = paramVector.size();
/* 1080 */     boolean[] arrayOfBoolean = new boolean[i];
/* 1081 */     TreePath[] arrayOfTreePath = new TreePath[i];
/*      */ 
/*      */     
/* 1084 */     for (byte b = 0; b < i; b++) {
/* 1085 */       PathPlaceHolder pathPlaceHolder = (PathPlaceHolder)paramVector.elementAt(b);
/* 1086 */       arrayOfBoolean[b] = pathPlaceHolder.isNew;
/* 1087 */       arrayOfTreePath[b] = pathPlaceHolder.path;
/*      */     } 
/*      */     
/* 1090 */     TreeSelectionEvent treeSelectionEvent = new TreeSelectionEvent(this, arrayOfTreePath, arrayOfBoolean, paramTreePath, this.leadPath);
/*      */ 
/*      */     
/* 1093 */     fireValueChanged(treeSelectionEvent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateLeadIndex() {
/* 1100 */     if (this.leadPath != null) {
/* 1101 */       if (this.selection == null) {
/* 1102 */         this.leadPath = null;
/* 1103 */         this.leadIndex = this.leadRow = -1;
/*      */       } else {
/*      */         
/* 1106 */         this.leadRow = this.leadIndex = -1;
/* 1107 */         for (int i = this.selection.length - 1; i >= 0; 
/* 1108 */           i--) {
/*      */ 
/*      */           
/* 1111 */           if (this.selection[i] == this.leadPath) {
/* 1112 */             this.leadIndex = i;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/* 1119 */       this.leadIndex = -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void insureUniqueness() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*      */     Object object;
/* 1139 */     int i = getSelectionCount();
/* 1140 */     StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */     
/* 1143 */     if (this.rowMapper != null) {
/* 1144 */       object = this.rowMapper.getRowsForPaths(this.selection);
/*      */     } else {
/* 1146 */       object = null;
/* 1147 */     }  stringBuffer.append(getClass().getName() + " " + hashCode() + " [ ");
/* 1148 */     for (byte b = 0; b < i; b++) {
/* 1149 */       if (object != null) {
/* 1150 */         stringBuffer.append(this.selection[b].toString() + "@" + 
/* 1151 */             Integer.toString(object[b]) + " ");
/*      */       } else {
/* 1153 */         stringBuffer.append(this.selection[b].toString() + " ");
/*      */       } 
/* 1155 */     }  stringBuffer.append("]");
/* 1156 */     return stringBuffer.toString();
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
/*      */   public Object clone() throws CloneNotSupportedException {
/* 1169 */     DefaultTreeSelectionModel defaultTreeSelectionModel = (DefaultTreeSelectionModel)super.clone();
/*      */     
/* 1171 */     defaultTreeSelectionModel.changeSupport = null;
/* 1172 */     if (this.selection != null) {
/* 1173 */       int i = this.selection.length;
/*      */       
/* 1175 */       defaultTreeSelectionModel.selection = new TreePath[i];
/* 1176 */       System.arraycopy(this.selection, 0, defaultTreeSelectionModel.selection, 0, i);
/*      */     } 
/* 1178 */     defaultTreeSelectionModel.listenerList = new EventListenerList();
/* 1179 */     defaultTreeSelectionModel
/* 1180 */       .listSelectionModel = (DefaultListSelectionModel)this.listSelectionModel.clone();
/* 1181 */     defaultTreeSelectionModel.uniquePaths = new Hashtable<>();
/* 1182 */     defaultTreeSelectionModel.lastPaths = new Hashtable<>();
/* 1183 */     defaultTreeSelectionModel.tempPaths = new TreePath[1];
/* 1184 */     return defaultTreeSelectionModel;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*      */     Object[] arrayOfObject;
/* 1191 */     paramObjectOutputStream.defaultWriteObject();
/*      */     
/* 1193 */     if (this.rowMapper != null && this.rowMapper instanceof Serializable) {
/* 1194 */       arrayOfObject = new Object[2];
/* 1195 */       arrayOfObject[0] = "rowMapper";
/* 1196 */       arrayOfObject[1] = this.rowMapper;
/*      */     } else {
/*      */       
/* 1199 */       arrayOfObject = new Object[0];
/* 1200 */     }  paramObjectOutputStream.writeObject(arrayOfObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1208 */     paramObjectInputStream.defaultReadObject();
/*      */     
/* 1210 */     Object[] arrayOfObject = (Object[])paramObjectInputStream.readObject();
/*      */     
/* 1212 */     if (arrayOfObject.length > 0 && arrayOfObject[0].equals("rowMapper"))
/* 1213 */       this.rowMapper = (RowMapper)arrayOfObject[1]; 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/tree/DefaultTreeSelectionModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */