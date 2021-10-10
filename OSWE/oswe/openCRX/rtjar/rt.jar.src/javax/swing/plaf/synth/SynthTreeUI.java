/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.Enumeration;
/*     */ import javax.swing.DefaultCellEditor;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.TransferHandler;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.plaf.basic.BasicTreeUI;
/*     */ import javax.swing.tree.DefaultTreeCellEditor;
/*     */ import javax.swing.tree.DefaultTreeCellRenderer;
/*     */ import javax.swing.tree.TreeCellEditor;
/*     */ import javax.swing.tree.TreeCellRenderer;
/*     */ import javax.swing.tree.TreeModel;
/*     */ import javax.swing.tree.TreePath;
/*     */ import sun.swing.plaf.synth.SynthIcon;
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
/*     */ public class SynthTreeUI
/*     */   extends BasicTreeUI
/*     */   implements PropertyChangeListener, SynthUI
/*     */ {
/*     */   private SynthStyle style;
/*     */   private SynthStyle cellStyle;
/*     */   private SynthContext paintContext;
/*     */   private boolean drawHorizontalLines;
/*     */   private boolean drawVerticalLines;
/*     */   private Object linesStyle;
/*     */   private int padding;
/*     */   private boolean useTreeColors;
/*  75 */   private Icon expandedIconWrapper = (Icon)new ExpandedIconWrapper();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  84 */     return new SynthTreeUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Icon getExpandedIcon() {
/*  92 */     return this.expandedIconWrapper;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults() {
/* 100 */     updateStyle(this.tree);
/*     */   }
/*     */   
/*     */   private void updateStyle(JTree paramJTree) {
/* 104 */     SynthContext synthContext = getContext(paramJTree, 1);
/* 105 */     SynthStyle synthStyle = this.style;
/*     */     
/* 107 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/* 108 */     if (this.style != synthStyle) {
/*     */ 
/*     */       
/* 111 */       setExpandedIcon(this.style.getIcon(synthContext, "Tree.expandedIcon"));
/* 112 */       setCollapsedIcon(this.style.getIcon(synthContext, "Tree.collapsedIcon"));
/*     */       
/* 114 */       setLeftChildIndent(this.style.getInt(synthContext, "Tree.leftChildIndent", 0));
/*     */       
/* 116 */       setRightChildIndent(this.style.getInt(synthContext, "Tree.rightChildIndent", 0));
/*     */ 
/*     */       
/* 119 */       this.drawHorizontalLines = this.style.getBoolean(synthContext, "Tree.drawHorizontalLines", true);
/*     */       
/* 121 */       this.drawVerticalLines = this.style.getBoolean(synthContext, "Tree.drawVerticalLines", true);
/*     */       
/* 123 */       this.linesStyle = this.style.get(synthContext, "Tree.linesStyle");
/*     */       
/* 125 */       Object object = this.style.get(synthContext, "Tree.rowHeight");
/* 126 */       if (object != null) {
/* 127 */         LookAndFeel.installProperty(paramJTree, "rowHeight", object);
/*     */       }
/*     */       
/* 130 */       object = this.style.get(synthContext, "Tree.scrollsOnExpand");
/* 131 */       LookAndFeel.installProperty(paramJTree, "scrollsOnExpand", (object != null) ? object : Boolean.TRUE);
/*     */ 
/*     */       
/* 134 */       this.padding = this.style.getInt(synthContext, "Tree.padding", 0);
/*     */       
/* 136 */       this.largeModel = (paramJTree.isLargeModel() && paramJTree.getRowHeight() > 0);
/*     */       
/* 138 */       this.useTreeColors = this.style.getBoolean(synthContext, "Tree.rendererUseTreeColors", true);
/*     */ 
/*     */       
/* 141 */       Boolean bool = Boolean.valueOf(this.style.getBoolean(synthContext, "Tree.showsRootHandles", Boolean.TRUE
/* 142 */             .booleanValue()));
/* 143 */       LookAndFeel.installProperty(paramJTree, "showsRootHandles", bool);
/*     */ 
/*     */       
/* 146 */       if (synthStyle != null) {
/* 147 */         uninstallKeyboardActions();
/* 148 */         installKeyboardActions();
/*     */       } 
/*     */     } 
/* 151 */     synthContext.dispose();
/*     */     
/* 153 */     synthContext = getContext(paramJTree, Region.TREE_CELL, 1);
/* 154 */     this.cellStyle = SynthLookAndFeel.updateStyle(synthContext, this);
/* 155 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners() {
/* 163 */     super.installListeners();
/* 164 */     this.tree.addPropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 172 */     return getContext(paramJComponent, SynthLookAndFeel.getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 176 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, Region paramRegion) {
/* 180 */     return getContext(paramJComponent, paramRegion, getComponentState(paramJComponent, paramRegion));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, Region paramRegion, int paramInt) {
/* 184 */     return SynthContext.getContext(paramJComponent, paramRegion, this.cellStyle, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int getComponentState(JComponent paramJComponent, Region paramRegion) {
/* 190 */     return 513;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TreeCellEditor createDefaultCellEditor() {
/*     */     SynthTreeCellEditor synthTreeCellEditor;
/* 198 */     TreeCellRenderer treeCellRenderer = this.tree.getCellRenderer();
/*     */ 
/*     */     
/* 201 */     if (treeCellRenderer != null && treeCellRenderer instanceof DefaultTreeCellRenderer) {
/* 202 */       synthTreeCellEditor = new SynthTreeCellEditor(this.tree, (DefaultTreeCellRenderer)treeCellRenderer);
/*     */     }
/*     */     else {
/*     */       
/* 206 */       synthTreeCellEditor = new SynthTreeCellEditor(this.tree, null);
/*     */     } 
/* 208 */     return synthTreeCellEditor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TreeCellRenderer createDefaultCellRenderer() {
/* 216 */     return new SynthTreeCellRenderer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults() {
/* 224 */     SynthContext synthContext = getContext(this.tree, 1);
/*     */     
/* 226 */     this.style.uninstallDefaults(synthContext);
/* 227 */     synthContext.dispose();
/* 228 */     this.style = null;
/*     */     
/* 230 */     synthContext = getContext(this.tree, Region.TREE_CELL, 1);
/* 231 */     this.cellStyle.uninstallDefaults(synthContext);
/* 232 */     synthContext.dispose();
/* 233 */     this.cellStyle = null;
/*     */ 
/*     */     
/* 236 */     if (this.tree.getTransferHandler() instanceof UIResource) {
/* 237 */       this.tree.setTransferHandler((TransferHandler)null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners() {
/* 246 */     super.uninstallListeners();
/* 247 */     this.tree.removePropertyChangeListener(this);
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
/*     */   public void update(Graphics paramGraphics, JComponent paramJComponent) {
/* 264 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 266 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 267 */     synthContext.getPainter().paintTreeBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/* 268 */         .getWidth(), paramJComponent.getHeight());
/* 269 */     paint(synthContext, paramGraphics);
/* 270 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 279 */     paramSynthContext.getPainter().paintTreeBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
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
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 293 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 295 */     paint(synthContext, paramGraphics);
/* 296 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paint(SynthContext paramSynthContext, Graphics paramGraphics) {
/* 307 */     this.paintContext = paramSynthContext;
/*     */     
/* 309 */     updateLeadSelectionRow();
/*     */     
/* 311 */     Rectangle rectangle = paramGraphics.getClipBounds();
/* 312 */     Insets insets = this.tree.getInsets();
/* 313 */     TreePath treePath = getClosestPathForLocation(this.tree, 0, rectangle.y);
/*     */ 
/*     */     
/* 316 */     Enumeration<TreePath> enumeration = this.treeState.getVisiblePathsFrom(treePath);
/* 317 */     int i = this.treeState.getRowForPath(treePath);
/* 318 */     int j = rectangle.y + rectangle.height;
/* 319 */     TreeModel treeModel = this.tree.getModel();
/* 320 */     SynthContext synthContext = getContext(this.tree, Region.TREE_CELL);
/*     */     
/* 322 */     this.drawingCache.clear();
/*     */     
/* 324 */     setHashColor(paramSynthContext.getStyle().getColor(paramSynthContext, ColorType.FOREGROUND));
/*     */ 
/*     */     
/* 327 */     if (enumeration != null) {
/*     */ 
/*     */       
/* 330 */       boolean bool = false;
/*     */ 
/*     */ 
/*     */       
/* 334 */       Rectangle rectangle1 = new Rectangle(0, 0, this.tree.getWidth(), 0);
/*     */ 
/*     */       
/* 337 */       TreeCellRenderer treeCellRenderer = this.tree.getCellRenderer();
/* 338 */       DefaultTreeCellRenderer defaultTreeCellRenderer = (treeCellRenderer instanceof DefaultTreeCellRenderer) ? (DefaultTreeCellRenderer)treeCellRenderer : null;
/*     */ 
/*     */ 
/*     */       
/* 342 */       configureRenderer(synthContext);
/* 343 */       while (!bool && enumeration.hasMoreElements()) {
/* 344 */         TreePath treePath2 = enumeration.nextElement();
/* 345 */         Rectangle rectangle2 = getPathBounds(this.tree, treePath2);
/* 346 */         if (treePath2 != null && rectangle2 != null) {
/* 347 */           boolean bool2, bool3, bool4 = treeModel.isLeaf(treePath2.getLastPathComponent());
/* 348 */           if (bool4) {
/* 349 */             bool2 = bool3 = false;
/*     */           } else {
/*     */             
/* 352 */             bool2 = this.treeState.getExpandedState(treePath2);
/* 353 */             bool3 = this.tree.hasBeenExpanded(treePath2);
/*     */           } 
/* 355 */           rectangle1.y = rectangle2.y;
/* 356 */           rectangle1.height = rectangle2.height;
/* 357 */           paintRow(treeCellRenderer, defaultTreeCellRenderer, paramSynthContext, synthContext, paramGraphics, rectangle, insets, rectangle2, rectangle1, treePath2, i, bool2, bool3, bool4);
/*     */ 
/*     */           
/* 360 */           if (rectangle2.y + rectangle2.height >= j) {
/* 361 */             bool = true;
/*     */           }
/*     */         } else {
/*     */           
/* 365 */           bool = true;
/*     */         } 
/* 367 */         i++;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 372 */       boolean bool1 = this.tree.isRootVisible();
/* 373 */       TreePath treePath1 = treePath;
/* 374 */       treePath1 = treePath1.getParentPath();
/* 375 */       while (treePath1 != null) {
/* 376 */         paintVerticalPartOfLeg(paramGraphics, rectangle, insets, treePath1);
/* 377 */         this.drawingCache.put(treePath1, Boolean.TRUE);
/* 378 */         treePath1 = treePath1.getParentPath();
/*     */       } 
/* 380 */       bool = false;
/* 381 */       enumeration = this.treeState.getVisiblePathsFrom(treePath);
/* 382 */       while (!bool && enumeration.hasMoreElements()) {
/* 383 */         TreePath treePath2 = enumeration.nextElement();
/* 384 */         Rectangle rectangle2 = getPathBounds(this.tree, treePath2);
/* 385 */         if (treePath2 != null && rectangle2 != null) {
/* 386 */           boolean bool2, bool3, bool4 = treeModel.isLeaf(treePath2.getLastPathComponent());
/* 387 */           if (bool4) {
/* 388 */             bool2 = bool3 = false;
/*     */           } else {
/*     */             
/* 391 */             bool2 = this.treeState.getExpandedState(treePath2);
/* 392 */             bool3 = this.tree.hasBeenExpanded(treePath2);
/*     */           } 
/*     */           
/* 395 */           treePath1 = treePath2.getParentPath();
/* 396 */           if (treePath1 != null) {
/* 397 */             if (this.drawingCache.get(treePath1) == null) {
/* 398 */               paintVerticalPartOfLeg(paramGraphics, rectangle, insets, treePath1);
/*     */               
/* 400 */               this.drawingCache.put(treePath1, Boolean.TRUE);
/*     */             } 
/* 402 */             paintHorizontalPartOfLeg(paramGraphics, rectangle, insets, rectangle2, treePath2, i, bool2, bool3, bool4);
/*     */ 
/*     */ 
/*     */           
/*     */           }
/* 407 */           else if (bool1 && i == 0) {
/* 408 */             paintHorizontalPartOfLeg(paramGraphics, rectangle, insets, rectangle2, treePath2, i, bool2, bool3, bool4);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 413 */           if (shouldPaintExpandControl(treePath2, i, bool2, bool3, bool4))
/*     */           {
/* 415 */             paintExpandControl(paramGraphics, rectangle, insets, rectangle2, treePath2, i, bool2, bool3, bool4);
/*     */           }
/*     */ 
/*     */           
/* 419 */           if (rectangle2.y + rectangle2.height >= j) {
/* 420 */             bool = true;
/*     */           }
/*     */         } else {
/*     */           
/* 424 */           bool = true;
/*     */         } 
/* 426 */         i++;
/*     */       } 
/*     */     } 
/* 429 */     synthContext.dispose();
/*     */     
/* 431 */     paintDropLine(paramGraphics);
/*     */ 
/*     */     
/* 434 */     this.rendererPane.removeAll();
/*     */     
/* 436 */     this.paintContext = null;
/*     */   }
/*     */   
/*     */   private void configureRenderer(SynthContext paramSynthContext) {
/* 440 */     TreeCellRenderer treeCellRenderer = this.tree.getCellRenderer();
/*     */     
/* 442 */     if (treeCellRenderer instanceof DefaultTreeCellRenderer) {
/* 443 */       DefaultTreeCellRenderer defaultTreeCellRenderer = (DefaultTreeCellRenderer)treeCellRenderer;
/* 444 */       SynthStyle synthStyle = paramSynthContext.getStyle();
/*     */       
/* 446 */       paramSynthContext.setComponentState(513);
/* 447 */       Color color = defaultTreeCellRenderer.getTextSelectionColor();
/* 448 */       if (color == null || color instanceof UIResource) {
/* 449 */         defaultTreeCellRenderer.setTextSelectionColor(synthStyle.getColor(paramSynthContext, ColorType.TEXT_FOREGROUND));
/*     */       }
/*     */       
/* 452 */       color = defaultTreeCellRenderer.getBackgroundSelectionColor();
/* 453 */       if (color == null || color instanceof UIResource) {
/* 454 */         defaultTreeCellRenderer.setBackgroundSelectionColor(synthStyle.getColor(paramSynthContext, ColorType.TEXT_BACKGROUND));
/*     */       }
/*     */ 
/*     */       
/* 458 */       paramSynthContext.setComponentState(1);
/* 459 */       color = defaultTreeCellRenderer.getTextNonSelectionColor();
/* 460 */       if (color == null || color instanceof UIResource) {
/* 461 */         defaultTreeCellRenderer.setTextNonSelectionColor(synthStyle.getColorForState(paramSynthContext, ColorType.TEXT_FOREGROUND));
/*     */       }
/*     */       
/* 464 */       color = defaultTreeCellRenderer.getBackgroundNonSelectionColor();
/* 465 */       if (color == null || color instanceof UIResource) {
/* 466 */         defaultTreeCellRenderer.setBackgroundNonSelectionColor(synthStyle.getColorForState(paramSynthContext, ColorType.TEXT_BACKGROUND));
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
/*     */   protected void paintHorizontalPartOfLeg(Graphics paramGraphics, Rectangle paramRectangle1, Insets paramInsets, Rectangle paramRectangle2, TreePath paramTreePath, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
/* 482 */     if (this.drawHorizontalLines) {
/* 483 */       super.paintHorizontalPartOfLeg(paramGraphics, paramRectangle1, paramInsets, paramRectangle2, paramTreePath, paramInt, paramBoolean1, paramBoolean2, paramBoolean3);
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
/*     */   protected void paintHorizontalLine(Graphics paramGraphics, JComponent paramJComponent, int paramInt1, int paramInt2, int paramInt3) {
/* 495 */     this.paintContext.getStyle().getGraphicsUtils(this.paintContext).drawLine(this.paintContext, "Tree.horizontalLine", paramGraphics, paramInt2, paramInt1, paramInt3, paramInt1, this.linesStyle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintVerticalPartOfLeg(Graphics paramGraphics, Rectangle paramRectangle, Insets paramInsets, TreePath paramTreePath) {
/* 506 */     if (this.drawVerticalLines) {
/* 507 */       super.paintVerticalPartOfLeg(paramGraphics, paramRectangle, paramInsets, paramTreePath);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintVerticalLine(Graphics paramGraphics, JComponent paramJComponent, int paramInt1, int paramInt2, int paramInt3) {
/* 517 */     this.paintContext.getStyle().getGraphicsUtils(this.paintContext).drawLine(this.paintContext, "Tree.verticalLine", paramGraphics, paramInt1, paramInt2, paramInt1, paramInt3, this.linesStyle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void paintRow(TreeCellRenderer paramTreeCellRenderer, DefaultTreeCellRenderer paramDefaultTreeCellRenderer, SynthContext paramSynthContext1, SynthContext paramSynthContext2, Graphics paramGraphics, Rectangle paramRectangle1, Insets paramInsets, Rectangle paramRectangle2, Rectangle paramRectangle3, TreePath paramTreePath, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
/*     */     byte b;
/* 528 */     boolean bool = this.tree.isRowSelected(paramInt);
/*     */     
/* 530 */     JTree.DropLocation dropLocation = this.tree.getDropLocation();
/*     */ 
/*     */     
/* 533 */     boolean bool1 = (dropLocation != null && dropLocation.getChildIndex() == -1 && paramTreePath == dropLocation.getPath()) ? true : false;
/*     */     
/* 535 */     int i = 1;
/* 536 */     if (bool || bool1) {
/* 537 */       i |= 0x200;
/*     */     }
/*     */     
/* 540 */     if (this.tree.isFocusOwner() && paramInt == getLeadSelectionRow()) {
/* 541 */       i |= 0x100;
/*     */     }
/*     */     
/* 544 */     paramSynthContext2.setComponentState(i);
/*     */     
/* 546 */     if (paramDefaultTreeCellRenderer != null && paramDefaultTreeCellRenderer.getBorderSelectionColor() instanceof UIResource)
/*     */     {
/* 548 */       paramDefaultTreeCellRenderer.setBorderSelectionColor(this.style.getColor(paramSynthContext2, ColorType.FOCUS));
/*     */     }
/*     */     
/* 551 */     SynthLookAndFeel.updateSubregion(paramSynthContext2, paramGraphics, paramRectangle3);
/* 552 */     paramSynthContext2.getPainter().paintTreeCellBackground(paramSynthContext2, paramGraphics, paramRectangle3.x, paramRectangle3.y, paramRectangle3.width, paramRectangle3.height);
/*     */ 
/*     */     
/* 555 */     paramSynthContext2.getPainter().paintTreeCellBorder(paramSynthContext2, paramGraphics, paramRectangle3.x, paramRectangle3.y, paramRectangle3.width, paramRectangle3.height);
/*     */ 
/*     */     
/* 558 */     if (this.editingComponent != null && this.editingRow == paramInt) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 564 */     if (this.tree.hasFocus()) {
/* 565 */       b = getLeadSelectionRow();
/*     */     } else {
/*     */       
/* 568 */       b = -1;
/*     */     } 
/*     */     
/* 571 */     Component component = paramTreeCellRenderer.getTreeCellRendererComponent(this.tree, paramTreePath
/* 572 */         .getLastPathComponent(), bool, paramBoolean1, paramBoolean3, paramInt, (b == paramInt));
/*     */ 
/*     */ 
/*     */     
/* 576 */     this.rendererPane.paintComponent(paramGraphics, component, this.tree, paramRectangle2.x, paramRectangle2.y, paramRectangle2.width, paramRectangle2.height, true);
/*     */   }
/*     */ 
/*     */   
/*     */   private int findCenteredX(int paramInt1, int paramInt2) {
/* 581 */     return this.tree.getComponentOrientation().isLeftToRight() ? (paramInt1 - 
/* 582 */       (int)Math.ceil(paramInt2 / 2.0D)) : (paramInt1 - 
/* 583 */       (int)Math.floor(paramInt2 / 2.0D));
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
/*     */   protected void paintExpandControl(Graphics paramGraphics, Rectangle paramRectangle1, Insets paramInsets, Rectangle paramRectangle2, TreePath paramTreePath, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
/* 597 */     boolean bool = this.tree.getSelectionModel().isPathSelected(paramTreePath);
/* 598 */     int i = this.paintContext.getComponentState();
/* 599 */     if (bool) {
/* 600 */       this.paintContext.setComponentState(i | 0x200);
/*     */     }
/* 602 */     super.paintExpandControl(paramGraphics, paramRectangle1, paramInsets, paramRectangle2, paramTreePath, paramInt, paramBoolean1, paramBoolean2, paramBoolean3);
/*     */     
/* 604 */     this.paintContext.setComponentState(i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawCentered(Component paramComponent, Graphics paramGraphics, Icon paramIcon, int paramInt1, int paramInt2) {
/* 613 */     int i = SynthIcon.getIconWidth(paramIcon, this.paintContext);
/* 614 */     int j = SynthIcon.getIconHeight(paramIcon, this.paintContext);
/*     */     
/* 616 */     SynthIcon.paintIcon(paramIcon, this.paintContext, paramGraphics, 
/* 617 */         findCenteredX(paramInt1, i), paramInt2 - j / 2, i, j);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 626 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent)) {
/* 627 */       updateStyle((JTree)paramPropertyChangeEvent.getSource());
/*     */     }
/*     */     
/* 630 */     if ("dropLocation" == paramPropertyChangeEvent.getPropertyName()) {
/* 631 */       JTree.DropLocation dropLocation = (JTree.DropLocation)paramPropertyChangeEvent.getOldValue();
/* 632 */       repaintDropLocation(dropLocation);
/* 633 */       repaintDropLocation(this.tree.getDropLocation());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintDropLine(Graphics paramGraphics) {
/* 642 */     JTree.DropLocation dropLocation = this.tree.getDropLocation();
/* 643 */     if (!isDropLine(dropLocation)) {
/*     */       return;
/*     */     }
/*     */     
/* 647 */     Color color = (Color)this.style.get(this.paintContext, "Tree.dropLineColor");
/* 648 */     if (color != null) {
/* 649 */       paramGraphics.setColor(color);
/* 650 */       Rectangle rectangle = getDropLineRect(dropLocation);
/* 651 */       paramGraphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */     } 
/*     */   }
/*     */   private void repaintDropLocation(JTree.DropLocation paramDropLocation) {
/*     */     Rectangle rectangle;
/* 656 */     if (paramDropLocation == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 662 */     if (isDropLine(paramDropLocation)) {
/* 663 */       rectangle = getDropLineRect(paramDropLocation);
/*     */     } else {
/* 665 */       rectangle = this.tree.getPathBounds(paramDropLocation.getPath());
/* 666 */       if (rectangle != null) {
/* 667 */         rectangle.x = 0;
/* 668 */         rectangle.width = this.tree.getWidth();
/*     */       } 
/*     */     } 
/*     */     
/* 672 */     if (rectangle != null) {
/* 673 */       this.tree.repaint(rectangle);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getRowX(int paramInt1, int paramInt2) {
/* 682 */     return super.getRowX(paramInt1, paramInt2) + this.padding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class SynthTreeCellRenderer
/*     */     extends DefaultTreeCellRenderer
/*     */     implements UIResource
/*     */   {
/*     */     public String getName() {
/* 693 */       return "Tree.cellRenderer";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Component getTreeCellRendererComponent(JTree param1JTree, Object param1Object, boolean param1Boolean1, boolean param1Boolean2, boolean param1Boolean3, int param1Int, boolean param1Boolean4) {
/* 702 */       if (!SynthTreeUI.this.useTreeColors && (param1Boolean1 || param1Boolean4)) {
/* 703 */         SynthLookAndFeel.setSelectedUI(
/* 704 */             (SynthLabelUI)SynthLookAndFeel.getUIOfType(getUI(), SynthLabelUI.class), param1Boolean1, param1Boolean4, param1JTree
/* 705 */             .isEnabled(), false);
/*     */       } else {
/*     */         
/* 708 */         SynthLookAndFeel.resetSelectedUI();
/*     */       } 
/* 710 */       return super.getTreeCellRendererComponent(param1JTree, param1Object, param1Boolean1, param1Boolean2, param1Boolean3, param1Int, param1Boolean4);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void paint(Graphics param1Graphics) {
/* 716 */       paintComponent(param1Graphics);
/* 717 */       if (this.hasFocus) {
/* 718 */         SynthContext synthContext = SynthTreeUI.this.getContext(SynthTreeUI.this.tree, Region.TREE_CELL);
/*     */         
/* 720 */         if (synthContext.getStyle() == null) {
/* 721 */           assert false : "SynthTreeCellRenderer is being used outside of UI that created it";
/*     */           
/*     */           return;
/*     */         } 
/* 725 */         int i = 0;
/* 726 */         Icon icon = getIcon();
/*     */         
/* 728 */         if (icon != null && getText() != null)
/*     */         {
/* 730 */           i = icon.getIconWidth() + Math.max(0, getIconTextGap() - 1);
/*     */         }
/* 732 */         if (this.selected) {
/* 733 */           synthContext.setComponentState(513);
/*     */         } else {
/*     */           
/* 736 */           synthContext.setComponentState(1);
/*     */         } 
/* 738 */         if (getComponentOrientation().isLeftToRight()) {
/* 739 */           synthContext.getPainter().paintTreeCellFocus(synthContext, param1Graphics, i, 0, 
/* 740 */               getWidth() - i, 
/* 741 */               getHeight());
/*     */         } else {
/*     */           
/* 744 */           synthContext.getPainter().paintTreeCellFocus(synthContext, param1Graphics, 0, 0, 
/* 745 */               getWidth() - i, getHeight());
/*     */         } 
/* 747 */         synthContext.dispose();
/*     */       } 
/* 749 */       SynthLookAndFeel.resetSelectedUI();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SynthTreeCellEditor
/*     */     extends DefaultTreeCellEditor
/*     */   {
/*     */     public SynthTreeCellEditor(JTree param1JTree, DefaultTreeCellRenderer param1DefaultTreeCellRenderer) {
/* 757 */       super(param1JTree, param1DefaultTreeCellRenderer);
/* 758 */       setBorderSelectionColor(null);
/*     */     }
/*     */ 
/*     */     
/*     */     protected TreeCellEditor createTreeCellEditor() {
/* 763 */       JTextField jTextField = new JTextField()
/*     */         {
/*     */           public String getName() {
/* 766 */             return "Tree.cellEditor";
/*     */           }
/*     */         };
/* 769 */       DefaultCellEditor defaultCellEditor = new DefaultCellEditor(jTextField);
/*     */ 
/*     */       
/* 772 */       defaultCellEditor.setClickCountToStart(1);
/* 773 */       return defaultCellEditor;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private class ExpandedIconWrapper
/*     */     extends SynthIcon
/*     */   {
/*     */     private ExpandedIconWrapper() {}
/*     */ 
/*     */     
/*     */     public void paintIcon(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 785 */       if (param1SynthContext == null) {
/* 786 */         param1SynthContext = SynthTreeUI.this.getContext(SynthTreeUI.this.tree);
/* 787 */         SynthIcon.paintIcon(SynthTreeUI.this.expandedIcon, param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/* 788 */         param1SynthContext.dispose();
/*     */       } else {
/*     */         
/* 791 */         SynthIcon.paintIcon(SynthTreeUI.this.expandedIcon, param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int getIconWidth(SynthContext param1SynthContext) {
/*     */       int i;
/* 797 */       if (param1SynthContext == null) {
/* 798 */         param1SynthContext = SynthTreeUI.this.getContext(SynthTreeUI.this.tree);
/* 799 */         i = SynthIcon.getIconWidth(SynthTreeUI.this.expandedIcon, param1SynthContext);
/* 800 */         param1SynthContext.dispose();
/*     */       } else {
/*     */         
/* 803 */         i = SynthIcon.getIconWidth(SynthTreeUI.this.expandedIcon, param1SynthContext);
/*     */       } 
/* 805 */       return i;
/*     */     }
/*     */     
/*     */     public int getIconHeight(SynthContext param1SynthContext) {
/*     */       int i;
/* 810 */       if (param1SynthContext == null) {
/* 811 */         param1SynthContext = SynthTreeUI.this.getContext(SynthTreeUI.this.tree);
/* 812 */         i = SynthIcon.getIconHeight(SynthTreeUI.this.expandedIcon, param1SynthContext);
/* 813 */         param1SynthContext.dispose();
/*     */       } else {
/*     */         
/* 816 */         i = SynthIcon.getIconHeight(SynthTreeUI.this.expandedIcon, param1SynthContext);
/*     */       } 
/* 818 */       return i;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthTreeUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */