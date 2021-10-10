/*     */ package javax.swing.plaf.multi;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.util.Vector;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.TextUI;
/*     */ import javax.swing.text.BadLocationException;
/*     */ import javax.swing.text.EditorKit;
/*     */ import javax.swing.text.JTextComponent;
/*     */ import javax.swing.text.Position;
/*     */ import javax.swing.text.View;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultiTextUI
/*     */   extends TextUI
/*     */ {
/*  58 */   protected Vector uis = new Vector();
/*     */ 
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
/*  70 */     return MultiLookAndFeel.uisToArray(this.uis);
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
/*     */   public String getToolTipText(JTextComponent paramJTextComponent, Point paramPoint) {
/*  86 */     String str = ((TextUI)this.uis.elementAt(0)).getToolTipText(paramJTextComponent, paramPoint);
/*  87 */     for (byte b = 1; b < this.uis.size(); b++) {
/*  88 */       ((TextUI)this.uis.elementAt(b)).getToolTipText(paramJTextComponent, paramPoint);
/*     */     }
/*  90 */     return str;
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
/*     */   public Rectangle modelToView(JTextComponent paramJTextComponent, int paramInt) throws BadLocationException {
/* 102 */     Rectangle rectangle = ((TextUI)this.uis.elementAt(0)).modelToView(paramJTextComponent, paramInt);
/* 103 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 104 */       ((TextUI)this.uis.elementAt(b)).modelToView(paramJTextComponent, paramInt);
/*     */     }
/* 106 */     return rectangle;
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
/*     */   public Rectangle modelToView(JTextComponent paramJTextComponent, int paramInt, Position.Bias paramBias) throws BadLocationException {
/* 118 */     Rectangle rectangle = ((TextUI)this.uis.elementAt(0)).modelToView(paramJTextComponent, paramInt, paramBias);
/* 119 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 120 */       ((TextUI)this.uis.elementAt(b)).modelToView(paramJTextComponent, paramInt, paramBias);
/*     */     }
/* 122 */     return rectangle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int viewToModel(JTextComponent paramJTextComponent, Point paramPoint) {
/* 133 */     int i = ((TextUI)this.uis.elementAt(0)).viewToModel(paramJTextComponent, paramPoint);
/* 134 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 135 */       ((TextUI)this.uis.elementAt(b)).viewToModel(paramJTextComponent, paramPoint);
/*     */     }
/* 137 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int viewToModel(JTextComponent paramJTextComponent, Point paramPoint, Position.Bias[] paramArrayOfBias) {
/* 148 */     int i = ((TextUI)this.uis.elementAt(0)).viewToModel(paramJTextComponent, paramPoint, paramArrayOfBias);
/* 149 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 150 */       ((TextUI)this.uis.elementAt(b)).viewToModel(paramJTextComponent, paramPoint, paramArrayOfBias);
/*     */     }
/* 152 */     return i;
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
/*     */   public int getNextVisualPositionFrom(JTextComponent paramJTextComponent, int paramInt1, Position.Bias paramBias, int paramInt2, Position.Bias[] paramArrayOfBias) throws BadLocationException {
/* 164 */     int i = ((TextUI)this.uis.elementAt(0)).getNextVisualPositionFrom(paramJTextComponent, paramInt1, paramBias, paramInt2, paramArrayOfBias);
/* 165 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 166 */       ((TextUI)this.uis.elementAt(b)).getNextVisualPositionFrom(paramJTextComponent, paramInt1, paramBias, paramInt2, paramArrayOfBias);
/*     */     }
/* 168 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void damageRange(JTextComponent paramJTextComponent, int paramInt1, int paramInt2) {
/* 175 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 176 */       ((TextUI)this.uis.elementAt(b)).damageRange(paramJTextComponent, paramInt1, paramInt2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void damageRange(JTextComponent paramJTextComponent, int paramInt1, int paramInt2, Position.Bias paramBias1, Position.Bias paramBias2) {
/* 184 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 185 */       ((TextUI)this.uis.elementAt(b)).damageRange(paramJTextComponent, paramInt1, paramInt2, paramBias1, paramBias2);
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
/*     */   public EditorKit getEditorKit(JTextComponent paramJTextComponent) {
/* 197 */     EditorKit editorKit = ((TextUI)this.uis.elementAt(0)).getEditorKit(paramJTextComponent);
/* 198 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 199 */       ((TextUI)this.uis.elementAt(b)).getEditorKit(paramJTextComponent);
/*     */     }
/* 201 */     return editorKit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public View getRootView(JTextComponent paramJTextComponent) {
/* 212 */     View view = ((TextUI)this.uis.elementAt(0)).getRootView(paramJTextComponent);
/* 213 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 214 */       ((TextUI)this.uis.elementAt(b)).getRootView(paramJTextComponent);
/*     */     }
/* 216 */     return view;
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
/* 231 */     boolean bool = ((ComponentUI)this.uis.elementAt(0)).contains(paramJComponent, paramInt1, paramInt2);
/* 232 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 233 */       ((ComponentUI)this.uis.elementAt(b)).contains(paramJComponent, paramInt1, paramInt2);
/*     */     }
/* 235 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(Graphics paramGraphics, JComponent paramJComponent) {
/* 242 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 243 */       ((ComponentUI)this.uis.elementAt(b)).update(paramGraphics, paramJComponent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 253 */     MultiTextUI multiTextUI = new MultiTextUI();
/* 254 */     return MultiLookAndFeel.createUIs(multiTextUI, multiTextUI.uis, paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/* 263 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 264 */       ((ComponentUI)this.uis.elementAt(b)).installUI(paramJComponent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/* 272 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 273 */       ((ComponentUI)this.uis.elementAt(b)).uninstallUI(paramJComponent);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 281 */     for (byte b = 0; b < this.uis.size(); b++) {
/* 282 */       ((ComponentUI)this.uis.elementAt(b)).paint(paramGraphics, paramJComponent);
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
/* 294 */     Dimension dimension = ((ComponentUI)this.uis.elementAt(0)).getPreferredSize(paramJComponent);
/* 295 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 296 */       ((ComponentUI)this.uis.elementAt(b)).getPreferredSize(paramJComponent);
/*     */     }
/* 298 */     return dimension;
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
/* 309 */     Dimension dimension = ((ComponentUI)this.uis.elementAt(0)).getMinimumSize(paramJComponent);
/* 310 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 311 */       ((ComponentUI)this.uis.elementAt(b)).getMinimumSize(paramJComponent);
/*     */     }
/* 313 */     return dimension;
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
/* 324 */     Dimension dimension = ((ComponentUI)this.uis.elementAt(0)).getMaximumSize(paramJComponent);
/* 325 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 326 */       ((ComponentUI)this.uis.elementAt(b)).getMaximumSize(paramJComponent);
/*     */     }
/* 328 */     return dimension;
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
/* 339 */     int i = ((ComponentUI)this.uis.elementAt(0)).getAccessibleChildrenCount(paramJComponent);
/* 340 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 341 */       ((ComponentUI)this.uis.elementAt(b)).getAccessibleChildrenCount(paramJComponent);
/*     */     }
/* 343 */     return i;
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
/* 354 */     Accessible accessible = ((ComponentUI)this.uis.elementAt(0)).getAccessibleChild(paramJComponent, paramInt);
/* 355 */     for (byte b = 1; b < this.uis.size(); b++) {
/* 356 */       ((ComponentUI)this.uis.elementAt(b)).getAccessibleChild(paramJComponent, paramInt);
/*     */     }
/* 358 */     return accessible;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/multi/MultiTextUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */