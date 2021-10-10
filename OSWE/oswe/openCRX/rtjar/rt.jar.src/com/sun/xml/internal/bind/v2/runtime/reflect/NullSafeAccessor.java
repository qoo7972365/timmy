/*    */ package com.sun.xml.internal.bind.v2.runtime.reflect;
/*    */ 
/*    */ import com.sun.xml.internal.bind.api.AccessorException;
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
/*    */ public class NullSafeAccessor<B, V, P>
/*    */   extends Accessor<B, V>
/*    */ {
/*    */   private final Accessor<B, V> core;
/*    */   private final Lister<B, V, ?, P> lister;
/*    */   
/*    */   public NullSafeAccessor(Accessor<B, V> core, Lister<B, V, ?, P> lister) {
/* 44 */     super(core.getValueType());
/* 45 */     this.core = core;
/* 46 */     this.lister = lister;
/*    */   }
/*    */   
/*    */   public V get(B bean) throws AccessorException {
/* 50 */     V v = this.core.get(bean);
/* 51 */     if (v == null) {
/*    */       
/* 53 */       P pack = this.lister.startPacking(bean, this.core);
/* 54 */       this.lister.endPacking(pack, bean, this.core);
/* 55 */       v = this.core.get(bean);
/*    */     } 
/* 57 */     return v;
/*    */   }
/*    */   
/*    */   public void set(B bean, V value) throws AccessorException {
/* 61 */     this.core.set(bean, value);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/reflect/NullSafeAccessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */