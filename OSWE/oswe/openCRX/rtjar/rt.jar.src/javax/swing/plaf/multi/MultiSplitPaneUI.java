/*     */ package javax.swing.plaf.multi;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.util.Vector;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JSplitPane;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.SplitPaneUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultiSplitPaneUI
/*     */   extends SplitPaneUI
/*     */ {
/*  51 */   protected Vector uis = new Vector();
/*     */ 
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
/*  63 */     return MultiLookAndFeel.uisToArray(this.uis);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetToPreferredSizes(JSplitPane paramJSplitPane) {
/*  74 */     for (byte b = 0; b < this.uis.size(); b++) {
/*  75 */       ((SplitPaneUI)this.uis.elementAt(b)).resetToPreferredSizes(paramJSplitPane);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDividerLocation(JSplitPane paramJSplitPane, int paramInt) {
/*  83 */     for (byte b = 0; b < this.uis.size(); b++) {
/*  84 */       ((SplitPaneUI)this.uis.elementAt(b)).setDividerLocation(paramJSplitPane, paramInt);
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
/*     */   public int getDividerLocation(JSplitPane paramJSplitPane) {
/*  96 */     int i = ((SplitPaneUI)this.uis.elementAt(0)).getDividerLocation(paramJSplitPane);
/*  97 */     for (byte b = 1; b < this.uis.size(); b++) {
/*  98 */       ((SplitPaneUI)this.uis.elementAt(b)).getDividerLocation(paramJSplitPane);
/*     */     }
/* 100 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinimumDividerLocation(JSplitPane paramJSplitPane) {
/* 111 */     int i = ((SplitPaneUI)this.uis.elementAt(0)).getMinimumDividerLocation(paramJSplitPane);
/* 112 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 113 */       ((SplitPaneUI)this.uis.elementAt(b)).getMinimumDividerLocation(paramJSplitPane);
/*     */     }
/* 115 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaximumDividerLocation(JSplitPane paramJSplitPane) {
/* 126 */     int i = ((SplitPaneUI)this.uis.elementAt(0)).getMaximumDividerLocation(paramJSplitPane);
/* 127 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 128 */       ((SplitPaneUI)this.uis.elementAt(b)).getMaximumDividerLocation(paramJSplitPane);
/*     */     }
/* 130 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void finishedPaintingChildren(JSplitPane paramJSplitPane, Graphics paramGraphics) {
/* 137 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 138 */       ((SplitPaneUI)this.uis.elementAt(b)).finishedPaintingChildren(paramJSplitPane, paramGraphics);
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
/*     */   public boolean contains(JComponent paramJComponent, int paramInt1, int paramInt2) {
/* 154 */     boolean bool = ((ComponentUI)this.uis.elementAt(0)).contains(paramJComponent, paramInt1, paramInt2);
/* 155 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 156 */       ((ComponentUI)this.uis.elementAt(b)).contains(paramJComponent, paramInt1, paramInt2);
/*     */     }
/* 158 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(Graphics paramGraphics, JComponent paramJComponent) {
/* 165 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 166 */       ((ComponentUI)this.uis.elementAt(b)).update(paramGraphics, paramJComponent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 176 */     MultiSplitPaneUI multiSplitPaneUI = new MultiSplitPaneUI();
/* 177 */     return MultiLookAndFeel.createUIs(multiSplitPaneUI, multiSplitPaneUI.uis, paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/* 186 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 187 */       ((ComponentUI)this.uis.elementAt(b)).installUI(paramJComponent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/* 195 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 196 */       ((ComponentUI)this.uis.elementAt(b)).uninstallUI(paramJComponent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 204 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 205 */       ((ComponentUI)this.uis.elementAt(b)).paint(paramGraphics, paramJComponent);
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
/* 217 */     Dimension dimension = ((ComponentUI)this.uis.elementAt(0)).getPreferredSize(paramJComponent);
/* 218 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 219 */       ((ComponentUI)this.uis.elementAt(b)).getPreferredSize(paramJComponent);
/*     */     }
/* 221 */     return dimension;
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
/* 232 */     Dimension dimension = ((ComponentUI)this.uis.elementAt(0)).getMinimumSize(paramJComponent);
/* 233 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 234 */       ((ComponentUI)this.uis.elementAt(b)).getMinimumSize(paramJComponent);
/*     */     }
/* 236 */     return dimension;
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
/* 247 */     Dimension dimension = ((ComponentUI)this.uis.elementAt(0)).getMaximumSize(paramJComponent);
/* 248 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 249 */       ((ComponentUI)this.uis.elementAt(b)).getMaximumSize(paramJComponent);
/*     */     }
/* 251 */     return dimension;
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
/* 262 */     int i = ((ComponentUI)this.uis.elementAt(0)).getAccessibleChildrenCount(paramJComponent);
/* 263 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 264 */       ((ComponentUI)this.uis.elementAt(b)).getAccessibleChildrenCount(paramJComponent);
/*     */     }
/* 266 */     return i;
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
/* 277 */     Accessible accessible = ((ComponentUI)this.uis.elementAt(0)).getAccessibleChild(paramJComponent, paramInt);
/* 278 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 279 */       ((ComponentUI)this.uis.elementAt(b)).getAccessibleChild(paramJComponent, paramInt);
/*     */     }
/* 281 */     return accessible;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/multi/MultiSplitPaneUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */