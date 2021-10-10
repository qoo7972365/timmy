/*     */ package javax.swing.plaf.multi;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.util.Vector;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.MenuItemUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultiMenuItemUI
/*     */   extends MenuItemUI
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
/*     */ 
/*     */ 
/*     */ 
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
/*  86 */     boolean bool = ((ComponentUI)this.uis.elementAt(0)).contains(paramJComponent, paramInt1, paramInt2);
/*  87 */     for (byte b = 1; b < this.uis.size(); b++) {
/*  88 */       ((ComponentUI)this.uis.elementAt(b)).contains(paramJComponent, paramInt1, paramInt2);
/*     */     }
/*  90 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(Graphics paramGraphics, JComponent paramJComponent) {
/*  97 */     for (byte b = 0; b < this.uis.size(); b++) {
/*  98 */       ((ComponentUI)this.uis.elementAt(b)).update(paramGraphics, paramJComponent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 108 */     MultiMenuItemUI multiMenuItemUI = new MultiMenuItemUI();
/* 109 */     return MultiLookAndFeel.createUIs(multiMenuItemUI, multiMenuItemUI.uis, paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/* 118 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 119 */       ((ComponentUI)this.uis.elementAt(b)).installUI(paramJComponent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/* 127 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 128 */       ((ComponentUI)this.uis.elementAt(b)).uninstallUI(paramJComponent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 136 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 137 */       ((ComponentUI)this.uis.elementAt(b)).paint(paramGraphics, paramJComponent);
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
/* 149 */     Dimension dimension = ((ComponentUI)this.uis.elementAt(0)).getPreferredSize(paramJComponent);
/* 150 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 151 */       ((ComponentUI)this.uis.elementAt(b)).getPreferredSize(paramJComponent);
/*     */     }
/* 153 */     return dimension;
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
/* 164 */     Dimension dimension = ((ComponentUI)this.uis.elementAt(0)).getMinimumSize(paramJComponent);
/* 165 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 166 */       ((ComponentUI)this.uis.elementAt(b)).getMinimumSize(paramJComponent);
/*     */     }
/* 168 */     return dimension;
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
/* 179 */     Dimension dimension = ((ComponentUI)this.uis.elementAt(0)).getMaximumSize(paramJComponent);
/* 180 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 181 */       ((ComponentUI)this.uis.elementAt(b)).getMaximumSize(paramJComponent);
/*     */     }
/* 183 */     return dimension;
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
/* 194 */     int i = ((ComponentUI)this.uis.elementAt(0)).getAccessibleChildrenCount(paramJComponent);
/* 195 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 196 */       ((ComponentUI)this.uis.elementAt(b)).getAccessibleChildrenCount(paramJComponent);
/*     */     }
/* 198 */     return i;
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
/* 209 */     Accessible accessible = ((ComponentUI)this.uis.elementAt(0)).getAccessibleChild(paramJComponent, paramInt);
/* 210 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 211 */       ((ComponentUI)this.uis.elementAt(b)).getAccessibleChild(paramJComponent, paramInt);
/*     */     }
/* 213 */     return accessible;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/multi/MultiMenuItemUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */