/*     */ package javax.swing.text.html;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.border.LineBorder;
/*     */ import javax.swing.text.AttributeSet;
/*     */ import javax.swing.text.Document;
/*     */ import javax.swing.text.Element;
/*     */ import javax.swing.text.JTextComponent;
/*     */ import javax.swing.text.SimpleAttributeSet;
/*     */ import javax.swing.text.StyledDocument;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class CommentView
/*     */   extends HiddenTagView
/*     */ {
/*     */   CommentView(Element paramElement) {
/*  48 */     super(paramElement);
/*     */   }
/*     */   protected Component createComponent() {
/*     */     Font font;
/*  52 */     Container container = getContainer();
/*  53 */     if (container != null && !((JTextComponent)container).isEditable()) {
/*  54 */       return null;
/*     */     }
/*  56 */     JTextArea jTextArea = new JTextArea(getRepresentedText());
/*  57 */     Document document = getDocument();
/*     */     
/*  59 */     if (document instanceof StyledDocument) {
/*  60 */       font = ((StyledDocument)document).getFont(getAttributes());
/*  61 */       jTextArea.setFont(font);
/*     */     } else {
/*     */       
/*  64 */       font = jTextArea.getFont();
/*     */     } 
/*  66 */     updateYAlign(font);
/*  67 */     jTextArea.setBorder(CBorder);
/*  68 */     jTextArea.getDocument().addDocumentListener(this);
/*  69 */     jTextArea.setFocusable(isVisible());
/*  70 */     return jTextArea;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void resetBorder() {}
/*     */ 
/*     */ 
/*     */   
/*     */   void _updateModelFromText() {
/*  81 */     JTextComponent jTextComponent = getTextComponent();
/*  82 */     Document document = getDocument();
/*  83 */     if (jTextComponent != null && document != null) {
/*  84 */       String str = jTextComponent.getText();
/*  85 */       SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
/*  86 */       this.isSettingAttributes = true;
/*     */       try {
/*  88 */         simpleAttributeSet.addAttribute(HTML.Attribute.COMMENT, str);
/*  89 */         ((StyledDocument)document)
/*  90 */           .setCharacterAttributes(getStartOffset(), getEndOffset() - 
/*  91 */             getStartOffset(), simpleAttributeSet, false);
/*     */       } finally {
/*     */         
/*  94 */         this.isSettingAttributes = false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   JTextComponent getTextComponent() {
/* 100 */     return (JTextComponent)getComponent();
/*     */   }
/*     */   
/*     */   String getRepresentedText() {
/* 104 */     AttributeSet attributeSet = getElement().getAttributes();
/* 105 */     if (attributeSet != null) {
/* 106 */       Object object = attributeSet.getAttribute(HTML.Attribute.COMMENT);
/* 107 */       if (object instanceof String) {
/* 108 */         return (String)object;
/*     */       }
/*     */     } 
/* 111 */     return "";
/*     */   }
/*     */   
/* 114 */   static final Border CBorder = new CommentBorder();
/*     */   static final int commentPadding = 3;
/*     */   static final int commentPaddingD = 9;
/*     */   
/*     */   static class CommentBorder extends LineBorder {
/*     */     CommentBorder() {
/* 120 */       super(Color.black, 1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 125 */       super.paintBorder(param1Component, param1Graphics, param1Int1 + 3, param1Int2, param1Int3 - 9, param1Int4);
/*     */     }
/*     */ 
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component, Insets param1Insets) {
/* 130 */       Insets insets = super.getBorderInsets(param1Component, param1Insets);
/*     */       
/* 132 */       insets.left += 3;
/* 133 */       insets.right += 3;
/* 134 */       return insets;
/*     */     }
/*     */     
/*     */     public boolean isBorderOpaque() {
/* 138 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/CommentView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */