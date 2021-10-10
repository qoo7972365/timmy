/*     */ package javax.swing.plaf.multi;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.util.Vector;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.TabbedPaneUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultiTabbedPaneUI
/*     */   extends TabbedPaneUI
/*     */ {
/*  52 */   protected Vector uis = new Vector();
/*     */ 
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
/*  64 */     return MultiLookAndFeel.uisToArray(this.uis);
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
/*     */   public int tabForCoordinate(JTabbedPane paramJTabbedPane, int paramInt1, int paramInt2) {
/*  79 */     int i = ((TabbedPaneUI)this.uis.elementAt(0)).tabForCoordinate(paramJTabbedPane, paramInt1, paramInt2);
/*  80 */     for (byte b = 1; b < this.uis.size(); b++) {
/*  81 */       ((TabbedPaneUI)this.uis.elementAt(b)).tabForCoordinate(paramJTabbedPane, paramInt1, paramInt2);
/*     */     }
/*  83 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle getTabBounds(JTabbedPane paramJTabbedPane, int paramInt) {
/*  94 */     Rectangle rectangle = ((TabbedPaneUI)this.uis.elementAt(0)).getTabBounds(paramJTabbedPane, paramInt);
/*  95 */     for (byte b = 1; b < this.uis.size(); b++) {
/*  96 */       ((TabbedPaneUI)this.uis.elementAt(b)).getTabBounds(paramJTabbedPane, paramInt);
/*     */     }
/*  98 */     return rectangle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTabRunCount(JTabbedPane paramJTabbedPane) {
/* 109 */     int i = ((TabbedPaneUI)this.uis.elementAt(0)).getTabRunCount(paramJTabbedPane);
/* 110 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 111 */       ((TabbedPaneUI)this.uis.elementAt(b)).getTabRunCount(paramJTabbedPane);
/*     */     }
/* 113 */     return i;
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
/* 128 */     boolean bool = ((ComponentUI)this.uis.elementAt(0)).contains(paramJComponent, paramInt1, paramInt2);
/* 129 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 130 */       ((ComponentUI)this.uis.elementAt(b)).contains(paramJComponent, paramInt1, paramInt2);
/*     */     }
/* 132 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(Graphics paramGraphics, JComponent paramJComponent) {
/* 139 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 140 */       ((ComponentUI)this.uis.elementAt(b)).update(paramGraphics, paramJComponent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 150 */     MultiTabbedPaneUI multiTabbedPaneUI = new MultiTabbedPaneUI();
/* 151 */     return MultiLookAndFeel.createUIs(multiTabbedPaneUI, multiTabbedPaneUI.uis, paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/* 160 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 161 */       ((ComponentUI)this.uis.elementAt(b)).installUI(paramJComponent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/* 169 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 170 */       ((ComponentUI)this.uis.elementAt(b)).uninstallUI(paramJComponent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 178 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 179 */       ((ComponentUI)this.uis.elementAt(b)).paint(paramGraphics, paramJComponent);
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
/* 191 */     Dimension dimension = ((ComponentUI)this.uis.elementAt(0)).getPreferredSize(paramJComponent);
/* 192 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 193 */       ((ComponentUI)this.uis.elementAt(b)).getPreferredSize(paramJComponent);
/*     */     }
/* 195 */     return dimension;
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
/* 206 */     Dimension dimension = ((ComponentUI)this.uis.elementAt(0)).getMinimumSize(paramJComponent);
/* 207 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 208 */       ((ComponentUI)this.uis.elementAt(b)).getMinimumSize(paramJComponent);
/*     */     }
/* 210 */     return dimension;
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
/* 221 */     Dimension dimension = ((ComponentUI)this.uis.elementAt(0)).getMaximumSize(paramJComponent);
/* 222 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 223 */       ((ComponentUI)this.uis.elementAt(b)).getMaximumSize(paramJComponent);
/*     */     }
/* 225 */     return dimension;
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
/* 236 */     int i = ((ComponentUI)this.uis.elementAt(0)).getAccessibleChildrenCount(paramJComponent);
/* 237 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 238 */       ((ComponentUI)this.uis.elementAt(b)).getAccessibleChildrenCount(paramJComponent);
/*     */     }
/* 240 */     return i;
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
/* 251 */     Accessible accessible = ((ComponentUI)this.uis.elementAt(0)).getAccessibleChild(paramJComponent, paramInt);
/* 252 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 253 */       ((ComponentUI)this.uis.elementAt(b)).getAccessibleChild(paramJComponent, paramInt);
/*     */     }
/* 255 */     return accessible;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/multi/MultiTabbedPaneUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */