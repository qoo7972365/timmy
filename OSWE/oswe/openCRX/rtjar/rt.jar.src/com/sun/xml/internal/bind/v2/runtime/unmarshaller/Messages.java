/*    */ package com.sun.xml.internal.bind.v2.runtime.unmarshaller;
/*    */ 
/*    */ import java.text.MessageFormat;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ enum Messages
/*    */ {
/*    */   private static final ResourceBundle rb;
/* 35 */   UNRESOLVED_IDREF,
/* 36 */   UNEXPECTED_ELEMENT,
/* 37 */   UNEXPECTED_TEXT,
/* 38 */   NOT_A_QNAME,
/* 39 */   UNRECOGNIZED_TYPE_NAME,
/* 40 */   UNRECOGNIZED_TYPE_NAME_MAYBE,
/* 41 */   UNABLE_TO_CREATE_MAP,
/* 42 */   UNINTERNED_STRINGS,
/* 43 */   ERRORS_LIMIT_EXCEEDED;
/*    */   
/*    */   static {
/* 46 */     rb = ResourceBundle.getBundle(Messages.class.getName());
/*    */   }
/*    */   public String toString() {
/* 49 */     return format(new Object[0]);
/*    */   }
/*    */   
/*    */   public String format(Object... args) {
/* 53 */     return MessageFormat.format(rb.getString(name()), args);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/unmarshaller/Messages.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */