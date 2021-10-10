/*    */ package com.sun.xml.internal.bind.v2.model.annotation;
/*    */ 
/*    */ import com.sun.xml.internal.bind.v2.runtime.Location;
/*    */ import java.lang.annotation.Annotation;
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
/*    */ public abstract class Quick
/*    */   implements Annotation, Locatable, Location
/*    */ {
/*    */   private final Locatable upstream;
/*    */   
/*    */   protected Quick(Locatable upstream) {
/* 45 */     this.upstream = upstream;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract Annotation getAnnotation();
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract Quick newInstance(Locatable paramLocatable, Annotation paramAnnotation);
/*    */ 
/*    */ 
/*    */   
/*    */   public final Location getLocation() {
/* 60 */     return this;
/*    */   }
/*    */   
/*    */   public final Locatable getUpstream() {
/* 64 */     return this.upstream;
/*    */   }
/*    */   
/*    */   public final String toString() {
/* 68 */     return getAnnotation().toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/annotation/Quick.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */