/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.ActionMap;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JEditorPane;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.TransferHandler;
/*     */ import javax.swing.plaf.ActionMapUIResource;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.UIResource;
/*     */ import javax.swing.text.Document;
/*     */ import javax.swing.text.EditorKit;
/*     */ import javax.swing.text.JTextComponent;
/*     */ import javax.swing.text.Style;
/*     */ import javax.swing.text.StyleConstants;
/*     */ import javax.swing.text.StyledDocument;
/*     */ import javax.swing.text.html.HTMLDocument;
/*     */ import javax.swing.text.html.StyleSheet;
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
/*     */ public class BasicEditorPaneUI
/*     */   extends BasicTextUI
/*     */ {
/*     */   private static final String FONT_ATTRIBUTE_KEY = "FONT_ATTRIBUTE_KEY";
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  62 */     return new BasicEditorPaneUI();
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
/*  80 */     return "EditorPane";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/*  89 */     super.installUI(paramJComponent);
/*  90 */     updateDisplayProperties(paramJComponent.getFont(), paramJComponent
/*  91 */         .getForeground());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/* 100 */     cleanDisplayProperties();
/* 101 */     super.uninstallUI(paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EditorKit getEditorKit(JTextComponent paramJTextComponent) {
/* 112 */     JEditorPane jEditorPane = (JEditorPane)getComponent();
/* 113 */     return jEditorPane.getEditorKit();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ActionMap getActionMap() {
/* 121 */     ActionMapUIResource actionMapUIResource = new ActionMapUIResource();
/* 122 */     actionMapUIResource.put("requestFocus", new BasicTextUI.FocusAction(this));
/* 123 */     EditorKit editorKit = getEditorKit(getComponent());
/* 124 */     if (editorKit != null) {
/* 125 */       Action[] arrayOfAction = editorKit.getActions();
/* 126 */       if (arrayOfAction != null) {
/* 127 */         addActions(actionMapUIResource, arrayOfAction);
/*     */       }
/*     */     } 
/* 130 */     actionMapUIResource.put(TransferHandler.getCutAction().getValue("Name"), 
/* 131 */         TransferHandler.getCutAction());
/* 132 */     actionMapUIResource.put(TransferHandler.getCopyAction().getValue("Name"), 
/* 133 */         TransferHandler.getCopyAction());
/* 134 */     actionMapUIResource.put(TransferHandler.getPasteAction().getValue("Name"), 
/* 135 */         TransferHandler.getPasteAction());
/* 136 */     return actionMapUIResource;
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
/*     */   protected void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 150 */     super.propertyChange(paramPropertyChangeEvent);
/* 151 */     String str = paramPropertyChangeEvent.getPropertyName();
/* 152 */     if ("editorKit".equals(str)) {
/* 153 */       ActionMap actionMap = SwingUtilities.getUIActionMap(getComponent());
/* 154 */       if (actionMap != null) {
/* 155 */         Object object1 = paramPropertyChangeEvent.getOldValue();
/* 156 */         if (object1 instanceof EditorKit) {
/* 157 */           Action[] arrayOfAction = ((EditorKit)object1).getActions();
/* 158 */           if (arrayOfAction != null) {
/* 159 */             removeActions(actionMap, arrayOfAction);
/*     */           }
/*     */         } 
/* 162 */         Object object2 = paramPropertyChangeEvent.getNewValue();
/* 163 */         if (object2 instanceof EditorKit) {
/* 164 */           Action[] arrayOfAction = ((EditorKit)object2).getActions();
/* 165 */           if (arrayOfAction != null) {
/* 166 */             addActions(actionMap, arrayOfAction);
/*     */           }
/*     */         } 
/*     */       } 
/* 170 */       updateFocusTraversalKeys();
/* 171 */     } else if ("editable".equals(str)) {
/* 172 */       updateFocusTraversalKeys();
/* 173 */     } else if ("foreground".equals(str) || "font"
/* 174 */       .equals(str) || "document"
/* 175 */       .equals(str) || "JEditorPane.w3cLengthUnits"
/* 176 */       .equals(str) || "JEditorPane.honorDisplayProperties"
/* 177 */       .equals(str)) {
/*     */       
/* 179 */       JTextComponent jTextComponent = getComponent();
/* 180 */       updateDisplayProperties(jTextComponent.getFont(), jTextComponent.getForeground());
/* 181 */       if ("JEditorPane.w3cLengthUnits".equals(str) || "JEditorPane.honorDisplayProperties"
/* 182 */         .equals(str)) {
/* 183 */         modelChanged();
/*     */       }
/* 185 */       if ("foreground".equals(str)) {
/*     */         
/* 187 */         Object object = jTextComponent.getClientProperty("JEditorPane.honorDisplayProperties");
/* 188 */         boolean bool = false;
/* 189 */         if (object instanceof Boolean)
/*     */         {
/* 191 */           bool = ((Boolean)object).booleanValue();
/*     */         }
/* 193 */         if (bool) {
/* 194 */           modelChanged();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void removeActions(ActionMap paramActionMap, Action[] paramArrayOfAction) {
/* 203 */     int i = paramArrayOfAction.length;
/* 204 */     for (byte b = 0; b < i; b++) {
/* 205 */       Action action = paramArrayOfAction[b];
/* 206 */       paramActionMap.remove(action.getValue("Name"));
/*     */     } 
/*     */   }
/*     */   
/*     */   void addActions(ActionMap paramActionMap, Action[] paramArrayOfAction) {
/* 211 */     int i = paramArrayOfAction.length;
/* 212 */     for (byte b = 0; b < i; b++) {
/* 213 */       Action action = paramArrayOfAction[b];
/* 214 */       paramActionMap.put(action.getValue("Name"), action);
/*     */     } 
/*     */   }
/*     */   
/*     */   void updateDisplayProperties(Font paramFont, Color paramColor) {
/* 219 */     JTextComponent jTextComponent = getComponent();
/*     */     
/* 221 */     Object object1 = jTextComponent.getClientProperty("JEditorPane.honorDisplayProperties");
/* 222 */     boolean bool1 = false;
/* 223 */     Object object2 = jTextComponent.getClientProperty("JEditorPane.w3cLengthUnits");
/*     */     
/* 225 */     boolean bool2 = false;
/* 226 */     if (object1 instanceof Boolean)
/*     */     {
/* 228 */       bool1 = ((Boolean)object1).booleanValue();
/*     */     }
/* 230 */     if (object2 instanceof Boolean) {
/* 231 */       bool2 = ((Boolean)object2).booleanValue();
/*     */     }
/* 233 */     if (this instanceof BasicTextPaneUI || bool1) {
/*     */ 
/*     */       
/* 236 */       Document document = getComponent().getDocument();
/* 237 */       if (document instanceof StyledDocument) {
/* 238 */         if (document instanceof HTMLDocument && bool1) {
/*     */           
/* 240 */           updateCSS(paramFont, paramColor);
/*     */         } else {
/* 242 */           updateStyle(paramFont, paramColor);
/*     */         } 
/*     */       }
/*     */     } else {
/* 246 */       cleanDisplayProperties();
/*     */     } 
/* 248 */     if (bool2) {
/* 249 */       Document document = getComponent().getDocument();
/* 250 */       if (document instanceof HTMLDocument) {
/*     */         
/* 252 */         StyleSheet styleSheet = ((HTMLDocument)document).getStyleSheet();
/* 253 */         styleSheet.addRule("W3C_LENGTH_UNITS_ENABLE");
/*     */       } 
/*     */     } else {
/* 256 */       Document document = getComponent().getDocument();
/* 257 */       if (document instanceof HTMLDocument) {
/*     */         
/* 259 */         StyleSheet styleSheet = ((HTMLDocument)document).getStyleSheet();
/* 260 */         styleSheet.addRule("W3C_LENGTH_UNITS_DISABLE");
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
/*     */   void cleanDisplayProperties() {
/* 274 */     Document document = getComponent().getDocument();
/* 275 */     if (document instanceof HTMLDocument) {
/*     */       
/* 277 */       StyleSheet styleSheet = ((HTMLDocument)document).getStyleSheet();
/* 278 */       StyleSheet[] arrayOfStyleSheet = styleSheet.getStyleSheets();
/* 279 */       if (arrayOfStyleSheet != null) {
/* 280 */         for (StyleSheet styleSheet1 : arrayOfStyleSheet) {
/* 281 */           if (styleSheet1 instanceof StyleSheetUIResource) {
/* 282 */             styleSheet.removeStyleSheet(styleSheet1);
/* 283 */             styleSheet.addRule("BASE_SIZE_DISABLE");
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/* 288 */       Style style = ((StyledDocument)document).getStyle("default");
/* 289 */       if (style.getAttribute("FONT_ATTRIBUTE_KEY") != null)
/* 290 */         style.removeAttribute("FONT_ATTRIBUTE_KEY"); 
/*     */     } 
/*     */   }
/*     */   
/*     */   static class StyleSheetUIResource
/*     */     extends StyleSheet
/*     */     implements UIResource {}
/*     */   
/*     */   private void updateCSS(Font paramFont, Color paramColor) {
/* 299 */     JTextComponent jTextComponent = getComponent();
/* 300 */     Document document = jTextComponent.getDocument();
/* 301 */     if (document instanceof HTMLDocument) {
/* 302 */       StyleSheetUIResource styleSheetUIResource = new StyleSheetUIResource();
/*     */       
/* 304 */       StyleSheet styleSheet = ((HTMLDocument)document).getStyleSheet();
/* 305 */       StyleSheet[] arrayOfStyleSheet = styleSheet.getStyleSheets();
/* 306 */       if (arrayOfStyleSheet != null) {
/* 307 */         for (StyleSheet styleSheet1 : arrayOfStyleSheet) {
/* 308 */           if (styleSheet1 instanceof StyleSheetUIResource) {
/* 309 */             styleSheet.removeStyleSheet(styleSheet1);
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 314 */       String str = SwingUtilities2.displayPropertiesToCSS(paramFont, paramColor);
/*     */       
/* 316 */       styleSheetUIResource.addRule(str);
/* 317 */       styleSheet.addStyleSheet(styleSheetUIResource);
/* 318 */       styleSheet.addRule("BASE_SIZE " + jTextComponent
/* 319 */           .getFont().getSize());
/* 320 */       Style style = ((StyledDocument)document).getStyle("default");
/* 321 */       if (!paramFont.equals(style.getAttribute("FONT_ATTRIBUTE_KEY"))) {
/* 322 */         style.addAttribute("FONT_ATTRIBUTE_KEY", paramFont);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateStyle(Font paramFont, Color paramColor) {
/* 328 */     updateFont(paramFont);
/* 329 */     updateForeground(paramColor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateForeground(Color paramColor) {
/* 339 */     StyledDocument styledDocument = (StyledDocument)getComponent().getDocument();
/* 340 */     Style style = styledDocument.getStyle("default");
/*     */     
/* 342 */     if (style == null) {
/*     */       return;
/*     */     }
/*     */     
/* 346 */     if (paramColor == null) {
/* 347 */       if (style.getAttribute(StyleConstants.Foreground) != null) {
/* 348 */         style.removeAttribute(StyleConstants.Foreground);
/*     */       }
/*     */     }
/* 351 */     else if (!paramColor.equals(StyleConstants.getForeground(style))) {
/* 352 */       StyleConstants.setForeground(style, paramColor);
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
/*     */   private void updateFont(Font paramFont) {
/* 364 */     StyledDocument styledDocument = (StyledDocument)getComponent().getDocument();
/* 365 */     Style style = styledDocument.getStyle("default");
/*     */     
/* 367 */     if (style == null) {
/*     */       return;
/*     */     }
/*     */     
/* 371 */     String str = (String)style.getAttribute(StyleConstants.FontFamily);
/* 372 */     Integer integer = (Integer)style.getAttribute(StyleConstants.FontSize);
/* 373 */     Boolean bool1 = (Boolean)style.getAttribute(StyleConstants.Bold);
/* 374 */     Boolean bool2 = (Boolean)style.getAttribute(StyleConstants.Italic);
/* 375 */     Font font = (Font)style.getAttribute("FONT_ATTRIBUTE_KEY");
/* 376 */     if (paramFont == null) {
/* 377 */       if (str != null) {
/* 378 */         style.removeAttribute(StyleConstants.FontFamily);
/*     */       }
/* 380 */       if (integer != null) {
/* 381 */         style.removeAttribute(StyleConstants.FontSize);
/*     */       }
/* 383 */       if (bool1 != null) {
/* 384 */         style.removeAttribute(StyleConstants.Bold);
/*     */       }
/* 386 */       if (bool2 != null) {
/* 387 */         style.removeAttribute(StyleConstants.Italic);
/*     */       }
/* 389 */       if (font != null) {
/* 390 */         style.removeAttribute("FONT_ATTRIBUTE_KEY");
/*     */       }
/*     */     } else {
/* 393 */       if (!paramFont.getName().equals(str)) {
/* 394 */         StyleConstants.setFontFamily(style, paramFont.getName());
/*     */       }
/* 396 */       if (integer == null || integer
/* 397 */         .intValue() != paramFont.getSize()) {
/* 398 */         StyleConstants.setFontSize(style, paramFont.getSize());
/*     */       }
/* 400 */       if (bool1 == null || bool1
/* 401 */         .booleanValue() != paramFont.isBold()) {
/* 402 */         StyleConstants.setBold(style, paramFont.isBold());
/*     */       }
/* 404 */       if (bool2 == null || bool2
/* 405 */         .booleanValue() != paramFont.isItalic()) {
/* 406 */         StyleConstants.setItalic(style, paramFont.isItalic());
/*     */       }
/* 408 */       if (!paramFont.equals(font))
/* 409 */         style.addAttribute("FONT_ATTRIBUTE_KEY", paramFont); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicEditorPaneUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */