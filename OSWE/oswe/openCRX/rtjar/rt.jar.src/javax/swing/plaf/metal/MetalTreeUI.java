/*     */ package javax.swing.plaf.metal;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicTreeUI;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetalTreeUI
/*     */   extends BasicTreeUI
/*     */ {
/*     */   private static Color lineColor;
/*     */   private static final String LINE_STYLE = "JTree.lineStyle";
/*     */   private static final String LEG_LINE_STYLE_STRING = "Angled";
/*     */   private static final String HORIZ_STYLE_STRING = "Horizontal";
/*     */   private static final String NO_STYLE_STRING = "None";
/*     */   private static final int LEG_LINE_STYLE = 2;
/*     */   private static final int HORIZ_LINE_STYLE = 1;
/*     */   private static final int NO_LINE_STYLE = 0;
/*  93 */   private int lineStyle = 2;
/*  94 */   private PropertyChangeListener lineStyleListener = new LineListener();
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  98 */     return new MetalTreeUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getHorizontalLegBuffer() {
/* 108 */     return 3;
/*     */   }
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/* 112 */     super.installUI(paramJComponent);
/* 113 */     lineColor = UIManager.getColor("Tree.line");
/*     */     
/* 115 */     Object object = paramJComponent.getClientProperty("JTree.lineStyle");
/* 116 */     decodeLineStyle(object);
/* 117 */     paramJComponent.addPropertyChangeListener(this.lineStyleListener);
/*     */   }
/*     */ 
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/* 122 */     paramJComponent.removePropertyChangeListener(this.lineStyleListener);
/* 123 */     super.uninstallUI(paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decodeLineStyle(Object paramObject) {
/* 131 */     if (paramObject == null || paramObject
/* 132 */       .equals("Angled")) {
/* 133 */       this.lineStyle = 2;
/*     */     }
/* 135 */     else if (paramObject.equals("None")) {
/* 136 */       this.lineStyle = 0;
/* 137 */     } else if (paramObject.equals("Horizontal")) {
/* 138 */       this.lineStyle = 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isLocationInExpandControl(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 146 */     if (this.tree != null && !isLeaf(paramInt1)) {
/*     */       byte b;
/*     */       
/* 149 */       if (getExpandedIcon() != null) {
/* 150 */         b = getExpandedIcon().getIconWidth() + 6;
/*     */       } else {
/* 152 */         b = 8;
/*     */       } 
/* 154 */       Insets insets = this.tree.getInsets();
/* 155 */       int i = (insets != null) ? insets.left : 0;
/*     */ 
/*     */       
/* 158 */       i += (paramInt2 + this.depthOffset - 1) * this.totalChildIndent + 
/* 159 */         getLeftChildIndent() - b / 2;
/*     */       
/* 161 */       int j = i + b;
/*     */       
/* 163 */       return (paramInt3 >= i && paramInt3 <= j);
/*     */     } 
/* 165 */     return false;
/*     */   }
/*     */   
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 169 */     super.paint(paramGraphics, paramJComponent);
/*     */ 
/*     */ 
/*     */     
/* 173 */     if (this.lineStyle == 1 && !this.largeModel) {
/* 174 */       paintHorizontalSeparators(paramGraphics, paramJComponent);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void paintHorizontalSeparators(Graphics paramGraphics, JComponent paramJComponent) {
/* 179 */     paramGraphics.setColor(lineColor);
/*     */     
/* 181 */     Rectangle rectangle = paramGraphics.getClipBounds();
/*     */     
/* 183 */     int i = getRowForPath(this.tree, 
/* 184 */         getClosestPathForLocation(this.tree, 0, rectangle.y));
/* 185 */     int j = getRowForPath(this.tree, 
/* 186 */         getClosestPathForLocation(this.tree, 0, rectangle.y + rectangle.height - 1));
/*     */     
/* 188 */     if (i <= -1 || j <= -1) {
/*     */       return;
/*     */     }
/*     */     
/* 192 */     for (int k = i; k <= j; k++) {
/* 193 */       TreePath treePath = getPathForRow(this.tree, k);
/*     */       
/* 195 */       if (treePath != null && treePath.getPathCount() == 2) {
/* 196 */         Rectangle rectangle1 = getPathBounds(this.tree, 
/* 197 */             getPathForRow(this.tree, k));
/*     */ 
/*     */         
/* 200 */         if (rectangle1 != null) {
/* 201 */           paramGraphics.drawLine(rectangle.x, rectangle1.y, rectangle.x + rectangle.width, rectangle1.y);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintVerticalPartOfLeg(Graphics paramGraphics, Rectangle paramRectangle, Insets paramInsets, TreePath paramTreePath) {
/* 210 */     if (this.lineStyle == 2) {
/* 211 */       super.paintVerticalPartOfLeg(paramGraphics, paramRectangle, paramInsets, paramTreePath);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintHorizontalPartOfLeg(Graphics paramGraphics, Rectangle paramRectangle1, Insets paramInsets, Rectangle paramRectangle2, TreePath paramTreePath, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
/* 221 */     if (this.lineStyle == 2) {
/* 222 */       super.paintHorizontalPartOfLeg(paramGraphics, paramRectangle1, paramInsets, paramRectangle2, paramTreePath, paramInt, paramBoolean1, paramBoolean2, paramBoolean3);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   class LineListener
/*     */     implements PropertyChangeListener
/*     */   {
/*     */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 231 */       String str = param1PropertyChangeEvent.getPropertyName();
/* 232 */       if (str.equals("JTree.lineStyle"))
/* 233 */         MetalTreeUI.this.decodeLineStyle(param1PropertyChangeEvent.getNewValue()); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalTreeUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */