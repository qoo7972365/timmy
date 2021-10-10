/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import java.util.Vector;
/*     */ import javax.swing.SizeRequirements;
/*     */ import javax.swing.event.DocumentEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class FlowView
/*     */   extends BoxView
/*     */ {
/*     */   protected int layoutSpan;
/*     */   protected View layoutPool;
/*     */   protected FlowStrategy strategy;
/*     */   
/*     */   public FlowView(Element paramElement, int paramInt) {
/*  61 */     super(paramElement, paramInt);
/*  62 */     this.layoutSpan = Integer.MAX_VALUE;
/*  63 */     this.strategy = new FlowStrategy();
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
/*     */   public int getFlowAxis() {
/*  75 */     if (getAxis() == 1) {
/*  76 */       return 0;
/*     */     }
/*  78 */     return 1;
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
/*     */   public int getFlowSpan(int paramInt) {
/*  95 */     return this.layoutSpan;
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
/*     */   public int getFlowStart(int paramInt) {
/* 110 */     return 0;
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
/*     */   protected abstract View createRow();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void loadChildren(ViewFactory paramViewFactory) {
/* 136 */     if (this.layoutPool == null) {
/* 137 */       this.layoutPool = new LogicalView(getElement());
/*     */     }
/* 139 */     this.layoutPool.setParent(this);
/*     */ 
/*     */ 
/*     */     
/* 143 */     this.strategy.insertUpdate(this, null, null);
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
/*     */   protected int getViewIndexAtPosition(int paramInt) {
/* 155 */     if (paramInt >= getStartOffset() && paramInt < getEndOffset()) {
/* 156 */       for (byte b = 0; b < getViewCount(); b++) {
/* 157 */         View view = getView(b);
/* 158 */         if (paramInt >= view.getStartOffset() && paramInt < view
/* 159 */           .getEndOffset()) {
/* 160 */           return b;
/*     */         }
/*     */       } 
/*     */     }
/* 164 */     return -1;
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
/*     */   protected void layout(int paramInt1, int paramInt2) {
/* 184 */     int j, i = getFlowAxis();
/*     */     
/* 186 */     if (i == 0) {
/* 187 */       j = paramInt1;
/*     */     } else {
/* 189 */       j = paramInt2;
/*     */     } 
/* 191 */     if (this.layoutSpan != j) {
/* 192 */       layoutChanged(i);
/* 193 */       layoutChanged(getAxis());
/* 194 */       this.layoutSpan = j;
/*     */     } 
/*     */ 
/*     */     
/* 198 */     if (!isLayoutValid(i)) {
/* 199 */       int k = getAxis();
/* 200 */       int m = (k == 0) ? getWidth() : getHeight();
/* 201 */       this.strategy.layout(this);
/* 202 */       int n = (int)getPreferredSpan(k);
/* 203 */       if (m != n) {
/* 204 */         View view = getParent();
/* 205 */         if (view != null) {
/* 206 */           view.preferenceChanged(this, (k == 0), (k == 1));
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 212 */         Container container = getContainer();
/* 213 */         if (container != null)
/*     */         {
/* 215 */           container.repaint();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 220 */     super.layout(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SizeRequirements calculateMinorAxisRequirements(int paramInt, SizeRequirements paramSizeRequirements) {
/* 230 */     if (paramSizeRequirements == null) {
/* 231 */       paramSizeRequirements = new SizeRequirements();
/*     */     }
/* 233 */     float f1 = this.layoutPool.getPreferredSpan(paramInt);
/* 234 */     float f2 = this.layoutPool.getMinimumSpan(paramInt);
/*     */     
/* 236 */     paramSizeRequirements.minimum = (int)f2;
/* 237 */     paramSizeRequirements.preferred = Math.max(paramSizeRequirements.minimum, (int)f1);
/* 238 */     paramSizeRequirements.maximum = Integer.MAX_VALUE;
/* 239 */     paramSizeRequirements.alignment = 0.5F;
/* 240 */     return paramSizeRequirements;
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
/*     */   public void insertUpdate(DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/* 255 */     this.layoutPool.insertUpdate(paramDocumentEvent, paramShape, paramViewFactory);
/* 256 */     this.strategy.insertUpdate(this, paramDocumentEvent, getInsideAllocation(paramShape));
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
/*     */   public void removeUpdate(DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/* 269 */     this.layoutPool.removeUpdate(paramDocumentEvent, paramShape, paramViewFactory);
/* 270 */     this.strategy.removeUpdate(this, paramDocumentEvent, getInsideAllocation(paramShape));
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
/*     */   public void changedUpdate(DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/* 283 */     this.layoutPool.changedUpdate(paramDocumentEvent, paramShape, paramViewFactory);
/* 284 */     this.strategy.changedUpdate(this, paramDocumentEvent, getInsideAllocation(paramShape));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParent(View paramView) {
/* 289 */     super.setParent(paramView);
/* 290 */     if (paramView == null && this.layoutPool != null)
/*     */     {
/* 292 */       this.layoutPool.setParent(null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class FlowStrategy
/*     */   {
/* 336 */     Position damageStart = null;
/*     */     Vector<View> viewBuffer;
/*     */     
/*     */     void addDamage(FlowView param1FlowView, int param1Int) {
/* 340 */       if (param1Int >= param1FlowView.getStartOffset() && param1Int < param1FlowView.getEndOffset() && (
/* 341 */         this.damageStart == null || param1Int < this.damageStart.getOffset())) {
/*     */         try {
/* 343 */           this.damageStart = param1FlowView.getDocument().createPosition(param1Int);
/* 344 */         } catch (BadLocationException badLocationException) {
/*     */           assert false;
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void unsetDamage() {
/* 353 */       this.damageStart = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void insertUpdate(FlowView param1FlowView, DocumentEvent param1DocumentEvent, Rectangle param1Rectangle) {
/* 370 */       if (param1DocumentEvent != null) {
/* 371 */         addDamage(param1FlowView, param1DocumentEvent.getOffset());
/*     */       }
/*     */       
/* 374 */       if (param1Rectangle != null) {
/* 375 */         Container container = param1FlowView.getContainer();
/* 376 */         if (container != null) {
/* 377 */           container.repaint(param1Rectangle.x, param1Rectangle.y, param1Rectangle.width, param1Rectangle.height);
/*     */         }
/*     */       } else {
/* 380 */         param1FlowView.preferenceChanged((View)null, true, true);
/*     */       } 
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
/*     */     public void removeUpdate(FlowView param1FlowView, DocumentEvent param1DocumentEvent, Rectangle param1Rectangle) {
/* 393 */       addDamage(param1FlowView, param1DocumentEvent.getOffset());
/* 394 */       if (param1Rectangle != null) {
/* 395 */         Container container = param1FlowView.getContainer();
/* 396 */         if (container != null) {
/* 397 */           container.repaint(param1Rectangle.x, param1Rectangle.y, param1Rectangle.width, param1Rectangle.height);
/*     */         }
/*     */       } else {
/* 400 */         param1FlowView.preferenceChanged((View)null, true, true);
/*     */       } 
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
/*     */     
/*     */     public void changedUpdate(FlowView param1FlowView, DocumentEvent param1DocumentEvent, Rectangle param1Rectangle) {
/* 415 */       addDamage(param1FlowView, param1DocumentEvent.getOffset());
/* 416 */       if (param1Rectangle != null) {
/* 417 */         Container container = param1FlowView.getContainer();
/* 418 */         if (container != null) {
/* 419 */           container.repaint(param1Rectangle.x, param1Rectangle.y, param1Rectangle.width, param1Rectangle.height);
/*     */         }
/*     */       } else {
/* 422 */         param1FlowView.preferenceChanged((View)null, true, true);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected View getLogicalView(FlowView param1FlowView) {
/* 431 */       return param1FlowView.layoutPool;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void layout(FlowView param1FlowView) {
/*     */       byte b;
/*     */       int i;
/* 443 */       View view = getLogicalView(param1FlowView);
/*     */       
/* 445 */       int j = param1FlowView.getEndOffset();
/*     */       
/* 447 */       if (param1FlowView.majorAllocValid) {
/* 448 */         if (this.damageStart == null) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 453 */         int m = this.damageStart.getOffset();
/* 454 */         while ((b = param1FlowView.getViewIndexAtPosition(m)) < 0) {
/* 455 */           m--;
/*     */         }
/* 457 */         if (b > 0) {
/* 458 */           b--;
/*     */         }
/* 460 */         i = param1FlowView.getView(b).getStartOffset();
/*     */       } else {
/* 462 */         b = 0;
/* 463 */         i = param1FlowView.getStartOffset();
/*     */       } 
/* 465 */       reparentViews(view, i);
/*     */       
/* 467 */       this.viewBuffer = new Vector<>(10, 10);
/* 468 */       int k = param1FlowView.getViewCount();
/* 469 */       while (i < j) {
/*     */         
/* 471 */         if (b >= k) {
/* 472 */           View view1 = param1FlowView.createRow();
/* 473 */           param1FlowView.append(view1);
/*     */         } else {
/* 475 */           View view1 = param1FlowView.getView(b);
/*     */         } 
/* 477 */         i = layoutRow(param1FlowView, b, i);
/* 478 */         b++;
/*     */       } 
/* 480 */       this.viewBuffer = null;
/*     */       
/* 482 */       if (b < k) {
/* 483 */         param1FlowView.replace(b, k - b, (View[])null);
/*     */       }
/* 485 */       unsetDamage();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected int layoutRow(FlowView param1FlowView, int param1Int1, int param1Int2) {
/* 505 */       View view = param1FlowView.getView(param1Int1);
/* 506 */       float f1 = param1FlowView.getFlowStart(param1Int1);
/* 507 */       float f2 = param1FlowView.getFlowSpan(param1Int1);
/* 508 */       int i = param1FlowView.getEndOffset();
/* 509 */       TabExpander tabExpander = (param1FlowView instanceof TabExpander) ? (TabExpander)param1FlowView : null;
/* 510 */       int j = param1FlowView.getFlowAxis();
/*     */       
/* 512 */       int k = 0;
/* 513 */       float f3 = 0.0F;
/* 514 */       float f4 = 0.0F;
/* 515 */       byte b = -1;
/* 516 */       byte b1 = 0;
/*     */       
/* 518 */       this.viewBuffer.clear();
/* 519 */       while (param1Int2 < i && f2 >= 0.0F) {
/* 520 */         float f; View view1 = createView(param1FlowView, param1Int2, (int)f2, param1Int1);
/* 521 */         if (view1 == null) {
/*     */           break;
/*     */         }
/*     */         
/* 525 */         int m = view1.getBreakWeight(j, f1, f2);
/* 526 */         if (m >= 3000) {
/* 527 */           View view2 = view1.breakView(j, param1Int2, f1, f2);
/* 528 */           if (view2 != null) {
/* 529 */             this.viewBuffer.add(view2); break;
/* 530 */           }  if (!b1)
/*     */           {
/*     */             
/* 533 */             this.viewBuffer.add(view1); } 
/*     */           break;
/*     */         } 
/* 536 */         if (m >= k && m > 0) {
/* 537 */           k = m;
/* 538 */           f3 = f1;
/* 539 */           f4 = f2;
/* 540 */           b = b1;
/*     */         } 
/*     */ 
/*     */         
/* 544 */         if (j == 0 && view1 instanceof TabableView) {
/* 545 */           f = ((TabableView)view1).getTabbedSpan(f1, tabExpander);
/*     */         } else {
/* 547 */           f = view1.getPreferredSpan(j);
/*     */         } 
/*     */         
/* 550 */         if (f > f2 && b >= 0) {
/*     */           
/* 552 */           if (b < b1) {
/* 553 */             view1 = this.viewBuffer.get(b);
/*     */           }
/* 555 */           for (int n = b1 - 1; n >= b; n--) {
/* 556 */             this.viewBuffer.remove(n);
/*     */           }
/* 558 */           view1 = view1.breakView(j, view1.getStartOffset(), f3, f4);
/*     */         } 
/*     */         
/* 561 */         f2 -= f;
/* 562 */         f1 += f;
/* 563 */         this.viewBuffer.add(view1);
/* 564 */         param1Int2 = view1.getEndOffset();
/* 565 */         b1++;
/*     */       } 
/*     */       
/* 568 */       View[] arrayOfView = new View[this.viewBuffer.size()];
/* 569 */       this.viewBuffer.toArray(arrayOfView);
/* 570 */       view.replace(0, view.getViewCount(), arrayOfView);
/* 571 */       return (arrayOfView.length > 0) ? view.getEndOffset() : param1Int2;
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
/*     */ 
/*     */ 
/*     */     
/*     */     protected void adjustRow(FlowView param1FlowView, int param1Int1, int param1Int2, int param1Int3) {
/* 587 */       int i = param1FlowView.getFlowAxis();
/* 588 */       View view1 = param1FlowView.getView(param1Int1);
/* 589 */       int j = view1.getViewCount();
/* 590 */       int k = 0;
/* 591 */       int m = 0;
/* 592 */       int n = 0;
/* 593 */       int i1 = -1;
/*     */       int i2;
/* 595 */       for (i2 = 0; i2 < j; i2++) {
/* 596 */         View view = view1.getView(i2);
/* 597 */         int i5 = param1Int2 - k;
/*     */         
/* 599 */         int i6 = view.getBreakWeight(i, (param1Int3 + k), i5);
/* 600 */         if (i6 >= m && i6 > 0) {
/* 601 */           m = i6;
/* 602 */           i1 = i2;
/* 603 */           n = k;
/* 604 */           if (i6 >= 3000) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 610 */         k = (int)(k + view.getPreferredSpan(i));
/*     */       } 
/* 612 */       if (i1 < 0) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 619 */       i2 = param1Int2 - n;
/* 620 */       View view2 = view1.getView(i1);
/* 621 */       view2 = view2.breakView(i, view2.getStartOffset(), (param1Int3 + n), i2);
/* 622 */       View[] arrayOfView = new View[1];
/* 623 */       arrayOfView[0] = view2;
/* 624 */       View view3 = getLogicalView(param1FlowView);
/* 625 */       int i3 = view1.getView(i1).getStartOffset();
/* 626 */       int i4 = view1.getEndOffset();
/* 627 */       for (byte b = 0; b < view3.getViewCount(); b++) {
/* 628 */         View view = view3.getView(b);
/* 629 */         if (view.getEndOffset() > i4) {
/*     */           break;
/*     */         }
/* 632 */         if (view.getStartOffset() >= i3) {
/* 633 */           view.setParent(view3);
/*     */         }
/*     */       } 
/* 636 */       view1.replace(i1, j - i1, arrayOfView);
/*     */     }
/*     */     
/*     */     void reparentViews(View param1View, int param1Int) {
/* 640 */       int i = param1View.getViewIndex(param1Int, Position.Bias.Forward);
/* 641 */       if (i >= 0) {
/* 642 */         for (int j = i; j < param1View.getViewCount(); j++) {
/* 643 */           param1View.getView(j).setParent(param1View);
/*     */         }
/*     */       }
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
/*     */ 
/*     */     
/*     */     protected View createView(FlowView param1FlowView, int param1Int1, int param1Int2, int param1Int3) {
/* 660 */       View view1 = getLogicalView(param1FlowView);
/* 661 */       int i = view1.getViewIndex(param1Int1, Position.Bias.Forward);
/* 662 */       View view2 = view1.getView(i);
/* 663 */       if (param1Int1 == view2.getStartOffset())
/*     */       {
/* 665 */         return view2;
/*     */       }
/*     */ 
/*     */       
/* 669 */       view2 = view2.createFragment(param1Int1, view2.getEndOffset());
/* 670 */       return view2;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class LogicalView
/*     */     extends CompositeView
/*     */   {
/*     */     LogicalView(Element param1Element) {
/* 684 */       super(param1Element);
/*     */     }
/*     */     
/*     */     protected int getViewIndexAtPosition(int param1Int) {
/* 688 */       Element element = getElement();
/* 689 */       if (element.isLeaf()) {
/* 690 */         return 0;
/*     */       }
/* 692 */       return super.getViewIndexAtPosition(param1Int);
/*     */     }
/*     */     
/*     */     protected void loadChildren(ViewFactory param1ViewFactory) {
/* 696 */       Element element = getElement();
/* 697 */       if (element.isLeaf()) {
/* 698 */         LabelView labelView = new LabelView(element);
/* 699 */         append(labelView);
/*     */       } else {
/* 701 */         super.loadChildren(param1ViewFactory);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AttributeSet getAttributes() {
/* 711 */       View view = getParent();
/* 712 */       return (view != null) ? view.getAttributes() : null;
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
/*     */ 
/*     */     
/*     */     public float getPreferredSpan(int param1Int) {
/* 727 */       float f1 = 0.0F;
/* 728 */       float f2 = 0.0F;
/* 729 */       int i = getViewCount();
/* 730 */       for (byte b = 0; b < i; b++) {
/* 731 */         View view = getView(b);
/* 732 */         f2 += view.getPreferredSpan(param1Int);
/* 733 */         if (view.getBreakWeight(param1Int, 0.0F, 2.14748365E9F) >= 3000) {
/* 734 */           f1 = Math.max(f1, f2);
/* 735 */           f2 = 0.0F;
/*     */         } 
/*     */       } 
/* 738 */       f1 = Math.max(f1, f2);
/* 739 */       return f1;
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
/*     */ 
/*     */ 
/*     */     
/*     */     public float getMinimumSpan(int param1Int) {
/* 755 */       float f1 = 0.0F;
/* 756 */       float f2 = 0.0F;
/* 757 */       boolean bool = false;
/* 758 */       int i = getViewCount();
/* 759 */       for (byte b = 0; b < i; b++) {
/* 760 */         View view = getView(b);
/* 761 */         if (view.getBreakWeight(param1Int, 0.0F, 2.14748365E9F) == 0) {
/* 762 */           f2 += view.getPreferredSpan(param1Int);
/* 763 */           bool = true;
/* 764 */         } else if (bool) {
/* 765 */           f1 = Math.max(f2, f1);
/* 766 */           bool = false;
/* 767 */           f2 = 0.0F;
/*     */         } 
/* 769 */         if (view instanceof ComponentView) {
/* 770 */           f1 = Math.max(f1, view.getMinimumSpan(param1Int));
/*     */         }
/*     */       } 
/* 773 */       f1 = Math.max(f1, f2);
/* 774 */       return f1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void forwardUpdateToView(View param1View, DocumentEvent param1DocumentEvent, Shape param1Shape, ViewFactory param1ViewFactory) {
/* 793 */       View view = param1View.getParent();
/* 794 */       param1View.setParent(this);
/* 795 */       super.forwardUpdateToView(param1View, param1DocumentEvent, param1Shape, param1ViewFactory);
/* 796 */       param1View.setParent(view);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void forwardUpdate(DocumentEvent.ElementChange param1ElementChange, DocumentEvent param1DocumentEvent, Shape param1Shape, ViewFactory param1ViewFactory) {
/* 805 */       super.forwardUpdate(param1ElementChange, param1DocumentEvent, param1Shape, param1ViewFactory);
/*     */ 
/*     */ 
/*     */       
/* 809 */       DocumentEvent.EventType eventType = param1DocumentEvent.getType();
/* 810 */       if (eventType == DocumentEvent.EventType.INSERT || eventType == DocumentEvent.EventType.REMOVE) {
/*     */         
/* 812 */         this.firstUpdateIndex = Math.min(this.lastUpdateIndex + 1, getViewCount() - 1);
/* 813 */         this.lastUpdateIndex = Math.max(getViewCount() - 1, 0);
/* 814 */         for (int i = this.firstUpdateIndex; i <= this.lastUpdateIndex; i++) {
/* 815 */           View view = getView(i);
/* 816 */           if (view != null) {
/* 817 */             view.updateAfterChange();
/*     */           }
/*     */         } 
/*     */       } 
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
/*     */ 
/*     */ 
/*     */     
/*     */     public void paint(Graphics param1Graphics, Shape param1Shape) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean isBefore(int param1Int1, int param1Int2, Rectangle param1Rectangle) {
/* 849 */       return false;
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
/*     */     
/*     */     protected boolean isAfter(int param1Int1, int param1Int2, Rectangle param1Rectangle) {
/* 863 */       return false;
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
/*     */ 
/*     */     
/*     */     protected View getViewAtPoint(int param1Int1, int param1Int2, Rectangle param1Rectangle) {
/* 878 */       return null;
/*     */     }
/*     */     
/*     */     protected void childAllocation(int param1Int, Rectangle param1Rectangle) {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/FlowView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */