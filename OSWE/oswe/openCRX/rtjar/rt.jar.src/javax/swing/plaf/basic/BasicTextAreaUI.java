/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.SizeRequirements;
/*     */ import javax.swing.event.DocumentEvent;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.text.BoxView;
/*     */ import javax.swing.text.CompositeView;
/*     */ import javax.swing.text.Document;
/*     */ import javax.swing.text.Element;
/*     */ import javax.swing.text.GlyphView;
/*     */ import javax.swing.text.JTextComponent;
/*     */ import javax.swing.text.ParagraphView;
/*     */ import javax.swing.text.PlainView;
/*     */ import javax.swing.text.View;
/*     */ import javax.swing.text.ViewFactory;
/*     */ import javax.swing.text.WrappedPlainView;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicTextAreaUI
/*     */   extends BasicTextUI
/*     */ {
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  61 */     return new BasicTextAreaUI();
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
/*  79 */     return "TextArea";
/*     */   }
/*     */   
/*     */   protected void installDefaults() {
/*  83 */     super.installDefaults();
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
/*     */   protected void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/*  98 */     super.propertyChange(paramPropertyChangeEvent);
/*  99 */     if (paramPropertyChangeEvent.getPropertyName().equals("lineWrap") || paramPropertyChangeEvent
/* 100 */       .getPropertyName().equals("wrapStyleWord") || paramPropertyChangeEvent
/* 101 */       .getPropertyName().equals("tabSize")) {
/*     */       
/* 103 */       modelChanged();
/* 104 */     } else if ("editable".equals(paramPropertyChangeEvent.getPropertyName())) {
/* 105 */       updateFocusTraversalKeys();
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
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 120 */     return super.getPreferredSize(paramJComponent);
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
/*     */   public Dimension getMinimumSize(JComponent paramJComponent) {
/* 134 */     return super.getMinimumSize(paramJComponent);
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
/*     */   public View create(Element paramElement) {
/* 146 */     Document document = paramElement.getDocument();
/* 147 */     Object object = document.getProperty("i18n");
/* 148 */     if (object != null && object.equals(Boolean.TRUE))
/*     */     {
/* 150 */       return createI18N(paramElement);
/*     */     }
/* 152 */     JTextComponent jTextComponent = getComponent();
/* 153 */     if (jTextComponent instanceof JTextArea) {
/* 154 */       PlainView plainView; JTextArea jTextArea = (JTextArea)jTextComponent;
/*     */       
/* 156 */       if (jTextArea.getLineWrap()) {
/* 157 */         WrappedPlainView wrappedPlainView = new WrappedPlainView(paramElement, jTextArea.getWrapStyleWord());
/*     */       } else {
/* 159 */         plainView = new PlainView(paramElement);
/*     */       } 
/* 161 */       return plainView;
/*     */     } 
/*     */     
/* 164 */     return null;
/*     */   }
/*     */   
/*     */   View createI18N(Element paramElement) {
/* 168 */     String str = paramElement.getName();
/* 169 */     if (str != null) {
/* 170 */       if (str.equals("content"))
/* 171 */         return new PlainParagraph(paramElement); 
/* 172 */       if (str.equals("paragraph")) {
/* 173 */         return new BoxView(paramElement, 1);
/*     */       }
/*     */     } 
/* 176 */     return null;
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
/* 188 */     super.getBaseline(paramJComponent, paramInt1, paramInt2);
/*     */     
/* 190 */     Object object = ((JTextComponent)paramJComponent).getDocument().getProperty("i18n");
/* 191 */     Insets insets = paramJComponent.getInsets();
/* 192 */     if (Boolean.TRUE.equals(object)) {
/* 193 */       View view = getRootView((JTextComponent)paramJComponent);
/* 194 */       if (view.getViewCount() > 0) {
/* 195 */         paramInt2 = paramInt2 - insets.top - insets.bottom;
/* 196 */         int i = insets.top;
/* 197 */         int j = BasicHTML.getBaseline(view
/* 198 */             .getView(0), paramInt1 - insets.left - insets.right, paramInt2);
/*     */         
/* 200 */         if (j < 0) {
/* 201 */           return -1;
/*     */         }
/* 203 */         return i + j;
/*     */       } 
/* 205 */       return -1;
/*     */     } 
/* 207 */     FontMetrics fontMetrics = paramJComponent.getFontMetrics(paramJComponent.getFont());
/* 208 */     return insets.top + fontMetrics.getAscent();
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
/* 221 */     super.getBaselineResizeBehavior(paramJComponent);
/* 222 */     return Component.BaselineResizeBehavior.CONSTANT_ASCENT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class PlainParagraph
/*     */     extends ParagraphView
/*     */   {
/*     */     PlainParagraph(Element param1Element) {
/* 233 */       super(param1Element);
/* 234 */       this.layoutPool = new LogicalView(param1Element);
/* 235 */       this.layoutPool.setParent(this);
/*     */     }
/*     */     
/*     */     public void setParent(View param1View) {
/* 239 */       super.setParent(param1View);
/* 240 */       if (param1View != null) {
/* 241 */         setPropertiesFromAttributes();
/*     */       }
/*     */     }
/*     */     
/*     */     protected void setPropertiesFromAttributes() {
/* 246 */       Container container = getContainer();
/* 247 */       if (container != null && !container.getComponentOrientation().isLeftToRight()) {
/* 248 */         setJustification(2);
/*     */       } else {
/* 250 */         setJustification(0);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getFlowSpan(int param1Int) {
/* 259 */       Container container = getContainer();
/* 260 */       if (container instanceof JTextArea) {
/* 261 */         JTextArea jTextArea = (JTextArea)container;
/* 262 */         if (!jTextArea.getLineWrap())
/*     */         {
/* 264 */           return Integer.MAX_VALUE;
/*     */         }
/*     */       } 
/* 267 */       return super.getFlowSpan(param1Int);
/*     */     }
/*     */ 
/*     */     
/*     */     protected SizeRequirements calculateMinorAxisRequirements(int param1Int, SizeRequirements param1SizeRequirements) {
/* 272 */       SizeRequirements sizeRequirements = super.calculateMinorAxisRequirements(param1Int, param1SizeRequirements);
/* 273 */       Container container = getContainer();
/* 274 */       if (container instanceof JTextArea) {
/* 275 */         JTextArea jTextArea = (JTextArea)container;
/* 276 */         if (!jTextArea.getLineWrap()) {
/*     */           
/* 278 */           sizeRequirements.minimum = sizeRequirements.preferred;
/*     */         } else {
/* 280 */           sizeRequirements.minimum = 0;
/* 281 */           sizeRequirements.preferred = getWidth();
/* 282 */           if (sizeRequirements.preferred == Integer.MAX_VALUE)
/*     */           {
/*     */             
/* 285 */             sizeRequirements.preferred = 100;
/*     */           }
/*     */         } 
/*     */       } 
/* 289 */       return sizeRequirements;
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
/*     */     public void setSize(float param1Float1, float param1Float2) {
/* 301 */       if ((int)param1Float1 != getWidth()) {
/* 302 */         preferenceChanged(null, true, true);
/*     */       }
/* 304 */       super.setSize(param1Float1, param1Float2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static class LogicalView
/*     */       extends CompositeView
/*     */     {
/*     */       LogicalView(Element param2Element) {
/* 317 */         super(param2Element);
/*     */       }
/*     */       
/*     */       protected int getViewIndexAtPosition(int param2Int) {
/* 321 */         Element element = getElement();
/* 322 */         if (element.getElementCount() > 0) {
/* 323 */           return element.getElementIndex(param2Int);
/*     */         }
/* 325 */         return 0;
/*     */       }
/*     */ 
/*     */       
/*     */       protected boolean updateChildren(DocumentEvent.ElementChange param2ElementChange, DocumentEvent param2DocumentEvent, ViewFactory param2ViewFactory) {
/* 330 */         return false;
/*     */       }
/*     */       
/*     */       protected void loadChildren(ViewFactory param2ViewFactory) {
/* 334 */         Element element = getElement();
/* 335 */         if (element.getElementCount() > 0) {
/* 336 */           super.loadChildren(param2ViewFactory);
/*     */         } else {
/* 338 */           GlyphView glyphView = new GlyphView(element);
/* 339 */           append(glyphView);
/*     */         } 
/*     */       }
/*     */       
/*     */       public float getPreferredSpan(int param2Int) {
/* 344 */         if (getViewCount() != 1) {
/* 345 */           throw new Error("One child view is assumed.");
/*     */         }
/* 347 */         View view = getView(0);
/* 348 */         return view.getPreferredSpan(param2Int);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       protected void forwardUpdateToView(View param2View, DocumentEvent param2DocumentEvent, Shape param2Shape, ViewFactory param2ViewFactory) {
/* 367 */         param2View.setParent(this);
/* 368 */         super.forwardUpdateToView(param2View, param2DocumentEvent, param2Shape, param2ViewFactory);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public void paint(Graphics param2Graphics, Shape param2Shape) {}
/*     */ 
/*     */ 
/*     */       
/*     */       protected boolean isBefore(int param2Int1, int param2Int2, Rectangle param2Rectangle) {
/* 378 */         return false;
/*     */       }
/*     */       
/*     */       protected boolean isAfter(int param2Int1, int param2Int2, Rectangle param2Rectangle) {
/* 382 */         return false;
/*     */       }
/*     */       
/*     */       protected View getViewAtPoint(int param2Int1, int param2Int2, Rectangle param2Rectangle) {
/* 386 */         return null;
/*     */       }
/*     */       
/*     */       protected void childAllocation(int param2Int, Rectangle param2Rectangle) {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicTextAreaUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */