/*     */ package sun.awt;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RepaintArea
/*     */ {
/*     */   private static final int MAX_BENEFIT_RATIO = 4;
/*     */   private static final int HORIZONTAL = 0;
/*     */   private static final int VERTICAL = 1;
/*     */   private static final int UPDATE = 2;
/*     */   private static final int RECT_COUNT = 3;
/*  58 */   private Rectangle[] paintRects = new Rectangle[3];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RepaintArea() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RepaintArea(RepaintArea paramRepaintArea) {
/*  79 */     for (byte b = 0; b < 3; b++) {
/*  80 */       this.paintRects[b] = paramRepaintArea.paintRects[b];
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
/*     */   public synchronized void add(Rectangle paramRectangle, int paramInt) {
/*  96 */     if (paramRectangle.isEmpty()) {
/*     */       return;
/*     */     }
/*  99 */     byte b = 2;
/* 100 */     if (paramInt == 800) {
/* 101 */       b = (paramRectangle.width > paramRectangle.height) ? 0 : 1;
/*     */     }
/* 103 */     if (this.paintRects[b] != null) {
/* 104 */       this.paintRects[b].add(paramRectangle);
/*     */     } else {
/* 106 */       this.paintRects[b] = new Rectangle(paramRectangle);
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
/*     */   private synchronized RepaintArea cloneAndReset() {
/* 121 */     RepaintArea repaintArea = new RepaintArea(this);
/* 122 */     for (byte b = 0; b < 3; b++) {
/* 123 */       this.paintRects[b] = null;
/*     */     }
/* 125 */     return repaintArea;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 129 */     for (byte b = 0; b < 3; b++) {
/* 130 */       if (this.paintRects[b] != null) {
/* 131 */         return false;
/*     */       }
/*     */     } 
/* 134 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void constrain(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 141 */     for (byte b = 0; b < 3; b++) {
/* 142 */       Rectangle rectangle = this.paintRects[b];
/* 143 */       if (rectangle != null) {
/* 144 */         if (rectangle.x < paramInt1) {
/* 145 */           rectangle.width -= paramInt1 - rectangle.x;
/* 146 */           rectangle.x = paramInt1;
/*     */         } 
/* 148 */         if (rectangle.y < paramInt2) {
/* 149 */           rectangle.height -= paramInt2 - rectangle.y;
/* 150 */           rectangle.y = paramInt2;
/*     */         } 
/* 152 */         int i = rectangle.x + rectangle.width - paramInt1 - paramInt3;
/* 153 */         if (i > 0) {
/* 154 */           rectangle.width -= i;
/*     */         }
/* 156 */         int j = rectangle.y + rectangle.height - paramInt2 - paramInt4;
/* 157 */         if (j > 0) {
/* 158 */           rectangle.height -= j;
/*     */         }
/* 160 */         if (rectangle.width <= 0 || rectangle.height <= 0) {
/* 161 */           this.paintRects[b] = null;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void subtract(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 172 */     Rectangle rectangle = new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4);
/* 173 */     for (byte b = 0; b < 3; b++) {
/* 174 */       if (subtract(this.paintRects[b], rectangle) && 
/* 175 */         this.paintRects[b] != null && this.paintRects[b].isEmpty()) {
/* 176 */         this.paintRects[b] = null;
/*     */       }
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
/*     */   public void paint(Object paramObject, boolean paramBoolean) {
/* 193 */     Component component = (Component)paramObject;
/*     */     
/* 195 */     if (isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 199 */     if (!component.isVisible()) {
/*     */       return;
/*     */     }
/*     */     
/* 203 */     RepaintArea repaintArea = cloneAndReset();
/*     */     
/* 205 */     if (!subtract(repaintArea.paintRects[1], repaintArea.paintRects[0])) {
/* 206 */       subtract(repaintArea.paintRects[0], repaintArea.paintRects[1]);
/*     */     }
/*     */     
/* 209 */     if (repaintArea.paintRects[0] != null && repaintArea.paintRects[1] != null) {
/* 210 */       Rectangle rectangle = repaintArea.paintRects[0].union(repaintArea.paintRects[1]);
/* 211 */       int i = rectangle.width * rectangle.height;
/* 212 */       int j = i - (repaintArea.paintRects[0]).width * (repaintArea.paintRects[0]).height - (repaintArea.paintRects[1]).width * (repaintArea.paintRects[1]).height;
/*     */ 
/*     */ 
/*     */       
/* 216 */       if (4 * j < i) {
/* 217 */         repaintArea.paintRects[0] = rectangle;
/* 218 */         repaintArea.paintRects[1] = null;
/*     */       } 
/*     */     } 
/* 221 */     for (byte b = 0; b < this.paintRects.length; b++) {
/* 222 */       if (repaintArea.paintRects[b] != null && 
/* 223 */         !repaintArea.paintRects[b].isEmpty()) {
/*     */ 
/*     */ 
/*     */         
/* 227 */         Graphics graphics = component.getGraphics();
/* 228 */         if (graphics != null) {
/*     */           
/* 230 */           try { graphics.setClip(repaintArea.paintRects[b]);
/* 231 */             if (b == 2) {
/* 232 */               updateComponent(component, graphics);
/*     */             } else {
/* 234 */               if (paramBoolean) {
/* 235 */                 graphics.clearRect((repaintArea.paintRects[b]).x, (repaintArea.paintRects[b]).y, (repaintArea.paintRects[b]).width, (repaintArea.paintRects[b]).height);
/*     */               }
/*     */ 
/*     */ 
/*     */               
/* 240 */               paintComponent(component, graphics);
/*     */             } 
/*     */             
/* 243 */             graphics.dispose(); } finally { graphics.dispose(); }
/*     */         
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateComponent(Component paramComponent, Graphics paramGraphics) {
/* 254 */     if (paramComponent != null) {
/* 255 */       paramComponent.update(paramGraphics);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintComponent(Component paramComponent, Graphics paramGraphics) {
/* 263 */     if (paramComponent != null) {
/* 264 */       paramComponent.paint(paramGraphics);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean subtract(Rectangle paramRectangle1, Rectangle paramRectangle2) {
/* 273 */     if (paramRectangle1 == null || paramRectangle2 == null) {
/* 274 */       return true;
/*     */     }
/* 276 */     Rectangle rectangle = paramRectangle1.intersection(paramRectangle2);
/* 277 */     if (rectangle.isEmpty()) {
/* 278 */       return true;
/*     */     }
/* 280 */     if (paramRectangle1.x == rectangle.x && paramRectangle1.y == rectangle.y) {
/* 281 */       if (paramRectangle1.width == rectangle.width) {
/* 282 */         paramRectangle1.y += rectangle.height;
/* 283 */         paramRectangle1.height -= rectangle.height;
/* 284 */         return true;
/*     */       } 
/* 286 */       if (paramRectangle1.height == rectangle.height) {
/* 287 */         paramRectangle1.x += rectangle.width;
/* 288 */         paramRectangle1.width -= rectangle.width;
/* 289 */         return true;
/*     */       }
/*     */     
/* 292 */     } else if (paramRectangle1.x + paramRectangle1.width == rectangle.x + rectangle.width && paramRectangle1.y + paramRectangle1.height == rectangle.y + rectangle.height) {
/*     */ 
/*     */       
/* 295 */       if (paramRectangle1.width == rectangle.width) {
/* 296 */         paramRectangle1.height -= rectangle.height;
/* 297 */         return true;
/*     */       } 
/* 299 */       if (paramRectangle1.height == rectangle.height) {
/* 300 */         paramRectangle1.width -= rectangle.width;
/* 301 */         return true;
/*     */       } 
/*     */     } 
/* 304 */     return false;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 308 */     return super.toString() + "[ horizontal=" + this.paintRects[0] + " vertical=" + this.paintRects[1] + " update=" + this.paintRects[2] + "]";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/RepaintArea.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */