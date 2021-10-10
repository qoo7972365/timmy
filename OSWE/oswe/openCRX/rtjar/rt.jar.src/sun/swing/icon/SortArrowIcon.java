/*     */ package sun.swing.icon;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.UIResource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SortArrowIcon
/*     */   implements Icon, UIResource, Serializable
/*     */ {
/*     */   private static final int ARROW_HEIGHT = 5;
/*     */   private static final int X_PADDING = 7;
/*     */   private boolean ascending;
/*     */   private Color color;
/*     */   private String colorKey;
/*     */   
/*     */   public SortArrowIcon(boolean paramBoolean, Color paramColor) {
/*  64 */     this.ascending = paramBoolean;
/*  65 */     this.color = paramColor;
/*  66 */     if (paramColor == null) {
/*  67 */       throw new IllegalArgumentException();
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
/*     */   public SortArrowIcon(boolean paramBoolean, String paramString) {
/*  79 */     this.ascending = paramBoolean;
/*  80 */     this.colorKey = paramString;
/*  81 */     if (paramString == null) {
/*  82 */       throw new IllegalArgumentException();
/*     */     }
/*     */   }
/*     */   
/*     */   public void paintIcon(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2) {
/*  87 */     paramGraphics.setColor(getColor());
/*  88 */     int i = 7 + paramInt1 + 2;
/*  89 */     if (this.ascending) {
/*  90 */       int j = paramInt2;
/*  91 */       paramGraphics.fillRect(i, j, 1, 1);
/*  92 */       for (byte b = 1; b < 5; b++) {
/*  93 */         paramGraphics.fillRect(i - b, j + b, b + b + 1, 1);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  98 */       int j = paramInt2 + 5 - 1;
/*  99 */       paramGraphics.fillRect(i, j, 1, 1);
/* 100 */       for (byte b = 1; b < 5; b++) {
/* 101 */         paramGraphics.fillRect(i - b, j - b, b + b + 1, 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIconWidth() {
/* 108 */     return 17;
/*     */   }
/*     */   
/*     */   public int getIconHeight() {
/* 112 */     return 7;
/*     */   }
/*     */   
/*     */   private Color getColor() {
/* 116 */     if (this.color != null) {
/* 117 */       return this.color;
/*     */     }
/* 119 */     return UIManager.getColor(this.colorKey);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/swing/icon/SortArrowIcon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */