/*    */ package com.sun.org.apache.xerces.internal.xpointer;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.util.MessageFormatter;
/*    */ import com.sun.org.apache.xerces.internal.utils.SecuritySupport;
/*    */ import java.text.MessageFormat;
/*    */ import java.util.Locale;
/*    */ import java.util.MissingResourceException;
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
/*    */ 
/*    */ class XPointerMessageFormatter
/*    */   implements MessageFormatter
/*    */ {
/*    */   public static final String XPOINTER_DOMAIN = "http://www.w3.org/TR/XPTR";
/* 42 */   private Locale fLocale = null;
/*    */   
/* 44 */   private ResourceBundle fResourceBundle = null;
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
/*    */   public String formatMessage(Locale locale, String key, Object[] arguments) throws MissingResourceException {
/* 67 */     if (this.fResourceBundle == null || locale != this.fLocale) {
/* 68 */       if (locale != null) {
/* 69 */         this.fResourceBundle = SecuritySupport.getResourceBundle("com.sun.org.apache.xerces.internal.impl.msg.XPointerMessages", locale);
/*    */ 
/*    */         
/* 72 */         this.fLocale = locale;
/*    */       } 
/* 74 */       if (this.fResourceBundle == null) {
/* 75 */         this.fResourceBundle = SecuritySupport.getResourceBundle("com.sun.org.apache.xerces.internal.impl.msg.XPointerMessages");
/*    */       }
/*    */     } 
/*    */     
/* 79 */     String msg = this.fResourceBundle.getString(key);
/* 80 */     if (arguments != null) {
/*    */       try {
/* 82 */         msg = MessageFormat.format(msg, arguments);
/* 83 */       } catch (Exception e) {
/* 84 */         msg = this.fResourceBundle.getString("FormatFailed");
/* 85 */         msg = msg + " " + this.fResourceBundle.getString(key);
/*    */       } 
/*    */     }
/*    */     
/* 89 */     if (msg == null) {
/* 90 */       msg = this.fResourceBundle.getString("BadMessageKey");
/* 91 */       throw new MissingResourceException(msg, "com.sun.org.apache.xerces.internal.impl.msg.XPointerMessages", key);
/*    */     } 
/*    */ 
/*    */     
/* 95 */     return msg;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/xpointer/XPointerMessageFormatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */