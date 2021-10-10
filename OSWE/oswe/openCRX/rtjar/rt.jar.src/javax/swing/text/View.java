/*      */ package javax.swing.text;
/*      */ 
/*      */ import java.awt.Container;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import javax.swing.SwingConstants;
/*      */ import javax.swing.event.DocumentEvent;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class View
/*      */   implements SwingConstants
/*      */ {
/*      */   public static final int BadBreakWeight = 0;
/*      */   public static final int GoodBreakWeight = 1000;
/*      */   public static final int ExcellentBreakWeight = 2000;
/*      */   public static final int ForcedBreakWeight = 3000;
/*      */   public static final int X_AXIS = 0;
/*      */   public static final int Y_AXIS = 1;
/*      */   
/*      */   public View(Element paramElement) {
/*  199 */     this.elem = paramElement;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public View getParent() {
/*  208 */     return this.parent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isVisible() {
/*  219 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract float getPreferredSpan(int paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getMinimumSpan(int paramInt) {
/*  247 */     int i = getResizeWeight(paramInt);
/*  248 */     if (i == 0)
/*      */     {
/*  250 */       return getPreferredSpan(paramInt);
/*      */     }
/*  252 */     return 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getMaximumSpan(int paramInt) {
/*  265 */     int i = getResizeWeight(paramInt);
/*  266 */     if (i == 0)
/*      */     {
/*  268 */       return getPreferredSpan(paramInt);
/*      */     }
/*  270 */     return 2.14748365E9F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void preferenceChanged(View paramView, boolean paramBoolean1, boolean paramBoolean2) {
/*  286 */     View view = getParent();
/*  287 */     if (view != null) {
/*  288 */       view.preferenceChanged(this, paramBoolean1, paramBoolean2);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getAlignment(int paramInt) {
/*  305 */     return 0.5F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract void paint(Graphics paramGraphics, Shape paramShape);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setParent(View paramView) {
/*  336 */     if (paramView == null) {
/*  337 */       for (byte b = 0; b < getViewCount(); b++) {
/*  338 */         if (getView(b).getParent() == this)
/*      */         {
/*      */           
/*  341 */           getView(b).setParent(null);
/*      */         }
/*      */       } 
/*      */     }
/*  345 */     this.parent = paramView;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getViewCount() {
/*  357 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public View getView(int paramInt) {
/*  368 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAll() {
/*  379 */     replace(0, getViewCount(), null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void remove(int paramInt) {
/*  388 */     replace(paramInt, 1, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insert(int paramInt, View paramView) {
/*  401 */     View[] arrayOfView = new View[1];
/*  402 */     arrayOfView[0] = paramView;
/*  403 */     replace(paramInt, 0, arrayOfView);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void append(View paramView) {
/*  415 */     View[] arrayOfView = new View[1];
/*  416 */     arrayOfView[0] = paramView;
/*  417 */     replace(getViewCount(), 0, arrayOfView);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void replace(int paramInt1, int paramInt2, View[] paramArrayOfView) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getViewIndex(int paramInt, Position.Bias paramBias) {
/*  453 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Shape getChildAllocation(int paramInt, Shape paramShape) {
/*  469 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNextVisualPositionFrom(int paramInt1, Position.Bias paramBias, Shape paramShape, int paramInt2, Position.Bias[] paramArrayOfBias) throws BadLocationException {
/*  504 */     if (paramInt1 < -1)
/*      */     {
/*  506 */       throw new BadLocationException("Invalid position", paramInt1);
/*      */     }
/*      */     
/*  509 */     paramArrayOfBias[0] = Position.Bias.Forward;
/*  510 */     switch (paramInt2) {
/*      */       
/*      */       case 1:
/*      */       case 5:
/*  514 */         if (paramInt1 == -1) {
/*      */           
/*  516 */           paramInt1 = (paramInt2 == 1) ? Math.max(0, getEndOffset() - 1) : getStartOffset();
/*      */         } else {
/*      */           Object object; int i;
/*  519 */           JTextComponent jTextComponent = (JTextComponent)getContainer();
/*  520 */           Caret caret = (jTextComponent != null) ? jTextComponent.getCaret() : null;
/*      */ 
/*      */ 
/*      */           
/*  524 */           if (caret != null) {
/*  525 */             object = caret.getMagicCaretPosition();
/*      */           } else {
/*      */             
/*  528 */             object = null;
/*      */           } 
/*      */           
/*  531 */           if (object == null) {
/*  532 */             Rectangle rectangle = jTextComponent.modelToView(paramInt1);
/*  533 */             i = (rectangle == null) ? 0 : rectangle.x;
/*      */           } else {
/*      */             
/*  536 */             i = ((Point)object).x;
/*      */           } 
/*  538 */           if (paramInt2 == 1) {
/*  539 */             paramInt1 = Utilities.getPositionAbove(jTextComponent, paramInt1, i);
/*      */           } else {
/*      */             
/*  542 */             paramInt1 = Utilities.getPositionBelow(jTextComponent, paramInt1, i);
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  565 */         return paramInt1;case 7: if (paramInt1 == -1) { paramInt1 = Math.max(0, getEndOffset() - 1); } else { paramInt1 = Math.max(0, paramInt1 - 1); }  return paramInt1;case 3: if (paramInt1 == -1) { paramInt1 = getStartOffset(); } else { paramInt1 = Math.min(paramInt1 + 1, getDocument().getLength()); }  return paramInt1;
/*      */     } 
/*      */     throw new IllegalArgumentException("Bad direction: " + paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract Shape modelToView(int paramInt, Shape paramShape, Position.Bias paramBias) throws BadLocationException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Shape modelToView(int paramInt1, Position.Bias paramBias1, int paramInt2, Position.Bias paramBias2, Shape paramShape) throws BadLocationException {
/*  623 */     Shape shape2, shape1 = modelToView(paramInt1, paramShape, paramBias1);
/*      */     
/*  625 */     if (paramInt2 == getEndOffset()) {
/*      */       try {
/*  627 */         shape2 = modelToView(paramInt2, paramShape, paramBias2);
/*  628 */       } catch (BadLocationException badLocationException) {
/*  629 */         shape2 = null;
/*      */       } 
/*  631 */       if (shape2 == null)
/*      */       {
/*      */         
/*  634 */         Rectangle rectangle = (paramShape instanceof Rectangle) ? (Rectangle)paramShape : paramShape.getBounds();
/*  635 */         shape2 = new Rectangle(rectangle.x + rectangle.width - 1, rectangle.y, 1, rectangle.height);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  640 */       shape2 = modelToView(paramInt2, paramShape, paramBias2);
/*      */     } 
/*  642 */     Rectangle rectangle1 = shape1.getBounds();
/*      */     
/*  644 */     Rectangle rectangle2 = (shape2 instanceof Rectangle) ? (Rectangle)shape2 : shape2.getBounds();
/*  645 */     if (rectangle1.y != rectangle2.y) {
/*      */ 
/*      */       
/*  648 */       Rectangle rectangle = (paramShape instanceof Rectangle) ? (Rectangle)paramShape : paramShape.getBounds();
/*  649 */       rectangle1.x = rectangle.x;
/*  650 */       rectangle1.width = rectangle.width;
/*      */     } 
/*  652 */     rectangle1.add(rectangle2);
/*  653 */     return rectangle1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract int viewToModel(float paramFloat1, float paramFloat2, Shape paramShape, Position.Bias[] paramArrayOfBias);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insertUpdate(DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/*  700 */     if (getViewCount() > 0) {
/*  701 */       Element element = getElement();
/*  702 */       DocumentEvent.ElementChange elementChange = paramDocumentEvent.getChange(element);
/*  703 */       if (elementChange != null && 
/*  704 */         !updateChildren(elementChange, paramDocumentEvent, paramViewFactory))
/*      */       {
/*      */         
/*  707 */         elementChange = null;
/*      */       }
/*      */       
/*  710 */       forwardUpdate(elementChange, paramDocumentEvent, paramShape, paramViewFactory);
/*  711 */       updateLayout(elementChange, paramDocumentEvent, paramShape);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeUpdate(DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/*  741 */     if (getViewCount() > 0) {
/*  742 */       Element element = getElement();
/*  743 */       DocumentEvent.ElementChange elementChange = paramDocumentEvent.getChange(element);
/*  744 */       if (elementChange != null && 
/*  745 */         !updateChildren(elementChange, paramDocumentEvent, paramViewFactory))
/*      */       {
/*      */         
/*  748 */         elementChange = null;
/*      */       }
/*      */       
/*  751 */       forwardUpdate(elementChange, paramDocumentEvent, paramShape, paramViewFactory);
/*  752 */       updateLayout(elementChange, paramDocumentEvent, paramShape);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void changedUpdate(DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/*  782 */     if (getViewCount() > 0) {
/*  783 */       Element element = getElement();
/*  784 */       DocumentEvent.ElementChange elementChange = paramDocumentEvent.getChange(element);
/*  785 */       if (elementChange != null && 
/*  786 */         !updateChildren(elementChange, paramDocumentEvent, paramViewFactory))
/*      */       {
/*      */         
/*  789 */         elementChange = null;
/*      */       }
/*      */       
/*  792 */       forwardUpdate(elementChange, paramDocumentEvent, paramShape, paramViewFactory);
/*  793 */       updateLayout(elementChange, paramDocumentEvent, paramShape);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Document getDocument() {
/*  804 */     return this.elem.getDocument();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getStartOffset() {
/*  815 */     return this.elem.getStartOffset();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getEndOffset() {
/*  826 */     return this.elem.getEndOffset();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Element getElement() {
/*  838 */     return this.elem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Graphics getGraphics() {
/*  852 */     Container container = getContainer();
/*  853 */     return container.getGraphics();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AttributeSet getAttributes() {
/*  870 */     return this.elem.getAttributes();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public View breakView(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2) {
/*  906 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public View createFragment(int paramInt1, int paramInt2) {
/*  927 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getBreakWeight(int paramInt, float paramFloat1, float paramFloat2) {
/*  970 */     if (paramFloat2 > getPreferredSpan(paramInt)) {
/*  971 */       return 1000;
/*      */     }
/*  973 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getResizeWeight(int paramInt) {
/*  985 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSize(float paramFloat1, float paramFloat2) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Container getContainer() {
/* 1008 */     View view = getParent();
/* 1009 */     return (view != null) ? view.getContainer() : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ViewFactory getViewFactory() {
/* 1022 */     View view = getParent();
/* 1023 */     return (view != null) ? view.getViewFactory() : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getToolTipText(float paramFloat1, float paramFloat2, Shape paramShape) {
/* 1035 */     int i = getViewIndex(paramFloat1, paramFloat2, paramShape);
/* 1036 */     if (i >= 0) {
/* 1037 */       paramShape = getChildAllocation(i, paramShape);
/*      */       
/* 1039 */       Rectangle rectangle = (paramShape instanceof Rectangle) ? (Rectangle)paramShape : paramShape.getBounds();
/* 1040 */       if (rectangle.contains(paramFloat1, paramFloat2)) {
/* 1041 */         return getView(i).getToolTipText(paramFloat1, paramFloat2, paramShape);
/*      */       }
/*      */     } 
/* 1044 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getViewIndex(float paramFloat1, float paramFloat2, Shape paramShape) {
/* 1060 */     for (int i = getViewCount() - 1; i >= 0; i--) {
/* 1061 */       Shape shape = getChildAllocation(i, paramShape);
/*      */       
/* 1063 */       if (shape != null) {
/*      */         
/* 1065 */         Rectangle rectangle = (shape instanceof Rectangle) ? (Rectangle)shape : shape.getBounds();
/*      */         
/* 1067 */         if (rectangle.contains(paramFloat1, paramFloat2)) {
/* 1068 */           return i;
/*      */         }
/*      */       } 
/*      */     } 
/* 1072 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean updateChildren(DocumentEvent.ElementChange paramElementChange, DocumentEvent paramDocumentEvent, ViewFactory paramViewFactory) {
/* 1106 */     Element[] arrayOfElement1 = paramElementChange.getChildrenRemoved();
/* 1107 */     Element[] arrayOfElement2 = paramElementChange.getChildrenAdded();
/* 1108 */     View[] arrayOfView = null;
/* 1109 */     if (arrayOfElement2 != null) {
/* 1110 */       arrayOfView = new View[arrayOfElement2.length];
/* 1111 */       for (byte b = 0; b < arrayOfElement2.length; b++) {
/* 1112 */         arrayOfView[b] = paramViewFactory.create(arrayOfElement2[b]);
/*      */       }
/*      */     } 
/* 1115 */     int i = 0;
/* 1116 */     int j = paramElementChange.getIndex();
/* 1117 */     if (arrayOfElement1 != null) {
/* 1118 */       i = arrayOfElement1.length;
/*      */     }
/* 1120 */     replace(j, i, arrayOfView);
/* 1121 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void forwardUpdate(DocumentEvent.ElementChange paramElementChange, DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/* 1144 */     calculateUpdateIndexes(paramDocumentEvent);
/*      */     
/* 1146 */     int i = this.lastUpdateIndex + 1;
/* 1147 */     int j = i;
/* 1148 */     Element[] arrayOfElement = (paramElementChange != null) ? paramElementChange.getChildrenAdded() : null;
/* 1149 */     if (arrayOfElement != null && arrayOfElement.length > 0) {
/* 1150 */       i = paramElementChange.getIndex();
/* 1151 */       j = i + arrayOfElement.length - 1;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1157 */     for (int k = this.firstUpdateIndex; k <= this.lastUpdateIndex; k++) {
/* 1158 */       if (k < i || k > j) {
/* 1159 */         View view = getView(k);
/* 1160 */         if (view != null) {
/* 1161 */           Shape shape = getChildAllocation(k, paramShape);
/* 1162 */           forwardUpdateToView(view, paramDocumentEvent, shape, paramViewFactory);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void calculateUpdateIndexes(DocumentEvent paramDocumentEvent) {
/* 1174 */     int i = paramDocumentEvent.getOffset();
/* 1175 */     this.firstUpdateIndex = getViewIndex(i, Position.Bias.Forward);
/* 1176 */     if (this.firstUpdateIndex == -1 && paramDocumentEvent.getType() == DocumentEvent.EventType.REMOVE && i >= 
/* 1177 */       getEndOffset())
/*      */     {
/*      */ 
/*      */       
/* 1181 */       this.firstUpdateIndex = getViewCount() - 1;
/*      */     }
/* 1183 */     this.lastUpdateIndex = this.firstUpdateIndex;
/* 1184 */     View view = (this.firstUpdateIndex >= 0) ? getView(this.firstUpdateIndex) : null;
/* 1185 */     if (view != null && 
/* 1186 */       view.getStartOffset() == i && i > 0)
/*      */     {
/*      */       
/* 1189 */       this.firstUpdateIndex = Math.max(this.firstUpdateIndex - 1, 0);
/*      */     }
/*      */     
/* 1192 */     if (paramDocumentEvent.getType() != DocumentEvent.EventType.REMOVE) {
/* 1193 */       this.lastUpdateIndex = getViewIndex(i + paramDocumentEvent.getLength(), Position.Bias.Forward);
/* 1194 */       if (this.lastUpdateIndex < 0) {
/* 1195 */         this.lastUpdateIndex = getViewCount() - 1;
/*      */       }
/*      */     } 
/* 1198 */     this.firstUpdateIndex = Math.max(this.firstUpdateIndex, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void updateAfterChange() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void forwardUpdateToView(View paramView, DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/* 1225 */     DocumentEvent.EventType eventType = paramDocumentEvent.getType();
/* 1226 */     if (eventType == DocumentEvent.EventType.INSERT) {
/* 1227 */       paramView.insertUpdate(paramDocumentEvent, paramShape, paramViewFactory);
/* 1228 */     } else if (eventType == DocumentEvent.EventType.REMOVE) {
/* 1229 */       paramView.removeUpdate(paramDocumentEvent, paramShape, paramViewFactory);
/*      */     } else {
/* 1231 */       paramView.changedUpdate(paramDocumentEvent, paramShape, paramViewFactory);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateLayout(DocumentEvent.ElementChange paramElementChange, DocumentEvent paramDocumentEvent, Shape paramShape) {
/* 1252 */     if (paramElementChange != null && paramShape != null) {
/*      */       
/* 1254 */       preferenceChanged(null, true, true);
/* 1255 */       Container container = getContainer();
/* 1256 */       if (container != null) {
/* 1257 */         container.repaint();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Shape modelToView(int paramInt, Shape paramShape) throws BadLocationException {
/* 1338 */     return modelToView(paramInt, paramShape, Position.Bias.Forward);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public int viewToModel(float paramFloat1, float paramFloat2, Shape paramShape) {
/* 1356 */     sharedBiasReturn[0] = Position.Bias.Forward;
/* 1357 */     return viewToModel(paramFloat1, paramFloat2, paramShape, sharedBiasReturn);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 1362 */   static final Position.Bias[] sharedBiasReturn = new Position.Bias[1];
/*      */   private View parent;
/*      */   private Element elem;
/*      */   int firstUpdateIndex;
/*      */   int lastUpdateIndex;
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/View.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */