/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Label;
/*     */ import java.awt.peer.LabelPeer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class XLabelPeer
/*     */   extends XComponentPeer
/*     */   implements LabelPeer
/*     */ {
/*     */   static final int TEXT_XPAD = 8;
/*     */   static final int TEXT_YPAD = 6;
/*     */   String label;
/*     */   int alignment;
/*     */   FontMetrics cachedFontMetrics;
/*     */   Font oldfont;
/*     */   
/*     */   FontMetrics getFontMetrics() {
/*  46 */     if (this.cachedFontMetrics != null)
/*  47 */       return this.cachedFontMetrics; 
/*  48 */     return getFontMetrics(getPeerFont());
/*     */   }
/*     */ 
/*     */   
/*     */   void preInit(XCreateWindowParams paramXCreateWindowParams) {
/*  53 */     super.preInit(paramXCreateWindowParams);
/*  54 */     Label label = (Label)this.target;
/*  55 */     this.label = label.getText();
/*  56 */     if (this.label == null) {
/*  57 */       this.label = "";
/*     */     }
/*  59 */     this.alignment = label.getAlignment();
/*     */   }
/*     */   
/*     */   XLabelPeer(Label paramLabel) {
/*  63 */     super(paramLabel);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMinimumSize() {
/*     */     byte b;
/*  70 */     FontMetrics fontMetrics = getFontMetrics();
/*     */     
/*     */     try {
/*  73 */       b = fontMetrics.stringWidth(this.label);
/*     */     }
/*  75 */     catch (NullPointerException nullPointerException) {
/*  76 */       b = 0;
/*     */     } 
/*  78 */     return new Dimension(b + 8, fontMetrics
/*  79 */         .getAscent() + fontMetrics.getMaxDescent() + 6);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void paintPeer(Graphics paramGraphics) {
/*  90 */     int i = 0;
/*  91 */     int j = 0;
/*  92 */     paramGraphics.setColor(getPeerBackground());
/*  93 */     paramGraphics.fillRect(0, 0, this.width, this.height);
/*     */     
/*  95 */     Font font = getPeerFont();
/*  96 */     paramGraphics.setFont(font);
/*  97 */     FontMetrics fontMetrics = paramGraphics.getFontMetrics();
/*     */     
/*  99 */     if (this.cachedFontMetrics == null) {
/*     */       
/* 101 */       this.cachedFontMetrics = fontMetrics;
/*     */ 
/*     */     
/*     */     }
/* 105 */     else if (this.oldfont != font) {
/* 106 */       this.cachedFontMetrics = fontMetrics;
/*     */     } 
/*     */     
/* 109 */     switch (this.alignment) {
/*     */       case 0:
/* 111 */         i = 2;
/* 112 */         j = (this.height + fontMetrics.getMaxAscent() - fontMetrics.getMaxDescent()) / 2;
/*     */         break;
/*     */       case 2:
/* 115 */         i = this.width - fontMetrics.stringWidth(this.label) + 2;
/* 116 */         j = (this.height + fontMetrics.getMaxAscent() - fontMetrics.getMaxDescent()) / 2;
/*     */         break;
/*     */       case 1:
/* 119 */         i = (this.width - fontMetrics.stringWidth(this.label)) / 2;
/* 120 */         j = (this.height + fontMetrics.getMaxAscent() - fontMetrics.getMaxDescent()) / 2;
/*     */         break;
/*     */     } 
/* 123 */     if (isEnabled()) {
/* 124 */       paramGraphics.setColor(getPeerForeground());
/* 125 */       paramGraphics.drawString(this.label, i, j);
/*     */     } else {
/*     */       
/* 128 */       paramGraphics.setColor(getPeerBackground().brighter());
/* 129 */       paramGraphics.drawString(this.label, i, j);
/* 130 */       paramGraphics.setColor(getPeerBackground().darker());
/* 131 */       paramGraphics.drawString(this.label, i - 1, j - 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setText(String paramString) {
/* 137 */     if (paramString == null) {
/* 138 */       paramString = "";
/*     */     }
/* 140 */     if (!paramString.equals(this.label)) {
/* 141 */       this.label = paramString;
/* 142 */       repaint();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAlignment(int paramInt) {
/* 148 */     if (this.alignment != paramInt) {
/* 149 */       this.alignment = paramInt;
/* 150 */       repaint();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XLabelPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */