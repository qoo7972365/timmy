/*    */ package com.sun.xml.internal.stream.dtd.nonvalidating;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XMLNotationDecl
/*    */ {
/*    */   public String name;
/*    */   public String publicId;
/*    */   public String systemId;
/*    */   public String baseSystemId;
/*    */   
/*    */   public void setValues(String name, String publicId, String systemId, String baseSystemId) {
/* 55 */     this.name = name;
/* 56 */     this.publicId = publicId;
/* 57 */     this.systemId = systemId;
/* 58 */     this.baseSystemId = baseSystemId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void clear() {
/* 65 */     this.name = null;
/* 66 */     this.publicId = null;
/* 67 */     this.systemId = null;
/* 68 */     this.baseSystemId = null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/stream/dtd/nonvalidating/XMLNotationDecl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */