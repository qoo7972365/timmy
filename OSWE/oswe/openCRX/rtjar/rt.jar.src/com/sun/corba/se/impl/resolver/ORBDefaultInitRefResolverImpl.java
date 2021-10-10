/*    */ package com.sun.corba.se.impl.resolver;
/*    */ 
/*    */ import com.sun.corba.se.spi.orb.Operation;
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
/*    */ public class ORBDefaultInitRefResolverImpl
/*    */   implements Resolver
/*    */ {
/*    */   Operation urlHandler;
/*    */   String orbDefaultInitRef;
/*    */   
/*    */   public ORBDefaultInitRefResolverImpl(Operation paramOperation, String paramString) {
/* 38 */     this.urlHandler = paramOperation;
/*    */ 
/*    */     
/* 41 */     this.orbDefaultInitRef = paramString;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object resolve(String paramString) {
/*    */     String str;
/* 47 */     if (this.orbDefaultInitRef == null) {
/* 48 */       return null;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 56 */     if (this.orbDefaultInitRef.startsWith("corbaloc:")) {
/* 57 */       str = this.orbDefaultInitRef + "/" + paramString;
/*    */     } else {
/* 59 */       str = this.orbDefaultInitRef + "#" + paramString;
/*    */     } 
/*    */     
/* 62 */     return (Object)this.urlHandler.operate(str);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set list() {
/* 67 */     return new HashSet();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/resolver/ORBDefaultInitRefResolverImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */