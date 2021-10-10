/*    */ package com.sun.corba.se.impl.resolver;
/*    */ 
/*    */ import com.sun.corba.se.spi.orbutil.closure.Closure;
/*    */ import com.sun.corba.se.spi.resolver.LocalResolver;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ public class LocalResolverImpl
/*    */   implements LocalResolver
/*    */ {
/* 32 */   Map nameToClosure = new HashMap<>();
/*    */ 
/*    */   
/*    */   public synchronized Object resolve(String paramString) {
/* 36 */     Closure closure = (Closure)this.nameToClosure.get(paramString);
/* 37 */     if (closure == null) {
/* 38 */       return null;
/*    */     }
/* 40 */     return (Object)closure.evaluate();
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized Set list() {
/* 45 */     return this.nameToClosure.keySet();
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized void register(String paramString, Closure paramClosure) {
/* 50 */     this.nameToClosure.put(paramString, paramClosure);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/resolver/LocalResolverImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */