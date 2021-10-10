/*    */ package com.sun.xml.internal.txw2.output;
/*    */ 
/*    */ import com.sun.xml.internal.txw2.TypedXmlWriter;
/*    */ import javax.xml.transform.Result;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TXWResult
/*    */   implements Result
/*    */ {
/*    */   private String systemId;
/*    */   private TypedXmlWriter writer;
/*    */   
/*    */   public TXWResult(TypedXmlWriter writer) {
/* 48 */     this.writer = writer;
/*    */   }
/*    */   
/*    */   public TypedXmlWriter getWriter() {
/* 52 */     return this.writer;
/*    */   }
/*    */   
/*    */   public void setWriter(TypedXmlWriter writer) {
/* 56 */     this.writer = writer;
/*    */   }
/*    */   
/*    */   public String getSystemId() {
/* 60 */     return this.systemId;
/*    */   }
/*    */   
/*    */   public void setSystemId(String systemId) {
/* 64 */     this.systemId = systemId;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/txw2/output/TXWResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */