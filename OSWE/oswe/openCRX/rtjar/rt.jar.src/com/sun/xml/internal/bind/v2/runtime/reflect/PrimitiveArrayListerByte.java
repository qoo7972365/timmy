/*    */ package com.sun.xml.internal.bind.v2.runtime.reflect;
/*    */ 
/*    */ import com.sun.xml.internal.bind.api.AccessorException;
/*    */ import com.sun.xml.internal.bind.v2.runtime.XMLSerializer;
/*    */ import javax.xml.bind.JAXBException;
/*    */ import org.xml.sax.SAXException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class PrimitiveArrayListerByte<BeanT>
/*    */   extends Lister<BeanT, byte[], Byte, PrimitiveArrayListerByte.ByteArrayPack>
/*    */ {
/*    */   static void register() {
/* 44 */     Lister.primitiveArrayListers.put(byte.class, new PrimitiveArrayListerByte());
/*    */   }
/*    */   
/*    */   public ListIterator<Byte> iterator(final byte[] objects, XMLSerializer context) {
/* 48 */     return new ListIterator<Byte>() {
/* 49 */         int idx = 0;
/*    */         public boolean hasNext() {
/* 51 */           return (this.idx < objects.length);
/*    */         }
/*    */         
/*    */         public Byte next() {
/* 55 */           return Byte.valueOf(objects[this.idx++]);
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   public ByteArrayPack startPacking(BeanT current, Accessor<BeanT, byte[]> acc) {
/* 61 */     return new ByteArrayPack();
/*    */   }
/*    */   
/*    */   public void addToPack(ByteArrayPack objects, Byte o) {
/* 65 */     objects.add(o);
/*    */   }
/*    */   
/*    */   public void endPacking(ByteArrayPack pack, BeanT bean, Accessor<BeanT, byte[]> acc) throws AccessorException {
/* 69 */     acc.set(bean, pack.build());
/*    */   }
/*    */   
/*    */   public void reset(BeanT o, Accessor<BeanT, byte[]> acc) throws AccessorException {
/* 73 */     acc.set(o, new byte[0]);
/*    */   }
/*    */   
/*    */   static final class ByteArrayPack {
/* 77 */     byte[] buf = new byte[16];
/*    */     int size;
/*    */     
/*    */     void add(Byte b) {
/* 81 */       if (this.buf.length == this.size) {
/*    */         
/* 83 */         byte[] nb = new byte[this.buf.length * 2];
/* 84 */         System.arraycopy(this.buf, 0, nb, 0, this.buf.length);
/* 85 */         this.buf = nb;
/*    */       } 
/* 87 */       if (b != null)
/* 88 */         this.buf[this.size++] = b.byteValue(); 
/*    */     }
/*    */     
/*    */     byte[] build() {
/* 92 */       if (this.buf.length == this.size)
/*    */       {
/* 94 */         return this.buf;
/*    */       }
/* 96 */       byte[] r = new byte[this.size];
/* 97 */       System.arraycopy(this.buf, 0, r, 0, this.size);
/* 98 */       return r;
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/reflect/PrimitiveArrayListerByte.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */