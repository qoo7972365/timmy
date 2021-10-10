/*     */ package javax.swing.plaf.multi;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.util.Vector;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.TreeUI;
/*     */ import javax.swing.tree.TreePath;
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
/*     */ public class MultiTreeUI
/*     */   extends TreeUI
/*     */ {
/*  53 */   protected Vector uis = new Vector();
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
/*     */   public ComponentUI[] getUIs() {
/*  65 */     return MultiLookAndFeel.uisToArray(this.uis);
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
/*     */   public Rectangle getPathBounds(JTree paramJTree, TreePath paramTreePath) {
/*  80 */     Rectangle rectangle = ((TreeUI)this.uis.elementAt(0)).getPathBounds(paramJTree, paramTreePath);
/*  81 */     for (byte b = 1; b < this.uis.size(); b++) {
/*  82 */       ((TreeUI)this.uis.elementAt(b)).getPathBounds(paramJTree, paramTreePath);
/*     */     }
/*  84 */     return rectangle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TreePath getPathForRow(JTree paramJTree, int paramInt) {
/*  95 */     TreePath treePath = ((TreeUI)this.uis.elementAt(0)).getPathForRow(paramJTree, paramInt);
/*  96 */     for (byte b = 1; b < this.uis.size(); b++) {
/*  97 */       ((TreeUI)this.uis.elementAt(b)).getPathForRow(paramJTree, paramInt);
/*     */     }
/*  99 */     return treePath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRowForPath(JTree paramJTree, TreePath paramTreePath) {
/* 110 */     int i = ((TreeUI)this.uis.elementAt(0)).getRowForPath(paramJTree, paramTreePath);
/* 111 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 112 */       ((TreeUI)this.uis.elementAt(b)).getRowForPath(paramJTree, paramTreePath);
/*     */     }
/* 114 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRowCount(JTree paramJTree) {
/* 125 */     int i = ((TreeUI)this.uis.elementAt(0)).getRowCount(paramJTree);
/* 126 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 127 */       ((TreeUI)this.uis.elementAt(b)).getRowCount(paramJTree);
/*     */     }
/* 129 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TreePath getClosestPathForLocation(JTree paramJTree, int paramInt1, int paramInt2) {
/* 140 */     TreePath treePath = ((TreeUI)this.uis.elementAt(0)).getClosestPathForLocation(paramJTree, paramInt1, paramInt2);
/* 141 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 142 */       ((TreeUI)this.uis.elementAt(b)).getClosestPathForLocation(paramJTree, paramInt1, paramInt2);
/*     */     }
/* 144 */     return treePath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEditing(JTree paramJTree) {
/* 155 */     boolean bool = ((TreeUI)this.uis.elementAt(0)).isEditing(paramJTree);
/* 156 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 157 */       ((TreeUI)this.uis.elementAt(b)).isEditing(paramJTree);
/*     */     }
/* 159 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean stopEditing(JTree paramJTree) {
/* 170 */     boolean bool = ((TreeUI)this.uis.elementAt(0)).stopEditing(paramJTree);
/* 171 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 172 */       ((TreeUI)this.uis.elementAt(b)).stopEditing(paramJTree);
/*     */     }
/* 174 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancelEditing(JTree paramJTree) {
/* 181 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 182 */       ((TreeUI)this.uis.elementAt(b)).cancelEditing(paramJTree);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startEditingAtPath(JTree paramJTree, TreePath paramTreePath) {
/* 190 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 191 */       ((TreeUI)this.uis.elementAt(b)).startEditingAtPath(paramJTree, paramTreePath);
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
/*     */   public TreePath getEditingPath(JTree paramJTree) {
/* 203 */     TreePath treePath = ((TreeUI)this.uis.elementAt(0)).getEditingPath(paramJTree);
/* 204 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 205 */       ((TreeUI)this.uis.elementAt(b)).getEditingPath(paramJTree);
/*     */     }
/* 207 */     return treePath;
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
/*     */   public boolean contains(JComponent paramJComponent, int paramInt1, int paramInt2) {
/* 222 */     boolean bool = ((ComponentUI)this.uis.elementAt(0)).contains(paramJComponent, paramInt1, paramInt2);
/* 223 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 224 */       ((ComponentUI)this.uis.elementAt(b)).contains(paramJComponent, paramInt1, paramInt2);
/*     */     }
/* 226 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(Graphics paramGraphics, JComponent paramJComponent) {
/* 233 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 234 */       ((ComponentUI)this.uis.elementAt(b)).update(paramGraphics, paramJComponent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 244 */     MultiTreeUI multiTreeUI = new MultiTreeUI();
/* 245 */     return MultiLookAndFeel.createUIs(multiTreeUI, multiTreeUI.uis, paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/* 254 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 255 */       ((ComponentUI)this.uis.elementAt(b)).installUI(paramJComponent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/* 263 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 264 */       ((ComponentUI)this.uis.elementAt(b)).uninstallUI(paramJComponent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 272 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 273 */       ((ComponentUI)this.uis.elementAt(b)).paint(paramGraphics, paramJComponent);
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
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 285 */     Dimension dimension = ((ComponentUI)this.uis.elementAt(0)).getPreferredSize(paramJComponent);
/* 286 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 287 */       ((ComponentUI)this.uis.elementAt(b)).getPreferredSize(paramJComponent);
/*     */     }
/* 289 */     return dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMinimumSize(JComponent paramJComponent) {
/* 300 */     Dimension dimension = ((ComponentUI)this.uis.elementAt(0)).getMinimumSize(paramJComponent);
/* 301 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 302 */       ((ComponentUI)this.uis.elementAt(b)).getMinimumSize(paramJComponent);
/*     */     }
/* 304 */     return dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMaximumSize(JComponent paramJComponent) {
/* 315 */     Dimension dimension = ((ComponentUI)this.uis.elementAt(0)).getMaximumSize(paramJComponent);
/* 316 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 317 */       ((ComponentUI)this.uis.elementAt(b)).getMaximumSize(paramJComponent);
/*     */     }
/* 319 */     return dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAccessibleChildrenCount(JComponent paramJComponent) {
/* 330 */     int i = ((ComponentUI)this.uis.elementAt(0)).getAccessibleChildrenCount(paramJComponent);
/* 331 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 332 */       ((ComponentUI)this.uis.elementAt(b)).getAccessibleChildrenCount(paramJComponent);
/*     */     }
/* 334 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Accessible getAccessibleChild(JComponent paramJComponent, int paramInt) {
/* 345 */     Accessible accessible = ((ComponentUI)this.uis.elementAt(0)).getAccessibleChild(paramJComponent, paramInt);
/* 346 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 347 */       ((ComponentUI)this.uis.elementAt(b)).getAccessibleChild(paramJComponent, paramInt);
/*     */     }
/* 349 */     return accessible;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/multi/MultiTreeUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */