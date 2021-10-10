/*     */ package javax.swing.tree;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.EventObject;
/*     */ import java.util.Vector;
/*     */ import javax.swing.DefaultCellEditor;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.Timer;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.event.CellEditorListener;
/*     */ import javax.swing.event.TreeSelectionEvent;
/*     */ import javax.swing.event.TreeSelectionListener;
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
/*     */ 
/*     */ 
/*     */ public class DefaultTreeCellEditor
/*     */   implements ActionListener, TreeCellEditor, TreeSelectionListener
/*     */ {
/*     */   protected TreeCellEditor realEditor;
/*     */   protected DefaultTreeCellRenderer renderer;
/*     */   protected Container editingContainer;
/*     */   protected transient Component editingComponent;
/*     */   protected boolean canEdit;
/*     */   protected transient int offset;
/*     */   protected transient JTree tree;
/*     */   protected transient TreePath lastPath;
/*     */   protected transient Timer timer;
/*     */   protected transient int lastRow;
/*     */   protected Color borderSelectionColor;
/*     */   protected transient Icon editingIcon;
/*     */   protected Font font;
/*     */   
/*     */   public DefaultTreeCellEditor(JTree paramJTree, DefaultTreeCellRenderer paramDefaultTreeCellRenderer) {
/* 131 */     this(paramJTree, paramDefaultTreeCellRenderer, null);
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
/*     */   public DefaultTreeCellEditor(JTree paramJTree, DefaultTreeCellRenderer paramDefaultTreeCellRenderer, TreeCellEditor paramTreeCellEditor) {
/* 146 */     this.renderer = paramDefaultTreeCellRenderer;
/* 147 */     this.realEditor = paramTreeCellEditor;
/* 148 */     if (this.realEditor == null)
/* 149 */       this.realEditor = createTreeCellEditor(); 
/* 150 */     this.editingContainer = createContainer();
/* 151 */     setTree(paramJTree);
/* 152 */     setBorderSelectionColor(
/* 153 */         UIManager.getColor("Tree.editorBorderSelectionColor"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBorderSelectionColor(Color paramColor) {
/* 161 */     this.borderSelectionColor = paramColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getBorderSelectionColor() {
/* 169 */     return this.borderSelectionColor;
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
/*     */   public void setFont(Font paramFont) {
/* 184 */     this.font = paramFont;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font getFont() {
/* 194 */     return this.font;
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
/*     */   public Component getTreeCellEditorComponent(JTree paramJTree, Object paramObject, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt) {
/* 208 */     setTree(paramJTree);
/* 209 */     this.lastRow = paramInt;
/* 210 */     determineOffset(paramJTree, paramObject, paramBoolean1, paramBoolean2, paramBoolean3, paramInt);
/*     */     
/* 212 */     if (this.editingComponent != null) {
/* 213 */       this.editingContainer.remove(this.editingComponent);
/*     */     }
/* 215 */     this.editingComponent = this.realEditor.getTreeCellEditorComponent(paramJTree, paramObject, paramBoolean1, paramBoolean2, paramBoolean3, paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 221 */     TreePath treePath = paramJTree.getPathForRow(paramInt);
/*     */     
/* 223 */     this
/* 224 */       .canEdit = (this.lastPath != null && treePath != null && this.lastPath.equals(treePath));
/*     */     
/* 226 */     Font font = getFont();
/*     */     
/* 228 */     if (font == null) {
/* 229 */       if (this.renderer != null)
/* 230 */         font = this.renderer.getFont(); 
/* 231 */       if (font == null)
/* 232 */         font = paramJTree.getFont(); 
/*     */     } 
/* 234 */     this.editingContainer.setFont(font);
/* 235 */     prepareForEditing();
/* 236 */     return this.editingContainer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getCellEditorValue() {
/* 244 */     return this.realEditor.getCellEditorValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCellEditable(EventObject paramEventObject) {
/* 253 */     boolean bool1 = false;
/* 254 */     boolean bool2 = false;
/*     */     
/* 256 */     if (paramEventObject != null && 
/* 257 */       paramEventObject.getSource() instanceof JTree) {
/* 258 */       setTree((JTree)paramEventObject.getSource());
/* 259 */       if (paramEventObject instanceof MouseEvent) {
/* 260 */         TreePath treePath = this.tree.getPathForLocation(((MouseEvent)paramEventObject)
/* 261 */             .getX(), ((MouseEvent)paramEventObject)
/* 262 */             .getY());
/*     */         
/* 264 */         bool2 = (this.lastPath != null && treePath != null && this.lastPath.equals(treePath)) ? true : false;
/* 265 */         if (treePath != null) {
/* 266 */           this.lastRow = this.tree.getRowForPath(treePath);
/* 267 */           Object object = treePath.getLastPathComponent();
/* 268 */           boolean bool3 = this.tree.isRowSelected(this.lastRow);
/* 269 */           boolean bool4 = this.tree.isExpanded(treePath);
/* 270 */           TreeModel treeModel = this.tree.getModel();
/* 271 */           boolean bool5 = treeModel.isLeaf(object);
/* 272 */           determineOffset(this.tree, object, bool3, bool4, bool5, this.lastRow);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 278 */     if (!this.realEditor.isCellEditable(paramEventObject))
/* 279 */       return false; 
/* 280 */     if (canEditImmediately(paramEventObject)) {
/* 281 */       bool1 = true;
/* 282 */     } else if (bool2 && shouldStartEditingTimer(paramEventObject)) {
/* 283 */       startEditingTimer();
/*     */     }
/* 285 */     else if (this.timer != null && this.timer.isRunning()) {
/* 286 */       this.timer.stop();
/* 287 */     }  if (bool1)
/* 288 */       prepareForEditing(); 
/* 289 */     return bool1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldSelectCell(EventObject paramEventObject) {
/* 296 */     return this.realEditor.shouldSelectCell(paramEventObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean stopCellEditing() {
/* 305 */     if (this.realEditor.stopCellEditing()) {
/* 306 */       cleanupAfterEditing();
/* 307 */       return true;
/*     */     } 
/* 309 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancelCellEditing() {
/* 317 */     this.realEditor.cancelCellEditing();
/* 318 */     cleanupAfterEditing();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCellEditorListener(CellEditorListener paramCellEditorListener) {
/* 326 */     this.realEditor.addCellEditorListener(paramCellEditorListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeCellEditorListener(CellEditorListener paramCellEditorListener) {
/* 334 */     this.realEditor.removeCellEditorListener(paramCellEditorListener);
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
/*     */   public CellEditorListener[] getCellEditorListeners() {
/* 346 */     return ((DefaultCellEditor)this.realEditor).getCellEditorListeners();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void valueChanged(TreeSelectionEvent paramTreeSelectionEvent) {
/* 357 */     if (this.tree != null)
/* 358 */       if (this.tree.getSelectionCount() == 1) {
/* 359 */         this.lastPath = this.tree.getSelectionPath();
/*     */       } else {
/* 361 */         this.lastPath = null;
/*     */       }  
/* 363 */     if (this.timer != null) {
/* 364 */       this.timer.stop();
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
/*     */   public void actionPerformed(ActionEvent paramActionEvent) {
/* 377 */     if (this.tree != null && this.lastPath != null) {
/* 378 */       this.tree.startEditingAtPath(this.lastPath);
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
/*     */   protected void setTree(JTree paramJTree) {
/* 392 */     if (this.tree != paramJTree) {
/* 393 */       if (this.tree != null)
/* 394 */         this.tree.removeTreeSelectionListener(this); 
/* 395 */       this.tree = paramJTree;
/* 396 */       if (this.tree != null)
/* 397 */         this.tree.addTreeSelectionListener(this); 
/* 398 */       if (this.timer != null) {
/* 399 */         this.timer.stop();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean shouldStartEditingTimer(EventObject paramEventObject) {
/* 410 */     if (paramEventObject instanceof MouseEvent && 
/* 411 */       SwingUtilities.isLeftMouseButton((MouseEvent)paramEventObject)) {
/* 412 */       MouseEvent mouseEvent = (MouseEvent)paramEventObject;
/*     */       
/* 414 */       return (mouseEvent.getClickCount() == 1 && 
/* 415 */         inHitRegion(mouseEvent.getX(), mouseEvent.getY()));
/*     */     } 
/* 417 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void startEditingTimer() {
/* 424 */     if (this.timer == null) {
/* 425 */       this.timer = new Timer(1200, this);
/* 426 */       this.timer.setRepeats(false);
/*     */     } 
/* 428 */     this.timer.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canEditImmediately(EventObject paramEventObject) {
/* 438 */     if (paramEventObject instanceof MouseEvent && 
/* 439 */       SwingUtilities.isLeftMouseButton((MouseEvent)paramEventObject)) {
/* 440 */       MouseEvent mouseEvent = (MouseEvent)paramEventObject;
/*     */       
/* 442 */       return (mouseEvent.getClickCount() > 2 && 
/* 443 */         inHitRegion(mouseEvent.getX(), mouseEvent.getY()));
/*     */     } 
/* 445 */     return (paramEventObject == null);
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
/*     */   protected boolean inHitRegion(int paramInt1, int paramInt2) {
/* 460 */     if (this.lastRow != -1 && this.tree != null) {
/* 461 */       Rectangle rectangle = this.tree.getRowBounds(this.lastRow);
/* 462 */       ComponentOrientation componentOrientation = this.tree.getComponentOrientation();
/*     */       
/* 464 */       if (componentOrientation.isLeftToRight()) {
/* 465 */         if (rectangle != null && paramInt1 <= rectangle.x + this.offset && this.offset < rectangle.width - 5)
/*     */         {
/* 467 */           return false;
/*     */         }
/* 469 */       } else if (rectangle != null && (paramInt1 >= rectangle.x + rectangle.width - this.offset + 5 || paramInt1 <= rectangle.x + 5) && this.offset < rectangle.width - 5) {
/*     */ 
/*     */ 
/*     */         
/* 473 */         return false;
/*     */       } 
/*     */     } 
/* 476 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void determineOffset(JTree paramJTree, Object paramObject, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt) {
/* 482 */     if (this.renderer != null) {
/* 483 */       if (paramBoolean3) {
/* 484 */         this.editingIcon = this.renderer.getLeafIcon();
/* 485 */       } else if (paramBoolean2) {
/* 486 */         this.editingIcon = this.renderer.getOpenIcon();
/*     */       } else {
/* 488 */         this.editingIcon = this.renderer.getClosedIcon();
/* 489 */       }  if (this.editingIcon != null) {
/* 490 */         this
/* 491 */           .offset = this.renderer.getIconTextGap() + this.editingIcon.getIconWidth();
/*     */       } else {
/* 493 */         this.offset = this.renderer.getIconTextGap();
/*     */       } 
/*     */     } else {
/* 496 */       this.editingIcon = null;
/* 497 */       this.offset = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void prepareForEditing() {
/* 507 */     if (this.editingComponent != null) {
/* 508 */       this.editingContainer.add(this.editingComponent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Container createContainer() {
/* 517 */     return new EditorContainer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TreeCellEditor createTreeCellEditor() {
/* 527 */     Border border = UIManager.getBorder("Tree.editorBorder");
/* 528 */     DefaultCellEditor defaultCellEditor = new DefaultCellEditor(new DefaultTextField(border))
/*     */       {
/*     */         public boolean shouldSelectCell(EventObject param1EventObject) {
/* 531 */           return super.shouldSelectCell(param1EventObject);
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */     
/* 537 */     defaultCellEditor.setClickCountToStart(1);
/* 538 */     return defaultCellEditor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void cleanupAfterEditing() {
/* 546 */     if (this.editingComponent != null) {
/* 547 */       this.editingContainer.remove(this.editingComponent);
/*     */     }
/* 549 */     this.editingComponent = null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 554 */     Vector<String> vector = new Vector();
/*     */     
/* 556 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/* 558 */     if (this.realEditor != null && this.realEditor instanceof java.io.Serializable) {
/* 559 */       vector.addElement("realEditor");
/* 560 */       vector.addElement(this.realEditor);
/*     */     } 
/* 562 */     paramObjectOutputStream.writeObject(vector);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 567 */     paramObjectInputStream.defaultReadObject();
/*     */     
/* 569 */     Vector<E> vector = (Vector)paramObjectInputStream.readObject();
/* 570 */     byte b = 0;
/* 571 */     int i = vector.size();
/*     */     
/* 573 */     if (b < i && vector.elementAt(b)
/* 574 */       .equals("realEditor")) {
/* 575 */       this.realEditor = (TreeCellEditor)vector.elementAt(++b);
/* 576 */       b++;
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
/*     */   public class DefaultTextField
/*     */     extends JTextField
/*     */   {
/*     */     protected Border border;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DefaultTextField(Border param1Border) {
/* 600 */       setBorder(param1Border);
/*     */     }
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
/*     */     public void setBorder(Border param1Border) {
/* 617 */       super.setBorder(param1Border);
/* 618 */       this.border = param1Border;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Border getBorder() {
/* 626 */       return this.border;
/*     */     }
/*     */ 
/*     */     
/*     */     public Font getFont() {
/* 631 */       Font font = super.getFont();
/*     */ 
/*     */ 
/*     */       
/* 635 */       if (font instanceof javax.swing.plaf.FontUIResource) {
/* 636 */         Container container = getParent();
/*     */         
/* 638 */         if (container != null && container.getFont() != null)
/* 639 */           font = container.getFont(); 
/*     */       } 
/* 641 */       return font;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Dimension getPreferredSize() {
/* 652 */       Dimension dimension = super.getPreferredSize();
/*     */ 
/*     */       
/* 655 */       if (DefaultTreeCellEditor.this.renderer != null && DefaultTreeCellEditor.this
/* 656 */         .getFont() == null) {
/* 657 */         Dimension dimension1 = DefaultTreeCellEditor.this.renderer.getPreferredSize();
/*     */         
/* 659 */         dimension.height = dimension1.height;
/*     */       } 
/* 661 */       return dimension;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class EditorContainer
/*     */     extends Container
/*     */   {
/*     */     public EditorContainer() {
/* 674 */       setLayout((LayoutManager)null);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void EditorContainer() {
/* 680 */       setLayout((LayoutManager)null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void paint(Graphics param1Graphics) {
/* 688 */       int i = getWidth();
/* 689 */       int j = getHeight();
/*     */ 
/*     */       
/* 692 */       if (DefaultTreeCellEditor.this.editingIcon != null) {
/* 693 */         int k = calculateIconY(DefaultTreeCellEditor.this.editingIcon);
/*     */         
/* 695 */         if (getComponentOrientation().isLeftToRight()) {
/* 696 */           DefaultTreeCellEditor.this.editingIcon.paintIcon(this, param1Graphics, 0, k);
/*     */         } else {
/* 698 */           DefaultTreeCellEditor.this.editingIcon.paintIcon(this, param1Graphics, i - DefaultTreeCellEditor.this.editingIcon
/* 699 */               .getIconWidth(), k);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 705 */       Color color = DefaultTreeCellEditor.this.getBorderSelectionColor();
/* 706 */       if (color != null) {
/* 707 */         param1Graphics.setColor(color);
/* 708 */         param1Graphics.drawRect(0, 0, i - 1, j - 1);
/*     */       } 
/* 710 */       super.paint(param1Graphics);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void doLayout() {
/* 719 */       if (DefaultTreeCellEditor.this.editingComponent != null) {
/* 720 */         int i = getWidth();
/* 721 */         int j = getHeight();
/* 722 */         if (getComponentOrientation().isLeftToRight()) {
/* 723 */           DefaultTreeCellEditor.this.editingComponent.setBounds(DefaultTreeCellEditor.this.offset, 0, i - DefaultTreeCellEditor.this.offset, j);
/*     */         } else {
/*     */           
/* 726 */           DefaultTreeCellEditor.this.editingComponent.setBounds(0, 0, i - DefaultTreeCellEditor.this.offset, j);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int calculateIconY(Icon param1Icon) {
/* 739 */       int i = param1Icon.getIconHeight();
/*     */       
/* 741 */       int j = DefaultTreeCellEditor.this.editingComponent.getFontMetrics(DefaultTreeCellEditor.this.editingComponent.getFont()).getHeight();
/* 742 */       int k = i / 2 - j / 2;
/* 743 */       int m = Math.min(0, k);
/* 744 */       int n = Math.max(i, k + j) - m;
/*     */       
/* 746 */       return getHeight() / 2 - m + n / 2;
/*     */     }
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
/*     */     public Dimension getPreferredSize() {
/* 759 */       if (DefaultTreeCellEditor.this.editingComponent != null) {
/* 760 */         Dimension dimension1 = DefaultTreeCellEditor.this.editingComponent.getPreferredSize();
/*     */         
/* 762 */         dimension1.width += DefaultTreeCellEditor.this.offset + 5;
/*     */ 
/*     */         
/* 765 */         Dimension dimension2 = (DefaultTreeCellEditor.this.renderer != null) ? DefaultTreeCellEditor.this.renderer.getPreferredSize() : null;
/*     */         
/* 767 */         if (dimension2 != null)
/* 768 */           dimension1.height = Math.max(dimension1.height, dimension2.height); 
/* 769 */         if (DefaultTreeCellEditor.this.editingIcon != null) {
/* 770 */           dimension1.height = Math.max(dimension1.height, DefaultTreeCellEditor.this.editingIcon
/* 771 */               .getIconHeight());
/*     */         }
/*     */         
/* 774 */         dimension1.width = Math.max(dimension1.width, 100);
/* 775 */         return dimension1;
/*     */       } 
/* 777 */       return new Dimension(0, 0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/tree/DefaultTreeCellEditor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */