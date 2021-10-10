/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.font.FontRenderContext;
/*     */ import java.awt.font.LineBreakMeasurer;
/*     */ import java.awt.font.TextAttribute;
/*     */ import java.awt.font.TextLayout;
/*     */ import java.text.AttributedCharacterIterator;
/*     */ import java.text.BreakIterator;
/*     */ import java.util.HashSet;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.event.DocumentEvent;
/*     */ import sun.font.BidiUtils;
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
/*     */ class TextLayoutStrategy
/*     */   extends FlowView.FlowStrategy
/*     */ {
/*     */   private LineBreakMeasurer measurer;
/*  53 */   private AttributedSegment text = new AttributedSegment();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insertUpdate(FlowView paramFlowView, DocumentEvent paramDocumentEvent, Rectangle paramRectangle) {
/*  70 */     sync(paramFlowView);
/*  71 */     super.insertUpdate(paramFlowView, paramDocumentEvent, paramRectangle);
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
/*     */   public void removeUpdate(FlowView paramFlowView, DocumentEvent paramDocumentEvent, Rectangle paramRectangle) {
/*  83 */     sync(paramFlowView);
/*  84 */     super.removeUpdate(paramFlowView, paramDocumentEvent, paramRectangle);
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
/*     */   public void changedUpdate(FlowView paramFlowView, DocumentEvent paramDocumentEvent, Rectangle paramRectangle) {
/*  97 */     sync(paramFlowView);
/*  98 */     super.changedUpdate(paramFlowView, paramDocumentEvent, paramRectangle);
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
/*     */   public void layout(FlowView paramFlowView) {
/* 110 */     super.layout(paramFlowView);
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
/*     */   protected int layoutRow(FlowView paramFlowView, int paramInt1, int paramInt2) {
/* 127 */     int i = super.layoutRow(paramFlowView, paramInt1, paramInt2);
/* 128 */     View view = paramFlowView.getView(paramInt1);
/* 129 */     Document document = paramFlowView.getDocument();
/* 130 */     Object object = document.getProperty("i18n");
/* 131 */     if (object != null && object.equals(Boolean.TRUE)) {
/* 132 */       int j = view.getViewCount();
/* 133 */       if (j > 1) {
/* 134 */         AbstractDocument abstractDocument = (AbstractDocument)paramFlowView.getDocument();
/* 135 */         Element element = abstractDocument.getBidiRootElement();
/* 136 */         byte[] arrayOfByte = new byte[j];
/* 137 */         View[] arrayOfView = new View[j];
/*     */         
/* 139 */         for (byte b = 0; b < j; b++) {
/* 140 */           View view1 = view.getView(b);
/* 141 */           int k = element.getElementIndex(view1.getStartOffset());
/* 142 */           Element element1 = element.getElement(k);
/* 143 */           arrayOfByte[b] = (byte)StyleConstants.getBidiLevel(element1.getAttributes());
/* 144 */           arrayOfView[b] = view1;
/*     */         } 
/*     */         
/* 147 */         BidiUtils.reorderVisually(arrayOfByte, (Object[])arrayOfView);
/* 148 */         view.replace(0, j, arrayOfView);
/*     */       } 
/*     */     } 
/* 151 */     return i;
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
/*     */   protected void adjustRow(FlowView paramFlowView, int paramInt1, int paramInt2, int paramInt3) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected View createView(FlowView paramFlowView, int paramInt1, int paramInt2, int paramInt3) {
/* 180 */     View view4, view1 = getLogicalView(paramFlowView);
/* 181 */     View view2 = paramFlowView.getView(paramInt3);
/* 182 */     boolean bool = (this.viewBuffer.size() == 0) ? false : true;
/* 183 */     int i = view1.getViewIndex(paramInt1, Position.Bias.Forward);
/* 184 */     View view3 = view1.getView(i);
/*     */     
/* 186 */     int j = getLimitingOffset(view3, paramInt1, paramInt2, bool);
/* 187 */     if (j == paramInt1) {
/* 188 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 192 */     if (paramInt1 == view3.getStartOffset() && j == view3.getEndOffset()) {
/*     */       
/* 194 */       view4 = view3;
/*     */     } else {
/*     */       
/* 197 */       view4 = view3.createFragment(paramInt1, j);
/*     */     } 
/*     */     
/* 200 */     if (view4 instanceof GlyphView && this.measurer != null) {
/*     */ 
/*     */ 
/*     */       
/* 204 */       boolean bool1 = false;
/* 205 */       int k = view4.getStartOffset();
/* 206 */       int m = view4.getEndOffset();
/* 207 */       if (m - k == 1) {
/*     */         
/* 209 */         Segment segment = ((GlyphView)view4).getText(k, m);
/* 210 */         char c = segment.first();
/* 211 */         if (c == '\t') {
/* 212 */           bool1 = true;
/*     */         }
/*     */       } 
/*     */       
/* 216 */       TextLayout textLayout = bool1 ? null : this.measurer.nextLayout(paramInt2, this.text.toIteratorIndex(j), bool);
/*     */       
/* 218 */       if (textLayout != null) {
/* 219 */         ((GlyphView)view4).setGlyphPainter(new GlyphPainter2(textLayout));
/*     */       }
/*     */     } 
/* 222 */     return view4;
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
/*     */   int getLimitingOffset(View paramView, int paramInt1, int paramInt2, boolean paramBoolean) {
/* 238 */     int i = paramView.getEndOffset();
/*     */ 
/*     */     
/* 241 */     Document document = paramView.getDocument();
/* 242 */     if (document instanceof AbstractDocument) {
/* 243 */       AbstractDocument abstractDocument = (AbstractDocument)document;
/* 244 */       Element element = abstractDocument.getBidiRootElement();
/* 245 */       if (element.getElementCount() > 1) {
/* 246 */         int k = element.getElementIndex(paramInt1);
/* 247 */         Element element1 = element.getElement(k);
/* 248 */         i = Math.min(element1.getEndOffset(), i);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 253 */     if (paramView instanceof GlyphView) {
/* 254 */       Segment segment = ((GlyphView)paramView).getText(paramInt1, i);
/* 255 */       char c = segment.first();
/* 256 */       if (c == '\t') {
/*     */ 
/*     */         
/* 259 */         i = paramInt1 + 1;
/*     */       } else {
/* 261 */         for (c = segment.next(); c != Character.MAX_VALUE; c = segment.next()) {
/* 262 */           if (c == '\t') {
/*     */             
/* 264 */             i = paramInt1 + segment.getIndex() - segment.getBeginIndex();
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 272 */     int j = this.text.toIteratorIndex(i);
/* 273 */     if (this.measurer != null) {
/* 274 */       int k = this.text.toIteratorIndex(paramInt1);
/* 275 */       if (this.measurer.getPosition() != k) {
/* 276 */         this.measurer.setPosition(k);
/*     */       }
/* 278 */       j = this.measurer.nextOffset(paramInt2, j, paramBoolean);
/*     */     } 
/* 280 */     return this.text.toModelPosition(j);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void sync(FlowView paramFlowView) {
/*     */     BreakIterator breakIterator;
/* 291 */     View view = getLogicalView(paramFlowView);
/* 292 */     this.text.setView(view);
/*     */     
/* 294 */     Container container1 = paramFlowView.getContainer();
/*     */     
/* 296 */     FontRenderContext fontRenderContext = SwingUtilities2.getFontRenderContext(container1);
/*     */     
/* 298 */     Container container2 = paramFlowView.getContainer();
/* 299 */     if (container2 != null) {
/* 300 */       breakIterator = BreakIterator.getLineInstance(container2.getLocale());
/*     */     } else {
/* 302 */       breakIterator = BreakIterator.getLineInstance();
/*     */     } 
/*     */     
/* 305 */     Object object = null;
/* 306 */     if (container2 instanceof JComponent) {
/* 307 */       object = ((JComponent)container2).getClientProperty(TextAttribute.NUMERIC_SHAPING);
/*     */     }
/*     */     
/* 310 */     this.text.setShaper(object);
/*     */     
/* 312 */     this.measurer = new LineBreakMeasurer(this.text, breakIterator, fontRenderContext);
/*     */ 
/*     */ 
/*     */     
/* 316 */     int i = view.getViewCount();
/* 317 */     for (byte b = 0; b < i; b++) {
/* 318 */       View view1 = view.getView(b);
/* 319 */       if (view1 instanceof GlyphView) {
/* 320 */         int j = view1.getStartOffset();
/* 321 */         int k = view1.getEndOffset();
/* 322 */         this.measurer.setPosition(this.text.toIteratorIndex(j));
/*     */         
/* 324 */         TextLayout textLayout = this.measurer.nextLayout(Float.MAX_VALUE, this.text
/* 325 */             .toIteratorIndex(k), false);
/* 326 */         ((GlyphView)view1).setGlyphPainter(new GlyphPainter2(textLayout));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 331 */     this.measurer.setPosition(this.text.getBeginIndex());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class AttributedSegment
/*     */     extends Segment
/*     */     implements AttributedCharacterIterator
/*     */   {
/*     */     View v;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     View getView() {
/* 351 */       return this.v;
/*     */     }
/*     */     
/*     */     void setView(View param1View) {
/* 355 */       this.v = param1View;
/* 356 */       Document document = param1View.getDocument();
/* 357 */       int i = param1View.getStartOffset();
/* 358 */       int j = param1View.getEndOffset();
/*     */       try {
/* 360 */         document.getText(i, j - i, this);
/* 361 */       } catch (BadLocationException badLocationException) {
/* 362 */         throw new IllegalArgumentException("Invalid view");
/*     */       } 
/* 364 */       first();
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
/*     */     
/*     */     int getFontBoundary(int param1Int1, int param1Int2) {
/* 377 */       View view = this.v.getView(param1Int1);
/* 378 */       Font font = getFont(param1Int1);
/* 379 */       for (param1Int1 += param1Int2; param1Int1 >= 0 && param1Int1 < this.v.getViewCount(); 
/* 380 */         param1Int1 += param1Int2) {
/* 381 */         Font font1 = getFont(param1Int1);
/* 382 */         if (font1 != font) {
/*     */           break;
/*     */         }
/*     */         
/* 386 */         view = this.v.getView(param1Int1);
/*     */       } 
/* 388 */       return (param1Int2 < 0) ? view.getStartOffset() : view.getEndOffset();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Font getFont(int param1Int) {
/* 395 */       View view = this.v.getView(param1Int);
/* 396 */       if (view instanceof GlyphView) {
/* 397 */         return ((GlyphView)view).getFont();
/*     */       }
/* 399 */       return null;
/*     */     }
/*     */     
/*     */     int toModelPosition(int param1Int) {
/* 403 */       return this.v.getStartOffset() + param1Int - getBeginIndex();
/*     */     }
/*     */     
/*     */     int toIteratorIndex(int param1Int) {
/* 407 */       return param1Int - this.v.getStartOffset() + getBeginIndex();
/*     */     }
/*     */     
/*     */     private void setShaper(Object param1Object) {
/* 411 */       this.shaper = param1Object;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getRunStart() {
/* 421 */       int i = toModelPosition(getIndex());
/* 422 */       int j = this.v.getViewIndex(i, Position.Bias.Forward);
/* 423 */       View view = this.v.getView(j);
/* 424 */       return toIteratorIndex(view.getStartOffset());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getRunStart(AttributedCharacterIterator.Attribute param1Attribute) {
/* 432 */       if (param1Attribute instanceof TextAttribute) {
/* 433 */         int i = toModelPosition(getIndex());
/* 434 */         int j = this.v.getViewIndex(i, Position.Bias.Forward);
/* 435 */         if (param1Attribute == TextAttribute.FONT) {
/* 436 */           return toIteratorIndex(getFontBoundary(j, -1));
/*     */         }
/*     */       } 
/* 439 */       return getBeginIndex();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getRunStart(Set<? extends AttributedCharacterIterator.Attribute> param1Set) {
/* 447 */       int i = getBeginIndex();
/* 448 */       Object[] arrayOfObject = param1Set.toArray();
/* 449 */       for (byte b = 0; b < arrayOfObject.length; b++) {
/* 450 */         TextAttribute textAttribute = (TextAttribute)arrayOfObject[b];
/* 451 */         i = Math.max(getRunStart(textAttribute), i);
/*     */       } 
/* 453 */       return Math.min(getIndex(), i);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getRunLimit() {
/* 461 */       int i = toModelPosition(getIndex());
/* 462 */       int j = this.v.getViewIndex(i, Position.Bias.Forward);
/* 463 */       View view = this.v.getView(j);
/* 464 */       return toIteratorIndex(view.getEndOffset());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getRunLimit(AttributedCharacterIterator.Attribute param1Attribute) {
/* 472 */       if (param1Attribute instanceof TextAttribute) {
/* 473 */         int i = toModelPosition(getIndex());
/* 474 */         int j = this.v.getViewIndex(i, Position.Bias.Forward);
/* 475 */         if (param1Attribute == TextAttribute.FONT) {
/* 476 */           return toIteratorIndex(getFontBoundary(j, 1));
/*     */         }
/*     */       } 
/* 479 */       return getEndIndex();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getRunLimit(Set<? extends AttributedCharacterIterator.Attribute> param1Set) {
/* 487 */       int i = getEndIndex();
/* 488 */       Object[] arrayOfObject = param1Set.toArray();
/* 489 */       for (byte b = 0; b < arrayOfObject.length; b++) {
/* 490 */         TextAttribute textAttribute = (TextAttribute)arrayOfObject[b];
/* 491 */         i = Math.min(getRunLimit(textAttribute), i);
/*     */       } 
/* 493 */       return Math.max(getIndex(), i);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Map<AttributedCharacterIterator.Attribute, Object> getAttributes() {
/* 501 */       Object[] arrayOfObject = keys.toArray();
/* 502 */       Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 503 */       for (byte b = 0; b < arrayOfObject.length; b++) {
/* 504 */         TextAttribute textAttribute = (TextAttribute)arrayOfObject[b];
/* 505 */         Object object = getAttribute(textAttribute);
/* 506 */         if (object != null) {
/* 507 */           hashtable.put(textAttribute, object);
/*     */         }
/*     */       } 
/* 510 */       return (Map)hashtable;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getAttribute(AttributedCharacterIterator.Attribute param1Attribute) {
/* 519 */       int i = toModelPosition(getIndex());
/* 520 */       int j = this.v.getViewIndex(i, Position.Bias.Forward);
/* 521 */       if (param1Attribute == TextAttribute.FONT)
/* 522 */         return getFont(j); 
/* 523 */       if (param1Attribute == TextAttribute.RUN_DIRECTION)
/* 524 */         return this.v
/* 525 */           .getDocument().getProperty(TextAttribute.RUN_DIRECTION); 
/* 526 */       if (param1Attribute == TextAttribute.NUMERIC_SHAPING) {
/* 527 */         return this.shaper;
/*     */       }
/* 529 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<AttributedCharacterIterator.Attribute> getAllAttributeKeys() {
/* 538 */       return keys;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 546 */     static Set<AttributedCharacterIterator.Attribute> keys = new HashSet<>(); static {
/* 547 */       keys.add(TextAttribute.FONT);
/* 548 */       keys.add(TextAttribute.RUN_DIRECTION);
/* 549 */       keys.add(TextAttribute.NUMERIC_SHAPING);
/*     */     }
/*     */     
/* 552 */     private Object shaper = null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/TextLayoutStrategy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */