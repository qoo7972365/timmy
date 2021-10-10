/*    */ package com.sun.xml.internal.bind.v2.schemagen;
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
/*    */ 
/*    */ enum Messages
/*    */ {
/* 35 */   ANONYMOUS_TYPE_CYCLE;
/*    */   
/*    */   static {
/* 38 */     rb = ResourceBundle.getBundle(Messages.class.getName());
/*    */   }
/*    */   private static final ResourceBundle rb;
/*    */   public String toString() {
/* 42 */     return format(new Object[0]);
/*    */   }
/*    */   
/*    */   public String format(Object... args) {
/* 46 */     return MessageFormat.format(rb.getString(name()), args);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/schemagen/Messages.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */