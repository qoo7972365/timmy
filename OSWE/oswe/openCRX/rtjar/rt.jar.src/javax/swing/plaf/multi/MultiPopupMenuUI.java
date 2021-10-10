/*     */ package javax.swing.plaf.multi;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.util.Vector;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.Popup;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.PopupMenuUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultiPopupMenuUI
/*     */   extends PopupMenuUI
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
/*     */   
/*     */   public boolean isPopupTrigger(MouseEvent paramMouseEvent) {
/*  81 */     boolean bool = ((PopupMenuUI)this.uis.elementAt(0)).isPopupTrigger(paramMouseEvent);
/*  82 */     for (byte b = 1; b < this.uis.size(); b++) {
/*  83 */       ((PopupMenuUI)this.uis.elementAt(b)).isPopupTrigger(paramMouseEvent);
/*     */     }
/*  85 */     return bool;
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
/*     */   public Popup getPopup(JPopupMenu paramJPopupMenu, int paramInt1, int paramInt2) {
/*  97 */     Popup popup = ((PopupMenuUI)this.uis.elementAt(0)).getPopup(paramJPopupMenu, paramInt1, paramInt2);
/*  98 */     for (byte b = 1; b < this.uis.size(); b++) {
/*  99 */       ((PopupMenuUI)this.uis.elementAt(b)).getPopup(paramJPopupMenu, paramInt1, paramInt2);
/*     */     }
/* 101 */     return popup;
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
/* 116 */     boolean bool = ((ComponentUI)this.uis.elementAt(0)).contains(paramJComponent, paramInt1, paramInt2);
/* 117 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 118 */       ((ComponentUI)this.uis.elementAt(b)).contains(paramJComponent, paramInt1, paramInt2);
/*     */     }
/* 120 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(Graphics paramGraphics, JComponent paramJComponent) {
/* 127 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 128 */       ((ComponentUI)this.uis.elementAt(b)).update(paramGraphics, paramJComponent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 138 */     MultiPopupMenuUI multiPopupMenuUI = new MultiPopupMenuUI();
/* 139 */     return MultiLookAndFeel.createUIs(multiPopupMenuUI, multiPopupMenuUI.uis, paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/* 148 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 149 */       ((ComponentUI)this.uis.elementAt(b)).installUI(paramJComponent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/* 157 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 158 */       ((ComponentUI)this.uis.elementAt(b)).uninstallUI(paramJComponent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 166 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 167 */       ((ComponentUI)this.uis.elementAt(b)).paint(paramGraphics, paramJComponent);
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
/* 179 */     Dimension dimension = ((ComponentUI)this.uis.elementAt(0)).getPreferredSize(paramJComponent);
/* 180 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 181 */       ((ComponentUI)this.uis.elementAt(b)).getPreferredSize(paramJComponent);
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
/*     */   public Dimension getMinimumSize(JComponent paramJComponent) {
/* 194 */     Dimension dimension = ((ComponentUI)this.uis.elementAt(0)).getMinimumSize(paramJComponent);
/* 195 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 196 */       ((ComponentUI)this.uis.elementAt(b)).getMinimumSize(paramJComponent);
/*     */     }
/* 198 */     return dimension;
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
/* 209 */     Dimension dimension = ((ComponentUI)this.uis.elementAt(0)).getMaximumSize(paramJComponent);
/* 210 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 211 */       ((ComponentUI)this.uis.elementAt(b)).getMaximumSize(paramJComponent);
/*     */     }
/* 213 */     return dimension;
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
/* 224 */     int i = ((ComponentUI)this.uis.elementAt(0)).getAccessibleChildrenCount(paramJComponent);
/* 225 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 226 */       ((ComponentUI)this.uis.elementAt(b)).getAccessibleChildrenCount(paramJComponent);
/*     */     }
/* 228 */     return i;
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
/* 239 */     Accessible accessible = ((ComponentUI)this.uis.elementAt(0)).getAccessibleChild(paramJComponent, paramInt);
/* 240 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 241 */       ((ComponentUI)this.uis.elementAt(b)).getAccessibleChild(paramJComponent, paramInt);
/*     */     }
/* 243 */     return accessible;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/multi/MultiPopupMenuUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */