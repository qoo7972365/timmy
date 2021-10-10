/*    */ package com.sun.xml.internal.ws.api.addressing;
/*    */ 
/*    */ import com.oracle.webservices.internal.api.message.BasePropertySet;
/*    */ import com.oracle.webservices.internal.api.message.PropertySet.Property;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AddressingPropertySet
/*    */   extends BasePropertySet
/*    */ {
/*    */   public static final String ADDRESSING_FAULT_TO = "com.sun.xml.internal.ws.api.addressing.fault.to";
/*    */   private String faultTo;
/*    */   public static final String ADDRESSING_MESSAGE_ID = "com.sun.xml.internal.ws.api.addressing.message.id";
/*    */   private String messageId;
/*    */   public static final String ADDRESSING_RELATES_TO = "com.sun.xml.internal.ws.api.addressing.relates.to";
/*    */   @Property({"com.sun.xml.internal.ws.api.addressing.relates.to"})
/*    */   private String relatesTo;
/*    */   public static final String ADDRESSING_REPLY_TO = "com.sun.xml.internal.ws.api.addressing.reply.to";
/*    */   @Property({"com.sun.xml.internal.ws.api.addressing.reply.to"})
/*    */   private String replyTo;
/*    */   
/*    */   @Property({"com.sun.xml.internal.ws.api.addressing.fault.to"})
/*    */   public String getFaultTo() {
/* 47 */     return this.faultTo; } public void setFaultTo(String x) {
/* 48 */     this.faultTo = x;
/*    */   }
/*    */   
/*    */   public String getMessageId() {
/* 52 */     return this.messageId; } public void setMessageId(String x) {
/* 53 */     this.messageId = x;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getRelatesTo() {
/* 58 */     return this.relatesTo; } public void setRelatesTo(String x) {
/* 59 */     this.relatesTo = x;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getReplyTo() {
/* 64 */     return this.replyTo; } public void setReplyTo(String x) {
/* 65 */     this.replyTo = x;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 75 */   private static final BasePropertySet.PropertyMap model = parse(AddressingPropertySet.class);
/*    */ 
/*    */ 
/*    */   
/*    */   protected BasePropertySet.PropertyMap getPropertyMap() {
/* 80 */     return model;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/addressing/AddressingPropertySet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */