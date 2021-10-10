/*     */ package javax.swing.text.html;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import javax.swing.SizeRequirements;
/*     */ import javax.swing.text.AttributeSet;
/*     */ import javax.swing.text.Element;
/*     */ import javax.swing.text.JTextComponent;
/*     */ import javax.swing.text.ParagraphView;
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
/*     */ public class ParagraphView
/*     */   extends ParagraphView
/*     */ {
/*     */   private AttributeSet attr;
/*     */   private StyleSheet.BoxPainter painter;
/*     */   private CSS.LengthValue cssWidth;
/*     */   private CSS.LengthValue cssHeight;
/*     */   
/*     */   public ParagraphView(Element paramElement) {
/*  54 */     super(paramElement);
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
/*     */   
/*     */   public void setParent(View paramView) {
/*  75 */     super.setParent(paramView);
/*  76 */     if (paramView != null) {
/*  77 */       setPropertiesFromAttributes();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeSet getAttributes() {
/*  87 */     if (this.attr == null) {
/*  88 */       StyleSheet styleSheet = getStyleSheet();
/*  89 */       this.attr = styleSheet.getViewAttributes(this);
/*     */     } 
/*  91 */     return this.attr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setPropertiesFromAttributes() {
/* 100 */     StyleSheet styleSheet = getStyleSheet();
/* 101 */     this.attr = styleSheet.getViewAttributes(this);
/* 102 */     this.painter = styleSheet.getBoxPainter(this.attr);
/* 103 */     if (this.attr != null) {
/* 104 */       super.setPropertiesFromAttributes();
/* 105 */       setInsets((short)(int)this.painter.getInset(1, this), 
/* 106 */           (short)(int)this.painter.getInset(2, this), 
/* 107 */           (short)(int)this.painter.getInset(3, this), 
/* 108 */           (short)(int)this.painter.getInset(4, this));
/* 109 */       Object object = this.attr.getAttribute(CSS.Attribute.TEXT_ALIGN);
/* 110 */       if (object != null) {
/*     */         
/* 112 */         String str = object.toString();
/* 113 */         if (str.equals("left")) {
/* 114 */           setJustification(0);
/* 115 */         } else if (str.equals("center")) {
/* 116 */           setJustification(1);
/* 117 */         } else if (str.equals("right")) {
/* 118 */           setJustification(2);
/* 119 */         } else if (str.equals("justify")) {
/* 120 */           setJustification(3);
/*     */         } 
/*     */       } 
/*     */       
/* 124 */       this.cssWidth = (CSS.LengthValue)this.attr.getAttribute(CSS.Attribute.WIDTH);
/*     */       
/* 126 */       this.cssHeight = (CSS.LengthValue)this.attr.getAttribute(CSS.Attribute.HEIGHT);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected StyleSheet getStyleSheet() {
/* 132 */     HTMLDocument hTMLDocument = (HTMLDocument)getDocument();
/* 133 */     return hTMLDocument.getStyleSheet();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SizeRequirements calculateMinorAxisRequirements(int paramInt, SizeRequirements paramSizeRequirements) {
/* 157 */     paramSizeRequirements = super.calculateMinorAxisRequirements(paramInt, paramSizeRequirements);
/*     */     
/* 159 */     if (BlockView.spanSetFromAttributes(paramInt, paramSizeRequirements, this.cssWidth, this.cssHeight)) {
/*     */ 
/*     */ 
/*     */       
/* 163 */       int i = (paramInt == 0) ? (getLeftInset() + getRightInset()) : (getTopInset() + getBottomInset());
/* 164 */       paramSizeRequirements.minimum -= i;
/* 165 */       paramSizeRequirements.preferred -= i;
/* 166 */       paramSizeRequirements.maximum -= i;
/*     */     } 
/* 168 */     return paramSizeRequirements;
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
/*     */   public boolean isVisible() {
/* 184 */     int i = getLayoutViewCount() - 1; byte b;
/* 185 */     for (b = 0; b < i; b++) {
/* 186 */       View view = getLayoutView(b);
/* 187 */       if (view.isVisible()) {
/* 188 */         return true;
/*     */       }
/*     */     } 
/* 191 */     if (i > 0) {
/* 192 */       View view = getLayoutView(i);
/* 193 */       if (view.getEndOffset() - view.getStartOffset() == 1) {
/* 194 */         return false;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 199 */     if (getStartOffset() == getDocument().getLength()) {
/* 200 */       boolean bool; b = 0;
/* 201 */       Container container = getContainer();
/* 202 */       if (container instanceof JTextComponent) {
/* 203 */         bool = ((JTextComponent)container).isEditable();
/*     */       }
/* 205 */       if (!bool) {
/* 206 */         return false;
/*     */       }
/*     */     } 
/* 209 */     return true;
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
/*     */   public void paint(Graphics paramGraphics, Shape paramShape) {
/*     */     Rectangle rectangle;
/* 222 */     if (paramShape == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 227 */     if (paramShape instanceof Rectangle) {
/* 228 */       rectangle = (Rectangle)paramShape;
/*     */     } else {
/* 230 */       rectangle = paramShape.getBounds();
/*     */     } 
/* 232 */     this.painter.paint(paramGraphics, rectangle.x, rectangle.y, rectangle.width, rectangle.height, this);
/* 233 */     super.paint(paramGraphics, paramShape);
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
/*     */   public float getPreferredSpan(int paramInt) {
/* 250 */     if (!isVisible()) {
/* 251 */       return 0.0F;
/*     */     }
/* 253 */     return super.getPreferredSpan(paramInt);
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
/*     */   public float getMinimumSpan(int paramInt) {
/* 267 */     if (!isVisible()) {
/* 268 */       return 0.0F;
/*     */     }
/* 270 */     return super.getMinimumSpan(paramInt);
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
/*     */   public float getMaximumSpan(int paramInt) {
/* 284 */     if (!isVisible()) {
/* 285 */       return 0.0F;
/*     */     }
/* 287 */     return super.getMaximumSpan(paramInt);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/ParagraphView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */