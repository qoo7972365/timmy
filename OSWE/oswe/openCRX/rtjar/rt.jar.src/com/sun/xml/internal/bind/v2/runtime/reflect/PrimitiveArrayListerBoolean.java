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
/*     */ final class PrimitiveArrayListerBoolean<BeanT>
/*     */   extends Lister<BeanT, boolean[], Boolean, PrimitiveArrayListerBoolean.BooleanArrayPack>
/*     */ {
/*     */   static void register() {
/*  47 */     Lister.primitiveArrayListers.put(boolean.class, new PrimitiveArrayListerBoolean());
/*     */   }
/*     */   
/*     */   public ListIterator<Boolean> iterator(final boolean[] objects, XMLSerializer context) {
/*  51 */     return new ListIterator<Boolean>() {
/*  52 */         int idx = 0;
/*     */         public boolean hasNext() {
/*  54 */           return (this.idx < objects.length);
/*     */         }
/*     */         
/*     */         public Boolean next() {
/*  58 */           return Boolean.valueOf(objects[this.idx++]);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public BooleanArrayPack startPacking(BeanT current, Accessor<BeanT, boolean[]> acc) {
/*  64 */     return new BooleanArrayPack();
/*     */   }
/*     */   
/*     */   public void addToPack(BooleanArrayPack objects, Boolean o) {
/*  68 */     objects.add(o);
/*     */   }
/*     */   
/*     */   public void endPacking(BooleanArrayPack pack, BeanT bean, Accessor<BeanT, boolean[]> acc) throws AccessorException {
/*  72 */     acc.set(bean, pack.build());
/*     */   }
/*     */   
/*     */   public void reset(BeanT o, Accessor<BeanT, boolean[]> acc) throws AccessorException {
/*  76 */     acc.set(o, new boolean[0]);
/*     */   }
/*     */   
/*     */   static final class BooleanArrayPack {
/*  80 */     boolean[] buf = new boolean[16];
/*     */     int size;
/*     */     
/*     */     void add(Boolean b) {
/*  84 */       if (this.buf.length == this.size) {
/*     */         
/*  86 */         boolean[] nb = new boolean[this.buf.length * 2];
/*  87 */         System.arraycopy(this.buf, 0, nb, 0, this.buf.length);
/*  88 */         this.buf = nb;
/*     */       } 
/*  90 */       if (b != null)
/*  91 */         this.buf[this.size++] = b.booleanValue(); 
/*     */     }
/*     */     
/*     */     boolean[] build() {
/*  95 */       if (this.buf.length == this.size)
/*     */       {
/*  97 */         return this.buf;
/*     */       }
/*  99 */       boolean[] r = new boolean[this.size];
/* 100 */       System.arraycopy(this.buf, 0, r, 0, this.size);
/* 101 */       return r;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/reflect/PrimitiveArrayListerBoolean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */