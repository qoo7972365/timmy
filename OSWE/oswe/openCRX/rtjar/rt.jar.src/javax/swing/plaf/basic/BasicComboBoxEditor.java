/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.FocusListener;
/*     */ import java.lang.reflect.Method;
/*     */ import javax.swing.ComboBoxEditor;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.border.Border;
/*     */ import sun.reflect.misc.MethodUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicComboBoxEditor
/*     */   implements ComboBoxEditor, FocusListener
/*     */ {
/*  48 */   protected JTextField editor = createEditorComponent();
/*     */   private Object oldValue;
/*     */   
/*     */   public Component getEditorComponent() {
/*  52 */     return this.editor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JTextField createEditorComponent() {
/*  63 */     BorderlessTextField borderlessTextField = new BorderlessTextField("", 9);
/*  64 */     borderlessTextField.setBorder(null);
/*  65 */     return borderlessTextField;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItem(Object paramObject) {
/*     */     String str;
/*  76 */     if (paramObject != null) {
/*  77 */       str = paramObject.toString();
/*  78 */       if (str == null) {
/*  79 */         str = "";
/*     */       }
/*  81 */       this.oldValue = paramObject;
/*     */     } else {
/*  83 */       str = "";
/*     */     } 
/*     */     
/*  86 */     if (!str.equals(this.editor.getText())) {
/*  87 */       this.editor.setText(str);
/*     */     }
/*     */   }
/*     */   
/*     */   public Object getItem() {
/*  92 */     Object object = this.editor.getText();
/*     */     
/*  94 */     if (this.oldValue != null && !(this.oldValue instanceof String)) {
/*     */ 
/*     */       
/*  97 */       if (object.equals(this.oldValue.toString())) {
/*  98 */         return this.oldValue;
/*     */       }
/*     */       
/* 101 */       Class<?> clazz = this.oldValue.getClass();
/*     */       try {
/* 103 */         Method method = MethodUtil.getMethod(clazz, "valueOf", new Class[] { String.class });
/* 104 */         object = MethodUtil.invoke(method, this.oldValue, new Object[] { this.editor.getText() });
/* 105 */       } catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 110 */     return object;
/*     */   }
/*     */   
/*     */   public void selectAll() {
/* 114 */     this.editor.selectAll();
/* 115 */     this.editor.requestFocus();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void focusGained(FocusEvent paramFocusEvent) {}
/*     */ 
/*     */   
/*     */   public void focusLost(FocusEvent paramFocusEvent) {}
/*     */ 
/*     */   
/*     */   public void addActionListener(ActionListener paramActionListener) {
/* 127 */     this.editor.addActionListener(paramActionListener);
/*     */   }
/*     */   
/*     */   public void removeActionListener(ActionListener paramActionListener) {
/* 131 */     this.editor.removeActionListener(paramActionListener);
/*     */   }
/*     */   
/*     */   static class BorderlessTextField extends JTextField {
/*     */     public BorderlessTextField(String param1String, int param1Int) {
/* 136 */       super(param1String, param1Int);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setText(String param1String) {
/* 141 */       if (getText().equals(param1String)) {
/*     */         return;
/*     */       }
/* 144 */       super.setText(param1String);
/*     */     }
/*     */     
/*     */     public void setBorder(Border param1Border) {
/* 148 */       if (!(param1Border instanceof BasicComboBoxEditor.UIResource))
/* 149 */         super.setBorder(param1Border); 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class UIResource extends BasicComboBoxEditor implements javax.swing.plaf.UIResource {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicComboBoxEditor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */