/*    */ package com.sun.xml.internal.ws.message;
/*    */ 
/*    */ import com.sun.istack.internal.Nullable;
/*    */ import com.sun.xml.internal.ws.api.message.FilterMessageImpl;
/*    */ import com.sun.xml.internal.ws.api.message.Message;
/*    */ import javax.xml.namespace.QName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FaultMessage
/*    */   extends FilterMessageImpl
/*    */ {
/*    */   @Nullable
/*    */   private final QName detailEntryName;
/*    */   
/*    */   public FaultMessage(Message delegate, @Nullable QName detailEntryName) {
/* 47 */     super(delegate);
/* 48 */     this.detailEntryName = detailEntryName;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public QName getFirstDetailEntryName() {
/* 53 */     return this.detailEntryName;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/message/FaultMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */