/*     */ package javax.swing.text.html;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLEncoder;
/*     */ import javax.swing.JEditorPane;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.text.AttributeSet;
/*     */ import javax.swing.text.ComponentView;
/*     */ import javax.swing.text.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class IsindexView
/*     */   extends ComponentView
/*     */   implements ActionListener
/*     */ {
/*     */   JTextField textField;
/*     */   
/*     */   public IsindexView(Element paramElement) {
/*  52 */     super(paramElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Component createComponent() {
/*  62 */     AttributeSet attributeSet = getElement().getAttributes();
/*     */     
/*  64 */     JPanel jPanel = new JPanel(new BorderLayout());
/*  65 */     jPanel.setBackground((Color)null);
/*     */     
/*  67 */     String str = (String)attributeSet.getAttribute(HTML.Attribute.PROMPT);
/*  68 */     if (str == null) {
/*  69 */       str = UIManager.getString("IsindexView.prompt");
/*     */     }
/*  71 */     JLabel jLabel = new JLabel(str);
/*     */     
/*  73 */     this.textField = new JTextField();
/*  74 */     this.textField.addActionListener(this);
/*  75 */     jPanel.add(jLabel, "West");
/*  76 */     jPanel.add(this.textField, "Center");
/*  77 */     jPanel.setAlignmentY(1.0F);
/*  78 */     jPanel.setOpaque(false);
/*  79 */     return jPanel;
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
/*     */   public void actionPerformed(ActionEvent paramActionEvent) {
/*  93 */     String str1 = this.textField.getText();
/*  94 */     if (str1 != null) {
/*  95 */       str1 = URLEncoder.encode(str1);
/*     */     }
/*     */ 
/*     */     
/*  99 */     AttributeSet attributeSet = getElement().getAttributes();
/* 100 */     HTMLDocument hTMLDocument = (HTMLDocument)getElement().getDocument();
/*     */     
/* 102 */     String str2 = (String)attributeSet.getAttribute(HTML.Attribute.ACTION);
/* 103 */     if (str2 == null) {
/* 104 */       str2 = hTMLDocument.getBase().toString();
/*     */     }
/*     */     
/* 107 */     try { URL uRL = new URL(str2 + "?" + str1);
/* 108 */       JEditorPane jEditorPane = (JEditorPane)getContainer();
/* 109 */       jEditorPane.setPage(uRL); }
/* 110 */     catch (MalformedURLException malformedURLException) {  }
/* 111 */     catch (IOException iOException) {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/IsindexView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */