/*    */ package com.sun.org.apache.xerces.internal.util;
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
/*    */ final class XMLErrorCode
/*    */ {
/*    */   private String fDomain;
/*    */   private String fKey;
/*    */   
/*    */   public XMLErrorCode(String domain, String key) {
/* 49 */     this.fDomain = domain;
/* 50 */     this.fKey = key;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setValues(String domain, String key) {
/* 60 */     this.fDomain = domain;
/* 61 */     this.fKey = key;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 70 */     if (!(obj instanceof XMLErrorCode))
/* 71 */       return false; 
/* 72 */     XMLErrorCode err = (XMLErrorCode)obj;
/* 73 */     return (this.fDomain.equals(err.fDomain) && this.fKey.equals(err.fKey));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 82 */     return this.fDomain.hashCode() + this.fKey.hashCode();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/util/XMLErrorCode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */