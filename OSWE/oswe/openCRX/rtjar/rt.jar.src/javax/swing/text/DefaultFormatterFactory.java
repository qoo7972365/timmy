/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javax.swing.JFormattedTextField;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultFormatterFactory
/*     */   extends JFormattedTextField.AbstractFormatterFactory
/*     */   implements Serializable
/*     */ {
/*     */   private JFormattedTextField.AbstractFormatter defaultFormat;
/*     */   private JFormattedTextField.AbstractFormatter displayFormat;
/*     */   private JFormattedTextField.AbstractFormatter editFormat;
/*     */   private JFormattedTextField.AbstractFormatter nullFormat;
/*     */   
/*     */   public DefaultFormatterFactory() {}
/*     */   
/*     */   public DefaultFormatterFactory(JFormattedTextField.AbstractFormatter paramAbstractFormatter) {
/* 113 */     this(paramAbstractFormatter, null);
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
/*     */   public DefaultFormatterFactory(JFormattedTextField.AbstractFormatter paramAbstractFormatter1, JFormattedTextField.AbstractFormatter paramAbstractFormatter2) {
/* 130 */     this(paramAbstractFormatter1, paramAbstractFormatter2, null);
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
/*     */   public DefaultFormatterFactory(JFormattedTextField.AbstractFormatter paramAbstractFormatter1, JFormattedTextField.AbstractFormatter paramAbstractFormatter2, JFormattedTextField.AbstractFormatter paramAbstractFormatter3) {
/* 150 */     this(paramAbstractFormatter1, paramAbstractFormatter2, paramAbstractFormatter3, null);
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
/*     */   public DefaultFormatterFactory(JFormattedTextField.AbstractFormatter paramAbstractFormatter1, JFormattedTextField.AbstractFormatter paramAbstractFormatter2, JFormattedTextField.AbstractFormatter paramAbstractFormatter3, JFormattedTextField.AbstractFormatter paramAbstractFormatter4) {
/* 173 */     this.defaultFormat = paramAbstractFormatter1;
/* 174 */     this.displayFormat = paramAbstractFormatter2;
/* 175 */     this.editFormat = paramAbstractFormatter3;
/* 176 */     this.nullFormat = paramAbstractFormatter4;
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
/*     */   public void setDefaultFormatter(JFormattedTextField.AbstractFormatter paramAbstractFormatter) {
/* 189 */     this.defaultFormat = paramAbstractFormatter;
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
/*     */   public JFormattedTextField.AbstractFormatter getDefaultFormatter() {
/* 202 */     return this.defaultFormat;
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
/*     */   public void setDisplayFormatter(JFormattedTextField.AbstractFormatter paramAbstractFormatter) {
/* 215 */     this.displayFormat = paramAbstractFormatter;
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
/*     */   public JFormattedTextField.AbstractFormatter getDisplayFormatter() {
/* 228 */     return this.displayFormat;
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
/*     */   public void setEditFormatter(JFormattedTextField.AbstractFormatter paramAbstractFormatter) {
/* 241 */     this.editFormat = paramAbstractFormatter;
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
/*     */   public JFormattedTextField.AbstractFormatter getEditFormatter() {
/* 254 */     return this.editFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNullFormatter(JFormattedTextField.AbstractFormatter paramAbstractFormatter) {
/* 265 */     this.nullFormat = paramAbstractFormatter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JFormattedTextField.AbstractFormatter getNullFormatter() {
/* 275 */     return this.nullFormat;
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
/*     */   public JFormattedTextField.AbstractFormatter getFormatter(JFormattedTextField paramJFormattedTextField) {
/* 290 */     JFormattedTextField.AbstractFormatter abstractFormatter = null;
/*     */     
/* 292 */     if (paramJFormattedTextField == null) {
/* 293 */       return null;
/*     */     }
/* 295 */     Object object = paramJFormattedTextField.getValue();
/*     */     
/* 297 */     if (object == null) {
/* 298 */       abstractFormatter = getNullFormatter();
/*     */     }
/* 300 */     if (abstractFormatter == null) {
/* 301 */       if (paramJFormattedTextField.hasFocus()) {
/* 302 */         abstractFormatter = getEditFormatter();
/*     */       } else {
/*     */         
/* 305 */         abstractFormatter = getDisplayFormatter();
/*     */       } 
/* 307 */       if (abstractFormatter == null) {
/* 308 */         abstractFormatter = getDefaultFormatter();
/*     */       }
/*     */     } 
/* 311 */     return abstractFormatter;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/DefaultFormatterFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */