/*     */ package javax.swing.text.html;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Toolkit;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.event.DocumentEvent;
/*     */ import javax.swing.event.DocumentListener;
/*     */ import javax.swing.text.AbstractDocument;
/*     */ import javax.swing.text.AttributeSet;
/*     */ import javax.swing.text.Document;
/*     */ import javax.swing.text.Element;
/*     */ import javax.swing.text.JTextComponent;
/*     */ import javax.swing.text.SimpleAttributeSet;
/*     */ import javax.swing.text.StyleConstants;
/*     */ import javax.swing.text.StyledDocument;
/*     */ import javax.swing.text.ViewFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class HiddenTagView
/*     */   extends EditableView
/*     */   implements DocumentListener
/*     */ {
/*     */   float yAlign;
/*     */   boolean isSettingAttributes;
/*     */   static final int circleR = 3;
/*     */   static final int circleD = 6;
/*     */   static final int tagSize = 6;
/*     */   static final int padding = 3;
/*     */   
/*     */   HiddenTagView(Element paramElement) {
/*  48 */     super(paramElement);
/*  49 */     this.yAlign = 1.0F;
/*     */   }
/*     */   protected Component createComponent() {
/*     */     Font font;
/*  53 */     JTextField jTextField = new JTextField(getElement().getName());
/*  54 */     Document document = getDocument();
/*     */     
/*  56 */     if (document instanceof StyledDocument) {
/*  57 */       font = ((StyledDocument)document).getFont(getAttributes());
/*  58 */       jTextField.setFont(font);
/*     */     } else {
/*     */       
/*  61 */       font = jTextField.getFont();
/*     */     } 
/*  63 */     jTextField.getDocument().addDocumentListener(this);
/*  64 */     updateYAlign(font);
/*     */ 
/*     */ 
/*     */     
/*  68 */     JPanel jPanel = new JPanel(new BorderLayout());
/*  69 */     jPanel.setBackground((Color)null);
/*  70 */     if (isEndTag()) {
/*  71 */       jPanel.setBorder(EndBorder);
/*     */     } else {
/*     */       
/*  74 */       jPanel.setBorder(StartBorder);
/*     */     } 
/*  76 */     jPanel.add(jTextField);
/*  77 */     return jPanel;
/*     */   }
/*     */   
/*     */   public float getAlignment(int paramInt) {
/*  81 */     if (paramInt == 1) {
/*  82 */       return this.yAlign;
/*     */     }
/*  84 */     return 0.5F;
/*     */   }
/*     */   
/*     */   public float getMinimumSpan(int paramInt) {
/*  88 */     if (paramInt == 0 && isVisible())
/*     */     {
/*  90 */       return Math.max(30.0F, super.getPreferredSpan(paramInt));
/*     */     }
/*  92 */     return super.getMinimumSpan(paramInt);
/*     */   }
/*     */   
/*     */   public float getPreferredSpan(int paramInt) {
/*  96 */     if (paramInt == 0 && isVisible()) {
/*  97 */       return Math.max(30.0F, super.getPreferredSpan(paramInt));
/*     */     }
/*  99 */     return super.getPreferredSpan(paramInt);
/*     */   }
/*     */   
/*     */   public float getMaximumSpan(int paramInt) {
/* 103 */     if (paramInt == 0 && isVisible())
/*     */     {
/* 105 */       return Math.max(30.0F, super.getMaximumSpan(paramInt));
/*     */     }
/* 107 */     return super.getMaximumSpan(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void insertUpdate(DocumentEvent paramDocumentEvent) {
/* 112 */     updateModelFromText();
/*     */   }
/*     */   
/*     */   public void removeUpdate(DocumentEvent paramDocumentEvent) {
/* 116 */     updateModelFromText();
/*     */   }
/*     */   
/*     */   public void changedUpdate(DocumentEvent paramDocumentEvent) {
/* 120 */     updateModelFromText();
/*     */   }
/*     */ 
/*     */   
/*     */   public void changedUpdate(DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/* 125 */     if (!this.isSettingAttributes) {
/* 126 */       setTextFromModel();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void updateYAlign(Font paramFont) {
/* 133 */     Container container = getContainer();
/*     */     
/* 135 */     FontMetrics fontMetrics = (container != null) ? container.getFontMetrics(paramFont) : Toolkit.getDefaultToolkit().getFontMetrics(paramFont);
/* 136 */     float f1 = fontMetrics.getHeight();
/* 137 */     float f2 = fontMetrics.getDescent();
/* 138 */     this.yAlign = (f1 > 0.0F) ? ((f1 - f2) / f1) : 0.0F;
/*     */   }
/*     */   
/*     */   void resetBorder() {
/* 142 */     Component component = getComponent();
/*     */     
/* 144 */     if (component != null) {
/* 145 */       if (isEndTag()) {
/* 146 */         ((JPanel)component).setBorder(EndBorder);
/*     */       } else {
/*     */         
/* 149 */         ((JPanel)component).setBorder(StartBorder);
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
/*     */   void setTextFromModel() {
/* 163 */     if (SwingUtilities.isEventDispatchThread()) {
/* 164 */       _setTextFromModel();
/*     */     } else {
/*     */       
/* 167 */       SwingUtilities.invokeLater(new Runnable() {
/*     */             public void run() {
/* 169 */               HiddenTagView.this._setTextFromModel();
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void _setTextFromModel() {
/* 180 */     Document document = getDocument();
/*     */     try {
/* 182 */       this.isSettingAttributes = true;
/* 183 */       if (document instanceof AbstractDocument) {
/* 184 */         ((AbstractDocument)document).readLock();
/*     */       }
/* 186 */       JTextComponent jTextComponent = getTextComponent();
/* 187 */       if (jTextComponent != null) {
/* 188 */         jTextComponent.setText(getRepresentedText());
/* 189 */         resetBorder();
/* 190 */         Container container = getContainer();
/* 191 */         if (container != null) {
/* 192 */           preferenceChanged(this, true, true);
/* 193 */           container.repaint();
/*     */         } 
/*     */       } 
/*     */     } finally {
/*     */       
/* 198 */       this.isSettingAttributes = false;
/* 199 */       if (document instanceof AbstractDocument) {
/* 200 */         ((AbstractDocument)document).readUnlock();
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
/*     */   void updateModelFromText() {
/* 214 */     if (!this.isSettingAttributes) {
/* 215 */       if (SwingUtilities.isEventDispatchThread()) {
/* 216 */         _updateModelFromText();
/*     */       } else {
/*     */         
/* 219 */         SwingUtilities.invokeLater(new Runnable() {
/*     */               public void run() {
/* 221 */                 HiddenTagView.this._updateModelFromText();
/*     */               }
/*     */             });
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void _updateModelFromText() {
/* 233 */     Document document = getDocument();
/*     */     
/* 235 */     Object object = getElement().getAttributes().getAttribute(StyleConstants.NameAttribute);
/* 236 */     if (object instanceof HTML.UnknownTag && document instanceof StyledDocument) {
/*     */       
/* 238 */       SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
/* 239 */       JTextComponent jTextComponent = getTextComponent();
/* 240 */       if (jTextComponent != null) {
/* 241 */         String str = jTextComponent.getText();
/* 242 */         this.isSettingAttributes = true;
/*     */         try {
/* 244 */           simpleAttributeSet.addAttribute(StyleConstants.NameAttribute, new HTML.UnknownTag(str));
/*     */           
/* 246 */           ((StyledDocument)document)
/* 247 */             .setCharacterAttributes(getStartOffset(), getEndOffset() - 
/* 248 */               getStartOffset(), simpleAttributeSet, false);
/*     */         } finally {
/*     */           
/* 251 */           this.isSettingAttributes = false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   JTextComponent getTextComponent() {
/* 258 */     Component component = getComponent();
/*     */     
/* 260 */     return (component == null) ? null : (JTextComponent)((Container)component)
/* 261 */       .getComponent(0);
/*     */   }
/*     */   
/*     */   String getRepresentedText() {
/* 265 */     String str = getElement().getName();
/* 266 */     return (str == null) ? "" : str;
/*     */   }
/*     */   
/*     */   boolean isEndTag() {
/* 270 */     AttributeSet attributeSet = getElement().getAttributes();
/* 271 */     if (attributeSet != null) {
/* 272 */       Object object = attributeSet.getAttribute(HTML.Attribute.ENDTAG);
/* 273 */       if (object != null && object instanceof String && ((String)object)
/* 274 */         .equals("true")) {
/* 275 */         return true;
/*     */       }
/*     */     } 
/* 278 */     return false;
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
/* 294 */   static final Color UnknownTagBorderColor = Color.black;
/* 295 */   static final Border StartBorder = new StartTagBorder();
/* 296 */   static final Border EndBorder = new EndTagBorder();
/*     */   
/*     */   static class StartTagBorder
/*     */     implements Border, Serializable
/*     */   {
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 302 */       param1Graphics.setColor(HiddenTagView.UnknownTagBorderColor);
/* 303 */       param1Int1 += 3;
/* 304 */       param1Int3 -= 6;
/* 305 */       param1Graphics.drawLine(param1Int1, param1Int2 + 3, param1Int1, param1Int2 + param1Int4 - 3);
/*     */       
/* 307 */       param1Graphics.drawArc(param1Int1, param1Int2 + param1Int4 - 6 - 1, 6, 6, 180, 90);
/*     */       
/* 309 */       param1Graphics.drawArc(param1Int1, param1Int2, 6, 6, 90, 90);
/* 310 */       param1Graphics.drawLine(param1Int1 + 3, param1Int2, param1Int1 + param1Int3 - 6, param1Int2);
/* 311 */       param1Graphics.drawLine(param1Int1 + 3, param1Int2 + param1Int4 - 1, param1Int1 + param1Int3 - 6, param1Int2 + param1Int4 - 1);
/*     */ 
/*     */       
/* 314 */       param1Graphics.drawLine(param1Int1 + param1Int3 - 6, param1Int2, param1Int1 + param1Int3 - 1, param1Int2 + param1Int4 / 2);
/*     */       
/* 316 */       param1Graphics.drawLine(param1Int1 + param1Int3 - 6, param1Int2 + param1Int4, param1Int1 + param1Int3 - 1, param1Int2 + param1Int4 / 2);
/*     */     }
/*     */ 
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component) {
/* 321 */       return new Insets(2, 5, 2, 11);
/*     */     }
/*     */     
/*     */     public boolean isBorderOpaque() {
/* 325 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   static class EndTagBorder
/*     */     implements Border, Serializable
/*     */   {
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 333 */       param1Graphics.setColor(HiddenTagView.UnknownTagBorderColor);
/* 334 */       param1Int1 += 3;
/* 335 */       param1Int3 -= 6;
/* 336 */       param1Graphics.drawLine(param1Int1 + param1Int3 - 1, param1Int2 + 3, param1Int1 + param1Int3 - 1, param1Int2 + param1Int4 - 3);
/*     */       
/* 338 */       param1Graphics.drawArc(param1Int1 + param1Int3 - 6 - 1, param1Int2 + param1Int4 - 6 - 1, 6, 6, 270, 90);
/*     */       
/* 340 */       param1Graphics.drawArc(param1Int1 + param1Int3 - 6 - 1, param1Int2, 6, 6, 0, 90);
/* 341 */       param1Graphics.drawLine(param1Int1 + 6, param1Int2, param1Int1 + param1Int3 - 3, param1Int2);
/* 342 */       param1Graphics.drawLine(param1Int1 + 6, param1Int2 + param1Int4 - 1, param1Int1 + param1Int3 - 3, param1Int2 + param1Int4 - 1);
/*     */ 
/*     */       
/* 345 */       param1Graphics.drawLine(param1Int1 + 6, param1Int2, param1Int1, param1Int2 + param1Int4 / 2);
/*     */       
/* 347 */       param1Graphics.drawLine(param1Int1 + 6, param1Int2 + param1Int4, param1Int1, param1Int2 + param1Int4 / 2);
/*     */     }
/*     */ 
/*     */     
/*     */     public Insets getBorderInsets(Component param1Component) {
/* 352 */       return new Insets(2, 11, 2, 5);
/*     */     }
/*     */     
/*     */     public boolean isBorderOpaque() {
/* 356 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/HiddenTagView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */