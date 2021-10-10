/*    */ package com.sun.xml.internal.bind.v2;
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
/*    */ public enum Messages
/*    */ {
/*    */   private static final ResourceBundle rb;
/* 35 */   ILLEGAL_ENTRY,
/* 36 */   ERROR_LOADING_CLASS,
/* 37 */   INVALID_PROPERTY_VALUE,
/* 38 */   UNSUPPORTED_PROPERTY,
/* 39 */   BROKEN_CONTEXTPATH,
/* 40 */   NO_DEFAULT_CONSTRUCTOR_IN_INNER_CLASS,
/* 41 */   INVALID_TYPE_IN_MAP,
/* 42 */   INVALID_JAXP_IMPLEMENTATION,
/* 43 */   JAXP_SUPPORTED_PROPERTY,
/* 44 */   JAXP_UNSUPPORTED_PROPERTY,
/* 45 */   JAXP_XML_SECURITY_DISABLED,
/* 46 */   JAXP_EXTERNAL_ACCESS_CONFIGURED;
/*    */   
/*    */   static {
/* 49 */     rb = ResourceBundle.getBundle(Messages.class.getName());
/*    */   }
/*    */   public String toString() {
/* 52 */     return format(new Object[0]);
/*    */   }
/*    */   
/*    */   public String format(Object... args) {
/* 56 */     return MessageFormat.format(rb.getString(name()), args);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/Messages.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */