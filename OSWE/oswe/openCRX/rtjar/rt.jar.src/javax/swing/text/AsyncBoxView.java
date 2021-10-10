/*      */ package javax.swing.text;
/*      */ 
/*      */ import java.awt.Container;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
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
/*      */ public class AsyncBoxView
/*      */   extends View
/*      */ {
/*      */   int axis;
/*      */   List<ChildState> stats;
/*      */   float majorSpan;
/*      */   boolean estimatedMajorSpan;
/*      */   float minorSpan;
/*      */   protected ChildLocator locator;
/*      */   float topInset;
/*      */   float bottomInset;
/*      */   float leftInset;
/*      */   float rightInset;
/*      */   ChildState minRequest;
/*      */   ChildState prefRequest;
/*      */   boolean majorChanged;
/*      */   boolean minorChanged;
/*      */   Runnable flushTask;
/*      */   ChildState changing;
/*      */   
/*      */   public AsyncBoxView(Element paramElement, int paramInt) {
/*   61 */     super(paramElement);
/*   62 */     this.stats = new ArrayList<>();
/*   63 */     this.axis = paramInt;
/*   64 */     this.locator = new ChildLocator();
/*   65 */     this.flushTask = new FlushTask();
/*   66 */     this.minorSpan = 32767.0F;
/*   67 */     this.estimatedMajorSpan = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMajorAxis() {
/*   76 */     return this.axis;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinorAxis() {
/*   85 */     return (this.axis == 0) ? 1 : 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getTopInset() {
/*   92 */     return this.topInset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTopInset(float paramFloat) {
/*  101 */     this.topInset = paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getBottomInset() {
/*  108 */     return this.bottomInset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBottomInset(float paramFloat) {
/*  117 */     this.bottomInset = paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getLeftInset() {
/*  124 */     return this.leftInset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLeftInset(float paramFloat) {
/*  133 */     this.leftInset = paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getRightInset() {
/*  140 */     return this.rightInset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRightInset(float paramFloat) {
/*  149 */     this.rightInset = paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float getInsetSpan(int paramInt) {
/*  160 */     return (paramInt == 0) ? (
/*  161 */       getLeftInset() + getRightInset()) : (getTopInset() + getBottomInset());
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
/*      */   protected void setEstimatedMajorSpan(boolean paramBoolean) {
/*  179 */     this.estimatedMajorSpan = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean getEstimatedMajorSpan() {
/*  188 */     return this.estimatedMajorSpan;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ChildState getChildState(int paramInt) {
/*  199 */     synchronized (this.stats) {
/*  200 */       if (paramInt >= 0 && paramInt < this.stats.size()) {
/*  201 */         return this.stats.get(paramInt);
/*      */       }
/*  203 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected LayoutQueue getLayoutQueue() {
/*  211 */     return LayoutQueue.getDefaultQueue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ChildState createChildState(View paramView) {
/*  220 */     return new ChildState(paramView);
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
/*      */   protected synchronized void majorRequirementChange(ChildState paramChildState, float paramFloat) {
/*  242 */     if (!this.estimatedMajorSpan) {
/*  243 */       this.majorSpan += paramFloat;
/*      */     }
/*  245 */     this.majorChanged = true;
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
/*      */   protected synchronized void minorRequirementChange(ChildState paramChildState) {
/*  259 */     this.minorChanged = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void flushRequirementChanges() {
/*  267 */     AbstractDocument abstractDocument = (AbstractDocument)getDocument();
/*      */     try {
/*  269 */       abstractDocument.readLock();
/*      */       
/*  271 */       View view = null;
/*  272 */       boolean bool1 = false;
/*  273 */       boolean bool2 = false;
/*      */       
/*  275 */       synchronized (this) {
/*      */ 
/*      */         
/*  278 */         synchronized (this.stats) {
/*  279 */           int i = getViewCount();
/*  280 */           if (i > 0 && (this.minorChanged || this.estimatedMajorSpan)) {
/*  281 */             LayoutQueue layoutQueue = getLayoutQueue();
/*  282 */             ChildState childState1 = getChildState(0);
/*  283 */             ChildState childState2 = getChildState(0);
/*  284 */             float f = 0.0F;
/*  285 */             for (byte b = 1; b < i; b++) {
/*  286 */               ChildState childState = getChildState(b);
/*  287 */               if (this.minorChanged) {
/*  288 */                 if (childState.min > childState1.min) {
/*  289 */                   childState1 = childState;
/*      */                 }
/*  291 */                 if (childState.pref > childState2.pref) {
/*  292 */                   childState2 = childState;
/*      */                 }
/*      */               } 
/*  295 */               if (this.estimatedMajorSpan) {
/*  296 */                 f += childState.getMajorSpan();
/*      */               }
/*      */             } 
/*      */             
/*  300 */             if (this.minorChanged) {
/*  301 */               this.minRequest = childState1;
/*  302 */               this.prefRequest = childState2;
/*      */             } 
/*  304 */             if (this.estimatedMajorSpan) {
/*  305 */               this.majorSpan = f;
/*  306 */               this.estimatedMajorSpan = false;
/*  307 */               this.majorChanged = true;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  313 */         if (this.majorChanged || this.minorChanged) {
/*  314 */           view = getParent();
/*  315 */           if (view != null) {
/*  316 */             if (this.axis == 0) {
/*  317 */               bool1 = this.majorChanged;
/*  318 */               bool2 = this.minorChanged;
/*      */             } else {
/*  320 */               bool2 = this.majorChanged;
/*  321 */               bool1 = this.minorChanged;
/*      */             } 
/*      */           }
/*  324 */           this.majorChanged = false;
/*  325 */           this.minorChanged = false;
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  331 */       if (view != null) {
/*  332 */         view.preferenceChanged(this, bool1, bool2);
/*      */ 
/*      */         
/*  335 */         Container container = getContainer();
/*  336 */         if (container != null) {
/*  337 */           container.repaint();
/*      */         }
/*      */       } 
/*      */     } finally {
/*  341 */       abstractDocument.readUnlock();
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
/*      */   public void replace(int paramInt1, int paramInt2, View[] paramArrayOfView) {
/*  358 */     synchronized (this.stats) {
/*      */       
/*  360 */       for (byte b = 0; b < paramInt2; b++) {
/*  361 */         ChildState childState = this.stats.remove(paramInt1);
/*  362 */         float f = childState.getMajorSpan();
/*      */         
/*  364 */         childState.getChildView().setParent(null);
/*  365 */         if (f != 0.0F) {
/*  366 */           majorRequirementChange(childState, -f);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  371 */       LayoutQueue layoutQueue = getLayoutQueue();
/*  372 */       if (paramArrayOfView != null) {
/*  373 */         for (byte b1 = 0; b1 < paramArrayOfView.length; b1++) {
/*  374 */           ChildState childState = createChildState(paramArrayOfView[b1]);
/*  375 */           this.stats.add(paramInt1 + b1, childState);
/*  376 */           layoutQueue.addTask(childState);
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*  381 */       layoutQueue.addTask(this.flushTask);
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
/*      */   protected void loadChildren(ViewFactory paramViewFactory) {
/*  404 */     Element element = getElement();
/*  405 */     int i = element.getElementCount();
/*  406 */     if (i > 0) {
/*  407 */       View[] arrayOfView = new View[i];
/*  408 */       for (byte b = 0; b < i; b++) {
/*  409 */         arrayOfView[b] = paramViewFactory.create(element.getElement(b));
/*      */       }
/*  411 */       replace(0, 0, arrayOfView);
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
/*      */   protected synchronized int getViewIndexAtPosition(int paramInt, Position.Bias paramBias) {
/*  425 */     boolean bool = (paramBias == Position.Bias.Backward) ? true : false;
/*  426 */     paramInt = bool ? Math.max(0, paramInt - 1) : paramInt;
/*  427 */     Element element = getElement();
/*  428 */     return element.getElementIndex(paramInt);
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
/*      */   protected void updateLayout(DocumentEvent.ElementChange paramElementChange, DocumentEvent paramDocumentEvent, Shape paramShape) {
/*  447 */     if (paramElementChange != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  452 */       int i = Math.max(paramElementChange.getIndex() - 1, 0);
/*  453 */       ChildState childState = getChildState(i);
/*  454 */       this.locator.childChanged(childState);
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
/*      */   public void setParent(View paramView) {
/*  476 */     super.setParent(paramView);
/*  477 */     if (paramView != null && getViewCount() == 0) {
/*  478 */       ViewFactory viewFactory = getViewFactory();
/*  479 */       loadChildren(viewFactory);
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
/*      */   public synchronized void preferenceChanged(View paramView, boolean paramBoolean1, boolean paramBoolean2) {
/*  496 */     if (paramView == null) {
/*  497 */       getParent().preferenceChanged(this, paramBoolean1, paramBoolean2);
/*      */     } else {
/*  499 */       if (this.changing != null) {
/*  500 */         View view = this.changing.getChildView();
/*  501 */         if (view == paramView) {
/*      */ 
/*      */           
/*  504 */           this.changing.preferenceChanged(paramBoolean1, paramBoolean2);
/*      */           return;
/*      */         } 
/*      */       } 
/*  508 */       int i = getViewIndex(paramView.getStartOffset(), Position.Bias.Forward);
/*      */       
/*  510 */       ChildState childState = getChildState(i);
/*  511 */       childState.preferenceChanged(paramBoolean1, paramBoolean2);
/*  512 */       LayoutQueue layoutQueue = getLayoutQueue();
/*  513 */       layoutQueue.addTask(childState);
/*  514 */       layoutQueue.addTask(this.flushTask);
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
/*      */   public void setSize(float paramFloat1, float paramFloat2) {
/*  532 */     setSpanOnAxis(0, paramFloat1);
/*  533 */     setSpanOnAxis(1, paramFloat2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   float getSpanOnAxis(int paramInt) {
/*  544 */     if (paramInt == getMajorAxis()) {
/*  545 */       return this.majorSpan;
/*      */     }
/*  547 */     return this.minorSpan;
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
/*      */   void setSpanOnAxis(int paramInt, float paramFloat) {
/*  562 */     float f = getInsetSpan(paramInt);
/*  563 */     if (paramInt == getMinorAxis()) {
/*  564 */       float f1 = paramFloat - f;
/*  565 */       if (f1 != this.minorSpan) {
/*  566 */         this.minorSpan = f1;
/*      */ 
/*      */ 
/*      */         
/*  570 */         int i = getViewCount();
/*  571 */         if (i != 0) {
/*  572 */           LayoutQueue layoutQueue = getLayoutQueue();
/*  573 */           for (byte b = 0; b < i; b++) {
/*  574 */             ChildState childState = getChildState(b);
/*  575 */             childState.childSizeValid = false;
/*  576 */             layoutQueue.addTask(childState);
/*      */           } 
/*  578 */           layoutQueue.addTask(this.flushTask);
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  585 */     else if (this.estimatedMajorSpan) {
/*  586 */       this.majorSpan = paramFloat - f;
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
/*      */   public void paint(Graphics paramGraphics, Shape paramShape) {
/*  610 */     synchronized (this.locator) {
/*  611 */       this.locator.setAllocation(paramShape);
/*  612 */       this.locator.paintChildren(paramGraphics);
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
/*      */   public float getPreferredSpan(int paramInt) {
/*  628 */     float f = getInsetSpan(paramInt);
/*  629 */     if (paramInt == this.axis) {
/*  630 */       return this.majorSpan + f;
/*      */     }
/*  632 */     if (this.prefRequest != null) {
/*  633 */       View view = this.prefRequest.getChildView();
/*  634 */       return view.getPreferredSpan(paramInt) + f;
/*      */     } 
/*      */ 
/*      */     
/*  638 */     return f + 30.0F;
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
/*      */   public float getMinimumSpan(int paramInt) {
/*  653 */     if (paramInt == this.axis) {
/*  654 */       return getPreferredSpan(paramInt);
/*      */     }
/*  656 */     if (this.minRequest != null) {
/*  657 */       View view = this.minRequest.getChildView();
/*  658 */       return view.getMinimumSpan(paramInt);
/*      */     } 
/*      */ 
/*      */     
/*  662 */     if (paramInt == 0) {
/*  663 */       return getLeftInset() + getRightInset() + 5.0F;
/*      */     }
/*  665 */     return getTopInset() + getBottomInset() + 5.0F;
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
/*      */   public float getMaximumSpan(int paramInt) {
/*  681 */     if (paramInt == this.axis) {
/*  682 */       return getPreferredSpan(paramInt);
/*      */     }
/*  684 */     return 2.14748365E9F;
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
/*      */   public int getViewCount() {
/*  697 */     synchronized (this.stats) {
/*  698 */       return this.stats.size();
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
/*      */   public View getView(int paramInt) {
/*  710 */     ChildState childState = getChildState(paramInt);
/*  711 */     if (childState != null) {
/*  712 */       return childState.getChildView();
/*      */     }
/*  714 */     return null;
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
/*      */   public Shape getChildAllocation(int paramInt, Shape paramShape) {
/*  729 */     return this.locator.getChildAllocation(paramInt, paramShape);
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
/*      */   public int getViewIndex(int paramInt, Position.Bias paramBias) {
/*  745 */     return getViewIndexAtPosition(paramInt, paramBias);
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
/*      */   public Shape modelToView(int paramInt, Shape paramShape, Position.Bias paramBias) throws BadLocationException {
/*  764 */     int i = getViewIndex(paramInt, paramBias);
/*  765 */     Shape shape = this.locator.getChildAllocation(i, paramShape);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  770 */     ChildState childState = getChildState(i);
/*  771 */     synchronized (childState) {
/*  772 */       View view = childState.getChildView();
/*  773 */       return view.modelToView(paramInt, shape, paramBias);
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
/*      */   public int viewToModel(float paramFloat1, float paramFloat2, Shape paramShape, Position.Bias[] paramArrayOfBias) {
/*      */     int i, j;
/*      */     Shape shape;
/*  809 */     synchronized (this.locator) {
/*  810 */       j = this.locator.getViewIndexAtPoint(paramFloat1, paramFloat2, paramShape);
/*  811 */       shape = this.locator.getChildAllocation(j, paramShape);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  817 */     ChildState childState = getChildState(j);
/*  818 */     synchronized (childState) {
/*  819 */       View view = childState.getChildView();
/*  820 */       i = view.viewToModel(paramFloat1, paramFloat2, shape, paramArrayOfBias);
/*      */     } 
/*  822 */     return i;
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
/*  857 */     if (paramInt1 < -1) {
/*  858 */       throw new BadLocationException("invalid position", paramInt1);
/*      */     }
/*  860 */     return Utilities.getNextVisualPositionFrom(this, paramInt1, paramBias, paramShape, paramInt2, paramArrayOfBias);
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
/*      */   public class ChildLocator
/*      */   {
/*      */     protected AsyncBoxView.ChildState lastValidOffset;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  939 */     protected Rectangle lastAlloc = new Rectangle();
/*  940 */     protected Rectangle childAlloc = new Rectangle();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public synchronized void childChanged(AsyncBoxView.ChildState param1ChildState) {
/*  951 */       if (this.lastValidOffset == null) {
/*  952 */         this.lastValidOffset = param1ChildState;
/*  953 */       } else if (param1ChildState.getChildView().getStartOffset() < this.lastValidOffset
/*  954 */         .getChildView().getStartOffset()) {
/*  955 */         this.lastValidOffset = param1ChildState;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public synchronized void paintChildren(Graphics param1Graphics) {
/*  963 */       Rectangle rectangle = param1Graphics.getClipBounds();
/*  964 */       float f1 = (AsyncBoxView.this.axis == 0) ? (rectangle.x - this.lastAlloc.x) : (rectangle.y - this.lastAlloc.y);
/*      */       
/*  966 */       int i = getViewIndexAtVisualOffset(f1);
/*  967 */       int j = AsyncBoxView.this.getViewCount();
/*  968 */       float f2 = AsyncBoxView.this.getChildState(i).getMajorOffset();
/*  969 */       for (int k = i; k < j; ) {
/*  970 */         AsyncBoxView.ChildState childState = AsyncBoxView.this.getChildState(k);
/*  971 */         childState.setMajorOffset(f2);
/*  972 */         Shape shape = getChildAllocation(k);
/*  973 */         if (intersectsClip(shape, rectangle)) {
/*  974 */           synchronized (childState) {
/*  975 */             View view = childState.getChildView();
/*  976 */             view.paint(param1Graphics, shape);
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  982 */           f2 += childState.getMajorSpan();
/*      */           k++;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public synchronized Shape getChildAllocation(int param1Int, Shape param1Shape) {
/*  992 */       if (param1Shape == null) {
/*  993 */         return null;
/*      */       }
/*  995 */       setAllocation(param1Shape);
/*  996 */       AsyncBoxView.ChildState childState = AsyncBoxView.this.getChildState(param1Int);
/*  997 */       if (this.lastValidOffset == null) {
/*  998 */         this.lastValidOffset = AsyncBoxView.this.getChildState(0);
/*      */       }
/* 1000 */       if (childState.getChildView().getStartOffset() > this.lastValidOffset
/* 1001 */         .getChildView().getStartOffset())
/*      */       {
/* 1003 */         updateChildOffsetsToIndex(param1Int);
/*      */       }
/* 1005 */       return getChildAllocation(param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getViewIndexAtPoint(float param1Float1, float param1Float2, Shape param1Shape) {
/* 1024 */       setAllocation(param1Shape);
/* 1025 */       float f = (AsyncBoxView.this.axis == 0) ? (param1Float1 - this.lastAlloc.x) : (param1Float2 - this.lastAlloc.y);
/* 1026 */       return getViewIndexAtVisualOffset(f);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Shape getChildAllocation(int param1Int) {
/* 1036 */       AsyncBoxView.ChildState childState = AsyncBoxView.this.getChildState(param1Int);
/* 1037 */       if (!childState.isLayoutValid()) {
/* 1038 */         childState.run();
/*      */       }
/* 1040 */       if (AsyncBoxView.this.axis == 0) {
/* 1041 */         this.lastAlloc.x += (int)childState.getMajorOffset();
/* 1042 */         this.lastAlloc.y += (int)childState.getMinorOffset();
/* 1043 */         this.childAlloc.width = (int)childState.getMajorSpan();
/* 1044 */         this.childAlloc.height = (int)childState.getMinorSpan();
/*      */       } else {
/* 1046 */         this.lastAlloc.y += (int)childState.getMajorOffset();
/* 1047 */         this.lastAlloc.x += (int)childState.getMinorOffset();
/* 1048 */         this.childAlloc.height = (int)childState.getMajorSpan();
/* 1049 */         this.childAlloc.width = (int)childState.getMinorSpan();
/*      */       } 
/* 1051 */       this.childAlloc.x += (int)AsyncBoxView.this.getLeftInset();
/* 1052 */       this.childAlloc.y += (int)AsyncBoxView.this.getRightInset();
/* 1053 */       return this.childAlloc;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void setAllocation(Shape param1Shape) {
/* 1062 */       if (param1Shape instanceof Rectangle) {
/* 1063 */         this.lastAlloc.setBounds((Rectangle)param1Shape);
/*      */       } else {
/* 1065 */         this.lastAlloc.setBounds(param1Shape.getBounds());
/*      */       } 
/* 1067 */       AsyncBoxView.this.setSize(this.lastAlloc.width, this.lastAlloc.height);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected int getViewIndexAtVisualOffset(float param1Float) {
/* 1081 */       int i = AsyncBoxView.this.getViewCount();
/* 1082 */       if (i > 0) {
/* 1083 */         boolean bool = (this.lastValidOffset != null) ? true : false;
/*      */         
/* 1085 */         if (this.lastValidOffset == null) {
/* 1086 */           this.lastValidOffset = AsyncBoxView.this.getChildState(0);
/*      */         }
/* 1088 */         if (param1Float > AsyncBoxView.this.majorSpan) {
/*      */           
/* 1090 */           if (!bool) {
/* 1091 */             return 0;
/*      */           }
/* 1093 */           int j = this.lastValidOffset.getChildView().getStartOffset();
/* 1094 */           return AsyncBoxView.this.getViewIndex(j, Position.Bias.Forward);
/*      */         } 
/* 1096 */         if (param1Float > this.lastValidOffset.getMajorOffset())
/*      */         {
/* 1098 */           return updateChildOffsets(param1Float);
/*      */         }
/*      */ 
/*      */         
/* 1102 */         float f = 0.0F;
/* 1103 */         for (byte b = 0; b < i; b++) {
/* 1104 */           AsyncBoxView.ChildState childState = AsyncBoxView.this.getChildState(b);
/* 1105 */           float f1 = f + childState.getMajorSpan();
/* 1106 */           if (param1Float < f1) {
/* 1107 */             return b;
/*      */           }
/* 1109 */           f = f1;
/*      */         } 
/*      */       } 
/*      */       
/* 1113 */       return i - 1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int updateChildOffsets(float param1Float) {
/* 1121 */       int i = AsyncBoxView.this.getViewCount();
/* 1122 */       int j = i - 1;
/* 1123 */       int k = this.lastValidOffset.getChildView().getStartOffset();
/* 1124 */       int m = AsyncBoxView.this.getViewIndex(k, Position.Bias.Forward);
/* 1125 */       float f1 = this.lastValidOffset.getMajorOffset();
/* 1126 */       float f2 = f1;
/* 1127 */       for (int n = m; n < i; n++) {
/* 1128 */         AsyncBoxView.ChildState childState = AsyncBoxView.this.getChildState(n);
/* 1129 */         childState.setMajorOffset(f2);
/* 1130 */         f2 += childState.getMajorSpan();
/* 1131 */         if (param1Float < f2) {
/* 1132 */           j = n;
/* 1133 */           this.lastValidOffset = childState;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/* 1138 */       return j;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void updateChildOffsetsToIndex(int param1Int) {
/* 1146 */       int i = this.lastValidOffset.getChildView().getStartOffset();
/* 1147 */       int j = AsyncBoxView.this.getViewIndex(i, Position.Bias.Forward);
/* 1148 */       float f = this.lastValidOffset.getMajorOffset();
/* 1149 */       for (int k = j; k <= param1Int; k++) {
/* 1150 */         AsyncBoxView.ChildState childState = AsyncBoxView.this.getChildState(k);
/* 1151 */         childState.setMajorOffset(f);
/* 1152 */         f += childState.getMajorSpan();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     boolean intersectsClip(Shape param1Shape, Rectangle param1Rectangle) {
/* 1158 */       Rectangle rectangle = (param1Shape instanceof Rectangle) ? (Rectangle)param1Shape : param1Shape.getBounds();
/* 1159 */       if (rectangle.intersects(param1Rectangle))
/*      */       {
/*      */         
/* 1162 */         return this.lastAlloc.intersects(rectangle);
/*      */       }
/* 1164 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class ChildState
/*      */     implements Runnable
/*      */   {
/*      */     private float min;
/*      */ 
/*      */ 
/*      */     
/*      */     private float pref;
/*      */ 
/*      */ 
/*      */     
/*      */     private float max;
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean minorValid;
/*      */ 
/*      */ 
/*      */     
/*      */     private float span;
/*      */ 
/*      */     
/*      */     private float offset;
/*      */ 
/*      */     
/*      */     private boolean majorValid;
/*      */ 
/*      */     
/*      */     private View child;
/*      */ 
/*      */     
/*      */     private boolean childSizeValid;
/*      */ 
/*      */ 
/*      */     
/*      */     public ChildState(View param1View) {
/* 1207 */       this.child = param1View;
/* 1208 */       this.minorValid = false;
/* 1209 */       this.majorValid = false;
/* 1210 */       this.childSizeValid = false;
/* 1211 */       this.child.setParent(AsyncBoxView.this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public View getChildView() {
/* 1218 */       return this.child;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void run() {
/* 1243 */       AbstractDocument abstractDocument = (AbstractDocument)AsyncBoxView.this.getDocument();
/*      */       try {
/* 1245 */         abstractDocument.readLock();
/* 1246 */         if (this.minorValid && this.majorValid && this.childSizeValid) {
/*      */           return;
/*      */         }
/*      */         
/* 1250 */         if (this.child.getParent() == AsyncBoxView.this) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1255 */           synchronized (AsyncBoxView.this) {
/* 1256 */             AsyncBoxView.this.changing = this;
/*      */           } 
/* 1258 */           updateChild();
/* 1259 */           synchronized (AsyncBoxView.this) {
/* 1260 */             AsyncBoxView.this.changing = null;
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1266 */           updateChild();
/*      */         } 
/*      */       } finally {
/* 1269 */         abstractDocument.readUnlock();
/*      */       } 
/*      */     }
/*      */     
/*      */     void updateChild() {
/* 1274 */       boolean bool1 = false;
/* 1275 */       synchronized (this) {
/* 1276 */         if (!this.minorValid) {
/* 1277 */           int i = AsyncBoxView.this.getMinorAxis();
/* 1278 */           this.min = this.child.getMinimumSpan(i);
/* 1279 */           this.pref = this.child.getPreferredSpan(i);
/* 1280 */           this.max = this.child.getMaximumSpan(i);
/* 1281 */           this.minorValid = true;
/* 1282 */           bool1 = true;
/*      */         } 
/*      */       } 
/* 1285 */       if (bool1) {
/* 1286 */         AsyncBoxView.this.minorRequirementChange(this);
/*      */       }
/*      */       
/* 1289 */       boolean bool2 = false;
/* 1290 */       float f = 0.0F;
/* 1291 */       synchronized (this) {
/* 1292 */         if (!this.majorValid) {
/* 1293 */           float f1 = this.span;
/* 1294 */           this.span = this.child.getPreferredSpan(AsyncBoxView.this.axis);
/* 1295 */           f = this.span - f1;
/* 1296 */           this.majorValid = true;
/* 1297 */           bool2 = true;
/*      */         } 
/*      */       } 
/* 1300 */       if (bool2) {
/* 1301 */         AsyncBoxView.this.majorRequirementChange(this, f);
/* 1302 */         AsyncBoxView.this.locator.childChanged(this);
/*      */       } 
/*      */       
/* 1305 */       synchronized (this) {
/* 1306 */         if (!this.childSizeValid) {
/*      */           float f1, f2;
/*      */           
/* 1309 */           if (AsyncBoxView.this.axis == 0) {
/* 1310 */             f1 = this.span;
/* 1311 */             f2 = getMinorSpan();
/*      */           } else {
/* 1313 */             f1 = getMinorSpan();
/* 1314 */             f2 = this.span;
/*      */           } 
/* 1316 */           this.childSizeValid = true;
/* 1317 */           this.child.setSize(f1, f2);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float getMinorSpan() {
/* 1327 */       if (this.max < AsyncBoxView.this.minorSpan) {
/* 1328 */         return this.max;
/*      */       }
/*      */       
/* 1331 */       return Math.max(this.min, AsyncBoxView.this.minorSpan);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float getMinorOffset() {
/* 1338 */       if (this.max < AsyncBoxView.this.minorSpan) {
/*      */         
/* 1340 */         float f = this.child.getAlignment(AsyncBoxView.this.getMinorAxis());
/* 1341 */         return (AsyncBoxView.this.minorSpan - this.max) * f;
/*      */       } 
/* 1343 */       return 0.0F;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float getMajorSpan() {
/* 1350 */       return this.span;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float getMajorOffset() {
/* 1357 */       return this.offset;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setMajorOffset(float param1Float) {
/* 1366 */       this.offset = param1Float;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void preferenceChanged(boolean param1Boolean1, boolean param1Boolean2) {
/* 1377 */       if (AsyncBoxView.this.axis == 0) {
/* 1378 */         if (param1Boolean1) {
/* 1379 */           this.majorValid = false;
/*      */         }
/* 1381 */         if (param1Boolean2) {
/* 1382 */           this.minorValid = false;
/*      */         }
/*      */       } else {
/* 1385 */         if (param1Boolean1) {
/* 1386 */           this.minorValid = false;
/*      */         }
/* 1388 */         if (param1Boolean2) {
/* 1389 */           this.majorValid = false;
/*      */         }
/*      */       } 
/* 1392 */       this.childSizeValid = false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isLayoutValid() {
/* 1399 */       return (this.minorValid && this.majorValid && this.childSizeValid);
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
/*      */   class FlushTask
/*      */     implements Runnable
/*      */   {
/*      */     public void run() {
/* 1423 */       AsyncBoxView.this.flushRequirementChanges();
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/AsyncBoxView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */