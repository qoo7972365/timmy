/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import javax.swing.BoundedRangeModel;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.event.DocumentEvent;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.text.BadLocationException;
/*     */ import javax.swing.text.Document;
/*     */ import javax.swing.text.Element;
/*     */ import javax.swing.text.FieldView;
/*     */ import javax.swing.text.GlyphView;
/*     */ import javax.swing.text.JTextComponent;
/*     */ import javax.swing.text.ParagraphView;
/*     */ import javax.swing.text.Position;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicTextFieldUI
/*     */   extends BasicTextUI
/*     */ {
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  63 */     return new BasicTextFieldUI();
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
/*     */   protected String getPropertyPrefix() {
/*  81 */     return "TextField";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public View create(Element paramElement) {
/*  91 */     Document document = paramElement.getDocument();
/*  92 */     Object object = document.getProperty("i18n");
/*  93 */     if (Boolean.TRUE.equals(object)) {
/*     */ 
/*     */       
/*  96 */       String str = paramElement.getName();
/*  97 */       if (str != null) {
/*  98 */         if (str.equals("content"))
/*  99 */           return new GlyphView(paramElement)
/*     */             {
/*     */               public float getMinimumSpan(int param1Int)
/*     */               {
/* 103 */                 return getPreferredSpan(param1Int);
/*     */               }
/*     */             }; 
/* 106 */         if (str.equals("paragraph")) {
/* 107 */           return new I18nFieldView(paramElement);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 112 */     return new FieldView(paramElement);
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
/*     */   public int getBaseline(JComponent paramJComponent, int paramInt1, int paramInt2) {
/* 124 */     super.getBaseline(paramJComponent, paramInt1, paramInt2);
/* 125 */     View view = getRootView((JTextComponent)paramJComponent);
/* 126 */     if (view.getViewCount() > 0) {
/* 127 */       Insets insets = paramJComponent.getInsets();
/* 128 */       paramInt2 = paramInt2 - insets.top - insets.bottom;
/* 129 */       if (paramInt2 > 0) {
/* 130 */         int i = insets.top;
/* 131 */         View view1 = view.getView(0);
/* 132 */         int j = (int)view1.getPreferredSpan(1);
/* 133 */         if (paramInt2 != j) {
/* 134 */           int k = paramInt2 - j;
/* 135 */           i += k / 2;
/*     */         } 
/* 137 */         if (view1 instanceof I18nFieldView) {
/* 138 */           int k = BasicHTML.getBaseline(view1, paramInt1 - insets.left - insets.right, paramInt2);
/*     */ 
/*     */           
/* 141 */           if (k < 0) {
/* 142 */             return -1;
/*     */           }
/* 144 */           i += k;
/*     */         } else {
/*     */           
/* 147 */           FontMetrics fontMetrics = paramJComponent.getFontMetrics(paramJComponent.getFont());
/* 148 */           i += fontMetrics.getAscent();
/*     */         } 
/* 150 */         return i;
/*     */       } 
/*     */     } 
/* 153 */     return -1;
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
/*     */   public Component.BaselineResizeBehavior getBaselineResizeBehavior(JComponent paramJComponent) {
/* 166 */     super.getBaselineResizeBehavior(paramJComponent);
/* 167 */     return Component.BaselineResizeBehavior.CENTER_OFFSET;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class I18nFieldView
/*     */     extends ParagraphView
/*     */   {
/*     */     I18nFieldView(Element param1Element) {
/* 178 */       super(param1Element);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getFlowSpan(int param1Int) {
/* 188 */       return Integer.MAX_VALUE;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void setJustification(int param1Int) {}
/*     */ 
/*     */     
/*     */     static boolean isLeftToRight(Component param1Component) {
/* 197 */       return param1Component.getComponentOrientation().isLeftToRight();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Shape adjustAllocation(Shape param1Shape) {
/* 215 */       if (param1Shape != null) {
/* 216 */         Rectangle rectangle = param1Shape.getBounds();
/* 217 */         int i = (int)getPreferredSpan(1);
/* 218 */         int j = (int)getPreferredSpan(0);
/* 219 */         if (rectangle.height != i) {
/* 220 */           int k = rectangle.height - i;
/* 221 */           rectangle.y += k / 2;
/* 222 */           rectangle.height -= k;
/*     */         } 
/*     */ 
/*     */         
/* 226 */         Container container = getContainer();
/* 227 */         if (container instanceof JTextField) {
/* 228 */           JTextField jTextField = (JTextField)container;
/* 229 */           BoundedRangeModel boundedRangeModel = jTextField.getHorizontalVisibility();
/* 230 */           int k = Math.max(j, rectangle.width);
/* 231 */           int m = boundedRangeModel.getValue();
/* 232 */           int n = Math.min(k, rectangle.width - 1);
/* 233 */           if (m + n > k) {
/* 234 */             m = k - n;
/*     */           }
/* 236 */           boundedRangeModel.setRangeProperties(m, n, boundedRangeModel.getMinimum(), k, false);
/*     */           
/* 238 */           if (j < rectangle.width) {
/*     */             
/* 240 */             int i1 = rectangle.width - 1 - j;
/*     */             
/* 242 */             int i2 = ((JTextField)container).getHorizontalAlignment();
/* 243 */             if (isLeftToRight(container)) {
/* 244 */               if (i2 == 10) {
/* 245 */                 i2 = 2;
/*     */               }
/* 247 */               else if (i2 == 11) {
/* 248 */                 i2 = 4;
/*     */               }
/*     */             
/*     */             }
/* 252 */             else if (i2 == 10) {
/* 253 */               i2 = 4;
/*     */             }
/* 255 */             else if (i2 == 11) {
/* 256 */               i2 = 2;
/*     */             } 
/*     */ 
/*     */             
/* 260 */             switch (i2) {
/*     */               case 0:
/* 262 */                 rectangle.x += i1 / 2;
/* 263 */                 rectangle.width -= i1;
/*     */                 break;
/*     */               case 4:
/* 266 */                 rectangle.x += i1;
/* 267 */                 rectangle.width -= i1;
/*     */                 break;
/*     */             } 
/*     */           
/*     */           } else {
/* 272 */             rectangle.width = j;
/* 273 */             rectangle.x -= boundedRangeModel.getValue();
/*     */           } 
/*     */         } 
/* 276 */         return rectangle;
/*     */       } 
/* 278 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void updateVisibilityModel() {
/* 290 */       Container container = getContainer();
/* 291 */       if (container instanceof JTextField) {
/* 292 */         JTextField jTextField = (JTextField)container;
/* 293 */         BoundedRangeModel boundedRangeModel = jTextField.getHorizontalVisibility();
/* 294 */         int i = (int)getPreferredSpan(0);
/* 295 */         int j = boundedRangeModel.getExtent();
/* 296 */         int k = Math.max(i, j);
/* 297 */         j = (j == 0) ? k : j;
/* 298 */         int m = k - j;
/* 299 */         int n = boundedRangeModel.getValue();
/* 300 */         if (n + j > k) {
/* 301 */           n = k - j;
/*     */         }
/* 303 */         m = Math.max(0, Math.min(m, n));
/* 304 */         boundedRangeModel.setRangeProperties(m, j, 0, k, false);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void paint(Graphics param1Graphics, Shape param1Shape) {
/* 321 */       Rectangle rectangle = (Rectangle)param1Shape;
/* 322 */       param1Graphics.clipRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/* 323 */       super.paint(param1Graphics, adjustAllocation(param1Shape));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getResizeWeight(int param1Int) {
/* 334 */       if (param1Int == 0) {
/* 335 */         return 1;
/*     */       }
/* 337 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Shape modelToView(int param1Int, Shape param1Shape, Position.Bias param1Bias) throws BadLocationException {
/* 352 */       return super.modelToView(param1Int, adjustAllocation(param1Shape), param1Bias);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Shape modelToView(int param1Int1, Position.Bias param1Bias1, int param1Int2, Position.Bias param1Bias2, Shape param1Shape) throws BadLocationException {
/* 378 */       return super.modelToView(param1Int1, param1Bias1, param1Int2, param1Bias2, adjustAllocation(param1Shape));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int viewToModel(float param1Float1, float param1Float2, Shape param1Shape, Position.Bias[] param1ArrayOfBias) {
/* 393 */       return super.viewToModel(param1Float1, param1Float2, adjustAllocation(param1Shape), param1ArrayOfBias);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void insertUpdate(DocumentEvent param1DocumentEvent, Shape param1Shape, ViewFactory param1ViewFactory) {
/* 406 */       super.insertUpdate(param1DocumentEvent, adjustAllocation(param1Shape), param1ViewFactory);
/* 407 */       updateVisibilityModel();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void removeUpdate(DocumentEvent param1DocumentEvent, Shape param1Shape, ViewFactory param1ViewFactory) {
/* 420 */       super.removeUpdate(param1DocumentEvent, adjustAllocation(param1Shape), param1ViewFactory);
/* 421 */       updateVisibilityModel();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicTextFieldUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */