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
/*     */ final class PrimitiveArrayListerLong<BeanT>
/*     */   extends Lister<BeanT, long[], Long, PrimitiveArrayListerLong.LongArrayPack>
/*     */ {
/*     */   static void register() {
/*  47 */     Lister.primitiveArrayListers.put(long.class, new PrimitiveArrayListerLong());
/*     */   }
/*     */   
/*     */   public ListIterator<Long> iterator(final long[] objects, XMLSerializer context) {
/*  51 */     return new ListIterator<Long>() {
/*  52 */         int idx = 0;
/*     */         public boolean hasNext() {
/*  54 */           return (this.idx < objects.length);
/*     */         }
/*     */         
/*     */         public Long next() {
/*  58 */           return Long.valueOf(objects[this.idx++]);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public LongArrayPack startPacking(BeanT current, Accessor<BeanT, long[]> acc) {
/*  64 */     return new LongArrayPack();
/*     */   }
/*     */   
/*     */   public void addToPack(LongArrayPack objects, Long o) {
/*  68 */     objects.add(o);
/*     */   }
/*     */   
/*     */   public void endPacking(LongArrayPack pack, BeanT bean, Accessor<BeanT, long[]> acc) throws AccessorException {
/*  72 */     acc.set(bean, pack.build());
/*     */   }
/*     */   
/*     */   public void reset(BeanT o, Accessor<BeanT, long[]> acc) throws AccessorException {
/*  76 */     acc.set(o, new long[0]);
/*     */   }
/*     */   
/*     */   static final class LongArrayPack {
/*  80 */     long[] buf = new long[16];
/*     */     int size;
/*     */     
/*     */     void add(Long b) {
/*  84 */       if (this.buf.length == this.size) {
/*     */         
/*  86 */         long[] nb = new long[this.buf.length * 2];
/*  87 */         System.arraycopy(this.buf, 0, nb, 0, this.buf.length);
/*  88 */         this.buf = nb;
/*     */       } 
/*  90 */       if (b != null)
/*  91 */         this.buf[this.size++] = b.longValue(); 
/*     */     }
/*     */     
/*     */     long[] build() {
/*  95 */       if (this.buf.length == this.size)
/*     */       {
/*  97 */         return this.buf;
/*     */       }
/*  99 */       long[] r = new long[this.size];
/* 100 */       System.arraycopy(this.buf, 0, r, 0, this.size);
/* 101 */       return r;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/reflect/PrimitiveArrayListerLong.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */