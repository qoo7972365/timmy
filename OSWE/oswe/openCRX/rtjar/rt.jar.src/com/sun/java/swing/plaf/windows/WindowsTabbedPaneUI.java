/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicTabbedPaneUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WindowsTabbedPaneUI
/*     */   extends BasicTabbedPaneUI
/*     */ {
/*     */   private static Set<KeyStroke> managingFocusForwardTraversalKeys;
/*     */   private static Set<KeyStroke> managingFocusBackwardTraversalKeys;
/*     */   private boolean contentOpaque = true;
/*     */   
/*     */   protected void installDefaults() {
/*  67 */     super.installDefaults();
/*  68 */     this.contentOpaque = UIManager.getBoolean("TabbedPane.contentOpaque");
/*     */ 
/*     */     
/*  71 */     if (managingFocusForwardTraversalKeys == null) {
/*  72 */       managingFocusForwardTraversalKeys = new HashSet<>();
/*  73 */       managingFocusForwardTraversalKeys.add(KeyStroke.getKeyStroke(9, 0));
/*     */     } 
/*  75 */     this.tabPane.setFocusTraversalKeys(0, (Set)managingFocusForwardTraversalKeys);
/*     */     
/*  77 */     if (managingFocusBackwardTraversalKeys == null) {
/*  78 */       managingFocusBackwardTraversalKeys = new HashSet<>();
/*  79 */       managingFocusBackwardTraversalKeys.add(KeyStroke.getKeyStroke(9, 1));
/*     */     } 
/*  81 */     this.tabPane.setFocusTraversalKeys(1, (Set)managingFocusBackwardTraversalKeys);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults() {
/*  87 */     this.tabPane.setFocusTraversalKeys(0, null);
/*  88 */     this.tabPane.setFocusTraversalKeys(1, null);
/*  89 */     super.uninstallDefaults();
/*     */   }
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  93 */     return new WindowsTabbedPaneUI();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setRolloverTab(int paramInt) {
/*  98 */     if (XPStyle.getXP() != null) {
/*  99 */       int i = getRolloverTab();
/* 100 */       super.setRolloverTab(paramInt);
/* 101 */       Rectangle rectangle1 = null;
/* 102 */       Rectangle rectangle2 = null;
/* 103 */       if (i >= 0 && i < this.tabPane.getTabCount()) {
/* 104 */         rectangle1 = getTabBounds(this.tabPane, i);
/*     */       }
/* 106 */       if (paramInt >= 0) {
/* 107 */         rectangle2 = getTabBounds(this.tabPane, paramInt);
/*     */       }
/* 109 */       if (rectangle1 != null) {
/* 110 */         if (rectangle2 != null) {
/* 111 */           this.tabPane.repaint(rectangle1.union(rectangle2));
/*     */         } else {
/* 113 */           this.tabPane.repaint(rectangle1);
/*     */         } 
/* 115 */       } else if (rectangle2 != null) {
/* 116 */         this.tabPane.repaint(rectangle2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void paintContentBorder(Graphics paramGraphics, int paramInt1, int paramInt2) {
/* 122 */     XPStyle xPStyle = XPStyle.getXP();
/* 123 */     if (xPStyle != null && (this.contentOpaque || this.tabPane.isOpaque())) {
/* 124 */       XPStyle.Skin skin = xPStyle.getSkin(this.tabPane, TMSchema.Part.TABP_PANE);
/* 125 */       if (skin != null) {
/* 126 */         Insets insets1 = this.tabPane.getInsets();
/*     */ 
/*     */         
/* 129 */         Insets insets2 = UIManager.getInsets("TabbedPane.tabAreaInsets");
/* 130 */         int i = insets1.left;
/* 131 */         int j = insets1.top;
/* 132 */         int k = this.tabPane.getWidth() - insets1.right - insets1.left;
/* 133 */         int m = this.tabPane.getHeight() - insets1.top - insets1.bottom;
/*     */ 
/*     */         
/* 136 */         if (paramInt1 == 2 || paramInt1 == 4) {
/* 137 */           int n = calculateTabAreaWidth(paramInt1, this.runCount, this.maxTabWidth);
/* 138 */           if (paramInt1 == 2) {
/* 139 */             i += n - insets2.bottom;
/*     */           }
/* 141 */           k -= n - insets2.bottom;
/*     */         } else {
/* 143 */           int n = calculateTabAreaHeight(paramInt1, this.runCount, this.maxTabHeight);
/* 144 */           if (paramInt1 == 1) {
/* 145 */             j += n - insets2.bottom;
/*     */           }
/* 147 */           m -= n - insets2.bottom;
/*     */         } 
/*     */         
/* 150 */         paintRotatedSkin(paramGraphics, skin, paramInt1, i, j, k, m, (TMSchema.State)null);
/*     */         return;
/*     */       } 
/*     */     } 
/* 154 */     super.paintContentBorder(paramGraphics, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void paintTabBackground(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean) {
/* 159 */     if (XPStyle.getXP() == null) {
/* 160 */       super.paintTabBackground(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramBoolean);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void paintTabBorder(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean) {
/* 166 */     XPStyle xPStyle = XPStyle.getXP();
/* 167 */     if (xPStyle != null) {
/*     */       TMSchema.Part part;
/*     */       
/* 170 */       int i = this.tabPane.getTabCount();
/* 171 */       int j = getRunForTab(i, paramInt2);
/* 172 */       if (this.tabRuns[j] == paramInt2) {
/* 173 */         part = TMSchema.Part.TABP_TABITEMLEFTEDGE;
/* 174 */       } else if (i > 1 && lastTabInRun(i, j) == paramInt2) {
/* 175 */         part = TMSchema.Part.TABP_TABITEMRIGHTEDGE;
/* 176 */         if (paramBoolean)
/*     */         {
/* 178 */           if (paramInt1 == 1 || paramInt1 == 3) {
/* 179 */             paramInt5++;
/*     */           } else {
/* 181 */             paramInt6++;
/*     */           } 
/*     */         }
/*     */       } else {
/* 185 */         part = TMSchema.Part.TABP_TABITEM;
/*     */       } 
/*     */       
/* 188 */       TMSchema.State state = TMSchema.State.NORMAL;
/* 189 */       if (paramBoolean) {
/* 190 */         state = TMSchema.State.SELECTED;
/* 191 */       } else if (paramInt2 == getRolloverTab()) {
/* 192 */         state = TMSchema.State.HOT;
/*     */       } 
/*     */       
/* 195 */       paintRotatedSkin(paramGraphics, xPStyle.getSkin(this.tabPane, part), paramInt1, paramInt3, paramInt4, paramInt5, paramInt6, state);
/*     */     } else {
/* 197 */       super.paintTabBorder(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramBoolean);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintRotatedSkin(Graphics paramGraphics, XPStyle.Skin paramSkin, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, TMSchema.State paramState) {
/* 203 */     Graphics2D graphics2D = (Graphics2D)paramGraphics.create();
/* 204 */     graphics2D.translate(paramInt2, paramInt3);
/* 205 */     switch (paramInt1) { case 4:
/* 206 */         graphics2D.translate(paramInt4, 0);
/* 207 */         graphics2D.rotate(Math.toRadians(90.0D));
/* 208 */         paramSkin.paintSkin(graphics2D, 0, 0, paramInt5, paramInt4, paramState);
/*     */         break;
/*     */       case 2:
/* 211 */         graphics2D.scale(-1.0D, 1.0D);
/* 212 */         graphics2D.rotate(Math.toRadians(90.0D));
/* 213 */         paramSkin.paintSkin(graphics2D, 0, 0, paramInt5, paramInt4, paramState);
/*     */         break;
/*     */       case 3:
/* 216 */         graphics2D.translate(0, paramInt5);
/* 217 */         graphics2D.scale(-1.0D, 1.0D);
/* 218 */         graphics2D.rotate(Math.toRadians(180.0D));
/* 219 */         paramSkin.paintSkin(graphics2D, 0, 0, paramInt4, paramInt5, paramState);
/*     */         break;
/*     */       
/*     */       default:
/* 223 */         paramSkin.paintSkin(graphics2D, 0, 0, paramInt4, paramInt5, paramState); break; }
/*     */     
/* 225 */     graphics2D.dispose();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsTabbedPaneUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */