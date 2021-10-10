/*    */ package com.sun.org.apache.xerces.internal.impl.xs.identity;
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
/*    */ public class UniqueOrKey
/*    */   extends IdentityConstraint
/*    */ {
/*    */   public UniqueOrKey(String namespace, String identityConstraintName, String elemName, short type) {
/* 43 */     super(namespace, identityConstraintName, elemName);
/* 44 */     this.type = type;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xs/identity/UniqueOrKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */