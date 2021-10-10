/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import javax.swing.text.AbstractDocument;
/*     */ import javax.swing.text.AttributeSet;
/*     */ import javax.swing.text.BadLocationException;
/*     */ import javax.swing.text.Caret;
/*     */ import javax.swing.text.Document;
/*     */ import javax.swing.text.EditorKit;
/*     */ import javax.swing.text.Element;
/*     */ import javax.swing.text.MutableAttributeSet;
/*     */ import javax.swing.text.Style;
/*     */ import javax.swing.text.StyleConstants;
/*     */ import javax.swing.text.StyledDocument;
/*     */ import javax.swing.text.StyledEditorKit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JTextPane
/*     */   extends JEditorPane
/*     */ {
/*     */   private static final String uiClassID = "TextPaneUI";
/*     */   
/*     */   public JTextPane() {
/*  91 */     EditorKit editorKit = createDefaultEditorKit();
/*  92 */     String str = editorKit.getContentType();
/*  93 */     if (str != null && 
/*  94 */       getEditorKitClassNameForContentType(str) == defaultEditorKitMap
/*  95 */       .get(str)) {
/*  96 */       setEditorKitForContentType(str, editorKit);
/*     */     }
/*  98 */     setEditorKit(editorKit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JTextPane(StyledDocument paramStyledDocument) {
/* 109 */     this();
/* 110 */     setStyledDocument(paramStyledDocument);
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
/*     */   public String getUIClassID() {
/* 122 */     return "TextPaneUI";
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
/*     */   public void setDocument(Document paramDocument) {
/* 135 */     if (paramDocument instanceof StyledDocument) {
/* 136 */       super.setDocument(paramDocument);
/*     */     } else {
/* 138 */       throw new IllegalArgumentException("Model must be StyledDocument");
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
/*     */   public void setStyledDocument(StyledDocument paramStyledDocument) {
/* 150 */     super.setDocument(paramStyledDocument);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StyledDocument getStyledDocument() {
/* 159 */     return (StyledDocument)getDocument();
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
/*     */   public void replaceSelection(String paramString) {
/* 175 */     replaceSelection(paramString, true);
/*     */   }
/*     */   
/*     */   private void replaceSelection(String paramString, boolean paramBoolean) {
/* 179 */     if (paramBoolean && !isEditable()) {
/* 180 */       UIManager.getLookAndFeel().provideErrorFeedback(this);
/*     */       return;
/*     */     } 
/* 183 */     StyledDocument styledDocument = getStyledDocument();
/* 184 */     if (styledDocument != null) {
/*     */       try {
/* 186 */         Caret caret = getCaret();
/* 187 */         boolean bool = saveComposedText(caret.getDot());
/* 188 */         int i = Math.min(caret.getDot(), caret.getMark());
/* 189 */         int j = Math.max(caret.getDot(), caret.getMark());
/* 190 */         AttributeSet attributeSet = getInputAttributes().copyAttributes();
/* 191 */         if (styledDocument instanceof AbstractDocument) {
/* 192 */           ((AbstractDocument)styledDocument).replace(i, j - i, paramString, attributeSet);
/*     */         } else {
/*     */           
/* 195 */           if (i != j) {
/* 196 */             styledDocument.remove(i, j - i);
/*     */           }
/* 198 */           if (paramString != null && paramString.length() > 0) {
/* 199 */             styledDocument.insertString(i, paramString, attributeSet);
/*     */           }
/*     */         } 
/* 202 */         if (bool) {
/* 203 */           restoreComposedText();
/*     */         }
/* 205 */       } catch (BadLocationException badLocationException) {
/* 206 */         UIManager.getLookAndFeel().provideErrorFeedback(this);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insertComponent(Component paramComponent) {
/* 236 */     MutableAttributeSet mutableAttributeSet = getInputAttributes();
/* 237 */     mutableAttributeSet.removeAttributes(mutableAttributeSet);
/* 238 */     StyleConstants.setComponent(mutableAttributeSet, paramComponent);
/* 239 */     replaceSelection(" ", false);
/* 240 */     mutableAttributeSet.removeAttributes(mutableAttributeSet);
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
/*     */   public void insertIcon(Icon paramIcon) {
/* 255 */     MutableAttributeSet mutableAttributeSet = getInputAttributes();
/* 256 */     mutableAttributeSet.removeAttributes(mutableAttributeSet);
/* 257 */     StyleConstants.setIcon(mutableAttributeSet, paramIcon);
/* 258 */     replaceSelection(" ", false);
/* 259 */     mutableAttributeSet.removeAttributes(mutableAttributeSet);
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
/*     */   public Style addStyle(String paramString, Style paramStyle) {
/* 280 */     StyledDocument styledDocument = getStyledDocument();
/* 281 */     return styledDocument.addStyle(paramString, paramStyle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeStyle(String paramString) {
/* 291 */     StyledDocument styledDocument = getStyledDocument();
/* 292 */     styledDocument.removeStyle(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style getStyle(String paramString) {
/* 302 */     StyledDocument styledDocument = getStyledDocument();
/* 303 */     return styledDocument.getStyle(paramString);
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
/*     */   public void setLogicalStyle(Style paramStyle) {
/* 318 */     StyledDocument styledDocument = getStyledDocument();
/* 319 */     styledDocument.setLogicalStyle(getCaretPosition(), paramStyle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Style getLogicalStyle() {
/* 329 */     StyledDocument styledDocument = getStyledDocument();
/* 330 */     return styledDocument.getLogicalStyle(getCaretPosition());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeSet getCharacterAttributes() {
/* 340 */     StyledDocument styledDocument = getStyledDocument();
/* 341 */     Element element = styledDocument.getCharacterElement(getCaretPosition());
/* 342 */     if (element != null) {
/* 343 */       return element.getAttributes();
/*     */     }
/* 345 */     return null;
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
/*     */   public void setCharacterAttributes(AttributeSet paramAttributeSet, boolean paramBoolean) {
/* 360 */     int i = getSelectionStart();
/* 361 */     int j = getSelectionEnd();
/* 362 */     if (i != j) {
/* 363 */       StyledDocument styledDocument = getStyledDocument();
/* 364 */       styledDocument.setCharacterAttributes(i, j - i, paramAttributeSet, paramBoolean);
/*     */     } else {
/* 366 */       MutableAttributeSet mutableAttributeSet = getInputAttributes();
/* 367 */       if (paramBoolean) {
/* 368 */         mutableAttributeSet.removeAttributes(mutableAttributeSet);
/*     */       }
/* 370 */       mutableAttributeSet.addAttributes(paramAttributeSet);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeSet getParagraphAttributes() {
/* 381 */     StyledDocument styledDocument = getStyledDocument();
/* 382 */     Element element = styledDocument.getParagraphElement(getCaretPosition());
/* 383 */     if (element != null) {
/* 384 */       return element.getAttributes();
/*     */     }
/* 386 */     return null;
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
/*     */   public void setParagraphAttributes(AttributeSet paramAttributeSet, boolean paramBoolean) {
/* 400 */     int i = getSelectionStart();
/* 401 */     int j = getSelectionEnd();
/* 402 */     StyledDocument styledDocument = getStyledDocument();
/* 403 */     styledDocument.setParagraphAttributes(i, j - i, paramAttributeSet, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MutableAttributeSet getInputAttributes() {
/* 412 */     return getStyledEditorKit().getInputAttributes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final StyledEditorKit getStyledEditorKit() {
/* 421 */     return (StyledEditorKit)getEditorKit();
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 439 */     paramObjectOutputStream.defaultWriteObject();
/* 440 */     if (getUIClassID().equals("TextPaneUI")) {
/* 441 */       byte b = JComponent.getWriteObjCounter(this);
/* 442 */       b = (byte)(b - 1); JComponent.setWriteObjCounter(this, b);
/* 443 */       if (b == 0 && this.ui != null) {
/* 444 */         this.ui.installUI(this);
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
/*     */   protected EditorKit createDefaultEditorKit() {
/* 459 */     return new StyledEditorKit();
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
/*     */   public final void setEditorKit(EditorKit paramEditorKit) {
/* 472 */     if (paramEditorKit instanceof StyledEditorKit) {
/* 473 */       super.setEditorKit(paramEditorKit);
/*     */     } else {
/* 475 */       throw new IllegalArgumentException("Must be StyledEditorKit");
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
/*     */   protected String paramString() {
/* 490 */     return super.paramString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JTextPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */