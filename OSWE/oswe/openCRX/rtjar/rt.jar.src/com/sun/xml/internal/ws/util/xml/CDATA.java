/*    */ package com.sun.xml.internal.ws.util.xml;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class CDATA
/*    */ {
/*    */   private String _text;
/*    */   
/*    */   public CDATA(String text) {
/* 34 */     this._text = text;
/*    */   }
/*    */   
/*    */   public String getText() {
/* 38 */     return this._text;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 42 */     if (obj == null) {
/* 43 */       return false;
/*    */     }
/* 45 */     if (!(obj instanceof CDATA)) {
/* 46 */       return false;
/*    */     }
/* 48 */     CDATA cdata = (CDATA)obj;
/*    */     
/* 50 */     return this._text.equals(cdata._text);
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 54 */     return this._text.hashCode();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/xml/CDATA.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */