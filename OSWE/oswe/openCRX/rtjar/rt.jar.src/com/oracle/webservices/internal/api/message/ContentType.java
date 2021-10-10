/*    */ package com.oracle.webservices.internal.api.message;
/*    */ 
/*    */ import com.sun.xml.internal.ws.encoding.ContentTypeImpl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ContentType
/*    */ {
/*    */   String getContentType();
/*    */   
/*    */   String getSOAPActionHeader();
/*    */   
/*    */   String getAcceptHeader();
/*    */   
/*    */   public static class Builder
/*    */   {
/*    */     private String contentType;
/*    */     private String soapAction;
/*    */     private String accept;
/*    */     private String charset;
/*    */     
/*    */     public Builder contentType(String s) {
/* 69 */       this.contentType = s; return this;
/* 70 */     } public Builder soapAction(String s) { this.soapAction = s; return this; }
/* 71 */     public Builder accept(String s) { this.accept = s; return this; } public Builder charset(String s) {
/* 72 */       this.charset = s; return this;
/*    */     }
/*    */     public ContentType build() {
/* 75 */       return (ContentType)new ContentTypeImpl(this.contentType, this.soapAction, this.accept, this.charset);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/oracle/webservices/internal/api/message/ContentType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */