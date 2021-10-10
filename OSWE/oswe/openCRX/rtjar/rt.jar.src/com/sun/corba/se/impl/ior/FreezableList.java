/*     */ package com.sun.corba.se.impl.ior;
/*     */ 
/*     */ import com.sun.corba.se.spi.ior.MakeImmutable;
/*     */ import java.util.AbstractList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FreezableList
/*     */   extends AbstractList
/*     */ {
/*  44 */   private List delegate = null;
/*     */   
/*     */   private boolean immutable = false;
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  49 */     if (paramObject == null) {
/*  50 */       return false;
/*     */     }
/*  52 */     if (!(paramObject instanceof FreezableList)) {
/*  53 */       return false;
/*     */     }
/*  55 */     FreezableList freezableList = (FreezableList)paramObject;
/*     */     
/*  57 */     return (this.delegate.equals(freezableList.delegate) && this.immutable == freezableList.immutable);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  63 */     return this.delegate.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public FreezableList(List paramList, boolean paramBoolean) {
/*  68 */     this.delegate = paramList;
/*  69 */     this.immutable = paramBoolean;
/*     */   }
/*     */ 
/*     */   
/*     */   public FreezableList(List paramList) {
/*  74 */     this(paramList, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void makeImmutable() {
/*  79 */     this.immutable = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isImmutable() {
/*  84 */     return this.immutable;
/*     */   }
/*     */ 
/*     */   
/*     */   public void makeElementsImmutable() {
/*  89 */     Iterator<E> iterator = iterator();
/*  90 */     while (iterator.hasNext()) {
/*  91 */       E e = iterator.next();
/*  92 */       if (e instanceof MakeImmutable) {
/*  93 */         MakeImmutable makeImmutable = (MakeImmutable)e;
/*  94 */         makeImmutable.makeImmutable();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 103 */     return this.delegate.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object get(int paramInt) {
/* 108 */     return this.delegate.get(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object set(int paramInt, Object paramObject) {
/* 113 */     if (this.immutable) {
/* 114 */       throw new UnsupportedOperationException();
/*     */     }
/* 116 */     return this.delegate.set(paramInt, paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(int paramInt, Object paramObject) {
/* 121 */     if (this.immutable) {
/* 122 */       throw new UnsupportedOperationException();
/*     */     }
/* 124 */     this.delegate.add(paramInt, paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object remove(int paramInt) {
/* 129 */     if (this.immutable) {
/* 130 */       throw new UnsupportedOperationException();
/*     */     }
/* 132 */     return this.delegate.remove(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List subList(int paramInt1, int paramInt2) {
/* 138 */     List list = this.delegate.subList(paramInt1, paramInt2);
/* 139 */     return new FreezableList(list, this.immutable);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/FreezableList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */