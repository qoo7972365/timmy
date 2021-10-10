/*      */ package java.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.function.Consumer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class LinkedList<E>
/*      */   extends AbstractSequentialList<E>
/*      */   implements List<E>, Deque<E>, Cloneable, Serializable
/*      */ {
/*   87 */   transient int size = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   transient Node<E> first;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   transient Node<E> last;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = 876323262645176354L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LinkedList() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LinkedList(Collection<? extends E> paramCollection) {
/*  118 */     this();
/*  119 */     addAll(paramCollection);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void linkFirst(E paramE) {
/*  126 */     Node<E> node1 = this.first;
/*  127 */     Node<E> node2 = new Node<>(null, paramE, node1);
/*  128 */     this.first = node2;
/*  129 */     if (node1 == null) {
/*  130 */       this.last = node2;
/*      */     } else {
/*  132 */       node1.prev = node2;
/*  133 */     }  this.size++;
/*  134 */     this.modCount++;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void linkLast(E paramE) {
/*  141 */     Node<E> node1 = this.last;
/*  142 */     Node<E> node2 = new Node<>(node1, paramE, null);
/*  143 */     this.last = node2;
/*  144 */     if (node1 == null) {
/*  145 */       this.first = node2;
/*      */     } else {
/*  147 */       node1.next = node2;
/*  148 */     }  this.size++;
/*  149 */     this.modCount++;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void linkBefore(E paramE, Node<E> paramNode) {
/*  157 */     Node<E> node1 = paramNode.prev;
/*  158 */     Node<E> node2 = new Node<>(node1, paramE, paramNode);
/*  159 */     paramNode.prev = node2;
/*  160 */     if (node1 == null) {
/*  161 */       this.first = node2;
/*      */     } else {
/*  163 */       node1.next = node2;
/*  164 */     }  this.size++;
/*  165 */     this.modCount++;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private E unlinkFirst(Node<E> paramNode) {
/*  173 */     E e = paramNode.item;
/*  174 */     Node<E> node = paramNode.next;
/*  175 */     paramNode.item = null;
/*  176 */     paramNode.next = null;
/*  177 */     this.first = node;
/*  178 */     if (node == null) {
/*  179 */       this.last = null;
/*      */     } else {
/*  181 */       node.prev = null;
/*  182 */     }  this.size--;
/*  183 */     this.modCount++;
/*  184 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private E unlinkLast(Node<E> paramNode) {
/*  192 */     E e = paramNode.item;
/*  193 */     Node<E> node = paramNode.prev;
/*  194 */     paramNode.item = null;
/*  195 */     paramNode.prev = null;
/*  196 */     this.last = node;
/*  197 */     if (node == null) {
/*  198 */       this.first = null;
/*      */     } else {
/*  200 */       node.next = null;
/*  201 */     }  this.size--;
/*  202 */     this.modCount++;
/*  203 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   E unlink(Node<E> paramNode) {
/*  211 */     E e = paramNode.item;
/*  212 */     Node<E> node1 = paramNode.next;
/*  213 */     Node<E> node2 = paramNode.prev;
/*      */     
/*  215 */     if (node2 == null) {
/*  216 */       this.first = node1;
/*      */     } else {
/*  218 */       node2.next = node1;
/*  219 */       paramNode.prev = null;
/*      */     } 
/*      */     
/*  222 */     if (node1 == null) {
/*  223 */       this.last = node2;
/*      */     } else {
/*  225 */       node1.prev = node2;
/*  226 */       paramNode.next = null;
/*      */     } 
/*      */     
/*  229 */     paramNode.item = null;
/*  230 */     this.size--;
/*  231 */     this.modCount++;
/*  232 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public E getFirst() {
/*  242 */     Node<E> node = this.first;
/*  243 */     if (node == null)
/*  244 */       throw new NoSuchElementException(); 
/*  245 */     return node.item;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public E getLast() {
/*  255 */     Node<E> node = this.last;
/*  256 */     if (node == null)
/*  257 */       throw new NoSuchElementException(); 
/*  258 */     return node.item;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public E removeFirst() {
/*  268 */     Node<E> node = this.first;
/*  269 */     if (node == null)
/*  270 */       throw new NoSuchElementException(); 
/*  271 */     return unlinkFirst(node);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public E removeLast() {
/*  281 */     Node<E> node = this.last;
/*  282 */     if (node == null)
/*  283 */       throw new NoSuchElementException(); 
/*  284 */     return unlinkLast(node);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addFirst(E paramE) {
/*  293 */     linkFirst(paramE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addLast(E paramE) {
/*  304 */     linkLast(paramE);
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
/*      */   public boolean contains(Object paramObject) {
/*  317 */     return (indexOf(paramObject) != -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/*  326 */     return this.size;
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
/*      */   public boolean add(E paramE) {
/*  338 */     linkLast(paramE);
/*  339 */     return true;
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
/*      */   public boolean remove(Object paramObject) {
/*  356 */     if (paramObject == null) {
/*  357 */       for (Node<E> node = this.first; node != null; node = node.next) {
/*  358 */         if (node.item == null) {
/*  359 */           unlink(node);
/*  360 */           return true;
/*      */         } 
/*      */       } 
/*      */     } else {
/*  364 */       for (Node<E> node = this.first; node != null; node = node.next) {
/*  365 */         if (paramObject.equals(node.item)) {
/*  366 */           unlink(node);
/*  367 */           return true;
/*      */         } 
/*      */       } 
/*      */     } 
/*  371 */     return false;
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
/*      */   public boolean addAll(Collection<? extends E> paramCollection) {
/*  387 */     return addAll(this.size, paramCollection);
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
/*      */   public boolean addAll(int paramInt, Collection<? extends E> paramCollection) {
/*      */     Node<E> node1, node2;
/*  406 */     checkPositionIndex(paramInt);
/*      */     
/*  408 */     Object[] arrayOfObject = paramCollection.toArray();
/*  409 */     int i = arrayOfObject.length;
/*  410 */     if (i == 0) {
/*  411 */       return false;
/*      */     }
/*      */     
/*  414 */     if (paramInt == this.size) {
/*  415 */       node2 = null;
/*  416 */       node1 = this.last;
/*      */     } else {
/*  418 */       node2 = node(paramInt);
/*  419 */       node1 = node2.prev;
/*      */     } 
/*      */     
/*  422 */     for (Object object1 : arrayOfObject) {
/*  423 */       Object object2 = object1;
/*  424 */       Node<E> node = new Node<>(node1, (E)object2, null);
/*  425 */       if (node1 == null) {
/*  426 */         this.first = node;
/*      */       } else {
/*  428 */         node1.next = node;
/*  429 */       }  node1 = node;
/*      */     } 
/*      */     
/*  432 */     if (node2 == null) {
/*  433 */       this.last = node1;
/*      */     } else {
/*  435 */       node1.next = node2;
/*  436 */       node2.prev = node1;
/*      */     } 
/*      */     
/*  439 */     this.size += i;
/*  440 */     this.modCount++;
/*  441 */     return true;
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
/*      */   public void clear() {
/*  453 */     for (Node<E> node = this.first; node != null; ) {
/*  454 */       Node<E> node1 = node.next;
/*  455 */       node.item = null;
/*  456 */       node.next = null;
/*  457 */       node.prev = null;
/*  458 */       node = node1;
/*      */     } 
/*  460 */     this.first = this.last = null;
/*  461 */     this.size = 0;
/*  462 */     this.modCount++;
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
/*      */   public E get(int paramInt) {
/*  476 */     checkElementIndex(paramInt);
/*  477 */     return (node(paramInt)).item;
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
/*      */   public E set(int paramInt, E paramE) {
/*  490 */     checkElementIndex(paramInt);
/*  491 */     Node<E> node = node(paramInt);
/*  492 */     E e = node.item;
/*  493 */     node.item = paramE;
/*  494 */     return e;
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
/*      */   public void add(int paramInt, E paramE) {
/*  507 */     checkPositionIndex(paramInt);
/*      */     
/*  509 */     if (paramInt == this.size) {
/*  510 */       linkLast(paramE);
/*      */     } else {
/*  512 */       linkBefore(paramE, node(paramInt));
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
/*      */   public E remove(int paramInt) {
/*  525 */     checkElementIndex(paramInt);
/*  526 */     return unlink(node(paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isElementIndex(int paramInt) {
/*  533 */     return (paramInt >= 0 && paramInt < this.size);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isPositionIndex(int paramInt) {
/*  541 */     return (paramInt >= 0 && paramInt <= this.size);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String outOfBoundsMsg(int paramInt) {
/*  550 */     return "Index: " + paramInt + ", Size: " + this.size;
/*      */   }
/*      */   
/*      */   private void checkElementIndex(int paramInt) {
/*  554 */     if (!isElementIndex(paramInt))
/*  555 */       throw new IndexOutOfBoundsException(outOfBoundsMsg(paramInt)); 
/*      */   }
/*      */   
/*      */   private void checkPositionIndex(int paramInt) {
/*  559 */     if (!isPositionIndex(paramInt)) {
/*  560 */       throw new IndexOutOfBoundsException(outOfBoundsMsg(paramInt));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Node<E> node(int paramInt) {
/*  569 */     if (paramInt < this.size >> 1) {
/*  570 */       Node<E> node1 = this.first;
/*  571 */       for (byte b = 0; b < paramInt; b++)
/*  572 */         node1 = node1.next; 
/*  573 */       return node1;
/*      */     } 
/*  575 */     Node<E> node = this.last;
/*  576 */     for (int i = this.size - 1; i > paramInt; i--)
/*  577 */       node = node.prev; 
/*  578 */     return node;
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
/*      */   public int indexOf(Object paramObject) {
/*  596 */     byte b = 0;
/*  597 */     if (paramObject == null) {
/*  598 */       for (Node<E> node = this.first; node != null; node = node.next) {
/*  599 */         if (node.item == null)
/*  600 */           return b; 
/*  601 */         b++;
/*      */       } 
/*      */     } else {
/*  604 */       for (Node<E> node = this.first; node != null; node = node.next) {
/*  605 */         if (paramObject.equals(node.item))
/*  606 */           return b; 
/*  607 */         b++;
/*      */       } 
/*      */     } 
/*  610 */     return -1;
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
/*      */   public int lastIndexOf(Object paramObject) {
/*  625 */     int i = this.size;
/*  626 */     if (paramObject == null) {
/*  627 */       for (Node<E> node = this.last; node != null; node = node.prev) {
/*  628 */         i--;
/*  629 */         if (node.item == null)
/*  630 */           return i; 
/*      */       } 
/*      */     } else {
/*  633 */       for (Node<E> node = this.last; node != null; node = node.prev) {
/*  634 */         i--;
/*  635 */         if (paramObject.equals(node.item))
/*  636 */           return i; 
/*      */       } 
/*      */     } 
/*  639 */     return -1;
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
/*      */   public E peek() {
/*  651 */     Node<E> node = this.first;
/*  652 */     return (node == null) ? null : node.item;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public E element() {
/*  663 */     return getFirst();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public E poll() {
/*  673 */     Node<E> node = this.first;
/*  674 */     return (node == null) ? null : unlinkFirst(node);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public E remove() {
/*  685 */     return removeFirst();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean offer(E paramE) {
/*  696 */     return add(paramE);
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
/*      */   public boolean offerFirst(E paramE) {
/*  708 */     addFirst(paramE);
/*  709 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean offerLast(E paramE) {
/*  720 */     addLast(paramE);
/*  721 */     return true;
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
/*      */   public E peekFirst() {
/*  733 */     Node<E> node = this.first;
/*  734 */     return (node == null) ? null : node.item;
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
/*      */   public E peekLast() {
/*  746 */     Node<E> node = this.last;
/*  747 */     return (node == null) ? null : node.item;
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
/*      */   public E pollFirst() {
/*  759 */     Node<E> node = this.first;
/*  760 */     return (node == null) ? null : unlinkFirst(node);
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
/*      */   public E pollLast() {
/*  772 */     Node<E> node = this.last;
/*  773 */     return (node == null) ? null : unlinkLast(node);
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
/*      */   public void push(E paramE) {
/*  786 */     addFirst(paramE);
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
/*      */   public E pop() {
/*  801 */     return removeFirst();
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
/*      */   public boolean removeFirstOccurrence(Object paramObject) {
/*  814 */     return remove(paramObject);
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
/*      */   public boolean removeLastOccurrence(Object paramObject) {
/*  827 */     if (paramObject == null) {
/*  828 */       for (Node<E> node = this.last; node != null; node = node.prev) {
/*  829 */         if (node.item == null) {
/*  830 */           unlink(node);
/*  831 */           return true;
/*      */         } 
/*      */       } 
/*      */     } else {
/*  835 */       for (Node<E> node = this.last; node != null; node = node.prev) {
/*  836 */         if (paramObject.equals(node.item)) {
/*  837 */           unlink(node);
/*  838 */           return true;
/*      */         } 
/*      */       } 
/*      */     } 
/*  842 */     return false;
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
/*      */   public ListIterator<E> listIterator(int paramInt) {
/*  867 */     checkPositionIndex(paramInt);
/*  868 */     return new ListItr(paramInt);
/*      */   }
/*      */   
/*      */   private class ListItr implements ListIterator<E> {
/*      */     private LinkedList.Node<E> lastReturned;
/*      */     private LinkedList.Node<E> next;
/*      */     private int nextIndex;
/*  875 */     private int expectedModCount = LinkedList.this.modCount;
/*      */ 
/*      */     
/*      */     ListItr(int param1Int) {
/*  879 */       this.next = (param1Int == LinkedList.this.size) ? null : LinkedList.this.node(param1Int);
/*  880 */       this.nextIndex = param1Int;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  884 */       return (this.nextIndex < LinkedList.this.size);
/*      */     }
/*      */     
/*      */     public E next() {
/*  888 */       checkForComodification();
/*  889 */       if (!hasNext()) {
/*  890 */         throw new NoSuchElementException();
/*      */       }
/*  892 */       this.lastReturned = this.next;
/*  893 */       this.next = this.next.next;
/*  894 */       this.nextIndex++;
/*  895 */       return this.lastReturned.item;
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  899 */       return (this.nextIndex > 0);
/*      */     }
/*      */     
/*      */     public E previous() {
/*  903 */       checkForComodification();
/*  904 */       if (!hasPrevious()) {
/*  905 */         throw new NoSuchElementException();
/*      */       }
/*  907 */       this.lastReturned = this.next = (this.next == null) ? LinkedList.this.last : this.next.prev;
/*  908 */       this.nextIndex--;
/*  909 */       return this.lastReturned.item;
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*  913 */       return this.nextIndex;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/*  917 */       return this.nextIndex - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/*  921 */       checkForComodification();
/*  922 */       if (this.lastReturned == null) {
/*  923 */         throw new IllegalStateException();
/*      */       }
/*  925 */       LinkedList.Node<E> node = this.lastReturned.next;
/*  926 */       LinkedList.this.unlink(this.lastReturned);
/*  927 */       if (this.next == this.lastReturned) {
/*  928 */         this.next = node;
/*      */       } else {
/*  930 */         this.nextIndex--;
/*  931 */       }  this.lastReturned = null;
/*  932 */       this.expectedModCount++;
/*      */     }
/*      */     
/*      */     public void set(E param1E) {
/*  936 */       if (this.lastReturned == null)
/*  937 */         throw new IllegalStateException(); 
/*  938 */       checkForComodification();
/*  939 */       this.lastReturned.item = param1E;
/*      */     }
/*      */     
/*      */     public void add(E param1E) {
/*  943 */       checkForComodification();
/*  944 */       this.lastReturned = null;
/*  945 */       if (this.next == null) {
/*  946 */         LinkedList.this.linkLast(param1E);
/*      */       } else {
/*  948 */         LinkedList.this.linkBefore(param1E, this.next);
/*  949 */       }  this.nextIndex++;
/*  950 */       this.expectedModCount++;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(Consumer<? super E> param1Consumer) {
/*  954 */       Objects.requireNonNull(param1Consumer);
/*  955 */       while (LinkedList.this.modCount == this.expectedModCount && this.nextIndex < LinkedList.this.size) {
/*  956 */         param1Consumer.accept(this.next.item);
/*  957 */         this.lastReturned = this.next;
/*  958 */         this.next = this.next.next;
/*  959 */         this.nextIndex++;
/*      */       } 
/*  961 */       checkForComodification();
/*      */     }
/*      */     
/*      */     final void checkForComodification() {
/*  965 */       if (LinkedList.this.modCount != this.expectedModCount)
/*  966 */         throw new ConcurrentModificationException(); 
/*      */     }
/*      */   }
/*      */   
/*      */   private static class Node<E> {
/*      */     E item;
/*      */     Node<E> next;
/*      */     Node<E> prev;
/*      */     
/*      */     Node(Node<E> param1Node1, E param1E, Node<E> param1Node2) {
/*  976 */       this.item = param1E;
/*  977 */       this.next = param1Node2;
/*  978 */       this.prev = param1Node1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Iterator<E> descendingIterator() {
/*  986 */     return new DescendingIterator();
/*      */   }
/*      */ 
/*      */   
/*      */   private class DescendingIterator
/*      */     implements Iterator<E>
/*      */   {
/*  993 */     private final LinkedList<E>.ListItr itr = new LinkedList.ListItr(LinkedList.this.size());
/*      */     public boolean hasNext() {
/*  995 */       return this.itr.hasPrevious();
/*      */     }
/*      */     public E next() {
/*  998 */       return this.itr.previous();
/*      */     }
/*      */     public void remove() {
/* 1001 */       this.itr.remove();
/*      */     }
/*      */     
/*      */     private DescendingIterator() {} }
/*      */   
/*      */   private LinkedList<E> superClone() {
/*      */     try {
/* 1008 */       return (LinkedList<E>)super.clone();
/* 1009 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 1010 */       throw new InternalError(cloneNotSupportedException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() {
/* 1021 */     LinkedList<E> linkedList = superClone();
/*      */ 
/*      */     
/* 1024 */     linkedList.first = linkedList.last = null;
/* 1025 */     linkedList.size = 0;
/* 1026 */     linkedList.modCount = 0;
/*      */ 
/*      */     
/* 1029 */     for (Node<E> node = this.first; node != null; node = node.next) {
/* 1030 */       linkedList.add(node.item);
/*      */     }
/* 1032 */     return linkedList;
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
/*      */   public Object[] toArray() {
/* 1050 */     Object[] arrayOfObject = new Object[this.size];
/* 1051 */     byte b = 0;
/* 1052 */     for (Node<E> node = this.first; node != null; node = node.next)
/* 1053 */       arrayOfObject[b++] = node.item; 
/* 1054 */     return arrayOfObject;
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
/*      */   public <T> T[] toArray(T[] paramArrayOfT) {
/* 1097 */     if (paramArrayOfT.length < this.size)
/* 1098 */       paramArrayOfT = (T[])Array.newInstance(paramArrayOfT
/* 1099 */           .getClass().getComponentType(), this.size); 
/* 1100 */     byte b = 0;
/* 1101 */     T[] arrayOfT = paramArrayOfT;
/* 1102 */     for (Node<E> node = this.first; node != null; node = node.next) {
/* 1103 */       arrayOfT[b++] = (T)node.item;
/*      */     }
/* 1105 */     if (paramArrayOfT.length > this.size) {
/* 1106 */       paramArrayOfT[this.size] = null;
/*      */     }
/* 1108 */     return paramArrayOfT;
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
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1124 */     paramObjectOutputStream.defaultWriteObject();
/*      */ 
/*      */     
/* 1127 */     paramObjectOutputStream.writeInt(this.size);
/*      */ 
/*      */     
/* 1130 */     for (Node<E> node = this.first; node != null; node = node.next) {
/* 1131 */       paramObjectOutputStream.writeObject(node.item);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1142 */     paramObjectInputStream.defaultReadObject();
/*      */ 
/*      */     
/* 1145 */     int i = paramObjectInputStream.readInt();
/*      */ 
/*      */     
/* 1148 */     for (byte b = 0; b < i; b++) {
/* 1149 */       linkLast((E)paramObjectInputStream.readObject());
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
/*      */   public Spliterator<E> spliterator() {
/* 1170 */     return new LLSpliterator<>(this, -1, 0);
/*      */   }
/*      */   
/*      */   static final class LLSpliterator<E>
/*      */     implements Spliterator<E> {
/*      */     static final int BATCH_UNIT = 1024;
/*      */     static final int MAX_BATCH = 33554432;
/*      */     final LinkedList<E> list;
/*      */     LinkedList.Node<E> current;
/*      */     int est;
/*      */     int expectedModCount;
/*      */     int batch;
/*      */     
/*      */     LLSpliterator(LinkedList<E> param1LinkedList, int param1Int1, int param1Int2) {
/* 1184 */       this.list = param1LinkedList;
/* 1185 */       this.est = param1Int1;
/* 1186 */       this.expectedModCount = param1Int2;
/*      */     }
/*      */ 
/*      */     
/*      */     final int getEst() {
/*      */       int i;
/* 1192 */       if ((i = this.est) < 0) {
/* 1193 */         LinkedList<E> linkedList; if ((linkedList = this.list) == null) {
/* 1194 */           i = this.est = 0;
/*      */         } else {
/* 1196 */           this.expectedModCount = linkedList.modCount;
/* 1197 */           this.current = linkedList.first;
/* 1198 */           i = this.est = linkedList.size;
/*      */         } 
/*      */       } 
/* 1201 */       return i;
/*      */     }
/*      */     public long estimateSize() {
/* 1204 */       return getEst();
/*      */     }
/*      */     
/*      */     public Spliterator<E> trySplit() {
/* 1208 */       int i = getEst(); LinkedList.Node<E> node;
/* 1209 */       if (i > 1 && (node = this.current) != null) {
/* 1210 */         int j = this.batch + 1024;
/* 1211 */         if (j > i)
/* 1212 */           j = i; 
/* 1213 */         if (j > 33554432)
/* 1214 */           j = 33554432; 
/* 1215 */         Object[] arrayOfObject = new Object[j];
/* 1216 */         byte b = 0; 
/* 1217 */         do { arrayOfObject[b++] = node.item; } while ((node = node.next) != null && b < j);
/* 1218 */         this.current = node;
/* 1219 */         this.batch = b;
/* 1220 */         this.est = i - b;
/* 1221 */         return Spliterators.spliterator(arrayOfObject, 0, b, 16);
/*      */       } 
/* 1223 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super E> param1Consumer) {
/* 1228 */       if (param1Consumer == null) throw new NullPointerException();  LinkedList.Node<E> node; int i;
/* 1229 */       if ((i = getEst()) > 0 && (node = this.current) != null) {
/* 1230 */         this.current = null;
/* 1231 */         this.est = 0;
/*      */         do {
/* 1233 */           E e = node.item;
/* 1234 */           node = node.next;
/* 1235 */           param1Consumer.accept(e);
/* 1236 */         } while (node != null && --i > 0);
/*      */       } 
/* 1238 */       if (this.list.modCount != this.expectedModCount) {
/* 1239 */         throw new ConcurrentModificationException();
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super E> param1Consumer) {
/* 1244 */       if (param1Consumer == null) throw new NullPointerException();  LinkedList.Node<E> node;
/* 1245 */       if (getEst() > 0 && (node = this.current) != null) {
/* 1246 */         this.est--;
/* 1247 */         E e = node.item;
/* 1248 */         this.current = node.next;
/* 1249 */         param1Consumer.accept(e);
/* 1250 */         if (this.list.modCount != this.expectedModCount)
/* 1251 */           throw new ConcurrentModificationException(); 
/* 1252 */         return true;
/*      */       } 
/* 1254 */       return false;
/*      */     }
/*      */     
/*      */     public int characteristics() {
/* 1258 */       return 16464;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/LinkedList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */