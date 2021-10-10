/*     */ package javax.swing.plaf.multi;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.util.Vector;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.ToolTipUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultiToolTipUI
/*     */   extends ToolTipUI
/*     */ {
/*  50 */   protected Vector uis = new Vector();
/*     */ 
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
/*  62 */     return MultiLookAndFeel.uisToArray(this.uis);
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
/*     */   public boolean contains(JComponent paramJComponent, int paramInt1, int paramInt2) {
/*  81 */     boolean bool = ((ComponentUI)this.uis.elementAt(0)).contains(paramJComponent, paramInt1, paramInt2);
/*  82 */     for (byte b = 1; b < this.uis.size(); b++) {
/*  83 */       ((ComponentUI)this.uis.elementAt(b)).contains(paramJComponent, paramInt1, paramInt2);
/*     */     }
/*  85 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(Graphics paramGraphics, JComponent paramJComponent) {
/*  92 */     for (byte b = 0; b < this.uis.size(); b++) {
/*  93 */       ((ComponentUI)this.uis.elementAt(b)).update(paramGraphics, paramJComponent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 103 */     MultiToolTipUI multiToolTipUI = new MultiToolTipUI();
/* 104 */     return MultiLookAndFeel.createUIs(multiToolTipUI, multiToolTipUI.uis, paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/* 113 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 114 */       ((ComponentUI)this.uis.elementAt(b)).installUI(paramJComponent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/* 122 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 123 */       ((ComponentUI)this.uis.elementAt(b)).uninstallUI(paramJComponent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 131 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 132 */       ((ComponentUI)this.uis.elementAt(b)).paint(paramGraphics, paramJComponent);
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
/* 144 */     Dimension dimension = ((ComponentUI)this.uis.elementAt(0)).getPreferredSize(paramJComponent);
/* 145 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 146 */       ((ComponentUI)this.uis.elementAt(b)).getPreferredSize(paramJComponent);
/*     */     }
/* 148 */     return dimension;
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
/* 159 */     Dimension dimension = ((ComponentUI)this.uis.elementAt(0)).getMinimumSize(paramJComponent);
/* 160 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 161 */       ((ComponentUI)this.uis.elementAt(b)).getMinimumSize(paramJComponent);
/*     */     }
/* 163 */     return dimension;
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
/* 174 */     Dimension dimension = ((ComponentUI)this.uis.elementAt(0)).getMaximumSize(paramJComponent);
/* 175 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 176 */       ((ComponentUI)this.uis.elementAt(b)).getMaximumSize(paramJComponent);
/*     */     }
/* 178 */     return dimension;
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
/* 189 */     int i = ((ComponentUI)this.uis.elementAt(0)).getAccessibleChildrenCount(paramJComponent);
/* 190 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 191 */       ((ComponentUI)this.uis.elementAt(b)).getAccessibleChildrenCount(paramJComponent);
/*     */     }
/* 193 */     return i;
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
/* 204 */     Accessible accessible = ((ComponentUI)this.uis.elementAt(0)).getAccessibleChild(paramJComponent, paramInt);
/* 205 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 206 */       ((ComponentUI)this.uis.elementAt(b)).getAccessibleChild(paramJComponent, paramInt);
/*     */     }
/* 208 */     return accessible;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/multi/MultiToolTipUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */