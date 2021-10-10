/*     */ package javax.swing.text.html;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Shape;
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import javax.swing.JEditorPane;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JViewport;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.event.DocumentEvent;
/*     */ import javax.swing.event.HyperlinkEvent;
/*     */ import javax.swing.event.HyperlinkListener;
/*     */ import javax.swing.text.AttributeSet;
/*     */ import javax.swing.text.ComponentView;
/*     */ import javax.swing.text.Document;
/*     */ import javax.swing.text.EditorKit;
/*     */ import javax.swing.text.Element;
/*     */ import javax.swing.text.JTextComponent;
/*     */ import javax.swing.text.View;
/*     */ import javax.swing.text.ViewFactory;
/*     */ import sun.swing.text.html.FrameEditorPaneTag;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class FrameView
/*     */   extends ComponentView
/*     */   implements HyperlinkListener
/*     */ {
/*     */   JEditorPane htmlPane;
/*     */   JScrollPane scroller;
/*     */   boolean editable;
/*     */   float width;
/*     */   float height;
/*     */   URL src;
/*     */   private boolean createdComponent;
/*     */   
/*     */   public FrameView(Element paramElement) {
/*  63 */     super(paramElement);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Component createComponent() {
/*  68 */     Element element = getElement();
/*  69 */     AttributeSet attributeSet = element.getAttributes();
/*  70 */     String str = (String)attributeSet.getAttribute(HTML.Attribute.SRC);
/*     */     
/*  72 */     if (str != null && !str.equals("")) {
/*     */       try {
/*  74 */         URL uRL = ((HTMLDocument)element.getDocument()).getBase();
/*  75 */         this.src = new URL(uRL, str);
/*  76 */         this.htmlPane = new FrameEditorPane();
/*  77 */         this.htmlPane.addHyperlinkListener(this);
/*  78 */         JEditorPane jEditorPane = getHostPane();
/*  79 */         boolean bool = true;
/*  80 */         if (jEditorPane != null) {
/*  81 */           this.htmlPane.setEditable(jEditorPane.isEditable());
/*  82 */           String str1 = (String)jEditorPane.getClientProperty("charset");
/*  83 */           if (str1 != null) {
/*  84 */             this.htmlPane.putClientProperty("charset", str1);
/*     */           }
/*  86 */           HTMLEditorKit hTMLEditorKit1 = (HTMLEditorKit)jEditorPane.getEditorKit();
/*  87 */           if (hTMLEditorKit1 != null) {
/*  88 */             bool = hTMLEditorKit1.isAutoFormSubmission();
/*     */           }
/*     */         } 
/*  91 */         this.htmlPane.setPage(this.src);
/*  92 */         HTMLEditorKit hTMLEditorKit = (HTMLEditorKit)this.htmlPane.getEditorKit();
/*  93 */         if (hTMLEditorKit != null) {
/*  94 */           hTMLEditorKit.setAutoFormSubmission(bool);
/*     */         }
/*     */         
/*  97 */         Document document = this.htmlPane.getDocument();
/*  98 */         if (document instanceof HTMLDocument) {
/*  99 */           ((HTMLDocument)document).setFrameDocumentState(true);
/*     */         }
/* 101 */         setMargin();
/* 102 */         createScrollPane();
/* 103 */         setBorder();
/* 104 */       } catch (MalformedURLException malformedURLException) {
/* 105 */         malformedURLException.printStackTrace();
/* 106 */       } catch (IOException iOException) {
/* 107 */         iOException.printStackTrace();
/*     */       } 
/*     */     }
/* 110 */     this.createdComponent = true;
/* 111 */     return this.scroller;
/*     */   }
/*     */   
/*     */   JEditorPane getHostPane() {
/* 115 */     Container container = getContainer();
/* 116 */     while (container != null && !(container instanceof JEditorPane)) {
/* 117 */       container = container.getParent();
/*     */     }
/* 119 */     return (JEditorPane)container;
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
/*     */   public void setParent(View paramView) {
/* 132 */     if (paramView != null) {
/* 133 */       JTextComponent jTextComponent = (JTextComponent)paramView.getContainer();
/* 134 */       this.editable = jTextComponent.isEditable();
/*     */     } 
/* 136 */     super.setParent(paramView);
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
/*     */   public void paint(Graphics paramGraphics, Shape paramShape) {
/* 151 */     Container container = getContainer();
/* 152 */     if (container != null && this.htmlPane != null && this.htmlPane
/* 153 */       .isEditable() != ((JTextComponent)container).isEditable()) {
/* 154 */       this.editable = ((JTextComponent)container).isEditable();
/* 155 */       this.htmlPane.setEditable(this.editable);
/*     */     } 
/* 157 */     super.paint(paramGraphics, paramShape);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setMargin() {
/*     */     Insets insets2;
/* 166 */     int i = 0;
/* 167 */     Insets insets1 = this.htmlPane.getMargin();
/*     */     
/* 169 */     boolean bool = false;
/* 170 */     AttributeSet attributeSet = getElement().getAttributes();
/* 171 */     String str = (String)attributeSet.getAttribute(HTML.Attribute.MARGINWIDTH);
/* 172 */     if (insets1 != null) {
/* 173 */       insets2 = new Insets(insets1.top, insets1.left, insets1.right, insets1.bottom);
/*     */     } else {
/* 175 */       insets2 = new Insets(0, 0, 0, 0);
/*     */     } 
/* 177 */     if (str != null) {
/* 178 */       i = Integer.parseInt(str);
/* 179 */       if (i > 0) {
/* 180 */         insets2.left = i;
/* 181 */         insets2.right = i;
/* 182 */         bool = true;
/*     */       } 
/*     */     } 
/* 185 */     str = (String)attributeSet.getAttribute(HTML.Attribute.MARGINHEIGHT);
/* 186 */     if (str != null) {
/* 187 */       i = Integer.parseInt(str);
/* 188 */       if (i > 0) {
/* 189 */         insets2.top = i;
/* 190 */         insets2.bottom = i;
/* 191 */         bool = true;
/*     */       } 
/*     */     } 
/* 194 */     if (bool) {
/* 195 */       this.htmlPane.setMargin(insets2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setBorder() {
/* 206 */     AttributeSet attributeSet = getElement().getAttributes();
/* 207 */     String str = (String)attributeSet.getAttribute(HTML.Attribute.FRAMEBORDER);
/* 208 */     if (str != null && (str
/* 209 */       .equals("no") || str.equals("0")))
/*     */     {
/* 211 */       this.scroller.setBorder((Border)null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createScrollPane() {
/* 222 */     AttributeSet attributeSet = getElement().getAttributes();
/* 223 */     String str = (String)attributeSet.getAttribute(HTML.Attribute.SCROLLING);
/* 224 */     if (str == null) {
/* 225 */       str = "auto";
/*     */     }
/*     */     
/* 228 */     if (!str.equals("no")) {
/* 229 */       if (str.equals("yes")) {
/* 230 */         this.scroller = new JScrollPane(22, 32);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 235 */         this.scroller = new JScrollPane();
/*     */       } 
/*     */     } else {
/* 238 */       this.scroller = new JScrollPane(21, 31);
/*     */     } 
/*     */ 
/*     */     
/* 242 */     JViewport jViewport = this.scroller.getViewport();
/* 243 */     jViewport.add(this.htmlPane);
/* 244 */     jViewport.setBackingStoreEnabled(true);
/* 245 */     this.scroller.setMinimumSize(new Dimension(5, 5));
/* 246 */     this.scroller.setMaximumSize(new Dimension(2147483647, 2147483647));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   JEditorPane getOutermostJEditorPane() {
/* 256 */     View view = getParent();
/* 257 */     FrameSetView frameSetView = null;
/* 258 */     while (view != null) {
/* 259 */       if (view instanceof FrameSetView) {
/* 260 */         frameSetView = (FrameSetView)view;
/*     */       }
/* 262 */       view = view.getParent();
/*     */     } 
/* 264 */     if (frameSetView != null) {
/* 265 */       return (JEditorPane)frameSetView.getContainer();
/*     */     }
/* 267 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean inNestedFrameSet() {
/* 276 */     FrameSetView frameSetView = (FrameSetView)getParent();
/* 277 */     return frameSetView.getParent() instanceof FrameSetView;
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
/*     */   public void hyperlinkUpdate(HyperlinkEvent paramHyperlinkEvent) {
/* 296 */     JEditorPane jEditorPane = getOutermostJEditorPane();
/* 297 */     if (jEditorPane == null) {
/*     */       return;
/*     */     }
/*     */     
/* 301 */     if (!(paramHyperlinkEvent instanceof HTMLFrameHyperlinkEvent)) {
/* 302 */       jEditorPane.fireHyperlinkUpdate(paramHyperlinkEvent);
/*     */       
/*     */       return;
/*     */     } 
/* 306 */     HTMLFrameHyperlinkEvent hTMLFrameHyperlinkEvent = (HTMLFrameHyperlinkEvent)paramHyperlinkEvent;
/*     */     
/* 308 */     if (hTMLFrameHyperlinkEvent.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
/* 309 */       String str1 = hTMLFrameHyperlinkEvent.getTarget();
/* 310 */       String str2 = str1;
/*     */       
/* 312 */       if (str1.equals("_parent") && !inNestedFrameSet()) {
/* 313 */         str1 = "_top";
/*     */       }
/*     */       
/* 316 */       if (paramHyperlinkEvent instanceof FormSubmitEvent) {
/* 317 */         HTMLEditorKit hTMLEditorKit = (HTMLEditorKit)jEditorPane.getEditorKit();
/* 318 */         if (hTMLEditorKit != null && hTMLEditorKit.isAutoFormSubmission()) {
/* 319 */           if (str1.equals("_top")) {
/*     */             try {
/* 321 */               movePostData(jEditorPane, str2);
/* 322 */               jEditorPane.setPage(hTMLFrameHyperlinkEvent.getURL());
/* 323 */             } catch (IOException iOException) {}
/*     */           }
/*     */           else {
/*     */             
/* 327 */             HTMLDocument hTMLDocument = (HTMLDocument)jEditorPane.getDocument();
/* 328 */             hTMLDocument.processHTMLFrameHyperlinkEvent(hTMLFrameHyperlinkEvent);
/*     */           } 
/*     */         } else {
/* 331 */           jEditorPane.fireHyperlinkUpdate(paramHyperlinkEvent);
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/* 336 */       if (str1.equals("_top")) {
/*     */         try {
/* 338 */           jEditorPane.setPage(hTMLFrameHyperlinkEvent.getURL());
/* 339 */         } catch (IOException iOException) {}
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 344 */       if (!jEditorPane.isEditable()) {
/* 345 */         jEditorPane.fireHyperlinkUpdate(new HTMLFrameHyperlinkEvent(jEditorPane, hTMLFrameHyperlinkEvent
/* 346 */               .getEventType(), hTMLFrameHyperlinkEvent
/* 347 */               .getURL(), hTMLFrameHyperlinkEvent
/* 348 */               .getDescription(), 
/* 349 */               getElement(), hTMLFrameHyperlinkEvent
/* 350 */               .getInputEvent(), str1));
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
/*     */   public void changedUpdate(DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/* 368 */     Element element = getElement();
/* 369 */     AttributeSet attributeSet = element.getAttributes();
/*     */     
/* 371 */     URL uRL1 = this.src;
/*     */     
/* 373 */     String str = (String)attributeSet.getAttribute(HTML.Attribute.SRC);
/* 374 */     URL uRL2 = ((HTMLDocument)element.getDocument()).getBase();
/*     */     try {
/* 376 */       if (!this.createdComponent) {
/*     */         return;
/*     */       }
/*     */       
/* 380 */       Object object = movePostData(this.htmlPane, (String)null);
/* 381 */       this.src = new URL(uRL2, str);
/* 382 */       if (uRL1.equals(this.src) && this.src.getRef() == null && object == null) {
/*     */         return;
/*     */       }
/*     */       
/* 386 */       this.htmlPane.setPage(this.src);
/* 387 */       Document document = this.htmlPane.getDocument();
/* 388 */       if (document instanceof HTMLDocument) {
/* 389 */         ((HTMLDocument)document).setFrameDocumentState(true);
/*     */       }
/* 391 */     } catch (MalformedURLException malformedURLException) {
/*     */ 
/*     */     
/* 394 */     } catch (IOException iOException) {}
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
/*     */   private Object movePostData(JEditorPane paramJEditorPane, String paramString) {
/* 406 */     Object object = null;
/* 407 */     JEditorPane jEditorPane = getOutermostJEditorPane();
/* 408 */     if (jEditorPane != null) {
/* 409 */       if (paramString == null) {
/* 410 */         paramString = (String)getElement().getAttributes().getAttribute(HTML.Attribute.NAME);
/*     */       }
/*     */       
/* 413 */       if (paramString != null) {
/* 414 */         String str = "javax.swing.JEditorPane.postdata." + paramString;
/* 415 */         Document document = jEditorPane.getDocument();
/* 416 */         object = document.getProperty(str);
/* 417 */         if (object != null) {
/* 418 */           paramJEditorPane.getDocument().putProperty("javax.swing.JEditorPane.postdata", object);
/*     */           
/* 420 */           document.putProperty(str, null);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 425 */     return object;
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
/*     */   public float getMinimumSpan(int paramInt) {
/* 440 */     return 5.0F;
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
/*     */   public float getMaximumSpan(int paramInt) {
/* 455 */     return 2.14748365E9F;
/*     */   }
/*     */   
/*     */   class FrameEditorPane
/*     */     extends JEditorPane
/*     */     implements FrameEditorPaneTag
/*     */   {
/*     */     public EditorKit getEditorKitForContentType(String param1String) {
/* 463 */       EditorKit editorKit = super.getEditorKitForContentType(param1String);
/* 464 */       JEditorPane jEditorPane = null;
/* 465 */       if ((jEditorPane = FrameView.this.getOutermostJEditorPane()) != null) {
/* 466 */         EditorKit editorKit1 = jEditorPane.getEditorKitForContentType(param1String);
/* 467 */         if (!editorKit.getClass().equals(editorKit1.getClass())) {
/* 468 */           editorKit = (EditorKit)editorKit1.clone();
/* 469 */           setEditorKitForContentType(param1String, editorKit);
/*     */         } 
/*     */       } 
/* 472 */       return editorKit;
/*     */     }
/*     */     
/*     */     FrameView getFrameView() {
/* 476 */       return FrameView.this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/FrameView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */