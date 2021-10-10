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
/*     */ final class PrimitiveArrayListerShort<BeanT>
/*     */   extends Lister<BeanT, short[], Short, PrimitiveArrayListerShort.ShortArrayPack>
/*     */ {
/*     */   static void register() {
/*  47 */     Lister.primitiveArrayListers.put(short.class, new PrimitiveArrayListerShort());
/*     */   }
/*     */   
/*     */   public ListIterator<Short> iterator(final short[] objects, XMLSerializer context) {
/*  51 */     return new ListIterator<Short>() {
/*  52 */         int idx = 0;
/*     */         public boolean hasNext() {
/*  54 */           return (this.idx < objects.length);
/*     */         }
/*     */         
/*     */         public Short next() {
/*  58 */           return Short.valueOf(objects[this.idx++]);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public ShortArrayPack startPacking(BeanT current, Accessor<BeanT, short[]> acc) {
/*  64 */     return new ShortArrayPack();
/*     */   }
/*     */   
/*     */   public void addToPack(ShortArrayPack objects, Short o) {
/*  68 */     objects.add(o);
/*     */   }
/*     */   
/*     */   public void endPacking(ShortArrayPack pack, BeanT bean, Accessor<BeanT, short[]> acc) throws AccessorException {
/*  72 */     acc.set(bean, pack.build());
/*     */   }
/*     */   
/*     */   public void reset(BeanT o, Accessor<BeanT, short[]> acc) throws AccessorException {
/*  76 */     acc.set(o, new short[0]);
/*     */   }
/*     */   
/*     */   static final class ShortArrayPack {
/*  80 */     short[] buf = new short[16];
/*     */     int size;
/*     */     
/*     */     void add(Short b) {
/*  84 */       if (this.buf.length == this.size) {
/*     */         
/*  86 */         short[] nb = new short[this.buf.length * 2];
/*  87 */         System.arraycopy(this.buf, 0, nb, 0, this.buf.length);
/*  88 */         this.buf = nb;
/*     */       } 
/*  90 */       if (b != null)
/*  91 */         this.buf[this.size++] = b.shortValue(); 
/*     */     }
/*     */     
/*     */     short[] build() {
/*  95 */       if (this.buf.length == this.size)
/*     */       {
/*  97 */         return this.buf;
/*     */       }
/*  99 */       short[] r = new short[this.size];
/* 100 */       System.arraycopy(this.buf, 0, r, 0, this.size);
/* 101 */       return r;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/reflect/PrimitiveArrayListerShort.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */