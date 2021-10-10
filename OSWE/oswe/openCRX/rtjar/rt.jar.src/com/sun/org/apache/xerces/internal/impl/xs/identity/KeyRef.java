/*    */ package com.sun.org.apache.xerces.internal.impl.xs.identity;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.xs.XSIDCDefinition;
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
/*    */ public class KeyRef
/*    */   extends IdentityConstraint
/*    */ {
/*    */   protected UniqueOrKey fKey;
/*    */   
/*    */   public KeyRef(String namespace, String identityConstraintName, String elemName, UniqueOrKey key) {
/* 49 */     super(namespace, identityConstraintName, elemName);
/* 50 */     this.fKey = key;
/* 51 */     this.type = 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public UniqueOrKey getKey() {
/* 60 */     return this.fKey;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public XSIDCDefinition getRefKey() {
/* 69 */     return this.fKey;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xs/identity/KeyRef.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */