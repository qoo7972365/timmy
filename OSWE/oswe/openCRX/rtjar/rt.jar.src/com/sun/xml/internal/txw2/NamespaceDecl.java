/*    */ package com.sun.xml.internal.txw2;
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
/*    */ final class NamespaceDecl
/*    */ {
/*    */   final String uri;
/*    */   boolean requirePrefix;
/*    */   final String dummyPrefix;
/*    */   final char uniqueId;
/*    */   String prefix;
/*    */   boolean declared;
/*    */   NamespaceDecl next;
/*    */   
/*    */   NamespaceDecl(char uniqueId, String uri, String prefix, boolean requirePrefix) {
/* 62 */     this.dummyPrefix = (new StringBuilder(2)).append(false).append(uniqueId).toString();
/* 63 */     this.uri = uri;
/* 64 */     this.prefix = prefix;
/* 65 */     this.requirePrefix = requirePrefix;
/* 66 */     this.uniqueId = uniqueId;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/txw2/NamespaceDecl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */