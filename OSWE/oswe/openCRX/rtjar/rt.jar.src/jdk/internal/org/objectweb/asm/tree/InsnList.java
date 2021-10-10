/*     */ package jdk.internal.org.objectweb.asm.tree;
/*     */ 
/*     */ import java.util.ListIterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import jdk.internal.org.objectweb.asm.MethodVisitor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InsnList
/*     */ {
/*     */   private int size;
/*     */   private AbstractInsnNode first;
/*     */   private AbstractInsnNode last;
/*     */   AbstractInsnNode[] cache;
/*     */   
/*     */   public int size() {
/*  99 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractInsnNode getFirst() {
/* 109 */     return this.first;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractInsnNode getLast() {
/* 119 */     return this.last;
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
/*     */   public AbstractInsnNode get(int paramInt) {
/* 135 */     if (paramInt < 0 || paramInt >= this.size) {
/* 136 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 138 */     if (this.cache == null) {
/* 139 */       this.cache = toArray();
/*     */     }
/* 141 */     return this.cache[paramInt];
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
/*     */   public boolean contains(AbstractInsnNode paramAbstractInsnNode) {
/* 154 */     AbstractInsnNode abstractInsnNode = this.first;
/* 155 */     while (abstractInsnNode != null && abstractInsnNode != paramAbstractInsnNode) {
/* 156 */       abstractInsnNode = abstractInsnNode.next;
/*     */     }
/* 158 */     return (abstractInsnNode != null);
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
/*     */   public int indexOf(AbstractInsnNode paramAbstractInsnNode) {
/* 176 */     if (this.cache == null) {
/* 177 */       this.cache = toArray();
/*     */     }
/* 179 */     return paramAbstractInsnNode.index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void accept(MethodVisitor paramMethodVisitor) {
/* 189 */     AbstractInsnNode abstractInsnNode = this.first;
/* 190 */     while (abstractInsnNode != null) {
/* 191 */       abstractInsnNode.accept(paramMethodVisitor);
/* 192 */       abstractInsnNode = abstractInsnNode.next;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ListIterator<AbstractInsnNode> iterator() {
/* 202 */     return iterator(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ListIterator<AbstractInsnNode> iterator(int paramInt) {
/* 212 */     return new InsnListIterator(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractInsnNode[] toArray() {
/* 221 */     byte b = 0;
/* 222 */     AbstractInsnNode abstractInsnNode = this.first;
/* 223 */     AbstractInsnNode[] arrayOfAbstractInsnNode = new AbstractInsnNode[this.size];
/* 224 */     while (abstractInsnNode != null) {
/* 225 */       arrayOfAbstractInsnNode[b] = abstractInsnNode;
/* 226 */       abstractInsnNode.index = b++;
/* 227 */       abstractInsnNode = abstractInsnNode.next;
/*     */     } 
/* 229 */     return arrayOfAbstractInsnNode;
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
/*     */   public void set(AbstractInsnNode paramAbstractInsnNode1, AbstractInsnNode paramAbstractInsnNode2) {
/* 242 */     AbstractInsnNode abstractInsnNode1 = paramAbstractInsnNode1.next;
/* 243 */     paramAbstractInsnNode2.next = abstractInsnNode1;
/* 244 */     if (abstractInsnNode1 != null) {
/* 245 */       abstractInsnNode1.prev = paramAbstractInsnNode2;
/*     */     } else {
/* 247 */       this.last = paramAbstractInsnNode2;
/*     */     } 
/* 249 */     AbstractInsnNode abstractInsnNode2 = paramAbstractInsnNode1.prev;
/* 250 */     paramAbstractInsnNode2.prev = abstractInsnNode2;
/* 251 */     if (abstractInsnNode2 != null) {
/* 252 */       abstractInsnNode2.next = paramAbstractInsnNode2;
/*     */     } else {
/* 254 */       this.first = paramAbstractInsnNode2;
/*     */     } 
/* 256 */     if (this.cache != null) {
/* 257 */       int i = paramAbstractInsnNode1.index;
/* 258 */       this.cache[i] = paramAbstractInsnNode2;
/* 259 */       paramAbstractInsnNode2.index = i;
/*     */     } else {
/* 261 */       paramAbstractInsnNode2.index = 0;
/*     */     } 
/* 263 */     paramAbstractInsnNode1.index = -1;
/* 264 */     paramAbstractInsnNode1.prev = null;
/* 265 */     paramAbstractInsnNode1.next = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(AbstractInsnNode paramAbstractInsnNode) {
/* 276 */     this.size++;
/* 277 */     if (this.last == null) {
/* 278 */       this.first = paramAbstractInsnNode;
/* 279 */       this.last = paramAbstractInsnNode;
/*     */     } else {
/* 281 */       this.last.next = paramAbstractInsnNode;
/* 282 */       paramAbstractInsnNode.prev = this.last;
/*     */     } 
/* 284 */     this.last = paramAbstractInsnNode;
/* 285 */     this.cache = null;
/* 286 */     paramAbstractInsnNode.index = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(InsnList paramInsnList) {
/* 297 */     if (paramInsnList.size == 0) {
/*     */       return;
/*     */     }
/* 300 */     this.size += paramInsnList.size;
/* 301 */     if (this.last == null) {
/* 302 */       this.first = paramInsnList.first;
/* 303 */       this.last = paramInsnList.last;
/*     */     } else {
/* 305 */       AbstractInsnNode abstractInsnNode = paramInsnList.first;
/* 306 */       this.last.next = abstractInsnNode;
/* 307 */       abstractInsnNode.prev = this.last;
/* 308 */       this.last = paramInsnList.last;
/*     */     } 
/* 310 */     this.cache = null;
/* 311 */     paramInsnList.removeAll(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insert(AbstractInsnNode paramAbstractInsnNode) {
/* 322 */     this.size++;
/* 323 */     if (this.first == null) {
/* 324 */       this.first = paramAbstractInsnNode;
/* 325 */       this.last = paramAbstractInsnNode;
/*     */     } else {
/* 327 */       this.first.prev = paramAbstractInsnNode;
/* 328 */       paramAbstractInsnNode.next = this.first;
/*     */     } 
/* 330 */     this.first = paramAbstractInsnNode;
/* 331 */     this.cache = null;
/* 332 */     paramAbstractInsnNode.index = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insert(InsnList paramInsnList) {
/* 343 */     if (paramInsnList.size == 0) {
/*     */       return;
/*     */     }
/* 346 */     this.size += paramInsnList.size;
/* 347 */     if (this.first == null) {
/* 348 */       this.first = paramInsnList.first;
/* 349 */       this.last = paramInsnList.last;
/*     */     } else {
/* 351 */       AbstractInsnNode abstractInsnNode = paramInsnList.last;
/* 352 */       this.first.prev = abstractInsnNode;
/* 353 */       abstractInsnNode.next = this.first;
/* 354 */       this.first = paramInsnList.first;
/*     */     } 
/* 356 */     this.cache = null;
/* 357 */     paramInsnList.removeAll(false);
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
/*     */   public void insert(AbstractInsnNode paramAbstractInsnNode1, AbstractInsnNode paramAbstractInsnNode2) {
/* 372 */     this.size++;
/* 373 */     AbstractInsnNode abstractInsnNode = paramAbstractInsnNode1.next;
/* 374 */     if (abstractInsnNode == null) {
/* 375 */       this.last = paramAbstractInsnNode2;
/*     */     } else {
/* 377 */       abstractInsnNode.prev = paramAbstractInsnNode2;
/*     */     } 
/* 379 */     paramAbstractInsnNode1.next = paramAbstractInsnNode2;
/* 380 */     paramAbstractInsnNode2.next = abstractInsnNode;
/* 381 */     paramAbstractInsnNode2.prev = paramAbstractInsnNode1;
/* 382 */     this.cache = null;
/* 383 */     paramAbstractInsnNode2.index = 0;
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
/*     */   public void insert(AbstractInsnNode paramAbstractInsnNode, InsnList paramInsnList) {
/* 397 */     if (paramInsnList.size == 0) {
/*     */       return;
/*     */     }
/* 400 */     this.size += paramInsnList.size;
/* 401 */     AbstractInsnNode abstractInsnNode1 = paramInsnList.first;
/* 402 */     AbstractInsnNode abstractInsnNode2 = paramInsnList.last;
/* 403 */     AbstractInsnNode abstractInsnNode3 = paramAbstractInsnNode.next;
/* 404 */     if (abstractInsnNode3 == null) {
/* 405 */       this.last = abstractInsnNode2;
/*     */     } else {
/* 407 */       abstractInsnNode3.prev = abstractInsnNode2;
/*     */     } 
/* 409 */     paramAbstractInsnNode.next = abstractInsnNode1;
/* 410 */     abstractInsnNode2.next = abstractInsnNode3;
/* 411 */     abstractInsnNode1.prev = paramAbstractInsnNode;
/* 412 */     this.cache = null;
/* 413 */     paramInsnList.removeAll(false);
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
/*     */   public void insertBefore(AbstractInsnNode paramAbstractInsnNode1, AbstractInsnNode paramAbstractInsnNode2) {
/* 428 */     this.size++;
/* 429 */     AbstractInsnNode abstractInsnNode = paramAbstractInsnNode1.prev;
/* 430 */     if (abstractInsnNode == null) {
/* 431 */       this.first = paramAbstractInsnNode2;
/*     */     } else {
/* 433 */       abstractInsnNode.next = paramAbstractInsnNode2;
/*     */     } 
/* 435 */     paramAbstractInsnNode1.prev = paramAbstractInsnNode2;
/* 436 */     paramAbstractInsnNode2.next = paramAbstractInsnNode1;
/* 437 */     paramAbstractInsnNode2.prev = abstractInsnNode;
/* 438 */     this.cache = null;
/* 439 */     paramAbstractInsnNode2.index = 0;
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
/*     */   public void insertBefore(AbstractInsnNode paramAbstractInsnNode, InsnList paramInsnList) {
/* 454 */     if (paramInsnList.size == 0) {
/*     */       return;
/*     */     }
/* 457 */     this.size += paramInsnList.size;
/* 458 */     AbstractInsnNode abstractInsnNode1 = paramInsnList.first;
/* 459 */     AbstractInsnNode abstractInsnNode2 = paramInsnList.last;
/* 460 */     AbstractInsnNode abstractInsnNode3 = paramAbstractInsnNode.prev;
/* 461 */     if (abstractInsnNode3 == null) {
/* 462 */       this.first = abstractInsnNode1;
/*     */     } else {
/* 464 */       abstractInsnNode3.next = abstractInsnNode1;
/*     */     } 
/* 466 */     paramAbstractInsnNode.prev = abstractInsnNode2;
/* 467 */     abstractInsnNode2.next = paramAbstractInsnNode;
/* 468 */     abstractInsnNode1.prev = abstractInsnNode3;
/* 469 */     this.cache = null;
/* 470 */     paramInsnList.removeAll(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(AbstractInsnNode paramAbstractInsnNode) {
/* 480 */     this.size--;
/* 481 */     AbstractInsnNode abstractInsnNode1 = paramAbstractInsnNode.next;
/* 482 */     AbstractInsnNode abstractInsnNode2 = paramAbstractInsnNode.prev;
/* 483 */     if (abstractInsnNode1 == null) {
/* 484 */       if (abstractInsnNode2 == null) {
/* 485 */         this.first = null;
/* 486 */         this.last = null;
/*     */       } else {
/* 488 */         abstractInsnNode2.next = null;
/* 489 */         this.last = abstractInsnNode2;
/*     */       }
/*     */     
/* 492 */     } else if (abstractInsnNode2 == null) {
/* 493 */       this.first = abstractInsnNode1;
/* 494 */       abstractInsnNode1.prev = null;
/*     */     } else {
/* 496 */       abstractInsnNode2.next = abstractInsnNode1;
/* 497 */       abstractInsnNode1.prev = abstractInsnNode2;
/*     */     } 
/*     */     
/* 500 */     this.cache = null;
/* 501 */     paramAbstractInsnNode.index = -1;
/* 502 */     paramAbstractInsnNode.prev = null;
/* 503 */     paramAbstractInsnNode.next = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void removeAll(boolean paramBoolean) {
/* 514 */     if (paramBoolean) {
/* 515 */       AbstractInsnNode abstractInsnNode = this.first;
/* 516 */       while (abstractInsnNode != null) {
/* 517 */         AbstractInsnNode abstractInsnNode1 = abstractInsnNode.next;
/* 518 */         abstractInsnNode.index = -1;
/* 519 */         abstractInsnNode.prev = null;
/* 520 */         abstractInsnNode.next = null;
/* 521 */         abstractInsnNode = abstractInsnNode1;
/*     */       } 
/*     */     } 
/* 524 */     this.size = 0;
/* 525 */     this.first = null;
/* 526 */     this.last = null;
/* 527 */     this.cache = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 534 */     removeAll(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetLabels() {
/* 543 */     AbstractInsnNode abstractInsnNode = this.first;
/* 544 */     while (abstractInsnNode != null) {
/* 545 */       if (abstractInsnNode instanceof LabelNode) {
/* 546 */         ((LabelNode)abstractInsnNode).resetLabel();
/*     */       }
/* 548 */       abstractInsnNode = abstractInsnNode.next;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private final class InsnListIterator
/*     */     implements ListIterator
/*     */   {
/*     */     AbstractInsnNode next;
/*     */     
/*     */     AbstractInsnNode prev;
/*     */     
/*     */     AbstractInsnNode remove;
/*     */     
/*     */     InsnListIterator(int param1Int) {
/* 563 */       if (param1Int == InsnList.this.size()) {
/* 564 */         this.next = null;
/* 565 */         this.prev = InsnList.this.getLast();
/*     */       } else {
/* 567 */         this.next = InsnList.this.get(param1Int);
/* 568 */         this.prev = this.next.prev;
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 573 */       return (this.next != null);
/*     */     }
/*     */     
/*     */     public Object next() {
/* 577 */       if (this.next == null) {
/* 578 */         throw new NoSuchElementException();
/*     */       }
/* 580 */       AbstractInsnNode abstractInsnNode = this.next;
/* 581 */       this.prev = abstractInsnNode;
/* 582 */       this.next = abstractInsnNode.next;
/* 583 */       this.remove = abstractInsnNode;
/* 584 */       return abstractInsnNode;
/*     */     }
/*     */     
/*     */     public void remove() {
/* 588 */       if (this.remove != null) {
/* 589 */         if (this.remove == this.next) {
/* 590 */           this.next = this.next.next;
/*     */         } else {
/* 592 */           this.prev = this.prev.prev;
/*     */         } 
/* 594 */         InsnList.this.remove(this.remove);
/* 595 */         this.remove = null;
/*     */       } else {
/* 597 */         throw new IllegalStateException();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean hasPrevious() {
/* 602 */       return (this.prev != null);
/*     */     }
/*     */     
/*     */     public Object previous() {
/* 606 */       AbstractInsnNode abstractInsnNode = this.prev;
/* 607 */       this.next = abstractInsnNode;
/* 608 */       this.prev = abstractInsnNode.prev;
/* 609 */       this.remove = abstractInsnNode;
/* 610 */       return abstractInsnNode;
/*     */     }
/*     */     
/*     */     public int nextIndex() {
/* 614 */       if (this.next == null) {
/* 615 */         return InsnList.this.size();
/*     */       }
/* 617 */       if (InsnList.this.cache == null) {
/* 618 */         InsnList.this.cache = InsnList.this.toArray();
/*     */       }
/* 620 */       return this.next.index;
/*     */     }
/*     */     
/*     */     public int previousIndex() {
/* 624 */       if (this.prev == null) {
/* 625 */         return -1;
/*     */       }
/* 627 */       if (InsnList.this.cache == null) {
/* 628 */         InsnList.this.cache = InsnList.this.toArray();
/*     */       }
/* 630 */       return this.prev.index;
/*     */     }
/*     */     
/*     */     public void add(Object param1Object) {
/* 634 */       InsnList.this.insertBefore(this.next, (AbstractInsnNode)param1Object);
/* 635 */       this.prev = (AbstractInsnNode)param1Object;
/* 636 */       this.remove = null;
/*     */     }
/*     */     
/*     */     public void set(Object param1Object) {
/* 640 */       InsnList.this.set(this.next.prev, (AbstractInsnNode)param1Object);
/* 641 */       this.prev = (AbstractInsnNode)param1Object;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/tree/InsnList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */