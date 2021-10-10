/*     */ package javax.swing.text.html;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import javax.swing.SizeRequirements;
/*     */ import javax.swing.event.DocumentEvent;
/*     */ import javax.swing.text.AttributeSet;
/*     */ import javax.swing.text.BoxView;
/*     */ import javax.swing.text.Element;
/*     */ import javax.swing.text.View;
/*     */ import javax.swing.text.ViewFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockView
/*     */   extends BoxView
/*     */ {
/*     */   private AttributeSet attr;
/*     */   private StyleSheet.BoxPainter painter;
/*     */   private CSS.LengthValue cssWidth;
/*     */   private CSS.LengthValue cssHeight;
/*     */   
/*     */   public BlockView(Element paramElement, int paramInt) {
/*  51 */     super(paramElement, paramInt);
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
/*  72 */     super.setParent(paramView);
/*  73 */     if (paramView != null) {
/*  74 */       setPropertiesFromAttributes();
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
/*     */   protected SizeRequirements calculateMajorAxisRequirements(int paramInt, SizeRequirements paramSizeRequirements) {
/*  86 */     if (paramSizeRequirements == null) {
/*  87 */       paramSizeRequirements = new SizeRequirements();
/*     */     }
/*  89 */     if (!spanSetFromAttributes(paramInt, paramSizeRequirements, this.cssWidth, this.cssHeight)) {
/*  90 */       paramSizeRequirements = super.calculateMajorAxisRequirements(paramInt, paramSizeRequirements);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/*  95 */       SizeRequirements sizeRequirements = super.calculateMajorAxisRequirements(paramInt, (SizeRequirements)null);
/*     */ 
/*     */       
/*  98 */       int i = (paramInt == 0) ? (getLeftInset() + getRightInset()) : (getTopInset() + getBottomInset());
/*  99 */       paramSizeRequirements.minimum -= i;
/* 100 */       paramSizeRequirements.preferred -= i;
/* 101 */       paramSizeRequirements.maximum -= i;
/* 102 */       constrainSize(paramInt, paramSizeRequirements, sizeRequirements);
/*     */     } 
/* 104 */     return paramSizeRequirements;
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
/*     */   protected SizeRequirements calculateMinorAxisRequirements(int paramInt, SizeRequirements paramSizeRequirements) {
/* 116 */     if (paramSizeRequirements == null) {
/* 117 */       paramSizeRequirements = new SizeRequirements();
/*     */     }
/*     */     
/* 120 */     if (!spanSetFromAttributes(paramInt, paramSizeRequirements, this.cssWidth, this.cssHeight)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 146 */       paramSizeRequirements = super.calculateMinorAxisRequirements(paramInt, paramSizeRequirements);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 151 */       SizeRequirements sizeRequirements = super.calculateMinorAxisRequirements(paramInt, (SizeRequirements)null);
/*     */ 
/*     */       
/* 154 */       int i = (paramInt == 0) ? (getLeftInset() + getRightInset()) : (getTopInset() + getBottomInset());
/* 155 */       paramSizeRequirements.minimum -= i;
/* 156 */       paramSizeRequirements.preferred -= i;
/* 157 */       paramSizeRequirements.maximum -= i;
/* 158 */       constrainSize(paramInt, paramSizeRequirements, sizeRequirements);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 166 */     if (paramInt == 0) {
/* 167 */       Object object = getAttributes().getAttribute(CSS.Attribute.TEXT_ALIGN);
/* 168 */       if (object != null) {
/* 169 */         String str = object.toString();
/* 170 */         if (str.equals("center")) {
/* 171 */           paramSizeRequirements.alignment = 0.5F;
/* 172 */         } else if (str.equals("right")) {
/* 173 */           paramSizeRequirements.alignment = 1.0F;
/*     */         } else {
/* 175 */           paramSizeRequirements.alignment = 0.0F;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 180 */     return paramSizeRequirements;
/*     */   }
/*     */   
/*     */   boolean isPercentage(int paramInt, AttributeSet paramAttributeSet) {
/* 184 */     if (paramInt == 0) {
/* 185 */       if (this.cssWidth != null) {
/* 186 */         return this.cssWidth.isPercentage();
/*     */       }
/*     */     }
/* 189 */     else if (this.cssHeight != null) {
/* 190 */       return this.cssHeight.isPercentage();
/*     */     } 
/*     */     
/* 193 */     return false;
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
/*     */   static boolean spanSetFromAttributes(int paramInt, SizeRequirements paramSizeRequirements, CSS.LengthValue paramLengthValue1, CSS.LengthValue paramLengthValue2) {
/* 205 */     if (paramInt == 0) {
/* 206 */       if (paramLengthValue1 != null && !paramLengthValue1.isPercentage()) {
/* 207 */         paramSizeRequirements.minimum = paramSizeRequirements.preferred = paramSizeRequirements.maximum = (int)paramLengthValue1.getValue();
/* 208 */         return true;
/*     */       }
/*     */     
/* 211 */     } else if (paramLengthValue2 != null && !paramLengthValue2.isPercentage()) {
/* 212 */       paramSizeRequirements.minimum = paramSizeRequirements.preferred = paramSizeRequirements.maximum = (int)paramLengthValue2.getValue();
/* 213 */       return true;
/*     */     } 
/*     */     
/* 216 */     return false;
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
/*     */   protected void layoutMinorAxis(int paramInt1, int paramInt2, int[] paramArrayOfint1, int[] paramArrayOfint2) {
/* 236 */     int i = getViewCount();
/* 237 */     CSS.Attribute attribute = (paramInt2 == 0) ? CSS.Attribute.WIDTH : CSS.Attribute.HEIGHT;
/* 238 */     for (byte b = 0; b < i; b++) {
/* 239 */       int k; View view = getView(b);
/* 240 */       int j = (int)view.getMinimumSpan(paramInt2);
/*     */ 
/*     */ 
/*     */       
/* 244 */       AttributeSet attributeSet = view.getAttributes();
/* 245 */       CSS.LengthValue lengthValue = (CSS.LengthValue)attributeSet.getAttribute(attribute);
/* 246 */       if (lengthValue != null && lengthValue.isPercentage()) {
/*     */         
/* 248 */         j = Math.max((int)lengthValue.getValue(paramInt1), j);
/* 249 */         k = j;
/*     */       } else {
/* 251 */         k = (int)view.getMaximumSpan(paramInt2);
/*     */       } 
/*     */ 
/*     */       
/* 255 */       if (k < paramInt1) {
/*     */         
/* 257 */         float f = view.getAlignment(paramInt2);
/* 258 */         paramArrayOfint1[b] = (int)((paramInt1 - k) * f);
/* 259 */         paramArrayOfint2[b] = k;
/*     */       } else {
/*     */         
/* 262 */         paramArrayOfint1[b] = 0;
/* 263 */         paramArrayOfint2[b] = Math.max(j, paramInt1);
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
/*     */   public void paint(Graphics paramGraphics, Shape paramShape) {
/* 280 */     Rectangle rectangle = (Rectangle)paramShape;
/* 281 */     this.painter.paint(paramGraphics, rectangle.x, rectangle.y, rectangle.width, rectangle.height, this);
/* 282 */     super.paint(paramGraphics, rectangle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeSet getAttributes() {
/* 291 */     if (this.attr == null) {
/* 292 */       StyleSheet styleSheet = getStyleSheet();
/* 293 */       this.attr = styleSheet.getViewAttributes(this);
/*     */     } 
/* 295 */     return this.attr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getResizeWeight(int paramInt) {
/* 306 */     switch (paramInt) {
/*     */       case 0:
/* 308 */         return 1;
/*     */       case 1:
/* 310 */         return 0;
/*     */     } 
/* 312 */     throw new IllegalArgumentException("Invalid axis: " + paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAlignment(int paramInt) {
/*     */     float f1;
/*     */     View view;
/*     */     float f2;
/* 323 */     switch (paramInt) {
/*     */       case 0:
/* 325 */         return 0.0F;
/*     */       case 1:
/* 327 */         if (getViewCount() == 0) {
/* 328 */           return 0.0F;
/*     */         }
/* 330 */         f1 = getPreferredSpan(1);
/* 331 */         view = getView(0);
/* 332 */         f2 = view.getPreferredSpan(1);
/* 333 */         return ((int)f1 != 0) ? (f2 * view.getAlignment(1) / f1) : 0.0F;
/*     */     } 
/*     */     
/* 336 */     throw new IllegalArgumentException("Invalid axis: " + paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void changedUpdate(DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/* 341 */     super.changedUpdate(paramDocumentEvent, paramShape, paramViewFactory);
/* 342 */     int i = paramDocumentEvent.getOffset();
/* 343 */     if (i <= getStartOffset() && i + paramDocumentEvent.getLength() >= 
/* 344 */       getEndOffset()) {
/* 345 */       setPropertiesFromAttributes();
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
/*     */   
/*     */   public float getPreferredSpan(int paramInt) {
/* 362 */     return super.getPreferredSpan(paramInt);
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
/*     */   public float getMinimumSpan(int paramInt) {
/* 378 */     return super.getMinimumSpan(paramInt);
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
/*     */   public float getMaximumSpan(int paramInt) {
/* 394 */     return super.getMaximumSpan(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setPropertiesFromAttributes() {
/* 403 */     StyleSheet styleSheet = getStyleSheet();
/* 404 */     this.attr = styleSheet.getViewAttributes(this);
/*     */ 
/*     */     
/* 407 */     this.painter = styleSheet.getBoxPainter(this.attr);
/* 408 */     if (this.attr != null) {
/* 409 */       setInsets((short)(int)this.painter.getInset(1, this), 
/* 410 */           (short)(int)this.painter.getInset(2, this), 
/* 411 */           (short)(int)this.painter.getInset(3, this), 
/* 412 */           (short)(int)this.painter.getInset(4, this));
/*     */     }
/*     */ 
/*     */     
/* 416 */     this.cssWidth = (CSS.LengthValue)this.attr.getAttribute(CSS.Attribute.WIDTH);
/* 417 */     this.cssHeight = (CSS.LengthValue)this.attr.getAttribute(CSS.Attribute.HEIGHT);
/*     */   }
/*     */   
/*     */   protected StyleSheet getStyleSheet() {
/* 421 */     HTMLDocument hTMLDocument = (HTMLDocument)getDocument();
/* 422 */     return hTMLDocument.getStyleSheet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void constrainSize(int paramInt, SizeRequirements paramSizeRequirements1, SizeRequirements paramSizeRequirements2) {
/* 431 */     if (paramSizeRequirements2.minimum > paramSizeRequirements1.minimum) {
/* 432 */       paramSizeRequirements1.minimum = paramSizeRequirements1.preferred = paramSizeRequirements2.minimum;
/* 433 */       paramSizeRequirements1.maximum = Math.max(paramSizeRequirements1.maximum, paramSizeRequirements2.maximum);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/BlockView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */