/*    */ package com.sun.corba.se.spi.ior;
/*    */ 
/*    */ import com.sun.corba.se.impl.ior.FreezableList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IdentifiableContainerBase
/*    */   extends FreezableList
/*    */ {
/*    */   public IdentifiableContainerBase() {
/* 50 */     super(new ArrayList());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Iterator iteratorById(final int id) {
/* 58 */     return new Iterator() {
/* 59 */         Iterator iter = IdentifiableContainerBase.this.iterator();
/* 60 */         Object current = advance();
/*    */ 
/*    */         
/*    */         private Object advance() {
/* 64 */           while (this.iter.hasNext()) {
/* 65 */             Identifiable identifiable = this.iter.next();
/* 66 */             if (identifiable.getId() == id) {
/* 67 */               return identifiable;
/*    */             }
/*    */           } 
/* 70 */           return null;
/*    */         }
/*    */ 
/*    */         
/*    */         public boolean hasNext() {
/* 75 */           return (this.current != null);
/*    */         }
/*    */ 
/*    */         
/*    */         public Object next() {
/* 80 */           Object object = this.current;
/* 81 */           this.current = advance();
/* 82 */           return object;
/*    */         }
/*    */ 
/*    */         
/*    */         public void remove() {
/* 87 */           this.iter.remove();
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/ior/IdentifiableContainerBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */