/*    */ package com.sun.xml.internal.bind.v2.runtime;
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
/*    */ public final class NameList
/*    */ {
/*    */   public final String[] namespaceURIs;
/*    */   public final boolean[] nsUriCannotBeDefaulted;
/*    */   public final String[] localNames;
/*    */   public final int numberOfElementNames;
/*    */   public final int numberOfAttributeNames;
/*    */   
/*    */   public NameList(String[] namespaceURIs, boolean[] nsUriCannotBeDefaulted, String[] localNames, int numberElementNames, int numberAttributeNames) {
/* 67 */     this.namespaceURIs = namespaceURIs;
/* 68 */     this.nsUriCannotBeDefaulted = nsUriCannotBeDefaulted;
/* 69 */     this.localNames = localNames;
/* 70 */     this.numberOfElementNames = numberElementNames;
/* 71 */     this.numberOfAttributeNames = numberAttributeNames;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/NameList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */