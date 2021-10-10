/*     */ package com.sun.java.swing.plaf.motif;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.plaf.basic.BasicArrowButton;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MotifScrollBarButton
/*     */   extends BasicArrowButton
/*     */ {
/*  48 */   private Color darkShadow = UIManager.getColor("controlShadow");
/*  49 */   private Color lightShadow = UIManager.getColor("controlLtHighlight");
/*     */ 
/*     */ 
/*     */   
/*     */   public MotifScrollBarButton(int paramInt) {
/*  54 */     super(paramInt);
/*     */     
/*  56 */     switch (paramInt) {
/*     */       case 1:
/*     */       case 3:
/*     */       case 5:
/*     */       case 7:
/*  61 */         this.direction = paramInt;
/*     */         break;
/*     */       default:
/*  64 */         throw new IllegalArgumentException("invalid direction");
/*     */     } 
/*     */     
/*  67 */     setRequestFocusEnabled(false);
/*  68 */     setOpaque(true);
/*  69 */     setBackground(UIManager.getColor("ScrollBar.background"));
/*  70 */     setForeground(UIManager.getColor("ScrollBar.foreground"));
/*     */   }
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize() {
/*  75 */     switch (this.direction) {
/*     */       case 1:
/*     */       case 5:
/*  78 */         return new Dimension(11, 12);
/*     */     } 
/*     */ 
/*     */     
/*  82 */     return new Dimension(12, 11);
/*     */   }
/*     */ 
/*     */   
/*     */   public Dimension getMinimumSize() {
/*  87 */     return getPreferredSize();
/*     */   }
/*     */   
/*     */   public Dimension getMaximumSize() {
/*  91 */     return getPreferredSize();
/*     */   }
/*     */   
/*     */   public boolean isFocusTraversable() {
/*  95 */     return false;
/*     */   }
/*     */   public void paint(Graphics paramGraphics) {
/*     */     int i1, i2;
/*     */     byte b;
/* 100 */     int i = getWidth();
/* 101 */     int j = getHeight();
/*     */     
/* 103 */     if (isOpaque()) {
/* 104 */       paramGraphics.setColor(getBackground());
/* 105 */       paramGraphics.fillRect(0, 0, i, j);
/*     */     } 
/*     */     
/* 108 */     boolean bool = getModel().isPressed();
/* 109 */     Color color1 = bool ? this.darkShadow : this.lightShadow;
/* 110 */     Color color2 = bool ? this.lightShadow : this.darkShadow;
/* 111 */     Color color3 = getBackground();
/*     */     
/* 113 */     int k = i / 2;
/* 114 */     int m = j / 2;
/* 115 */     int n = Math.min(i, j);
/*     */     
/* 117 */     switch (this.direction) {
/*     */       case 1:
/* 119 */         paramGraphics.setColor(color1);
/* 120 */         paramGraphics.drawLine(k, 0, k, 0);
/* 121 */         for (i1 = k - 1, i2 = 1, b = 1; i2 <= n - 2; i2 += 2) {
/* 122 */           paramGraphics.setColor(color1);
/* 123 */           paramGraphics.drawLine(i1, i2, i1, i2);
/* 124 */           if (i2 >= n - 2) {
/* 125 */             paramGraphics.drawLine(i1, i2 + 1, i1, i2 + 1);
/*     */           }
/* 127 */           paramGraphics.setColor(color3);
/* 128 */           paramGraphics.drawLine(i1 + 1, i2, i1 + b, i2);
/* 129 */           if (i2 < n - 2) {
/* 130 */             paramGraphics.drawLine(i1, i2 + 1, i1 + b + 1, i2 + 1);
/*     */           }
/* 132 */           paramGraphics.setColor(color2);
/* 133 */           paramGraphics.drawLine(i1 + b + 1, i2, i1 + b + 1, i2);
/* 134 */           if (i2 >= n - 2) {
/* 135 */             paramGraphics.drawLine(i1 + 1, i2 + 1, i1 + b + 1, i2 + 1);
/*     */           }
/* 137 */           b += 2;
/* 138 */           i1--;
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 5:
/* 143 */         paramGraphics.setColor(color2);
/* 144 */         paramGraphics.drawLine(k, n, k, n);
/* 145 */         for (i1 = k - 1, i2 = n - 1, b = 1; i2 >= 1; i2 -= 2) {
/* 146 */           paramGraphics.setColor(color1);
/* 147 */           paramGraphics.drawLine(i1, i2, i1, i2);
/* 148 */           if (i2 <= 2) {
/* 149 */             paramGraphics.drawLine(i1, i2 - 1, i1 + b + 1, i2 - 1);
/*     */           }
/* 151 */           paramGraphics.setColor(color3);
/* 152 */           paramGraphics.drawLine(i1 + 1, i2, i1 + b, i2);
/* 153 */           if (i2 > 2) {
/* 154 */             paramGraphics.drawLine(i1, i2 - 1, i1 + b + 1, i2 - 1);
/*     */           }
/* 156 */           paramGraphics.setColor(color2);
/* 157 */           paramGraphics.drawLine(i1 + b + 1, i2, i1 + b + 1, i2);
/*     */           
/* 159 */           b += 2;
/* 160 */           i1--;
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 3:
/* 165 */         paramGraphics.setColor(color1);
/* 166 */         paramGraphics.drawLine(n, m, n, m);
/* 167 */         for (i1 = m - 1, i2 = n - 1, b = 1; i2 >= 1; i2 -= 2) {
/* 168 */           paramGraphics.setColor(color1);
/* 169 */           paramGraphics.drawLine(i2, i1, i2, i1);
/* 170 */           if (i2 <= 2) {
/* 171 */             paramGraphics.drawLine(i2 - 1, i1, i2 - 1, i1 + b + 1);
/*     */           }
/* 173 */           paramGraphics.setColor(color3);
/* 174 */           paramGraphics.drawLine(i2, i1 + 1, i2, i1 + b);
/* 175 */           if (i2 > 2) {
/* 176 */             paramGraphics.drawLine(i2 - 1, i1, i2 - 1, i1 + b + 1);
/*     */           }
/* 178 */           paramGraphics.setColor(color2);
/* 179 */           paramGraphics.drawLine(i2, i1 + b + 1, i2, i1 + b + 1);
/*     */           
/* 181 */           b += 2;
/* 182 */           i1--;
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 7:
/* 187 */         paramGraphics.setColor(color2);
/* 188 */         paramGraphics.drawLine(0, m, 0, m);
/* 189 */         for (i1 = m - 1, i2 = 1, b = 1; i2 <= n - 2; i2 += 2) {
/* 190 */           paramGraphics.setColor(color1);
/* 191 */           paramGraphics.drawLine(i2, i1, i2, i1);
/* 192 */           if (i2 >= n - 2) {
/* 193 */             paramGraphics.drawLine(i2 + 1, i1, i2 + 1, i1);
/*     */           }
/* 195 */           paramGraphics.setColor(color3);
/* 196 */           paramGraphics.drawLine(i2, i1 + 1, i2, i1 + b);
/* 197 */           if (i2 < n - 2) {
/* 198 */             paramGraphics.drawLine(i2 + 1, i1, i2 + 1, i1 + b + 1);
/*     */           }
/* 200 */           paramGraphics.setColor(color2);
/* 201 */           paramGraphics.drawLine(i2, i1 + b + 1, i2, i1 + b + 1);
/* 202 */           if (i2 >= n - 2) {
/* 203 */             paramGraphics.drawLine(i2 + 1, i1 + 1, i2 + 1, i1 + b + 1);
/*     */           }
/* 205 */           b += 2;
/* 206 */           i1--;
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifScrollBarButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */