/*     */ package java.awt;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamField;
/*     */ import java.io.Serializable;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CardLayout
/*     */   implements LayoutManager2, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4328196481005934313L;
/*  69 */   Vector<Card> vector = new Vector<>();
/*     */   
/*     */   class Card
/*     */     implements Serializable
/*     */   {
/*     */     static final long serialVersionUID = 6640330810709497518L;
/*     */     public String name;
/*     */     public Component comp;
/*     */     
/*     */     public Card(String param1String, Component param1Component) {
/*  79 */       this.name = param1String;
/*  80 */       this.comp = param1Component;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   int currentCard = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int hgap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int vgap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("tab", Hashtable.class), new ObjectStreamField("hgap", int.class), new ObjectStreamField("vgap", int.class), new ObjectStreamField("vector", Vector.class), new ObjectStreamField("currentCard", int.class) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CardLayout() {
/* 130 */     this(0, 0);
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
/*     */   public CardLayout(int paramInt1, int paramInt2) {
/* 142 */     this.hgap = paramInt1;
/* 143 */     this.vgap = paramInt2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHgap() {
/* 154 */     return this.hgap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHgap(int paramInt) {
/* 165 */     this.hgap = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVgap() {
/* 175 */     return this.vgap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVgap(int paramInt) {
/* 186 */     this.vgap = paramInt;
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
/*     */   public void addLayoutComponent(Component paramComponent, Object paramObject) {
/* 203 */     synchronized (paramComponent.getTreeLock()) {
/* 204 */       if (paramObject == null) {
/* 205 */         paramObject = "";
/*     */       }
/* 207 */       if (paramObject instanceof String) {
/* 208 */         addLayoutComponent((String)paramObject, paramComponent);
/*     */       } else {
/* 210 */         throw new IllegalArgumentException("cannot add to layout: constraint must be a string");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void addLayoutComponent(String paramString, Component paramComponent) {
/* 221 */     synchronized (paramComponent.getTreeLock()) {
/* 222 */       if (!this.vector.isEmpty()) {
/* 223 */         paramComponent.setVisible(false);
/*     */       }
/* 225 */       for (byte b = 0; b < this.vector.size(); b++) {
/* 226 */         if (((Card)this.vector.get(b)).name.equals(paramString)) {
/* 227 */           ((Card)this.vector.get(b)).comp = paramComponent;
/*     */           return;
/*     */         } 
/*     */       } 
/* 231 */       this.vector.add(new Card(paramString, paramComponent));
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
/*     */   public void removeLayoutComponent(Component paramComponent) {
/* 243 */     synchronized (paramComponent.getTreeLock()) {
/* 244 */       for (byte b = 0; b < this.vector.size(); b++) {
/* 245 */         if (((Card)this.vector.get(b)).comp == paramComponent) {
/*     */           
/* 247 */           if (paramComponent.isVisible() && paramComponent.getParent() != null) {
/* 248 */             next(paramComponent.getParent());
/*     */           }
/*     */           
/* 251 */           this.vector.remove(b);
/*     */ 
/*     */           
/* 254 */           if (this.currentCard > b) {
/* 255 */             this.currentCard--;
/*     */           }
/*     */           break;
/*     */         } 
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
/*     */   public Dimension preferredLayoutSize(Container paramContainer) {
/* 273 */     synchronized (paramContainer.getTreeLock()) {
/* 274 */       Insets insets = paramContainer.getInsets();
/* 275 */       int i = paramContainer.getComponentCount();
/* 276 */       int j = 0;
/* 277 */       int k = 0;
/*     */       
/* 279 */       for (byte b = 0; b < i; b++) {
/* 280 */         Component component = paramContainer.getComponent(b);
/* 281 */         Dimension dimension = component.getPreferredSize();
/* 282 */         if (dimension.width > j) {
/* 283 */           j = dimension.width;
/*     */         }
/* 285 */         if (dimension.height > k) {
/* 286 */           k = dimension.height;
/*     */         }
/*     */       } 
/* 289 */       return new Dimension(insets.left + insets.right + j + this.hgap * 2, insets.top + insets.bottom + k + this.vgap * 2);
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
/*     */   public Dimension minimumLayoutSize(Container paramContainer) {
/* 303 */     synchronized (paramContainer.getTreeLock()) {
/* 304 */       Insets insets = paramContainer.getInsets();
/* 305 */       int i = paramContainer.getComponentCount();
/* 306 */       int j = 0;
/* 307 */       int k = 0;
/*     */       
/* 309 */       for (byte b = 0; b < i; b++) {
/* 310 */         Component component = paramContainer.getComponent(b);
/* 311 */         Dimension dimension = component.getMinimumSize();
/* 312 */         if (dimension.width > j) {
/* 313 */           j = dimension.width;
/*     */         }
/* 315 */         if (dimension.height > k) {
/* 316 */           k = dimension.height;
/*     */         }
/*     */       } 
/* 319 */       return new Dimension(insets.left + insets.right + j + this.hgap * 2, insets.top + insets.bottom + k + this.vgap * 2);
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
/*     */   public Dimension maximumLayoutSize(Container paramContainer) {
/* 333 */     return new Dimension(2147483647, 2147483647);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getLayoutAlignmentX(Container paramContainer) {
/* 344 */     return 0.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getLayoutAlignmentY(Container paramContainer) {
/* 355 */     return 0.5F;
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
/*     */   public void invalidateLayout(Container paramContainer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void layoutContainer(Container paramContainer) {
/* 376 */     synchronized (paramContainer.getTreeLock()) {
/* 377 */       Insets insets = paramContainer.getInsets();
/* 378 */       int i = paramContainer.getComponentCount();
/* 379 */       Component component = null;
/* 380 */       boolean bool = false;
/*     */       
/* 382 */       for (byte b = 0; b < i; b++) {
/* 383 */         component = paramContainer.getComponent(b);
/* 384 */         component.setBounds(this.hgap + insets.left, this.vgap + insets.top, paramContainer.width - this.hgap * 2 + insets.left + insets.right, paramContainer.height - this.vgap * 2 + insets.top + insets.bottom);
/*     */ 
/*     */         
/* 387 */         if (component.isVisible()) {
/* 388 */           bool = true;
/*     */         }
/*     */       } 
/*     */       
/* 392 */       if (!bool && i > 0) {
/* 393 */         paramContainer.getComponent(0).setVisible(true);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void checkLayout(Container paramContainer) {
/* 403 */     if (paramContainer.getLayout() != this) {
/* 404 */       throw new IllegalArgumentException("wrong parent for CardLayout");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void first(Container paramContainer) {
/* 414 */     synchronized (paramContainer.getTreeLock()) {
/* 415 */       checkLayout(paramContainer);
/* 416 */       int i = paramContainer.getComponentCount();
/* 417 */       for (byte b = 0; b < i; b++) {
/* 418 */         Component component = paramContainer.getComponent(b);
/* 419 */         if (component.isVisible()) {
/* 420 */           component.setVisible(false);
/*     */           break;
/*     */         } 
/*     */       } 
/* 424 */       if (i > 0) {
/* 425 */         this.currentCard = 0;
/* 426 */         paramContainer.getComponent(0).setVisible(true);
/* 427 */         paramContainer.validate();
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
/*     */   public void next(Container paramContainer) {
/* 440 */     synchronized (paramContainer.getTreeLock()) {
/* 441 */       checkLayout(paramContainer);
/* 442 */       int i = paramContainer.getComponentCount();
/* 443 */       for (byte b = 0; b < i; b++) {
/* 444 */         Component component = paramContainer.getComponent(b);
/* 445 */         if (component.isVisible()) {
/* 446 */           component.setVisible(false);
/* 447 */           this.currentCard = (b + 1) % i;
/* 448 */           component = paramContainer.getComponent(this.currentCard);
/* 449 */           component.setVisible(true);
/* 450 */           paramContainer.validate();
/*     */           return;
/*     */         } 
/*     */       } 
/* 454 */       showDefaultComponent(paramContainer);
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
/*     */   public void previous(Container paramContainer) {
/* 466 */     synchronized (paramContainer.getTreeLock()) {
/* 467 */       checkLayout(paramContainer);
/* 468 */       int i = paramContainer.getComponentCount();
/* 469 */       for (byte b = 0; b < i; b++) {
/* 470 */         Component component = paramContainer.getComponent(b);
/* 471 */         if (component.isVisible()) {
/* 472 */           component.setVisible(false);
/* 473 */           this.currentCard = (b > 0) ? (b - 1) : (i - 1);
/* 474 */           component = paramContainer.getComponent(this.currentCard);
/* 475 */           component.setVisible(true);
/* 476 */           paramContainer.validate();
/*     */           return;
/*     */         } 
/*     */       } 
/* 480 */       showDefaultComponent(paramContainer);
/*     */     } 
/*     */   }
/*     */   
/*     */   void showDefaultComponent(Container paramContainer) {
/* 485 */     if (paramContainer.getComponentCount() > 0) {
/* 486 */       this.currentCard = 0;
/* 487 */       paramContainer.getComponent(0).setVisible(true);
/* 488 */       paramContainer.validate();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void last(Container paramContainer) {
/* 498 */     synchronized (paramContainer.getTreeLock()) {
/* 499 */       checkLayout(paramContainer);
/* 500 */       int i = paramContainer.getComponentCount();
/* 501 */       for (byte b = 0; b < i; b++) {
/* 502 */         Component component = paramContainer.getComponent(b);
/* 503 */         if (component.isVisible()) {
/* 504 */           component.setVisible(false);
/*     */           break;
/*     */         } 
/*     */       } 
/* 508 */       if (i > 0) {
/* 509 */         this.currentCard = i - 1;
/* 510 */         paramContainer.getComponent(this.currentCard).setVisible(true);
/* 511 */         paramContainer.validate();
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
/*     */   public void show(Container paramContainer, String paramString) {
/* 525 */     synchronized (paramContainer.getTreeLock()) {
/* 526 */       checkLayout(paramContainer);
/* 527 */       Component component = null;
/* 528 */       int i = this.vector.size(); byte b;
/* 529 */       for (b = 0; b < i; b++) {
/* 530 */         Card card = this.vector.get(b);
/* 531 */         if (card.name.equals(paramString)) {
/* 532 */           component = card.comp;
/* 533 */           this.currentCard = b;
/*     */           break;
/*     */         } 
/*     */       } 
/* 537 */       if (component != null && !component.isVisible()) {
/* 538 */         i = paramContainer.getComponentCount();
/* 539 */         for (b = 0; b < i; b++) {
/* 540 */           Component component1 = paramContainer.getComponent(b);
/* 541 */           if (component1.isVisible()) {
/* 542 */             component1.setVisible(false);
/*     */             break;
/*     */           } 
/*     */         } 
/* 546 */         component.setVisible(true);
/* 547 */         paramContainer.validate();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 557 */     return getClass().getName() + "[hgap=" + this.hgap + ",vgap=" + this.vgap + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
/* 566 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/*     */     
/* 568 */     this.hgap = getField.get("hgap", 0);
/* 569 */     this.vgap = getField.get("vgap", 0);
/*     */     
/* 571 */     if (getField.defaulted("vector")) {
/*     */       
/* 573 */       Hashtable hashtable = (Hashtable)getField.get("tab", (Object)null);
/* 574 */       this.vector = new Vector<>();
/* 575 */       if (hashtable != null && !hashtable.isEmpty()) {
/* 576 */         for (Enumeration<String> enumeration = hashtable.keys(); enumeration.hasMoreElements(); ) {
/* 577 */           String str = enumeration.nextElement();
/* 578 */           Component component = (Component)hashtable.get(str);
/* 579 */           this.vector.add(new Card(str, component));
/* 580 */           if (component.isVisible()) {
/* 581 */             this.currentCard = this.vector.size() - 1;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } else {
/* 586 */       this.vector = (Vector<Card>)getField.get("vector", (Object)null);
/* 587 */       this.currentCard = getField.get("currentCard", 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 597 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 598 */     int i = this.vector.size();
/* 599 */     for (byte b = 0; b < i; b++) {
/* 600 */       Card card = this.vector.get(b);
/* 601 */       hashtable.put(card.name, card.comp);
/*     */     } 
/*     */     
/* 604 */     ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 605 */     putField.put("hgap", this.hgap);
/* 606 */     putField.put("vgap", this.vgap);
/* 607 */     putField.put("vector", this.vector);
/* 608 */     putField.put("currentCard", this.currentCard);
/* 609 */     putField.put("tab", hashtable);
/* 610 */     paramObjectOutputStream.writeFields();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/CardLayout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */