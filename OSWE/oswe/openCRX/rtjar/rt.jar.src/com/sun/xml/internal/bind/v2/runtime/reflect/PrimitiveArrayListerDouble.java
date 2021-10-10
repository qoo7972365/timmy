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
/*     */ final class PrimitiveArrayListerDouble<BeanT>
/*     */   extends Lister<BeanT, double[], Double, PrimitiveArrayListerDouble.DoubleArrayPack>
/*     */ {
/*     */   static void register() {
/*  47 */     Lister.primitiveArrayListers.put(double.class, new PrimitiveArrayListerDouble());
/*     */   }
/*     */   
/*     */   public ListIterator<Double> iterator(final double[] objects, XMLSerializer context) {
/*  51 */     return new ListIterator<Double>() {
/*  52 */         int idx = 0;
/*     */         public boolean hasNext() {
/*  54 */           return (this.idx < objects.length);
/*     */         }
/*     */         
/*     */         public Double next() {
/*  58 */           return Double.valueOf(objects[this.idx++]);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public DoubleArrayPack startPacking(BeanT current, Accessor<BeanT, double[]> acc) {
/*  64 */     return new DoubleArrayPack();
/*     */   }
/*     */   
/*     */   public void addToPack(DoubleArrayPack objects, Double o) {
/*  68 */     objects.add(o);
/*     */   }
/*     */   
/*     */   public void endPacking(DoubleArrayPack pack, BeanT bean, Accessor<BeanT, double[]> acc) throws AccessorException {
/*  72 */     acc.set(bean, pack.build());
/*     */   }
/*     */   
/*     */   public void reset(BeanT o, Accessor<BeanT, double[]> acc) throws AccessorException {
/*  76 */     acc.set(o, new double[0]);
/*     */   }
/*     */   
/*     */   static final class DoubleArrayPack {
/*  80 */     double[] buf = new double[16];
/*     */     int size;
/*     */     
/*     */     void add(Double b) {
/*  84 */       if (this.buf.length == this.size) {
/*     */         
/*  86 */         double[] nb = new double[this.buf.length * 2];
/*  87 */         System.arraycopy(this.buf, 0, nb, 0, this.buf.length);
/*  88 */         this.buf = nb;
/*     */       } 
/*  90 */       if (b != null)
/*  91 */         this.buf[this.size++] = b.doubleValue(); 
/*     */     }
/*     */     
/*     */     double[] build() {
/*  95 */       if (this.buf.length == this.size)
/*     */       {
/*  97 */         return this.buf;
/*     */       }
/*  99 */       double[] r = new double[this.size];
/* 100 */       System.arraycopy(this.buf, 0, r, 0, this.size);
/* 101 */       return r;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/reflect/PrimitiveArrayListerDouble.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */