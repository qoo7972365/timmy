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
/*    */ public class MethodLocatable<M>
/*    */   implements Locatable
/*    */ {
/*    */   private final Locatable upstream;
/*    */   private final M method;
/*    */   private final Navigator<?, ?, ?, M> nav;
/*    */   
/*    */   public MethodLocatable(Locatable upstream, M method, Navigator<?, ?, ?, M> nav) {
/* 42 */     this.upstream = upstream;
/* 43 */     this.method = method;
/* 44 */     this.nav = nav;
/*    */   }
/*    */   
/*    */   public Locatable getUpstream() {
/* 48 */     return this.upstream;
/*    */   }
/*    */   
/*    */   public Location getLocation() {
/* 52 */     return this.nav.getMethodLocation(this.method);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/annotation/MethodLocatable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */