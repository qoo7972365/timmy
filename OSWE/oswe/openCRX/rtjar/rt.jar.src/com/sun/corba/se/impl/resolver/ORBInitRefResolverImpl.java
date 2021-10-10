/*    */ package com.sun.corba.se.impl.resolver;
/*    */ 
/*    */ import com.sun.corba.se.spi.orb.Operation;
/*    */ import com.sun.corba.se.spi.orb.StringPair;
/*    */ import com.sun.corba.se.spi.resolver.Resolver;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ORBInitRefResolverImpl
/*    */   implements Resolver
/*    */ {
/*    */   Operation urlHandler;
/*    */   Map orbInitRefTable;
/*    */   
/*    */   public ORBInitRefResolverImpl(Operation paramOperation, StringPair[] paramArrayOfStringPair) {
/* 41 */     this.urlHandler = paramOperation;
/* 42 */     this.orbInitRefTable = new HashMap<>();
/*    */     
/* 44 */     for (byte b = 0; b < paramArrayOfStringPair.length; b++) {
/* 45 */       StringPair stringPair = paramArrayOfStringPair[b];
/* 46 */       this.orbInitRefTable.put(stringPair.getFirst(), stringPair.getSecond());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Object resolve(String paramString) {
/* 52 */     String str = (String)this.orbInitRefTable.get(paramString);
/* 53 */     if (str == null) {
/* 54 */       return null;
/*    */     }
/* 56 */     return (Object)this.urlHandler
/* 57 */       .operate(str);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Set list() {
/* 63 */     return this.orbInitRefTable.keySet();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/resolver/ORBInitRefResolverImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */