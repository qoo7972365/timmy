/*    */ package com.sun.xml.internal.ws.db.glassfish;
/*    */ 
/*    */ import com.sun.xml.internal.bind.api.AccessorException;
/*    */ import com.sun.xml.internal.bind.api.RawAccessor;
/*    */ import com.sun.xml.internal.ws.spi.db.DatabindingException;
/*    */ import com.sun.xml.internal.ws.spi.db.PropertyAccessor;
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
/*    */ public class RawAccessorWrapper
/*    */   implements PropertyAccessor
/*    */ {
/*    */   private RawAccessor accessor;
/*    */   
/*    */   public RawAccessorWrapper(RawAccessor a) {
/* 39 */     this.accessor = a;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 44 */     return this.accessor.equals(obj);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object get(Object bean) throws DatabindingException {
/*    */     try {
/* 50 */       return this.accessor.get(bean);
/* 51 */     } catch (AccessorException e) {
/* 52 */       throw new DatabindingException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 58 */     return this.accessor.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public void set(Object bean, Object value) throws DatabindingException {
/*    */     try {
/* 64 */       this.accessor.set(bean, value);
/* 65 */     } catch (AccessorException e) {
/* 66 */       throw new DatabindingException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 72 */     return this.accessor.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/db/glassfish/RawAccessorWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */