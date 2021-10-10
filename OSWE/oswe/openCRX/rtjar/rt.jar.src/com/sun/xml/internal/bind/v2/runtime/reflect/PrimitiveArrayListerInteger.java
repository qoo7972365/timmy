/*     */ package com.sun.xml.internal.bind.v2.runtime.reflect;
/*     */ 
/*     */ import com.sun.xml.internal.bind.api.AccessorException;
/*     */ import com.sun.xml.internal.bind.v2.runtime.XMLSerializer;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class PrimitiveArrayListerInteger<BeanT>
/*     */   extends Lister<BeanT, int[], Integer, PrimitiveArrayListerInteger.IntegerArrayPack>
/*     */ {
/*     */   static void register() {
/*  47 */     Lister.primitiveArrayListers.put(int.class, new PrimitiveArrayListerInteger());
/*     */   }
/*     */   
/*     */   public ListIterator<Integer> iterator(final int[] objects, XMLSerializer context) {
/*  51 */     return new ListIterator<Integer>() {
/*  52 */         int idx = 0;
/*     */         public boolean hasNext() {
/*  54 */           return (this.idx < objects.length);
/*     */         }
/*     */         
/*     */         public Integer next() {
/*  58 */           return Integer.valueOf(objects[this.idx++]);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public IntegerArrayPack startPacking(BeanT current, Accessor<BeanT, int[]> acc) {
/*  64 */     return new IntegerArrayPack();
/*     */   }
/*     */   
/*     */   public void addToPack(IntegerArrayPack objects, Integer o) {
/*  68 */     objects.add(o);
/*     */   }
/*     */   
/*     */   public void endPacking(IntegerArrayPack pack, BeanT bean, Accessor<BeanT, int[]> acc) throws AccessorException {
/*  72 */     acc.set(bean, pack.build());
/*     */   }
/*     */   
/*     */   public void reset(BeanT o, Accessor<BeanT, int[]> acc) throws AccessorException {
/*  76 */     acc.set(o, new int[0]);
/*     */   }
/*     */   
/*     */   static final class IntegerArrayPack {
/*  80 */     int[] buf = new int[16];
/*     */     int size;
/*     */     
/*     */     void add(Integer b) {
/*  84 */       if (this.buf.length == this.size) {
/*     */         
/*  86 */         int[] nb = new int[this.buf.length * 2];
/*  87 */         System.arraycopy(this.buf, 0, nb, 0, this.buf.length);
/*  88 */         this.buf = nb;
/*     */       } 
/*  90 */       if (b != null)
/*  91 */         this.buf[this.size++] = b.intValue(); 
/*     */     }
/*     */     
/*     */     int[] build() {
/*  95 */       if (this.buf.length == this.size)
/*     */       {
/*  97 */         return this.buf;
/*     */       }
/*  99 */       int[] r = new int[this.size];
/* 100 */       System.arraycopy(this.buf, 0, r, 0, this.size);
/* 101 */       return r;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/reflect/PrimitiveArrayListerInteger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */