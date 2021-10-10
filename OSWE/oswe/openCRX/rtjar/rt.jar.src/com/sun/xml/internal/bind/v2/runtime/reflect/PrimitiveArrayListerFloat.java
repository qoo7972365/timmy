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
/*     */ final class PrimitiveArrayListerFloat<BeanT>
/*     */   extends Lister<BeanT, float[], Float, PrimitiveArrayListerFloat.FloatArrayPack>
/*     */ {
/*     */   static void register() {
/*  47 */     Lister.primitiveArrayListers.put(float.class, new PrimitiveArrayListerFloat());
/*     */   }
/*     */   
/*     */   public ListIterator<Float> iterator(final float[] objects, XMLSerializer context) {
/*  51 */     return new ListIterator<Float>() {
/*  52 */         int idx = 0;
/*     */         public boolean hasNext() {
/*  54 */           return (this.idx < objects.length);
/*     */         }
/*     */         
/*     */         public Float next() {
/*  58 */           return Float.valueOf(objects[this.idx++]);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public FloatArrayPack startPacking(BeanT current, Accessor<BeanT, float[]> acc) {
/*  64 */     return new FloatArrayPack();
/*     */   }
/*     */   
/*     */   public void addToPack(FloatArrayPack objects, Float o) {
/*  68 */     objects.add(o);
/*     */   }
/*     */   
/*     */   public void endPacking(FloatArrayPack pack, BeanT bean, Accessor<BeanT, float[]> acc) throws AccessorException {
/*  72 */     acc.set(bean, pack.build());
/*     */   }
/*     */   
/*     */   public void reset(BeanT o, Accessor<BeanT, float[]> acc) throws AccessorException {
/*  76 */     acc.set(o, new float[0]);
/*     */   }
/*     */   
/*     */   static final class FloatArrayPack {
/*  80 */     float[] buf = new float[16];
/*     */     int size;
/*     */     
/*     */     void add(Float b) {
/*  84 */       if (this.buf.length == this.size) {
/*     */         
/*  86 */         float[] nb = new float[this.buf.length * 2];
/*  87 */         System.arraycopy(this.buf, 0, nb, 0, this.buf.length);
/*  88 */         this.buf = nb;
/*     */       } 
/*  90 */       if (b != null)
/*  91 */         this.buf[this.size++] = b.floatValue(); 
/*     */     }
/*     */     
/*     */     float[] build() {
/*  95 */       if (this.buf.length == this.size)
/*     */       {
/*  97 */         return this.buf;
/*     */       }
/*  99 */       float[] r = new float[this.size];
/* 100 */       System.arraycopy(this.buf, 0, r, 0, this.size);
/* 101 */       return r;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/reflect/PrimitiveArrayListerFloat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */