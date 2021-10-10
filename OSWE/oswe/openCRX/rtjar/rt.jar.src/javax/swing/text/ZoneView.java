/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Shape;
/*     */ import java.util.Vector;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ZoneView
/*     */   extends BoxView
/*     */ {
/*  80 */   int maxZoneSize = 8192;
/*  81 */   int maxZonesLoaded = 3;
/*     */ 
/*     */ 
/*     */   
/*     */   Vector<View> loadedZones;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZoneView(Element paramElement, int paramInt) {
/*  91 */     super(paramElement, paramInt);
/*  92 */     this.loadedZones = new Vector<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaximumZoneSize() {
/*  99 */     return this.maxZoneSize;
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
/*     */   public void setMaximumZoneSize(int paramInt) {
/* 114 */     this.maxZoneSize = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxZonesLoaded() {
/* 122 */     return this.maxZonesLoaded;
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
/*     */   public void setMaxZonesLoaded(int paramInt) {
/* 136 */     if (paramInt < 1) {
/* 137 */       throw new IllegalArgumentException("ZoneView.setMaxZonesLoaded must be greater than 0.");
/*     */     }
/* 139 */     this.maxZonesLoaded = paramInt;
/* 140 */     unloadOldZones();
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
/*     */   protected void zoneWasLoaded(View paramView) {
/* 154 */     this.loadedZones.addElement(paramView);
/* 155 */     unloadOldZones();
/*     */   }
/*     */   
/*     */   void unloadOldZones() {
/* 159 */     while (this.loadedZones.size() > getMaxZonesLoaded()) {
/* 160 */       View view = this.loadedZones.elementAt(0);
/* 161 */       this.loadedZones.removeElementAt(0);
/* 162 */       unloadZone(view);
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
/*     */   protected void unloadZone(View paramView) {
/* 178 */     paramView.removeAll();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isZoneLoaded(View paramView) {
/* 189 */     return (paramView.getViewCount() > 0);
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
/*     */   protected View createZone(int paramInt1, int paramInt2) {
/*     */     Zone zone;
/* 208 */     Document document = getDocument();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 213 */       zone = new Zone(getElement(), document.createPosition(paramInt1), document.createPosition(paramInt2));
/* 214 */     } catch (BadLocationException badLocationException) {
/*     */       
/* 216 */       throw new StateInvariantError(badLocationException.getMessage());
/*     */     } 
/* 218 */     return zone;
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
/*     */   protected void loadChildren(ViewFactory paramViewFactory) {
/* 234 */     Document document = getDocument();
/* 235 */     int i = getStartOffset();
/* 236 */     int j = getEndOffset();
/* 237 */     append(createZone(i, j));
/* 238 */     handleInsert(i, j - i);
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
/*     */   protected int getViewIndexAtPosition(int paramInt) {
/* 252 */     int i = getViewCount();
/* 253 */     if (paramInt == getEndOffset()) {
/* 254 */       return i - 1;
/*     */     }
/* 256 */     for (byte b = 0; b < i; b++) {
/* 257 */       View view = getView(b);
/* 258 */       if (paramInt >= view.getStartOffset() && paramInt < view
/* 259 */         .getEndOffset()) {
/* 260 */         return b;
/*     */       }
/*     */     } 
/* 263 */     return -1;
/*     */   }
/*     */   
/*     */   void handleInsert(int paramInt1, int paramInt2) {
/* 267 */     int i = getViewIndex(paramInt1, Position.Bias.Forward);
/* 268 */     View view = getView(i);
/* 269 */     int j = view.getStartOffset();
/* 270 */     int k = view.getEndOffset();
/* 271 */     if (k - j > this.maxZoneSize) {
/* 272 */       splitZone(i, j, k);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void handleRemove(int paramInt1, int paramInt2) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void splitZone(int paramInt1, int paramInt2, int paramInt3) {
/* 286 */     Element element = getElement();
/* 287 */     Document document = element.getDocument();
/* 288 */     Vector<View> vector = new Vector();
/* 289 */     int i = paramInt2;
/*     */     while (true) {
/* 291 */       paramInt2 = i;
/* 292 */       i = Math.min(getDesiredZoneEnd(paramInt2), paramInt3);
/* 293 */       vector.addElement(createZone(paramInt2, i));
/* 294 */       if (i >= paramInt3) {
/* 295 */         View view = getView(paramInt1);
/* 296 */         View[] arrayOfView = new View[vector.size()];
/* 297 */         vector.copyInto((Object[])arrayOfView);
/* 298 */         replace(paramInt1, 1, arrayOfView);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getDesiredZoneEnd(int paramInt) {
/* 308 */     Element element1 = getElement();
/* 309 */     int i = element1.getElementIndex(paramInt + this.maxZoneSize / 2);
/* 310 */     Element element2 = element1.getElement(i);
/* 311 */     int j = element2.getStartOffset();
/* 312 */     int k = element2.getEndOffset();
/* 313 */     if (k - paramInt > this.maxZoneSize && 
/* 314 */       j > paramInt) {
/* 315 */       return j;
/*     */     }
/*     */     
/* 318 */     return k;
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
/*     */   protected boolean updateChildren(DocumentEvent.ElementChange paramElementChange, DocumentEvent paramDocumentEvent, ViewFactory paramViewFactory) {
/* 332 */     return false;
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
/*     */   public void insertUpdate(DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/* 348 */     handleInsert(paramDocumentEvent.getOffset(), paramDocumentEvent.getLength());
/* 349 */     super.insertUpdate(paramDocumentEvent, paramShape, paramViewFactory);
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
/*     */   public void removeUpdate(DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/* 365 */     handleRemove(paramDocumentEvent.getOffset(), paramDocumentEvent.getLength());
/* 366 */     super.removeUpdate(paramDocumentEvent, paramShape, paramViewFactory);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   class Zone
/*     */     extends AsyncBoxView
/*     */   {
/*     */     private Position start;
/*     */     
/*     */     private Position end;
/*     */ 
/*     */     
/*     */     public Zone(Element param1Element, Position param1Position1, Position param1Position2) {
/* 380 */       super(param1Element, ZoneView.this.getAxis());
/* 381 */       this.start = param1Position1;
/* 382 */       this.end = param1Position2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void load() {
/* 393 */       if (!isLoaded()) {
/* 394 */         setEstimatedMajorSpan(true);
/* 395 */         Element element = getElement();
/* 396 */         ViewFactory viewFactory = getViewFactory();
/* 397 */         int i = element.getElementIndex(getStartOffset());
/* 398 */         int j = element.getElementIndex(getEndOffset());
/* 399 */         View[] arrayOfView = new View[j - i + 1];
/* 400 */         for (int k = i; k <= j; k++) {
/* 401 */           arrayOfView[k - i] = viewFactory.create(element.getElement(k));
/*     */         }
/* 403 */         replace(0, 0, arrayOfView);
/*     */         
/* 405 */         ZoneView.this.zoneWasLoaded(this);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void unload() {
/* 414 */       setEstimatedMajorSpan(true);
/* 415 */       removeAll();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isLoaded() {
/* 423 */       return (getViewCount() != 0);
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
/*     */     protected void loadChildren(ViewFactory param1ViewFactory) {
/* 437 */       setEstimatedMajorSpan(true);
/*     */ 
/*     */       
/* 440 */       Element element = getElement();
/* 441 */       int i = element.getElementIndex(getStartOffset());
/* 442 */       int j = element.getElementIndex(getEndOffset());
/* 443 */       int k = j - i;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 448 */       View view = param1ViewFactory.create(element.getElement(i));
/* 449 */       view.setParent(this);
/* 450 */       float f1 = view.getPreferredSpan(0);
/* 451 */       float f2 = view.getPreferredSpan(1);
/* 452 */       if (getMajorAxis() == 0) {
/* 453 */         f1 *= k;
/*     */       } else {
/* 455 */         f2 += k;
/*     */       } 
/*     */       
/* 458 */       setSize(f1, f2);
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
/*     */     protected void flushRequirementChanges() {
/* 472 */       if (isLoaded()) {
/* 473 */         super.flushRequirementChanges();
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
/*     */     public int getViewIndex(int param1Int, Position.Bias param1Bias) {
/* 490 */       boolean bool = (param1Bias == Position.Bias.Backward) ? true : false;
/* 491 */       param1Int = bool ? Math.max(0, param1Int - 1) : param1Int;
/* 492 */       Element element = getElement();
/* 493 */       int i = element.getElementIndex(param1Int);
/* 494 */       int j = element.getElementIndex(getStartOffset());
/* 495 */       return i - j;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean updateChildren(DocumentEvent.ElementChange param1ElementChange, DocumentEvent param1DocumentEvent, ViewFactory param1ViewFactory) {
/* 501 */       Element[] arrayOfElement1 = param1ElementChange.getChildrenRemoved();
/* 502 */       Element[] arrayOfElement2 = param1ElementChange.getChildrenAdded();
/* 503 */       Element element = getElement();
/* 504 */       int i = element.getElementIndex(getStartOffset());
/* 505 */       int j = element.getElementIndex(getEndOffset() - 1);
/* 506 */       int k = param1ElementChange.getIndex();
/* 507 */       if (k >= i && k <= j) {
/*     */         
/* 509 */         int m = k - i;
/* 510 */         int n = Math.min(j - i + 1, arrayOfElement2.length);
/* 511 */         int i1 = Math.min(j - i + 1, arrayOfElement1.length);
/* 512 */         View[] arrayOfView = new View[n];
/* 513 */         for (byte b = 0; b < n; b++) {
/* 514 */           arrayOfView[b] = param1ViewFactory.create(arrayOfElement2[b]);
/*     */         }
/* 516 */         replace(m, i1, arrayOfView);
/*     */       } 
/* 518 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AttributeSet getAttributes() {
/* 529 */       return ZoneView.this.getAttributes();
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
/*     */     public void paint(Graphics param1Graphics, Shape param1Shape) {
/* 542 */       load();
/* 543 */       super.paint(param1Graphics, param1Shape);
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
/*     */     public int viewToModel(float param1Float1, float param1Float2, Shape param1Shape, Position.Bias[] param1ArrayOfBias) {
/* 560 */       load();
/* 561 */       return super.viewToModel(param1Float1, param1Float2, param1Shape, param1ArrayOfBias);
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
/*     */     public Shape modelToView(int param1Int, Shape param1Shape, Position.Bias param1Bias) throws BadLocationException {
/* 579 */       load();
/* 580 */       return super.modelToView(param1Int, param1Shape, param1Bias);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getStartOffset() {
/* 589 */       return this.start.getOffset();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getEndOffset() {
/* 596 */       return this.end.getOffset();
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
/*     */     public void insertUpdate(DocumentEvent param1DocumentEvent, Shape param1Shape, ViewFactory param1ViewFactory) {
/* 611 */       if (isLoaded()) {
/* 612 */         super.insertUpdate(param1DocumentEvent, param1Shape, param1ViewFactory);
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
/*     */     public void removeUpdate(DocumentEvent param1DocumentEvent, Shape param1Shape, ViewFactory param1ViewFactory) {
/* 628 */       if (isLoaded()) {
/* 629 */         super.removeUpdate(param1DocumentEvent, param1Shape, param1ViewFactory);
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
/*     */     public void changedUpdate(DocumentEvent param1DocumentEvent, Shape param1Shape, ViewFactory param1ViewFactory) {
/* 645 */       if (isLoaded())
/* 646 */         super.changedUpdate(param1DocumentEvent, param1Shape, param1ViewFactory); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/ZoneView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */