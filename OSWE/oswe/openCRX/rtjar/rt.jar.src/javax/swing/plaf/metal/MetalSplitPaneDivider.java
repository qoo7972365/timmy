/*     */ package javax.swing.plaf.metal;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Rectangle;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JSplitPane;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.basic.BasicSplitPaneDivider;
/*     */ import javax.swing.plaf.basic.BasicSplitPaneUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MetalSplitPaneDivider
/*     */   extends BasicSplitPaneDivider
/*     */ {
/*  51 */   private MetalBumps bumps = new MetalBumps(10, 10, 
/*  52 */       MetalLookAndFeel.getControlHighlight(), 
/*  53 */       MetalLookAndFeel.getControlDarkShadow(), 
/*  54 */       MetalLookAndFeel.getControl());
/*     */   
/*  56 */   private MetalBumps focusBumps = new MetalBumps(10, 10, 
/*  57 */       MetalLookAndFeel.getPrimaryControlHighlight(), 
/*  58 */       MetalLookAndFeel.getPrimaryControlDarkShadow(), 
/*  59 */       UIManager.getColor("SplitPane.dividerFocusColor"));
/*     */   
/*  61 */   private int inset = 2;
/*     */   
/*  63 */   private Color controlColor = MetalLookAndFeel.getControl();
/*  64 */   private Color primaryControlColor = UIManager.getColor("SplitPane.dividerFocusColor");
/*     */ 
/*     */   
/*     */   public MetalSplitPaneDivider(BasicSplitPaneUI paramBasicSplitPaneUI) {
/*  68 */     super(paramBasicSplitPaneUI);
/*     */   }
/*     */   
/*     */   public void paint(Graphics paramGraphics) {
/*     */     MetalBumps metalBumps;
/*  73 */     if (this.splitPane.hasFocus()) {
/*  74 */       metalBumps = this.focusBumps;
/*  75 */       paramGraphics.setColor(this.primaryControlColor);
/*     */     } else {
/*     */       
/*  78 */       metalBumps = this.bumps;
/*  79 */       paramGraphics.setColor(this.controlColor);
/*     */     } 
/*  81 */     Rectangle rectangle = paramGraphics.getClipBounds();
/*  82 */     Insets insets = getInsets();
/*  83 */     paramGraphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*  84 */     Dimension dimension = getSize();
/*  85 */     dimension.width -= this.inset * 2;
/*  86 */     dimension.height -= this.inset * 2;
/*  87 */     int i = this.inset;
/*  88 */     int j = this.inset;
/*  89 */     if (insets != null) {
/*  90 */       dimension.width -= insets.left + insets.right;
/*  91 */       dimension.height -= insets.top + insets.bottom;
/*  92 */       i += insets.left;
/*  93 */       j += insets.top;
/*     */     } 
/*  95 */     metalBumps.setBumpArea(dimension);
/*  96 */     metalBumps.paintIcon(this, paramGraphics, i, j);
/*  97 */     super.paint(paramGraphics);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JButton createLeftOneTouchButton() {
/* 105 */     JButton jButton = new JButton()
/*     */       {
/* 107 */         int[][] buffer = new int[][] { { 0, 0, 0, 2, 2, 0, 0, 0, 0 }, { 0, 0, 2, 1, 1, 1, 0, 0, 0 }, { 0, 2, 1, 1, 1, 1, 1, 0, 0 }, { 2, 1, 1, 1, 1, 1, 1, 1, 0 }, { 0, 3, 3, 3, 3, 3, 3, 3, 3 } };
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void setBorder(Border param1Border) {}
/*     */ 
/*     */ 
/*     */         
/*     */         public void paint(Graphics param1Graphics) {
/* 117 */           JSplitPane jSplitPane = MetalSplitPaneDivider.this.getSplitPaneFromSuper();
/* 118 */           if (jSplitPane != null) {
/* 119 */             int i = MetalSplitPaneDivider.this.getOneTouchSizeFromSuper();
/* 120 */             int j = MetalSplitPaneDivider.this.getOrientationFromSuper();
/* 121 */             int k = Math.min(MetalSplitPaneDivider.this.getDividerSize(), i);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 129 */             Color[] arrayOfColor = { getBackground(), MetalLookAndFeel.getPrimaryControlDarkShadow(), MetalLookAndFeel.getPrimaryControlInfo(), MetalLookAndFeel.getPrimaryControlHighlight() };
/*     */ 
/*     */             
/* 132 */             param1Graphics.setColor(getBackground());
/* 133 */             if (isOpaque()) {
/* 134 */               param1Graphics.fillRect(0, 0, getWidth(), 
/* 135 */                   getHeight());
/*     */             }
/*     */ 
/*     */             
/* 139 */             if (getModel().isPressed())
/*     */             {
/* 141 */               arrayOfColor[1] = arrayOfColor[2];
/*     */             }
/* 143 */             if (j == 0) {
/*     */               
/* 145 */               for (byte b = 1; b <= (this.buffer[0]).length; b++) {
/* 146 */                 for (byte b1 = 1; b1 < k; b1++) {
/* 147 */                   if (this.buffer[b1 - 1][b - 1] != 0)
/*     */                   {
/*     */ 
/*     */                     
/* 151 */                     param1Graphics.setColor(arrayOfColor[this.buffer[b1 - 1][b - 1]]);
/*     */ 
/*     */                     
/* 154 */                     param1Graphics.drawLine(b, b1, b, b1);
/*     */                   
/*     */                   }
/*     */                 
/*     */                 }
/*     */               
/*     */               }
/*     */             
/*     */             }
/*     */             else {
/*     */               
/* 165 */               for (byte b = 1; b <= (this.buffer[0]).length; b++) {
/* 166 */                 for (byte b1 = 1; b1 < k; b1++) {
/* 167 */                   if (this.buffer[b1 - 1][b - 1] != 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                     
/* 175 */                     param1Graphics.setColor(arrayOfColor[this.buffer[b1 - 1][b - 1]]);
/*     */ 
/*     */ 
/*     */                     
/* 179 */                     param1Graphics.drawLine(b1, b, b1, b);
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/*     */         public boolean isFocusTraversable() {
/* 188 */           return false;
/*     */         }
/*     */       };
/* 191 */     jButton.setRequestFocusEnabled(false);
/* 192 */     jButton.setCursor(Cursor.getPredefinedCursor(0));
/* 193 */     jButton.setFocusPainted(false);
/* 194 */     jButton.setBorderPainted(false);
/* 195 */     maybeMakeButtonOpaque(jButton);
/* 196 */     return jButton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void maybeMakeButtonOpaque(JComponent paramJComponent) {
/* 203 */     Object object = UIManager.get("SplitPane.oneTouchButtonsOpaque");
/* 204 */     if (object != null) {
/* 205 */       paramJComponent.setOpaque(((Boolean)object).booleanValue());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JButton createRightOneTouchButton() {
/* 214 */     JButton jButton = new JButton()
/*     */       {
/* 216 */         int[][] buffer = new int[][] { { 2, 2, 2, 2, 2, 2, 2, 2 }, { 0, 1, 1, 1, 1, 1, 1, 3 }, { 0, 0, 1, 1, 1, 1, 3, 0 }, { 0, 0, 0, 1, 1, 3, 0, 0 }, { 0, 0, 0, 0, 3, 0, 0, 0 } };
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void setBorder(Border param1Border) {}
/*     */ 
/*     */ 
/*     */         
/*     */         public void paint(Graphics param1Graphics) {
/* 226 */           JSplitPane jSplitPane = MetalSplitPaneDivider.this.getSplitPaneFromSuper();
/* 227 */           if (jSplitPane != null) {
/* 228 */             int i = MetalSplitPaneDivider.this.getOneTouchSizeFromSuper();
/* 229 */             int j = MetalSplitPaneDivider.this.getOrientationFromSuper();
/* 230 */             int k = Math.min(MetalSplitPaneDivider.this.getDividerSize(), i);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 238 */             Color[] arrayOfColor = { getBackground(), MetalLookAndFeel.getPrimaryControlDarkShadow(), MetalLookAndFeel.getPrimaryControlInfo(), MetalLookAndFeel.getPrimaryControlHighlight() };
/*     */ 
/*     */             
/* 241 */             param1Graphics.setColor(getBackground());
/* 242 */             if (isOpaque()) {
/* 243 */               param1Graphics.fillRect(0, 0, getWidth(), 
/* 244 */                   getHeight());
/*     */             }
/*     */ 
/*     */             
/* 248 */             if (getModel().isPressed())
/*     */             {
/* 250 */               arrayOfColor[1] = arrayOfColor[2];
/*     */             }
/* 252 */             if (j == 0) {
/*     */               
/* 254 */               for (byte b = 1; b <= (this.buffer[0]).length; b++) {
/* 255 */                 for (byte b1 = 1; b1 < k; b1++) {
/* 256 */                   if (this.buffer[b1 - 1][b - 1] != 0)
/*     */                   {
/*     */ 
/*     */                     
/* 260 */                     param1Graphics.setColor(arrayOfColor[this.buffer[b1 - 1][b - 1]]);
/*     */ 
/*     */                     
/* 263 */                     param1Graphics.drawLine(b, b1, b, b1);
/*     */                   
/*     */                   }
/*     */                 
/*     */                 }
/*     */               
/*     */               }
/*     */             
/*     */             }
/*     */             else {
/*     */               
/* 274 */               for (byte b = 1; b <= (this.buffer[0]).length; b++) {
/* 275 */                 for (byte b1 = 1; b1 < k; b1++) {
/* 276 */                   if (this.buffer[b1 - 1][b - 1] != 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                     
/* 284 */                     param1Graphics.setColor(arrayOfColor[this.buffer[b1 - 1][b - 1]]);
/*     */ 
/*     */ 
/*     */                     
/* 288 */                     param1Graphics.drawLine(b1, b, b1, b);
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/*     */         public boolean isFocusTraversable() {
/* 297 */           return false;
/*     */         }
/*     */       };
/* 300 */     jButton.setCursor(Cursor.getPredefinedCursor(0));
/* 301 */     jButton.setFocusPainted(false);
/* 302 */     jButton.setBorderPainted(false);
/* 303 */     jButton.setRequestFocusEnabled(false);
/* 304 */     maybeMakeButtonOpaque(jButton);
/* 305 */     return jButton;
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
/*     */   public class MetalDividerLayout
/*     */     implements LayoutManager
/*     */   {
/*     */     public void layoutContainer(Container param1Container) {
/* 323 */       JButton jButton1 = MetalSplitPaneDivider.this.getLeftButtonFromSuper();
/* 324 */       JButton jButton2 = MetalSplitPaneDivider.this.getRightButtonFromSuper();
/* 325 */       JSplitPane jSplitPane = MetalSplitPaneDivider.this.getSplitPaneFromSuper();
/* 326 */       int i = MetalSplitPaneDivider.this.getOrientationFromSuper();
/* 327 */       int j = MetalSplitPaneDivider.this.getOneTouchSizeFromSuper();
/* 328 */       int k = MetalSplitPaneDivider.this.getOneTouchOffsetFromSuper();
/* 329 */       Insets insets = MetalSplitPaneDivider.this.getInsets();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 335 */       if (jButton1 != null && jButton2 != null && param1Container == MetalSplitPaneDivider.this)
/*     */       {
/* 337 */         if (jSplitPane.isOneTouchExpandable()) {
/* 338 */           if (i == 0) {
/* 339 */             boolean bool = (insets != null) ? insets.top : false;
/* 340 */             int m = MetalSplitPaneDivider.this.getDividerSize();
/*     */             
/* 342 */             if (insets != null) {
/* 343 */               m -= insets.top + insets.bottom;
/*     */             }
/* 345 */             m = Math.min(m, j);
/* 346 */             jButton1.setBounds(k, bool, m * 2, m);
/*     */             
/* 348 */             jButton2.setBounds(k + j * 2, bool, m * 2, m);
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 353 */             int m = MetalSplitPaneDivider.this.getDividerSize();
/* 354 */             boolean bool = (insets != null) ? insets.left : false;
/*     */             
/* 356 */             if (insets != null) {
/* 357 */               m -= insets.left + insets.right;
/*     */             }
/* 359 */             m = Math.min(m, j);
/* 360 */             jButton1.setBounds(bool, k, m, m * 2);
/*     */             
/* 362 */             jButton2.setBounds(bool, k + j * 2, m, m * 2);
/*     */           }
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 368 */           jButton1.setBounds(-5, -5, 1, 1);
/* 369 */           jButton2.setBounds(-5, -5, 1, 1);
/*     */         } 
/*     */       }
/*     */     }
/*     */     
/*     */     public Dimension minimumLayoutSize(Container param1Container) {
/* 375 */       return new Dimension(0, 0);
/*     */     }
/*     */     
/*     */     public Dimension preferredLayoutSize(Container param1Container) {
/* 379 */       return new Dimension(0, 0);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void removeLayoutComponent(Component param1Component) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void addLayoutComponent(String param1String, Component param1Component) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int getOneTouchSizeFromSuper() {
/* 394 */     return 6;
/*     */   }
/*     */   
/*     */   int getOneTouchOffsetFromSuper() {
/* 398 */     return 2;
/*     */   }
/*     */   
/*     */   int getOrientationFromSuper() {
/* 402 */     return this.orientation;
/*     */   }
/*     */   
/*     */   JSplitPane getSplitPaneFromSuper() {
/* 406 */     return this.splitPane;
/*     */   }
/*     */   
/*     */   JButton getLeftButtonFromSuper() {
/* 410 */     return this.leftButton;
/*     */   }
/*     */   
/*     */   JButton getRightButtonFromSuper() {
/* 414 */     return this.rightButton;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalSplitPaneDivider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */