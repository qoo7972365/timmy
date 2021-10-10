/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import javax.swing.BoundedRangeModel;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.event.DocumentEvent;
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
/*     */ public class FieldView
/*     */   extends PlainView
/*     */ {
/*     */   public FieldView(Element paramElement) {
/*  51 */     super(paramElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FontMetrics getFontMetrics() {
/*  61 */     Container container = getContainer();
/*  62 */     return container.getFontMetrics(container.getFont());
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
/*     */   protected Shape adjustAllocation(Shape paramShape) {
/*  80 */     if (paramShape != null) {
/*  81 */       Rectangle rectangle = paramShape.getBounds();
/*  82 */       int i = (int)getPreferredSpan(1);
/*  83 */       int j = (int)getPreferredSpan(0);
/*  84 */       if (rectangle.height != i) {
/*  85 */         int k = rectangle.height - i;
/*  86 */         rectangle.y += k / 2;
/*  87 */         rectangle.height -= k;
/*     */       } 
/*     */ 
/*     */       
/*  91 */       Container container = getContainer();
/*  92 */       if (container instanceof JTextField) {
/*  93 */         JTextField jTextField = (JTextField)container;
/*  94 */         BoundedRangeModel boundedRangeModel = jTextField.getHorizontalVisibility();
/*  95 */         int k = Math.max(j, rectangle.width);
/*  96 */         int m = boundedRangeModel.getValue();
/*  97 */         int n = Math.min(k, rectangle.width - 1);
/*  98 */         if (m + n > k) {
/*  99 */           m = k - n;
/*     */         }
/* 101 */         boundedRangeModel.setRangeProperties(m, n, boundedRangeModel.getMinimum(), k, false);
/*     */         
/* 103 */         if (j < rectangle.width) {
/*     */           
/* 105 */           int i1 = rectangle.width - 1 - j;
/*     */           
/* 107 */           int i2 = ((JTextField)container).getHorizontalAlignment();
/* 108 */           if (Utilities.isLeftToRight(container)) {
/* 109 */             if (i2 == 10) {
/* 110 */               i2 = 2;
/*     */             }
/* 112 */             else if (i2 == 11) {
/* 113 */               i2 = 4;
/*     */             }
/*     */           
/*     */           }
/* 117 */           else if (i2 == 10) {
/* 118 */             i2 = 4;
/*     */           }
/* 120 */           else if (i2 == 11) {
/* 121 */             i2 = 2;
/*     */           } 
/*     */ 
/*     */           
/* 125 */           switch (i2) {
/*     */             case 0:
/* 127 */               rectangle.x += i1 / 2;
/* 128 */               rectangle.width -= i1;
/*     */               break;
/*     */             case 4:
/* 131 */               rectangle.x += i1;
/* 132 */               rectangle.width -= i1;
/*     */               break;
/*     */           } 
/*     */         
/*     */         } else {
/* 137 */           rectangle.width = j;
/* 138 */           rectangle.x -= boundedRangeModel.getValue();
/*     */         } 
/*     */       } 
/* 141 */       return rectangle;
/*     */     } 
/* 143 */     return null;
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
/*     */   void updateVisibilityModel() {
/* 155 */     Container container = getContainer();
/* 156 */     if (container instanceof JTextField) {
/* 157 */       JTextField jTextField = (JTextField)container;
/* 158 */       BoundedRangeModel boundedRangeModel = jTextField.getHorizontalVisibility();
/* 159 */       int i = (int)getPreferredSpan(0);
/* 160 */       int j = boundedRangeModel.getExtent();
/* 161 */       int k = Math.max(i, j);
/* 162 */       j = (j == 0) ? k : j;
/* 163 */       int m = k - j;
/* 164 */       int n = boundedRangeModel.getValue();
/* 165 */       if (n + j > k) {
/* 166 */         n = k - j;
/*     */       }
/* 168 */       m = Math.max(0, Math.min(m, n));
/* 169 */       boundedRangeModel.setRangeProperties(m, j, 0, k, false);
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
/*     */   public void paint(Graphics paramGraphics, Shape paramShape) {
/* 186 */     Rectangle rectangle = (Rectangle)paramShape;
/* 187 */     paramGraphics.clipRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/* 188 */     super.paint(paramGraphics, paramShape);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Shape adjustPaintRegion(Shape paramShape) {
/* 195 */     return adjustAllocation(paramShape);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPreferredSpan(int paramInt) {
/*     */     Segment segment;
/*     */     Document document;
/*     */     byte b;
/* 209 */     switch (paramInt) {
/*     */       case 0:
/* 211 */         segment = SegmentCache.getSharedSegment();
/* 212 */         document = getDocument();
/*     */         
/*     */         try {
/* 215 */           FontMetrics fontMetrics = getFontMetrics();
/* 216 */           document.getText(0, document.getLength(), segment);
/* 217 */           b = Utilities.getTabbedTextWidth(segment, fontMetrics, 0, this, 0);
/* 218 */           if (segment.count > 0) {
/* 219 */             Container container = getContainer();
/* 220 */             this
/* 221 */               .firstLineOffset = SwingUtilities2.getLeftSideBearing((container instanceof JComponent) ? (JComponent)container : null, fontMetrics, segment.array[segment.offset]);
/*     */ 
/*     */             
/* 224 */             this.firstLineOffset = Math.max(0, -this.firstLineOffset);
/*     */           } else {
/*     */             
/* 227 */             this.firstLineOffset = 0;
/*     */           } 
/* 229 */         } catch (BadLocationException badLocationException) {
/* 230 */           b = 0;
/*     */         } 
/* 232 */         SegmentCache.releaseSharedSegment(segment);
/* 233 */         return (b + this.firstLineOffset);
/*     */     } 
/* 235 */     return super.getPreferredSpan(paramInt);
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
/*     */   public int getResizeWeight(int paramInt) {
/* 247 */     if (paramInt == 0) {
/* 248 */       return 1;
/*     */     }
/* 250 */     return 0;
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
/*     */   public Shape modelToView(int paramInt, Shape paramShape, Position.Bias paramBias) throws BadLocationException {
/* 265 */     return super.modelToView(paramInt, adjustAllocation(paramShape), paramBias);
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
/*     */   public int viewToModel(float paramFloat1, float paramFloat2, Shape paramShape, Position.Bias[] paramArrayOfBias) {
/* 280 */     return super.viewToModel(paramFloat1, paramFloat2, adjustAllocation(paramShape), paramArrayOfBias);
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
/*     */   public void insertUpdate(DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/* 293 */     super.insertUpdate(paramDocumentEvent, adjustAllocation(paramShape), paramViewFactory);
/* 294 */     updateVisibilityModel();
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
/*     */   public void removeUpdate(DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/* 307 */     super.removeUpdate(paramDocumentEvent, adjustAllocation(paramShape), paramViewFactory);
/* 308 */     updateVisibilityModel();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/FieldView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */