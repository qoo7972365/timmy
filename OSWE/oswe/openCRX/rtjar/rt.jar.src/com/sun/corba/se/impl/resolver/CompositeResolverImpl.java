/*    */ package com.sun.corba.se.impl.resolver;
/*    */ 
/*    */ import com.sun.corba.se.spi.resolver.Resolver;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import org.omg.CORBA.Object;
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
/*    */ public class CompositeResolverImpl
/*    */   implements Resolver
/*    */ {
/*    */   private Resolver first;
/*    */   private Resolver second;
/*    */   
/*    */   public CompositeResolverImpl(Resolver paramResolver1, Resolver paramResolver2) {
/* 39 */     this.first = paramResolver1;
/* 40 */     this.second = paramResolver2;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object resolve(String paramString) {
/* 45 */     Object object = this.first.resolve(paramString);
/* 46 */     if (object == null)
/* 47 */       object = this.second.resolve(paramString); 
/* 48 */     return object;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set list() {
/* 53 */     HashSet hashSet = new HashSet();
/* 54 */     hashSet.addAll(this.first.list());
/* 55 */     hashSet.addAll(this.second.list());
/* 56 */     return hashSet;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/resolver/CompositeResolverImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */