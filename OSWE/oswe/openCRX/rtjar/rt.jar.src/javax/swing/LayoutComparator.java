/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.ListIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class LayoutComparator
/*     */   implements Comparator<Component>, Serializable
/*     */ {
/*     */   private static final int ROW_TOLERANCE = 10;
/*     */   private boolean horizontal = true;
/*     */   private boolean leftToRight = true;
/*     */   
/*     */   void setComponentOrientation(ComponentOrientation paramComponentOrientation) {
/*  50 */     this.horizontal = paramComponentOrientation.isHorizontal();
/*  51 */     this.leftToRight = paramComponentOrientation.isLeftToRight();
/*     */   }
/*     */   
/*     */   public int compare(Component paramComponent1, Component paramComponent2) {
/*  55 */     if (paramComponent1 == paramComponent2) {
/*  56 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  64 */     if (paramComponent1.getParent() != paramComponent2.getParent()) {
/*  65 */       LinkedList<Component> linkedList1 = new LinkedList();
/*     */       
/*  67 */       for (; paramComponent1 != null; paramComponent1 = paramComponent1.getParent()) {
/*  68 */         linkedList1.add(paramComponent1);
/*  69 */         if (paramComponent1 instanceof java.awt.Window) {
/*     */           break;
/*     */         }
/*     */       } 
/*  73 */       if (paramComponent1 == null)
/*     */       {
/*  75 */         throw new ClassCastException();
/*     */       }
/*     */       
/*  78 */       LinkedList<Component> linkedList2 = new LinkedList();
/*     */       
/*  80 */       for (; paramComponent2 != null; paramComponent2 = paramComponent2.getParent()) {
/*  81 */         linkedList2.add(paramComponent2);
/*  82 */         if (paramComponent2 instanceof java.awt.Window) {
/*     */           break;
/*     */         }
/*     */       } 
/*  86 */       if (paramComponent2 == null)
/*     */       {
/*  88 */         throw new ClassCastException();
/*     */       }
/*     */ 
/*     */       
/*  92 */       ListIterator<Component> listIterator1 = linkedList1.listIterator(linkedList1.size());
/*  93 */       ListIterator<Component> listIterator2 = linkedList2.listIterator(linkedList2.size()); do {
/*  94 */         if (listIterator1.hasPrevious()) {
/*  95 */           paramComponent1 = listIterator1.previous();
/*     */         } else {
/*     */           
/*  98 */           return -1;
/*     */         } 
/*     */         
/* 101 */         if (listIterator2.hasPrevious()) {
/* 102 */           paramComponent2 = listIterator2.previous();
/*     */         } else {
/*     */           
/* 105 */           return 1;
/*     */         }
/*     */       
/* 108 */       } while (paramComponent1 == paramComponent2);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     int i = paramComponent1.getX(), j = paramComponent1.getY(), k = paramComponent2.getX(), m = paramComponent2.getY();
/*     */     
/* 116 */     int n = paramComponent1.getParent().getComponentZOrder(paramComponent1) - paramComponent2.getParent().getComponentZOrder(paramComponent2);
/* 117 */     if (this.horizontal) {
/* 118 */       if (this.leftToRight) {
/*     */ 
/*     */ 
/*     */         
/* 122 */         if (Math.abs(j - m) < 10) {
/* 123 */           return (i < k) ? -1 : ((i > k) ? 1 : n);
/*     */         }
/* 125 */         return (j < m) ? -1 : 1;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 131 */       if (Math.abs(j - m) < 10) {
/* 132 */         return (i > k) ? -1 : ((i < k) ? 1 : n);
/*     */       }
/* 134 */       return (j < m) ? -1 : 1;
/*     */     } 
/*     */ 
/*     */     
/* 138 */     if (this.leftToRight) {
/*     */ 
/*     */ 
/*     */       
/* 142 */       if (Math.abs(i - k) < 10) {
/* 143 */         return (j < m) ? -1 : ((j > m) ? 1 : n);
/*     */       }
/* 145 */       return (i < k) ? -1 : 1;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 151 */     if (Math.abs(i - k) < 10) {
/* 152 */       return (j < m) ? -1 : ((j > m) ? 1 : n);
/*     */     }
/* 154 */     return (i > k) ? -1 : 1;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/LayoutComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */