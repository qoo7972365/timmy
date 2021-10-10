/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.SwingConstants;
/*     */ import javax.swing.UIManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicArrowButton
/*     */   extends JButton
/*     */   implements SwingConstants
/*     */ {
/*     */   protected int direction;
/*     */   private Color shadow;
/*     */   private Color darkShadow;
/*     */   private Color highlight;
/*     */   
/*     */   public BasicArrowButton(int paramInt, Color paramColor1, Color paramColor2, Color paramColor3, Color paramColor4) {
/*  78 */     setRequestFocusEnabled(false);
/*  79 */     setDirection(paramInt);
/*  80 */     setBackground(paramColor1);
/*  81 */     this.shadow = paramColor2;
/*  82 */     this.darkShadow = paramColor3;
/*  83 */     this.highlight = paramColor4;
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
/*     */   public BasicArrowButton(int paramInt) {
/*  95 */     this(paramInt, UIManager.getColor("control"), UIManager.getColor("controlShadow"), 
/*  96 */         UIManager.getColor("controlDkShadow"), UIManager.getColor("controlLtHighlight"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDirection() {
/* 103 */     return this.direction;
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
/*     */   public void setDirection(int paramInt) {
/* 115 */     this.direction = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics paramGraphics) {
/* 123 */     int i = (getSize()).width;
/* 124 */     int j = (getSize()).height;
/* 125 */     Color color = paramGraphics.getColor();
/* 126 */     boolean bool1 = getModel().isPressed();
/* 127 */     boolean bool2 = isEnabled();
/*     */     
/* 129 */     paramGraphics.setColor(getBackground());
/* 130 */     paramGraphics.fillRect(1, 1, i - 2, j - 2);
/*     */ 
/*     */     
/* 133 */     if (getBorder() != null && !(getBorder() instanceof javax.swing.plaf.UIResource)) {
/* 134 */       paintBorder(paramGraphics);
/* 135 */     } else if (bool1) {
/* 136 */       paramGraphics.setColor(this.shadow);
/* 137 */       paramGraphics.drawRect(0, 0, i - 1, j - 1);
/*     */     } else {
/*     */       
/* 140 */       paramGraphics.drawLine(0, 0, 0, j - 1);
/* 141 */       paramGraphics.drawLine(1, 0, i - 2, 0);
/*     */       
/* 143 */       paramGraphics.setColor(this.highlight);
/* 144 */       paramGraphics.drawLine(1, 1, 1, j - 3);
/* 145 */       paramGraphics.drawLine(2, 1, i - 3, 1);
/*     */       
/* 147 */       paramGraphics.setColor(this.shadow);
/* 148 */       paramGraphics.drawLine(1, j - 2, i - 2, j - 2);
/* 149 */       paramGraphics.drawLine(i - 2, 1, i - 2, j - 3);
/*     */       
/* 151 */       paramGraphics.setColor(this.darkShadow);
/* 152 */       paramGraphics.drawLine(0, j - 1, i - 1, j - 1);
/* 153 */       paramGraphics.drawLine(i - 1, j - 1, i - 1, 0);
/*     */     } 
/*     */ 
/*     */     
/* 157 */     if (j < 5 || i < 5) {
/* 158 */       paramGraphics.setColor(color);
/*     */       
/*     */       return;
/*     */     } 
/* 162 */     if (bool1) {
/* 163 */       paramGraphics.translate(1, 1);
/*     */     }
/*     */ 
/*     */     
/* 167 */     int k = Math.min((j - 4) / 3, (i - 4) / 3);
/* 168 */     k = Math.max(k, 2);
/* 169 */     paintTriangle(paramGraphics, (i - k) / 2, (j - k) / 2, k, this.direction, bool2);
/*     */ 
/*     */ 
/*     */     
/* 173 */     if (bool1) {
/* 174 */       paramGraphics.translate(-1, -1);
/*     */     }
/* 176 */     paramGraphics.setColor(color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize() {
/* 186 */     return new Dimension(16, 16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMinimumSize() {
/* 195 */     return new Dimension(5, 5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMaximumSize() {
/* 204 */     return new Dimension(2147483647, 2147483647);
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
/*     */   public boolean isFocusTraversable() {
/* 217 */     return false;
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
/*     */   public void paintTriangle(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
/*     */     int j;
/* 235 */     Color color = paramGraphics.getColor();
/*     */ 
/*     */     
/* 238 */     byte b = 0;
/* 239 */     paramInt3 = Math.max(paramInt3, 2);
/* 240 */     int i = paramInt3 / 2 - 1;
/*     */     
/* 242 */     paramGraphics.translate(paramInt1, paramInt2);
/* 243 */     if (paramBoolean) {
/* 244 */       paramGraphics.setColor(this.darkShadow);
/*     */     } else {
/* 246 */       paramGraphics.setColor(this.shadow);
/*     */     } 
/* 248 */     switch (paramInt4) {
/*     */       case 1:
/* 250 */         for (j = 0; j < paramInt3; j++) {
/* 251 */           paramGraphics.drawLine(i - j, j, i + j, j);
/*     */         }
/* 253 */         if (!paramBoolean) {
/* 254 */           paramGraphics.setColor(this.highlight);
/* 255 */           paramGraphics.drawLine(i - j + 2, j, i + j, j);
/*     */         } 
/*     */         break;
/*     */       case 5:
/* 259 */         if (!paramBoolean) {
/* 260 */           paramGraphics.translate(1, 1);
/* 261 */           paramGraphics.setColor(this.highlight);
/* 262 */           for (j = paramInt3 - 1; j >= 0; j--) {
/* 263 */             paramGraphics.drawLine(i - j, b, i + j, b);
/* 264 */             b++;
/*     */           } 
/* 266 */           paramGraphics.translate(-1, -1);
/* 267 */           paramGraphics.setColor(this.shadow);
/*     */         } 
/*     */         
/* 270 */         b = 0;
/* 271 */         for (j = paramInt3 - 1; j >= 0; j--) {
/* 272 */           paramGraphics.drawLine(i - j, b, i + j, b);
/* 273 */           b++;
/*     */         } 
/*     */         break;
/*     */       case 7:
/* 277 */         for (j = 0; j < paramInt3; j++) {
/* 278 */           paramGraphics.drawLine(j, i - j, j, i + j);
/*     */         }
/* 280 */         if (!paramBoolean) {
/* 281 */           paramGraphics.setColor(this.highlight);
/* 282 */           paramGraphics.drawLine(j, i - j + 2, j, i + j);
/*     */         } 
/*     */         break;
/*     */       case 3:
/* 286 */         if (!paramBoolean) {
/* 287 */           paramGraphics.translate(1, 1);
/* 288 */           paramGraphics.setColor(this.highlight);
/* 289 */           for (j = paramInt3 - 1; j >= 0; j--) {
/* 290 */             paramGraphics.drawLine(b, i - j, b, i + j);
/* 291 */             b++;
/*     */           } 
/* 293 */           paramGraphics.translate(-1, -1);
/* 294 */           paramGraphics.setColor(this.shadow);
/*     */         } 
/*     */         
/* 297 */         b = 0;
/* 298 */         for (j = paramInt3 - 1; j >= 0; j--) {
/* 299 */           paramGraphics.drawLine(b, i - j, b, i + j);
/* 300 */           b++;
/*     */         } 
/*     */         break;
/*     */     } 
/* 304 */     paramGraphics.translate(-paramInt1, -paramInt2);
/* 305 */     paramGraphics.setColor(color);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicArrowButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */