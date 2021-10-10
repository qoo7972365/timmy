/*     */ package javax.swing.tree;
/*     */ 
/*     */ import java.awt.Rectangle;
/*     */ import java.util.Enumeration;
/*     */ import javax.swing.event.TreeModelEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractLayoutCache
/*     */   implements RowMapper
/*     */ {
/*     */   protected NodeDimensions nodeDimensions;
/*     */   protected TreeModel treeModel;
/*     */   protected TreeSelectionModel treeSelectionModel;
/*     */   protected boolean rootVisible;
/*     */   protected int rowHeight;
/*     */   
/*     */   public void setNodeDimensions(NodeDimensions paramNodeDimensions) {
/*  77 */     this.nodeDimensions = paramNodeDimensions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NodeDimensions getNodeDimensions() {
/*  87 */     return this.nodeDimensions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModel(TreeModel paramTreeModel) {
/*  97 */     this.treeModel = paramTreeModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TreeModel getModel() {
/* 106 */     return this.treeModel;
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
/*     */   public void setRootVisible(boolean paramBoolean) {
/* 121 */     this.rootVisible = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRootVisible() {
/* 131 */     return this.rootVisible;
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
/*     */   public void setRowHeight(int paramInt) {
/* 145 */     this.rowHeight = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRowHeight() {
/* 154 */     return this.rowHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectionModel(TreeSelectionModel paramTreeSelectionModel) {
/* 164 */     if (this.treeSelectionModel != null)
/* 165 */       this.treeSelectionModel.setRowMapper(null); 
/* 166 */     this.treeSelectionModel = paramTreeSelectionModel;
/* 167 */     if (this.treeSelectionModel != null) {
/* 168 */       this.treeSelectionModel.setRowMapper(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TreeSelectionModel getSelectionModel() {
/* 177 */     return this.treeSelectionModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPreferredHeight() {
/* 187 */     int i = getRowCount();
/*     */     
/* 189 */     if (i > 0) {
/* 190 */       Rectangle rectangle = getBounds(getPathForRow(i - 1), null);
/*     */ 
/*     */       
/* 193 */       if (rectangle != null)
/* 194 */         return rectangle.y + rectangle.height; 
/*     */     } 
/* 196 */     return 0;
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
/*     */   public int getPreferredWidth(Rectangle paramRectangle) {
/* 213 */     int i = getRowCount();
/*     */     
/* 215 */     if (i > 0) {
/*     */       TreePath treePath;
/*     */       
/*     */       int j;
/*     */       
/* 220 */       if (paramRectangle == null) {
/* 221 */         treePath = getPathForRow(0);
/* 222 */         j = Integer.MAX_VALUE;
/*     */       } else {
/*     */         
/* 225 */         treePath = getPathClosestTo(paramRectangle.x, paramRectangle.y);
/* 226 */         j = paramRectangle.height + paramRectangle.y;
/*     */       } 
/*     */       
/* 229 */       Enumeration<TreePath> enumeration = getVisiblePathsFrom(treePath);
/*     */       
/* 231 */       if (enumeration != null && enumeration.hasMoreElements()) {
/* 232 */         int k; Rectangle rectangle = getBounds(enumeration.nextElement(), null);
/*     */ 
/*     */ 
/*     */         
/* 236 */         if (rectangle != null) {
/* 237 */           k = rectangle.x + rectangle.width;
/* 238 */           if (rectangle.y >= j) {
/* 239 */             return k;
/*     */           }
/*     */         } else {
/*     */           
/* 243 */           k = 0;
/* 244 */         }  while (rectangle != null && enumeration.hasMoreElements()) {
/* 245 */           rectangle = getBounds(enumeration.nextElement(), rectangle);
/*     */           
/* 247 */           if (rectangle != null && rectangle.y < j) {
/* 248 */             k = Math.max(k, rectangle.x + rectangle.width);
/*     */             continue;
/*     */           } 
/* 251 */           rectangle = null;
/*     */         } 
/*     */         
/* 254 */         return k;
/*     */       } 
/*     */     } 
/* 257 */     return 0;
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
/*     */   public abstract boolean isExpanded(TreePath paramTreePath);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Rectangle getBounds(TreePath paramTreePath, Rectangle paramRectangle);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract TreePath getPathForRow(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getRowForPath(TreePath paramTreePath);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract TreePath getPathClosestTo(int paramInt1, int paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Enumeration<TreePath> getVisiblePathsFrom(TreePath paramTreePath);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getVisibleChildCount(TreePath paramTreePath);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void setExpandedState(TreePath paramTreePath, boolean paramBoolean);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean getExpandedState(TreePath paramTreePath);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getRowCount();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void invalidateSizes();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void invalidatePathBounds(TreePath paramTreePath);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void treeNodesChanged(TreeModelEvent paramTreeModelEvent);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void treeNodesInserted(TreeModelEvent paramTreeModelEvent);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void treeNodesRemoved(TreeModelEvent paramTreeModelEvent);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void treeStructureChanged(TreeModelEvent paramTreeModelEvent);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getRowsForPaths(TreePath[] paramArrayOfTreePath) {
/* 453 */     if (paramArrayOfTreePath == null) {
/* 454 */       return null;
/*     */     }
/* 456 */     int i = paramArrayOfTreePath.length;
/* 457 */     int[] arrayOfInt = new int[i];
/*     */     
/* 459 */     for (byte b = 0; b < i; b++)
/* 460 */       arrayOfInt[b] = getRowForPath(paramArrayOfTreePath[b]); 
/* 461 */     return arrayOfInt;
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
/*     */   protected Rectangle getNodeDimensions(Object paramObject, int paramInt1, int paramInt2, boolean paramBoolean, Rectangle paramRectangle) {
/* 489 */     NodeDimensions nodeDimensions = getNodeDimensions();
/*     */     
/* 491 */     if (nodeDimensions != null) {
/* 492 */       return nodeDimensions.getNodeDimensions(paramObject, paramInt1, paramInt2, paramBoolean, paramRectangle);
/*     */     }
/* 494 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isFixedRowHeight() {
/* 501 */     return (this.rowHeight > 0);
/*     */   }
/*     */   
/*     */   public static abstract class NodeDimensions {
/*     */     public abstract Rectangle getNodeDimensions(Object param1Object, int param1Int1, int param1Int2, boolean param1Boolean, Rectangle param1Rectangle);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/tree/AbstractLayoutCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */