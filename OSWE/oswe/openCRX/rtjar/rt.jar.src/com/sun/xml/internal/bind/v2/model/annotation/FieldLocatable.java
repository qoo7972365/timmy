/*    */ package com.sun.xml.internal.bind.v2.model.annotation;
/*    */ 
/*    */ import com.sun.xml.internal.bind.v2.model.nav.Navigator;
/*    */ import com.sun.xml.internal.bind.v2.runtime.Location;
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
/*    */ public class FieldLocatable<F>
/*    */   implements Locatable
/*    */ {
/*    */   private final Locatable upstream;
/*    */   private final F field;
/*    */   private final Navigator<?, ?, F, ?> nav;
/*    */   
/*    */   public FieldLocatable(Locatable upstream, F field, Navigator<?, ?, F, ?> nav) {
/* 42 */     this.upstream = upstream;
/* 43 */     this.field = field;
/* 44 */     this.nav = nav;
/*    */   }
/*    */   
/*    */   public Locatable getUpstream() {
/* 48 */     return this.upstream;
/*    */   }
/*    */   
/*    */   public Location getLocation() {
/* 52 */     return this.nav.getFieldLocation(this.field);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/annotation/FieldLocatable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */