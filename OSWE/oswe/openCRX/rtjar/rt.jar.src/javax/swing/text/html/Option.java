/*     */ package javax.swing.text.html;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javax.swing.text.AttributeSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Option
/*     */   implements Serializable
/*     */ {
/*     */   private boolean selected;
/*     */   private String label;
/*     */   private AttributeSet attr;
/*     */   
/*     */   public Option(AttributeSet paramAttributeSet) {
/*  57 */     this.attr = paramAttributeSet.copyAttributes();
/*  58 */     this.selected = (paramAttributeSet.getAttribute(HTML.Attribute.SELECTED) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLabel(String paramString) {
/*  65 */     this.label = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLabel() {
/*  72 */     return this.label;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeSet getAttributes() {
/*  79 */     return this.attr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  86 */     return this.label;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setSelection(boolean paramBoolean) {
/*  93 */     this.selected = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSelected() {
/* 100 */     return this.selected;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValue() {
/* 110 */     String str = (String)this.attr.getAttribute(HTML.Attribute.VALUE);
/* 111 */     if (str == null) {
/* 112 */       str = this.label;
/*     */     }
/* 114 */     return str;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/Option.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */