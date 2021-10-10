/*     */ package javax.swing.colorchooser;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import javax.swing.JColorChooser;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import sun.swing.SwingUtilities2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DefaultPreviewPanel
/*     */   extends JPanel
/*     */ {
/*  58 */   private int squareSize = 25;
/*  59 */   private int squareGap = 5;
/*  60 */   private int innerGap = 5;
/*     */ 
/*     */   
/*  63 */   private int textGap = 5;
/*  64 */   private Font font = new Font("Dialog", 0, 12);
/*     */   
/*     */   private String sampleText;
/*  67 */   private int swatchWidth = 50;
/*     */   
/*  69 */   private Color oldColor = null;
/*     */   
/*     */   private JColorChooser getColorChooser() {
/*  72 */     return (JColorChooser)SwingUtilities.getAncestorOfClass(JColorChooser.class, this);
/*     */   }
/*     */   
/*     */   public Dimension getPreferredSize() {
/*     */     DefaultPreviewPanel defaultPreviewPanel;
/*  77 */     JColorChooser jColorChooser = getColorChooser();
/*  78 */     if (jColorChooser == null) {
/*  79 */       defaultPreviewPanel = this;
/*     */     }
/*  81 */     FontMetrics fontMetrics = defaultPreviewPanel.getFontMetrics(getFont());
/*     */     
/*  83 */     int i = fontMetrics.getAscent();
/*  84 */     int j = fontMetrics.getHeight();
/*  85 */     int k = SwingUtilities2.stringWidth(defaultPreviewPanel, fontMetrics, getSampleText());
/*     */     
/*  87 */     int m = j * 3 + this.textGap * 3;
/*  88 */     int n = this.squareSize * 3 + this.squareGap * 2 + this.swatchWidth + k + this.textGap * 3;
/*  89 */     return new Dimension(n, m);
/*     */   }
/*     */   
/*     */   public void paintComponent(Graphics paramGraphics) {
/*  93 */     if (this.oldColor == null) {
/*  94 */       this.oldColor = getForeground();
/*     */     }
/*  96 */     paramGraphics.setColor(getBackground());
/*  97 */     paramGraphics.fillRect(0, 0, getWidth(), getHeight());
/*     */     
/*  99 */     if (getComponentOrientation().isLeftToRight()) {
/* 100 */       int i = paintSquares(paramGraphics, 0);
/* 101 */       int j = paintText(paramGraphics, i);
/* 102 */       paintSwatch(paramGraphics, i + j);
/*     */     } else {
/* 104 */       int i = paintSwatch(paramGraphics, 0);
/* 105 */       int j = paintText(paramGraphics, i);
/* 106 */       paintSquares(paramGraphics, i + j);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int paintSwatch(Graphics paramGraphics, int paramInt) {
/* 112 */     int i = paramInt;
/* 113 */     paramGraphics.setColor(this.oldColor);
/* 114 */     paramGraphics.fillRect(i, 0, this.swatchWidth, this.squareSize + this.squareGap / 2);
/* 115 */     paramGraphics.setColor(getForeground());
/* 116 */     paramGraphics.fillRect(i, this.squareSize + this.squareGap / 2, this.swatchWidth, this.squareSize + this.squareGap / 2);
/* 117 */     return i + this.swatchWidth;
/*     */   }
/*     */   private int paintText(Graphics paramGraphics, int paramInt) {
/*     */     DefaultPreviewPanel defaultPreviewPanel;
/* 121 */     paramGraphics.setFont(getFont());
/* 122 */     JColorChooser jColorChooser = getColorChooser();
/* 123 */     if (jColorChooser == null) {
/* 124 */       defaultPreviewPanel = this;
/*     */     }
/* 126 */     FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(defaultPreviewPanel, paramGraphics);
/*     */     
/* 128 */     int i = fontMetrics.getAscent();
/* 129 */     int j = fontMetrics.getHeight();
/* 130 */     int k = SwingUtilities2.stringWidth(defaultPreviewPanel, fontMetrics, getSampleText());
/*     */     
/* 132 */     int m = paramInt + this.textGap;
/*     */     
/* 134 */     Color color = getForeground();
/*     */     
/* 136 */     paramGraphics.setColor(color);
/*     */     
/* 138 */     SwingUtilities2.drawString(defaultPreviewPanel, paramGraphics, getSampleText(), m + this.textGap / 2, i + 2);
/*     */ 
/*     */     
/* 141 */     paramGraphics.fillRect(m, j + this.textGap, k + this.textGap, j + 2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 146 */     paramGraphics.setColor(Color.black);
/* 147 */     SwingUtilities2.drawString(defaultPreviewPanel, paramGraphics, getSampleText(), m + this.textGap / 2, j + i + this.textGap + 2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     paramGraphics.setColor(Color.white);
/*     */     
/* 154 */     paramGraphics.fillRect(m, (j + this.textGap) * 2, k + this.textGap, j + 2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 159 */     paramGraphics.setColor(color);
/* 160 */     SwingUtilities2.drawString(defaultPreviewPanel, paramGraphics, getSampleText(), m + this.textGap / 2, (j + this.textGap) * 2 + i + 2);
/*     */ 
/*     */ 
/*     */     
/* 164 */     return k + this.textGap * 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int paintSquares(Graphics paramGraphics, int paramInt) {
/* 170 */     int i = paramInt;
/* 171 */     Color color = getForeground();
/*     */     
/* 173 */     paramGraphics.setColor(Color.white);
/* 174 */     paramGraphics.fillRect(i, 0, this.squareSize, this.squareSize);
/* 175 */     paramGraphics.setColor(color);
/* 176 */     paramGraphics.fillRect(i + this.innerGap, this.innerGap, this.squareSize - this.innerGap * 2, this.squareSize - this.innerGap * 2);
/*     */ 
/*     */ 
/*     */     
/* 180 */     paramGraphics.setColor(Color.white);
/* 181 */     paramGraphics.fillRect(i + this.innerGap * 2, this.innerGap * 2, this.squareSize - this.innerGap * 4, this.squareSize - this.innerGap * 4);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 186 */     paramGraphics.setColor(color);
/* 187 */     paramGraphics.fillRect(i, this.squareSize + this.squareGap, this.squareSize, this.squareSize);
/*     */     
/* 189 */     paramGraphics.translate(this.squareSize + this.squareGap, 0);
/* 190 */     paramGraphics.setColor(Color.black);
/* 191 */     paramGraphics.fillRect(i, 0, this.squareSize, this.squareSize);
/* 192 */     paramGraphics.setColor(color);
/* 193 */     paramGraphics.fillRect(i + this.innerGap, this.innerGap, this.squareSize - this.innerGap * 2, this.squareSize - this.innerGap * 2);
/*     */ 
/*     */ 
/*     */     
/* 197 */     paramGraphics.setColor(Color.white);
/* 198 */     paramGraphics.fillRect(i + this.innerGap * 2, this.innerGap * 2, this.squareSize - this.innerGap * 4, this.squareSize - this.innerGap * 4);
/*     */ 
/*     */ 
/*     */     
/* 202 */     paramGraphics.translate(-(this.squareSize + this.squareGap), 0);
/*     */     
/* 204 */     paramGraphics.translate(this.squareSize + this.squareGap, this.squareSize + this.squareGap);
/* 205 */     paramGraphics.setColor(Color.white);
/* 206 */     paramGraphics.fillRect(i, 0, this.squareSize, this.squareSize);
/* 207 */     paramGraphics.setColor(color);
/* 208 */     paramGraphics.fillRect(i + this.innerGap, this.innerGap, this.squareSize - this.innerGap * 2, this.squareSize - this.innerGap * 2);
/*     */ 
/*     */ 
/*     */     
/* 212 */     paramGraphics.translate(-(this.squareSize + this.squareGap), -(this.squareSize + this.squareGap));
/*     */ 
/*     */ 
/*     */     
/* 216 */     paramGraphics.translate((this.squareSize + this.squareGap) * 2, 0);
/* 217 */     paramGraphics.setColor(Color.white);
/* 218 */     paramGraphics.fillRect(i, 0, this.squareSize, this.squareSize);
/* 219 */     paramGraphics.setColor(color);
/* 220 */     paramGraphics.fillRect(i + this.innerGap, this.innerGap, this.squareSize - this.innerGap * 2, this.squareSize - this.innerGap * 2);
/*     */ 
/*     */ 
/*     */     
/* 224 */     paramGraphics.setColor(Color.black);
/* 225 */     paramGraphics.fillRect(i + this.innerGap * 2, this.innerGap * 2, this.squareSize - this.innerGap * 4, this.squareSize - this.innerGap * 4);
/*     */ 
/*     */ 
/*     */     
/* 229 */     paramGraphics.translate(-((this.squareSize + this.squareGap) * 2), 0);
/*     */     
/* 231 */     paramGraphics.translate((this.squareSize + this.squareGap) * 2, this.squareSize + this.squareGap);
/* 232 */     paramGraphics.setColor(Color.black);
/* 233 */     paramGraphics.fillRect(i, 0, this.squareSize, this.squareSize);
/* 234 */     paramGraphics.setColor(color);
/* 235 */     paramGraphics.fillRect(i + this.innerGap, this.innerGap, this.squareSize - this.innerGap * 2, this.squareSize - this.innerGap * 2);
/*     */ 
/*     */ 
/*     */     
/* 239 */     paramGraphics.translate(-((this.squareSize + this.squareGap) * 2), -(this.squareSize + this.squareGap));
/*     */     
/* 241 */     return this.squareSize * 3 + this.squareGap * 2;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getSampleText() {
/* 246 */     if (this.sampleText == null) {
/* 247 */       this.sampleText = UIManager.getString("ColorChooser.sampleText", getLocale());
/*     */     }
/* 249 */     return this.sampleText;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/colorchooser/DefaultPreviewPanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */