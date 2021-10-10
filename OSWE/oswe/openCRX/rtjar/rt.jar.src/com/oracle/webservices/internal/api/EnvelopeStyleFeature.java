/*    */ package com.oracle.webservices.internal.api;
/*    */ 
/*    */ import javax.xml.ws.WebServiceFeature;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnvelopeStyleFeature
/*    */   extends WebServiceFeature
/*    */ {
/*    */   private EnvelopeStyle.Style[] styles;
/*    */   
/*    */   public EnvelopeStyleFeature(EnvelopeStyle.Style... s) {
/* 35 */     this.styles = s;
/*    */   }
/*    */   
/*    */   public EnvelopeStyle.Style[] getStyles() {
/* 39 */     return this.styles;
/*    */   }
/*    */   
/*    */   public String getID() {
/* 43 */     return EnvelopeStyleFeature.class.getName();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/oracle/webservices/internal/api/EnvelopeStyleFeature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */