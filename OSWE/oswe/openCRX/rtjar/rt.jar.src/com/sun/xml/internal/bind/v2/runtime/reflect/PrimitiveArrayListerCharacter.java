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
/*     */ final class PrimitiveArrayListerCharacter<BeanT>
/*     */   extends Lister<BeanT, char[], Character, PrimitiveArrayListerCharacter.CharacterArrayPack>
/*     */ {
/*     */   static void register() {
/*  47 */     Lister.primitiveArrayListers.put(char.class, new PrimitiveArrayListerCharacter());
/*     */   }
/*     */   
/*     */   public ListIterator<Character> iterator(final char[] objects, XMLSerializer context) {
/*  51 */     return new ListIterator<Character>() {
/*  52 */         int idx = 0;
/*     */         public boolean hasNext() {
/*  54 */           return (this.idx < objects.length);
/*     */         }
/*     */         
/*     */         public Character next() {
/*  58 */           return Character.valueOf(objects[this.idx++]);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public CharacterArrayPack startPacking(BeanT current, Accessor<BeanT, char[]> acc) {
/*  64 */     return new CharacterArrayPack();
/*     */   }
/*     */   
/*     */   public void addToPack(CharacterArrayPack objects, Character o) {
/*  68 */     objects.add(o);
/*     */   }
/*     */   
/*     */   public void endPacking(CharacterArrayPack pack, BeanT bean, Accessor<BeanT, char[]> acc) throws AccessorException {
/*  72 */     acc.set(bean, pack.build());
/*     */   }
/*     */   
/*     */   public void reset(BeanT o, Accessor<BeanT, char[]> acc) throws AccessorException {
/*  76 */     acc.set(o, new char[0]);
/*     */   }
/*     */   
/*     */   static final class CharacterArrayPack {
/*  80 */     char[] buf = new char[16];
/*     */     int size;
/*     */     
/*     */     void add(Character b) {
/*  84 */       if (this.buf.length == this.size) {
/*     */         
/*  86 */         char[] nb = new char[this.buf.length * 2];
/*  87 */         System.arraycopy(this.buf, 0, nb, 0, this.buf.length);
/*  88 */         this.buf = nb;
/*     */       } 
/*  90 */       if (b != null)
/*  91 */         this.buf[this.size++] = b.charValue(); 
/*     */     }
/*     */     
/*     */     char[] build() {
/*  95 */       if (this.buf.length == this.size)
/*     */       {
/*  97 */         return this.buf;
/*     */       }
/*  99 */       char[] r = new char[this.size];
/* 100 */       System.arraycopy(this.buf, 0, r, 0, this.size);
/* 101 */       return r;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/reflect/PrimitiveArrayListerCharacter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */