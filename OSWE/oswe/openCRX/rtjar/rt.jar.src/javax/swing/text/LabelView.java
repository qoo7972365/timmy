/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Toolkit;
/*     */ import javax.swing.event.DocumentEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LabelView
/*     */   extends GlyphView
/*     */   implements TabableView
/*     */ {
/*     */   private Font font;
/*     */   private Color fg;
/*     */   private Color bg;
/*     */   private boolean underline;
/*     */   private boolean strike;
/*     */   private boolean superscript;
/*     */   private boolean subscript;
/*     */   
/*     */   public LabelView(Element paramElement) {
/*  46 */     super(paramElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void sync() {
/*  55 */     if (this.font == null) {
/*  56 */       setPropertiesFromAttributes();
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
/*     */   protected void setUnderline(boolean paramBoolean) {
/*  71 */     this.underline = paramBoolean;
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
/*     */   protected void setStrikeThrough(boolean paramBoolean) {
/*  86 */     this.strike = paramBoolean;
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
/*     */   protected void setSuperscript(boolean paramBoolean) {
/* 102 */     this.superscript = paramBoolean;
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
/*     */   protected void setSubscript(boolean paramBoolean) {
/* 117 */     this.subscript = paramBoolean;
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
/*     */   protected void setBackground(Color paramColor) {
/* 133 */     this.bg = paramColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setPropertiesFromAttributes() {
/* 140 */     AttributeSet attributeSet = getAttributes();
/* 141 */     if (attributeSet != null) {
/* 142 */       Document document = getDocument();
/* 143 */       if (document instanceof StyledDocument) {
/* 144 */         StyledDocument styledDocument = (StyledDocument)document;
/* 145 */         this.font = styledDocument.getFont(attributeSet);
/* 146 */         this.fg = styledDocument.getForeground(attributeSet);
/* 147 */         if (attributeSet.isDefined(StyleConstants.Background)) {
/* 148 */           this.bg = styledDocument.getBackground(attributeSet);
/*     */         } else {
/* 150 */           this.bg = null;
/*     */         } 
/* 152 */         setUnderline(StyleConstants.isUnderline(attributeSet));
/* 153 */         setStrikeThrough(StyleConstants.isStrikeThrough(attributeSet));
/* 154 */         setSuperscript(StyleConstants.isSuperscript(attributeSet));
/* 155 */         setSubscript(StyleConstants.isSubscript(attributeSet));
/*     */       } else {
/* 157 */         throw new StateInvariantError("LabelView needs StyledDocument");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected FontMetrics getFontMetrics() {
/* 169 */     sync();
/* 170 */     Container container = getContainer();
/* 171 */     return (container != null) ? container.getFontMetrics(this.font) : 
/* 172 */       Toolkit.getDefaultToolkit().getFontMetrics(this.font);
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
/*     */   public Color getBackground() {
/* 184 */     sync();
/* 185 */     return this.bg;
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
/*     */   public Color getForeground() {
/* 197 */     sync();
/* 198 */     return this.fg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font getFont() {
/* 208 */     sync();
/* 209 */     return this.font;
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
/*     */   public boolean isUnderline() {
/* 228 */     sync();
/* 229 */     return this.underline;
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
/*     */   
/*     */   public boolean isStrikeThrough() {
/* 249 */     sync();
/* 250 */     return this.strike;
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
/*     */   public boolean isSubscript() {
/* 268 */     sync();
/* 269 */     return this.subscript;
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
/*     */   public boolean isSuperscript() {
/* 286 */     sync();
/* 287 */     return this.superscript;
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
/*     */   public void changedUpdate(DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/* 302 */     this.font = null;
/* 303 */     super.changedUpdate(paramDocumentEvent, paramShape, paramViewFactory);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/LabelView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */