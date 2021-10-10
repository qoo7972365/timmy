/*     */ package javax.swing.text.html;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Shape;
/*     */ import javax.swing.event.DocumentEvent;
/*     */ import javax.swing.text.AttributeSet;
/*     */ import javax.swing.text.Element;
/*     */ import javax.swing.text.LabelView;
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
/*     */ public class InlineView
/*     */   extends LabelView
/*     */ {
/*     */   private boolean nowrap;
/*     */   private AttributeSet attr;
/*     */   
/*     */   public InlineView(Element paramElement) {
/*  46 */     super(paramElement);
/*  47 */     StyleSheet styleSheet = getStyleSheet();
/*  48 */     this.attr = styleSheet.getViewAttributes(this);
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
/*     */   public void insertUpdate(DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/*  64 */     super.insertUpdate(paramDocumentEvent, paramShape, paramViewFactory);
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
/*     */   public void removeUpdate(DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/*  80 */     super.removeUpdate(paramDocumentEvent, paramShape, paramViewFactory);
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
/*     */   public void changedUpdate(DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/*  93 */     super.changedUpdate(paramDocumentEvent, paramShape, paramViewFactory);
/*  94 */     StyleSheet styleSheet = getStyleSheet();
/*  95 */     this.attr = styleSheet.getViewAttributes(this);
/*  96 */     preferenceChanged(null, true, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeSet getAttributes() {
/* 105 */     return this.attr;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBreakWeight(int paramInt, float paramFloat1, float paramFloat2) {
/* 147 */     if (this.nowrap) {
/* 148 */       return 0;
/*     */     }
/* 150 */     return super.getBreakWeight(paramInt, paramFloat1, paramFloat2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public View breakView(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2) {
/* 179 */     return super.breakView(paramInt1, paramInt2, paramFloat1, paramFloat2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setPropertiesFromAttributes() {
/* 187 */     super.setPropertiesFromAttributes();
/* 188 */     AttributeSet attributeSet = getAttributes();
/* 189 */     Object object1 = attributeSet.getAttribute(CSS.Attribute.TEXT_DECORATION);
/*     */     
/* 191 */     boolean bool1 = (object1 != null) ? ((object1.toString().indexOf("underline") >= 0) ? true : false) : false;
/* 192 */     setUnderline(bool1);
/*     */     
/* 194 */     boolean bool2 = (object1 != null) ? ((object1.toString().indexOf("line-through") >= 0) ? true : false) : false;
/* 195 */     setStrikeThrough(bool2);
/* 196 */     Object object2 = attributeSet.getAttribute(CSS.Attribute.VERTICAL_ALIGN);
/* 197 */     bool2 = (object2 != null) ? ((object2.toString().indexOf("sup") >= 0) ? true : false) : false;
/* 198 */     setSuperscript(bool2);
/* 199 */     bool2 = (object2 != null) ? ((object2.toString().indexOf("sub") >= 0) ? true : false) : false;
/* 200 */     setSubscript(bool2);
/*     */     
/* 202 */     Object object3 = attributeSet.getAttribute(CSS.Attribute.WHITE_SPACE);
/* 203 */     if (object3 != null && object3.equals("nowrap")) {
/* 204 */       this.nowrap = true;
/*     */     } else {
/* 206 */       this.nowrap = false;
/*     */     } 
/*     */     
/* 209 */     HTMLDocument hTMLDocument = (HTMLDocument)getDocument();
/*     */     
/* 211 */     Color color = hTMLDocument.getBackground(attributeSet);
/* 212 */     if (color != null) {
/* 213 */       setBackground(color);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected StyleSheet getStyleSheet() {
/* 219 */     HTMLDocument hTMLDocument = (HTMLDocument)getDocument();
/* 220 */     return hTMLDocument.getStyleSheet();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/InlineView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */